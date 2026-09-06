package ru.yakovlev05.infra.deployment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.yakovlev05.infra.docker.dto.DockerResourceInfoWrapper;

import java.time.LocalDateTime;
import java.util.List;

@Accessors(chain = true)
@Setter
@Getter
public class DeploymentInfoDto {

    @Schema(description = "ID сессии деплоя", example = "1")
    private Long id;

    @Schema(description = "Название", example = "Конструктор презентаций")
    private String name;

    @Schema(description = "Описание", example = "Конструктор презентаций, который делаем в университете")
    private String description;

    @Schema(description = "На какое время в секундах. После истечения деплой удаляется", example = "1440")
    private Long ttl;

    @Schema(description = "Когда создано")
    private LocalDateTime createdAt;

    @Schema(description = "Когда последний раз обновлено")
    private LocalDateTime updatedAt;

    @Schema(description = "Все созданные docker ресурсы для сессии деплоя")
    private List<DockerResourceInfoWrapper> dockerResources;

}
