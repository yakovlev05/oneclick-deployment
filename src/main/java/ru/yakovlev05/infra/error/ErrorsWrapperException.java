package ru.yakovlev05.infra.error;

import lombok.Getter;
import ru.yakovlev05.infra.error.model.Errors;

public class ErrorsWrapperException extends RuntimeException {

    @Getter
    private final Errors errors;

    public ErrorsWrapperException(Errors errors) {
        this.errors = errors;
    }
}
