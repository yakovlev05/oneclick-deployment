package ru.yakovlev05.infra.deployment.mapper;

import org.springframework.stereotype.Component;
import ru.yakovlev05.infra.deployment.dto.CreateDeploymentRequestDto;
import ru.yakovlev05.infra.deployment.dto.DeploymentInfoDto;
import ru.yakovlev05.infra.deployment.entity.Deployment;

import java.time.LocalDateTime;

@Component
public class DeploymentMapper {

    public Deployment toEntity(CreateDeploymentRequestDto dto) {
        return new Deployment()
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setTtl(dto.getTtl())
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now());
    }

    public DeploymentInfoDto toDeploymentInfoDto(Deployment entity) {
        return new DeploymentInfoDto()
                .setId(entity.getId())
                .setName(entity.getName())
                .setDescription(entity.getDescription())
                .setTtl(entity.getTtl())
                .setCreatedAt(entity.getCreatedAt())
                .setUpdatedAt(entity.getUpdatedAt());
    }

}
