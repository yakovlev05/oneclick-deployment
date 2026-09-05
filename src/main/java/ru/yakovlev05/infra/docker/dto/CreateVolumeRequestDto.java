package ru.yakovlev05.infra.docker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Schema(description = "Информация для создания Docker тома")
@Getter
public class CreateVolumeRequestDto {

    @NotNull
    @Schema(description = "Название тома")
    private String name;

}
