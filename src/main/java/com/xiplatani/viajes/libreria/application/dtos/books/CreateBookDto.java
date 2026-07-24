package com.xiplatani.viajes.libreria.application.dtos.books;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateBookDto {

    @NotBlank(message = "El título del libro es requerido.")
    @Size(min = 2, max = 255, message = "El título debe tener entre 2 y 255 caracteres.")
    private String title;

    @Size(max = 2000, message = "La descripción no puede exceder 2000 caracteres.")
    private String description;

    @NotNull(message = "El número de páginas es requerido.")
    @Min(value = 1, message = "El libro debe tener al menos 1 página.")
    private Integer pages;

    public CreateBookDto() {
    }

    public CreateBookDto(String title, String description, Integer pages) {
        this.title = title;
        this.description = description;
        this.pages = pages;
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
}
