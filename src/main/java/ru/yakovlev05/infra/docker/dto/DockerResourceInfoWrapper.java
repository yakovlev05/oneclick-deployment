package ru.yakovlev05.infra.docker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.yakovlev05.infra.docker.entity.DockerResourceType;

@Schema(description = "Информация о Docker ресурсе вместе с информацией какой ти ресурса")
@Accessors(chain = true)
@Getter
@Setter
public class DockerResourceInfoWrapper {

    @Schema(description = "Тип Docker ресурса", example = "CONTAINER")
    private DockerResourceType type;

    @Schema(description = "Информация о ресурсе Docker. Разная в зависимости от типа")
    private DockerResourceInfo info;

}
