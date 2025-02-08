package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class EmailAlreadyExits extends IllegalArgumentException {
    public EmailAlreadyExits(String message) {
        super(message);
    }
}
