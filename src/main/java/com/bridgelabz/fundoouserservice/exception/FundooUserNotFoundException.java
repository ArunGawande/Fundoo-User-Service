package com.bridgelabz.fundoouserservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class FundooUserNotFoundException extends RuntimeException {
    private int statusCode;
    private String statusMessage;

    public FundooUserNotFoundException(int statusCode, String statusMessage) {
        super(statusMessage);
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }
}
