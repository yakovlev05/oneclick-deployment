package ru.yakovlev05.infra.docker.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Информация о Docker ресурсе. Конкретная форма зависит от типа ресурса",
        oneOf = {ContainerInfoDto.class, NetworkInfoDto.class, VolumeInfoDto.class}
)
public interface DockerResourceInfo {

}
