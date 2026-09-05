package ru.yakovlev05.infra.error;

import com.github.dockerjava.api.exception.DockerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yakovlev05.infra.error.model.Errors;

@RestControllerAdvice
public class DockerErrorHandler {

    @ExceptionHandler(DockerException.class)
    public ResponseEntity<Errors> handleDockerException(DockerException ex) {
        Errors errors = new Errors(
                "dockerError",
                ex.getMessage()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }


}
