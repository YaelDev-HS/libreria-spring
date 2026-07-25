package com.xiplatani.viajes.libreria.application.useCases;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiplatani.viajes.libreria.application.dtos.auth.RoleDto;
import com.xiplatani.viajes.libreria.application.dtos.books.BookResponseDto;
import com.xiplatani.viajes.libreria.application.dtos.books.CreateBookDto;
import com.xiplatani.viajes.libreria.application.dtos.loans.LoanRequestResponseDto;
import com.xiplatani.viajes.libreria.application.dtos.users.UserDto;
import com.xiplatani.viajes.libreria.domain.exceptions.CustomException;
import com.xiplatani.viajes.libreria.domain.models.Book;
import com.xiplatani.viajes.libreria.domain.models.LoanRequest;
import com.xiplatani.viajes.libreria.domain.models.User;
import com.xiplatani.viajes.libreria.domain.repositories.IBookRepository;
import com.xiplatani.viajes.libreria.domain.repositories.ILoanRequestRepository;
import com.xiplatani.viajes.libreria.infrastructure.security.UserAuth;

@Service
public class BookUseCases {

    private final IBookRepository bookRepository;
    private final ILoanRequestRepository loanRequestRepository;

    public BookUseCases(
            IBookRepository bookRepository,
            ILoanRequestRepository loanRequestRepository) {
        this.bookRepository = bookRepository;
        this.loanRequestRepository = loanRequestRepository;
    }

