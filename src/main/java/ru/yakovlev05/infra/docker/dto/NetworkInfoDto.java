package ru.yakovlev05.infra.docker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Schema(description = "Информация о Docker сети")
@Accessors(chain = true)
@Getter
@Setter
public class NetworkInfoDto {

    @NotNull
    @Schema(description = "Название сети")
    private String name;

}
