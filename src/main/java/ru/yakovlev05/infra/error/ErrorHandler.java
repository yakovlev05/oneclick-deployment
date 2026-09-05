package ru.yakovlev05.infra.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yakovlev05.infra.error.model.Errors;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(ErrorsWrapperException.class)
    public ResponseEntity<Errors> handleResponseException(ErrorsWrapperException ex) {
        return ResponseEntity
                .status(ex.getErrors().getCode())
                .body(ex.getErrors());
    }

}