    private UserAuth getUserAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw CustomException.Unauthorized("Usuario no válido o no autenticado.");
        }

        try {
            return (UserAuth) auth.getPrincipal();
        } catch (Exception e) {
            throw CustomException.InternalServerError("Usuario autenticado no válido: " + e.getMessage());
        }
    }

    public Map<String, Object> createBook(CreateBookDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setPages(dto.getPages());
        book.setIsAvailable(true);
        book.setCreatedAt(new Date());
        book.setUpdatedAt(new Date());

        Book savedBook = bookRepository.save(book);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Libro creado exitosamente.");
        response.put("book", mapToBookResponseDto(savedBook));
        return response;
    }

    public Map<String, Object> getAllBooks() {
        List<BookResponseDto> books = bookRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::mapToBookResponseDto)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("books", books);
        return response;
    }

    public Map<String, Object> getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> CustomException.NotFound("Libro no encontrado."));

        Map<String, Object> response = new HashMap<>();
        response.put("book", mapToBookResponseDto(book));
        return response;
    }

    public Map<String, Object> updateBook(Long id, CreateBookDto dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> CustomException.NotFound("Libro no encontrado."));

        book.setTitle(dto.getTitle());
        book.setDescription(dto.getDescription());
        book.setPages(dto.getPages());
        book.setUpdatedAt(new Date());

        Book updatedBook = bookRepository.update(book);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Libro actualizado exitosamente.");
        response.put("book", mapToBookResponseDto(updatedBook));
        return response;
    }

    public Map<String, Object> deleteBook(Long id) {
        bookRepository.findById(id)
                .orElseThrow(() -> CustomException.NotFound("Libro no encontrado."));
        bookRepository.delete(id);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Libro eliminado exitosamente.");
        return response;
    }

    public Map<String, Object> requestLoan(Long bookId) {
        UserAuth userAuth = this.getUserAuth();
        Boolean ok = this.loanRequestRepository.hasPendindBookByUserID(userAuth.userId(), bookId);

        if (ok) {
            throw CustomException.BadRequest("Ya tienes una petición pendiente para este libro");
        }

        Long activeCount = loanRequestRepository.countActiveLoansByUserId(userAuth.userId());

        if (activeCount >= 3) {
            throw CustomException
                    .BadRequest("Ha alcanzado el límite máximo de 3 solicitudes o préstamos activos simultáneos.");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> CustomException.NotFound("Libro no encontrado."));

        if (Boolean.FALSE.equals(book.getIsAvailable())) {
            throw CustomException.BadRequest("El libro no se encuentra disponible para préstamo.");
        }

        LoanRequest loanRequest = new LoanRequest();
        User u = new User();
        u.setId(userAuth.userId());

        loanRequest.setUser(u);
        loanRequest.setBook(book);
        loanRequest.setStatus("PENDING");
        loanRequest.setRequestDate(new Date());
        loanRequest.setCreatedAt(new Date());
        loanRequest.setUpdatedAt(new Date());

        LoanRequest savedRequest = loanRequestRepository.save(loanRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Solicitud de préstamo creada exitosamente.");
        response.put("loanRequest", mapToLoanRequestResponseDto(savedRequest));
        return response;
    }

    @Transactional
    public Map<String, Object> approveLoanRequest(Long requestId, Boolean rejectOthers) {
        LoanRequest request = loanRequestRepository.findById(requestId)
                .orElseThrow(() -> CustomException.NotFound("Solicitud del usuario no encontrada."));

        if (!"PENDING".equalsIgnoreCase(request.getStatus())) {
            throw CustomException.BadRequest("Solo se pueden aprobar solicitudes en estado PENDIENTE.");
        }

        Book book = request.getBook();

        if (Boolean.FALSE.equals(book.getIsAvailable())) {
            throw CustomException.BadRequest("El libro ya no está disponible para préstamo.");
        }

        book.setIsAvailable(false);
        book.setUpdatedAt(new Date());
        bookRepository.update(book);

        request.setStatus("APPROVED");
        request.setResponseDate(new Date());
        request.setUpdatedAt(new Date());

        LoanRequest updatedRequest = loanRequestRepository.update(request);

        if (Boolean.TRUE.equals(rejectOthers) && book.getId() != null) {
            loanRequestRepository.rejectOtherPendingRequestsByBookId(book.getId(), requestId);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Solicitud de préstamo aprobada exitosamente.");
        response.put("loanRequest", mapToLoanRequestResponseDto(updatedRequest));
        return response;
    }

    public Map<String, Object> rejectLoanRequest(Long requestId) {
        LoanRequest request = loanRequestRepository.findById(requestId)
                .orElseThrow(() -> CustomException.NotFound("La solicitud no se encuentra disponible."));

        if (!"PENDING".equalsIgnoreCase(request.getStatus())) {
            throw CustomException.BadRequest("Solo se pueden rechazar solicitudes en estado PENDIENTE.");
        }

        request.setStatus("REJECTED");
        request.setResponseDate(new Date());
        request.setUpdatedAt(new Date());

        LoanRequest updatedRequest = loanRequestRepository.update(request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Solicitud de préstamo rechazada exitosamente.");
        response.put("loanRequest", mapToLoanRequestResponseDto(updatedRequest));
        return response;
    }

    public Map<String, Object> returnLoanRequest(Long requestId) {
        LoanRequest request = loanRequestRepository.findById(requestId)
                .orElseThrow(() -> CustomException.NotFound("La solicitud no se encuentra disponible."));

        if (!"APPROVED".equalsIgnoreCase(request.getStatus())) {
            throw CustomException.BadRequest("Solo se pueden devolver libros con estado APROBADO.");
        }

        Book book = request.getBook();
        book.setIsAvailable(true);
        book.setUpdatedAt(new Date());
        bookRepository.update(book);

        request.setStatus("RETURNED");
        request.setReturnDate(new Date());
        request.setUpdatedAt(new Date());

        LoanRequest updatedRequest = loanRequestRepository.update(request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Devolución de libro confirmada exitosamente.");
        response.put("loanRequest", mapToLoanRequestResponseDto(updatedRequest));
        return response;
    }

    public Map<String, Object> getMyLoanRequests() {
        UserAuth userAuth = this.getUserAuth();
        List<LoanRequestResponseDto> requests = loanRequestRepository.findByUserId(userAuth.userId()).stream()
                .map(this::mapToLoanRequestResponseDto)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("loanRequests", requests);
        return response;
    }

    public Map<String, Object> getLoanRequestsByStatus(String status) {
        List<LoanRequest> requests;
        if (status == null || status.trim().isEmpty() || "ALL".equalsIgnoreCase(status)) {
            requests = loanRequestRepository.findAllByOrderByCreatedAtDesc();
        } else {
            requests = loanRequestRepository.findByStatusOrderByCreatedAtDesc(status.toUpperCase());
        }

        List<LoanRequestResponseDto> dtos = requests.stream()
                .map(this::mapToLoanRequestResponseDto)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("loanRequests", dtos);
        return response;
    }

    private BookResponseDto mapToBookResponseDto(Book book) {
        BookResponseDto dto = new BookResponseDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setPages(book.getPages());
        dto.setVersion(book.getVersion());
        dto.setIsAvailable(book.getIsAvailable());
        dto.setCreatedAt(book.getCreatedAt());
        dto.setUpdatedAt(book.getUpdatedAt());
        return dto;
    }

    private LoanRequestResponseDto mapToLoanRequestResponseDto(LoanRequest request) {
        LoanRequestResponseDto dto = new LoanRequestResponseDto();
        dto.setId(request.getId());
        dto.setStatus(request.getStatus());
        dto.setRequestDate(request.getRequestDate());
        dto.setResponseDate(request.getResponseDate());
        dto.setReturnDate(request.getReturnDate());
        dto.setCreatedAt(request.getCreatedAt());
        dto.setUpdatedAt(request.getUpdatedAt());

        if (request.getUser() != null) {
            User user = request.getUser();
            UserDto userDto = new UserDto();
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setIsActive(user.getIsActive());
            userDto.setCreatedAt(user.getCreatedAt());
            userDto.setUpdatedAt(user.getUpdatedAt());

            if (user.getRoles() != null) {
                List<RoleDto> roleDtos = user.getRoles().stream()
                        .map(r -> new RoleDto(r.getRole()))
                        .toList();
                userDto.setRoles(roleDtos);
            }
            dto.setUser(userDto);
        }

        if (request.getBook() != null) {
            dto.setBook(mapToBookResponseDto(request.getBook()));
        }

        return dto;
    }
}
