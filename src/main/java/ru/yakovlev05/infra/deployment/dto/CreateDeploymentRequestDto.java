package ru.yakovlev05.infra.deployment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Schema(name = "Тело запрос создания сессии деплоя")
@Getter
public class CreateDeploymentRequestDto {

    @NotNull
    @Size(max = 255)
    @Schema(description = "Название сессии", example = "Конструктор презентаций")
    private String name;

    @NotNull
    @Schema(description = "Описание", example = "Конструктор презентаций, который делаем в университете")
    private String description;

    @NotNull
    @Schema(description = "На какое время в секундах. После истечения деплой удаляется", example = "1440")
    private Long ttl;
}
