package com.xiplatani.viajes.libreria.application.useCases;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xiplatani.viajes.libreria.domain.exceptions.CustomException;
import com.xiplatani.viajes.libreria.domain.models.Book;
import com.xiplatani.viajes.libreria.domain.models.LoanRequest;
import com.xiplatani.viajes.libreria.domain.models.Role;
import com.xiplatani.viajes.libreria.domain.models.User;
import com.xiplatani.viajes.libreria.domain.repositories.IBookRepository;
import com.xiplatani.viajes.libreria.domain.repositories.ILoanRequestRepository;
import com.xiplatani.viajes.libreria.domain.repositories.IRoleRepository;
import com.xiplatani.viajes.libreria.domain.repositories.IUserRepository;

@Service
public class SeedUseCases {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IBookRepository bookRepository;
    private final ILoanRequestRepository loanRequestRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    public SeedUseCases(
            IUserRepository userRepository,
            IRoleRepository roleRepository,
            IBookRepository bookRepository,
            ILoanRequestRepository loanRequestRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bookRepository = bookRepository;
        this.loanRequestRepository = loanRequestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void executeSeed() {
        if (userRepository.existsByEmail(adminEmail)) {
            return;
        }

        Role adminRole = roleRepository.findByRole("ADMIN")
                .orElseThrow(() -> CustomException.InternalServerError("No viene el role ADMIN en las migraciones SQL"));
        Role librarianRole = roleRepository.findByRole("LIBRARIAN")
                .orElseThrow(() -> CustomException.InternalServerError("No viene el role LIBRARIAN en las migraciones SQL"));
        Role userRole = roleRepository.findByRole("USER")
                .orElseThrow(() -> CustomException.InternalServerError("No viene el role USER en las migraciones SQL"));

        User adminUser = createUser("Administrador General", adminEmail, adminPassword, adminRole);
        User librarianUser = createUser("Bibliotecario Principal", "bibliotecario@libreria.com", "Librarian123!", librarianRole);
        User reader1 = createUser("Carlos Mendoza", "carlos@gmail.com", "User1234!", userRole);
        User reader2 = createUser("Ana Gómez", "ana@gmail.com", "User1234!", userRole);

        List<User> savedUsers = userRepository.saveAll(List.of(adminUser, librarianUser, reader1, reader2));
        User savedReader1 = savedUsers.stream().filter(u -> u.getEmail().equals("carlos@gmail.com")).findFirst().orElse(reader1);
        User savedReader2 = savedUsers.stream().filter(u -> u.getEmail().equals("ana@gmail.com")).findFirst().orElse(reader2);

        Book b1 = createBookModel("Cien Años de Soledad", "Obra maestra de Gabriel García Márquez", 471, false);
        Book b2 = createBookModel("Don Quijote de la Mancha", "Novela cumbre de Miguel de Cervantes", 863, true);
        Book b3 = createBookModel("Clean Code", "Guía de desarrollo de software limpio de Robert C. Martin", 464, true);
        Book b4 = createBookModel("El Principito", "Clásico universal de Antoine de Saint-Exupéry", 96, true);
        Book b5 = createBookModel("1984", "Distopía futurista de George Orwell sobre el control totalitario", 328, true);

        List<Book> savedBooks = bookRepository.saveAll(List.of(b1, b2, b3, b4, b5));
        Book savedB1 = savedBooks.get(0);
        Book savedB2 = savedBooks.get(1);
        Book savedB3 = savedBooks.get(2);
        Book savedB4 = savedBooks.get(3);

        List<LoanRequest> requestsToSave = new ArrayList<>();
        requestsToSave.add(createLoanRequestModel(savedReader1, savedB1, "APPROVED"));
        requestsToSave.add(createLoanRequestModel(savedReader2, savedB2, "PENDING"));
        requestsToSave.add(createLoanRequestModel(savedReader1, savedB3, "RETURNED"));
        requestsToSave.add(createLoanRequestModel(savedReader2, savedB4, "REJECTED"));

        loanRequestRepository.saveAll(requestsToSave);
    }

    private User createUser(String name, String email, String rawPassword, Role role) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setIsActive(true);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setRole(role);
        return user;
    }

    private Book createBookModel(String title, String description, Integer pages, Boolean isAvailable) {
        Book book = new Book();
        book.setTitle(title);
        book.setDescription(description);
        book.setPages(pages);
        book.setIsAvailable(isAvailable);
        book.setCreatedAt(new Date());
        book.setUpdatedAt(new Date());
        return book;
    }

    private LoanRequest createLoanRequestModel(User user, Book book, String status) {
        LoanRequest req = new LoanRequest();
        req.setUser(user);
        req.setBook(book);
        req.setStatus(status);
        req.setRequestDate(new Date());
        if ("APPROVED".equalsIgnoreCase(status) || "REJECTED".equalsIgnoreCase(status)) {
            req.setResponseDate(new Date());
        }
        if ("RETURNED".equalsIgnoreCase(status)) {
            req.setResponseDate(new Date());
            req.setReturnDate(new Date());
        }
        req.setCreatedAt(new Date());
        req.setUpdatedAt(new Date());
        return req;
    }

}
