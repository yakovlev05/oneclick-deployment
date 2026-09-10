package ru.yakovlev05.infra.docker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Schema(description = "Объект для настройки томов в Docker контейнере")
@Getter
public class VolumeBindInfoDto {

    @Pattern(regexp = "[^/]*")
    @NotNull
    @Schema(description = "Том на хосте", example = "postgres-data")
    private String host;

    @NotNull
    @Schema(description = "Путь в контейнере", example = "/data")
    private String target;

}
