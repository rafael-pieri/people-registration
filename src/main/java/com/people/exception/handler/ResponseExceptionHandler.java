package com.people.exception.handler;

import com.people.exception.ResourceNotFoundException;
import com.people.dto.error.ErrorView;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ResponseExceptionHandler {

    private static final String INVALID_REQUEST_OBJECT = "Invalid Request Object";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorView handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final Collection<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream().map(fieldError -> fieldError.getField()
                        .concat(" ")
                        .concat(Objects.requireNonNull(fieldError.getDefaultMessage())))
                .collect(Collectors.toList());
        return new ErrorView(INVALID_REQUEST_OBJECT, errors);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ErrorView handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception) {
        return new ErrorView(INVALID_REQUEST_OBJECT, "Unsupported content type: " + exception.getContentType());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorView handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return new ErrorView(INVALID_REQUEST_OBJECT, exception.getMostSpecificCause().getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorView handleMessageNotFoundException(ResourceNotFoundException exception) {
        return new ErrorView(exception.getMessage());
    }
}
