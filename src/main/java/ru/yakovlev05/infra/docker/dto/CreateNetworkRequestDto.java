package ru.yakovlev05.infra.docker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Schema(description = "Информация для создания Docker сети")
@Getter
public class CreateNetworkRequestDto {

    @NotNull
    @Schema(description = "Название сети")
    private String name;

}
