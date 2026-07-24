package com.xiplatani.viajes.libreria.domain.models;

import java.util.Date;

public class Book {

    private Long id;
    private String title;
    private String description;
    private Integer pages;
    private Long version;
    private Boolean isAvailable;
    private Date createdAt;
    private Date updatedAt;

    public Book() {
    }

    public Book(Long id, String title, String description, Integer pages, Long version, Boolean isAvailable,
            Date createdAt, Date updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.pages = pages;
        this.version = version;
        this.isAvailable = isAvailable;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
