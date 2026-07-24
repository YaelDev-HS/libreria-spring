package com.xiplatani.viajes.libreria.infrastructure.mappers;

import org.springframework.stereotype.Component;

import com.xiplatani.viajes.libreria.domain.models.LoanRequest;
import com.xiplatani.viajes.libreria.infrastructure.entities.LoanRequestEntity;

@Component
public class LoanRequestMapper implements IBaseMapper<LoanRequest, LoanRequestEntity> {

    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public LoanRequestMapper(UserMapper userMapper, BookMapper bookMapper) {
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    @Override
    public LoanRequest toDomain(LoanRequestEntity entity) {
        if (entity == null) {
            return null;
        }
        LoanRequest model = new LoanRequest();
        model.setId(entity.getId());
        model.setStatus(entity.getStatus());
        model.setRequestDate(entity.getRequestDate());
        model.setResponseDate(entity.getResponseDate());
        model.setReturnDate(entity.getReturnDate());
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());

        if (entity.getUser() != null) {
            model.setUser(userMapper.toDomain(entity.getUser()));
        }
        if (entity.getBook() != null) {
            model.setBook(bookMapper.toDomain(entity.getBook()));
        }

        return model;
    }

    @Override
    public LoanRequestEntity toEntity(LoanRequest domain) {
        if (domain == null) {
            return null;
        }
        LoanRequestEntity entity = new LoanRequestEntity();
        entity.setId(domain.getId());
        entity.setStatus(domain.getStatus());
        entity.setRequestDate(domain.getRequestDate());
        entity.setResponseDate(domain.getResponseDate());
        entity.setReturnDate(domain.getReturnDate());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());

        if (domain.getUser() != null) {
            entity.setUser(userMapper.toEntity(domain.getUser()));
        }
        if (domain.getBook() != null) {
            entity.setBook(bookMapper.toEntity(domain.getBook()));
        }

        return entity;
    }
}
