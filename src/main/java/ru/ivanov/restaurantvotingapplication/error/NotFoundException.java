package ru.ivanov.restaurantvotingapplication.error;

public class NotFoundException extends AppException {
    public NotFoundException(String message) {
        super(message);
    }
}