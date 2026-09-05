package ru.yakovlev05.infra.error.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Error {
    private String key;
    private String description;
}
