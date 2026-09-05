package ru.yakovlev05.infra.deployment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Setter
@Getter
public class DeploymentInfoDto {

    @Schema(description = "ID сессии деплоя",  example = "Конструктор презентаций")
    private Long id;

    @Schema(description = "Название", example = "Конструктор презентаций, который делаем в университете")
    private String name;

    @Schema(description = "Описание")
    private String description;

    @Schema(description = "На какое время в секундах. После истечения деплой удаляется", example = "1440")
    private Long ttl;

    @Schema(description = "Когда создано")
    private LocalDateTime createdAt;

    @Schema(description = "Когда последний раз обновлено")
    private LocalDateTime updatedAt;

}
