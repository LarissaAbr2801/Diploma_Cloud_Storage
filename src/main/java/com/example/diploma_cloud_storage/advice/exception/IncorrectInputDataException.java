package com.example.diploma_cloud_storage.advice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IncorrectInputDataException extends IllegalArgumentException {

    public IncorrectInputDataException() {
    }

    public IncorrectInputDataException(String s) {
        super(s);
    }

    public IncorrectInputDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectInputDataException(Throwable cause) {
        super(cause);
    }
}
