package com.people.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Collection;
import java.util.Collections;

public class ErrorView {

    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Collection<String> errors;

    public ErrorView(String message, Collection<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    public ErrorView(String message, String error) {
        this.message = message;
        this.errors = Collections.singletonList(error);
    }

    public ErrorView() {}

    public ErrorView(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<String> getErrors() {
        return errors;
    }

    public void setErrors(Collection<String> errors) {
        this.errors = errors;
    }
}
