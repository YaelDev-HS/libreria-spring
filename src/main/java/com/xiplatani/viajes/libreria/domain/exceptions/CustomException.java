package com.xiplatani.viajes.libreria.domain.exceptions;

public class CustomException extends RuntimeException {

    private final int status;
    private final String error;

    public CustomException(String error, int status) {
        this.error = error;
        this.status = status;
    }

    public static CustomException BadRequest(String error) {
        return new CustomException(error, 400);
    }

    public static CustomException NotFound(String error) {
        return new CustomException(error, 404);
    }

    public static CustomException Unauthorized(String error) {
        return new CustomException(error, 401);
    }

    public static CustomException InternalServerError(String error) {
        // TODO: logger
        System.out.println("Internal server error: " + error);
        return new CustomException("Unexpected error, please try again later.", 500);
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

}
