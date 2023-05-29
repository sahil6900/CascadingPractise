package com.cascading.demo.custom.exception.handling;

import org.springframework.stereotype.Component;

@Component
public class BussinessExceptions extends RuntimeException{

    private static final long serialVersioUID=1L;
    private String errorCode;
    private String errorMessage;

    public BussinessExceptions(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BussinessExceptions() {
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
