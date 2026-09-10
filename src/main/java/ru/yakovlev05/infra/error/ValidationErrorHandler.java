package ru.yakovlev05.infra.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yakovlev05.infra.error.model.Error;
import ru.yakovlev05.infra.error.model.Errors;

@RestControllerAdvice
public class ValidationErrorHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Errors> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var errorList = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> new Error(e.getField(), e.getDefaultMessage()))
                .toList();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(new Errors(HttpStatus.BAD_REQUEST.value(), errorList));
    }

}
