package ru.yakovlev05.infra.error.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import ru.yakovlev05.infra.error.ErrorsWrapperException;

import java.util.LinkedList;
import java.util.List;

@Getter
public class Errors {

    private final List<Error> errors = new LinkedList<>();
    @JsonIgnore
    private final int code;

    public Errors(int code, String key, String description) {
        errors.add(new Error(key, description));
        this.code = code;
    }

    public Errors(String key, String description) {
        errors.add(new Error(key, description));
        this.code = 400;
    }

    public Errors(int code, List<Error> errors) {
        this.code = code;
        this.errors.addAll(errors);
    }

    public RuntimeException toEx() {
        return new ErrorsWrapperException(this);
    }
}
