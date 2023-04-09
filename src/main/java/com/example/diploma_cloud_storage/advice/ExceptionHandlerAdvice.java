package com.example.diploma_cloud_storage.advice;

import com.example.diploma_cloud_storage.advice.exception.IncorrectInputDataException;
import com.example.diploma_cloud_storage.advice.exception.ServerException;
import com.example.diploma_cloud_storage.advice.exception.UnauthorizedException;
import com.example.diploma_cloud_storage.model.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {


    @ExceptionHandler(IncorrectInputDataException.class)
    public ResponseEntity<Error> fileNotFound(IncorrectInputDataException e) {
        log.warn(e.toString());
        return new ResponseEntity<>(new Error(e.hashCode(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Error> userNotFound(IncorrectInputDataException e) {
        log.warn(e.toString());
        return new ResponseEntity<>(new Error(e.hashCode(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Error> unauthorizedUser(UnauthorizedException e) {
        log.warn(e.toString());
        return new ResponseEntity<>(new Error(e.hashCode(), e.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<Error> serverError(ServerException e) {
        log.error(e.toString());
        return new ResponseEntity<>(new Error(e.hashCode(), e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
