package ru.yakovlev05.infra.deployment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.infra.deployment.dto.CreateDeploymentRequestDto;
import ru.yakovlev05.infra.deployment.dto.DeploymentInfoDto;
import ru.yakovlev05.infra.deployment.entity.Deployment;
import ru.yakovlev05.infra.deployment.mapper.DeploymentMapper;
import ru.yakovlev05.infra.deployment.repository.DeploymentRepository;
import ru.yakovlev05.infra.error.model.Errors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Service
public class DeploymentService {

    private final DeploymentMapper deploymentMapper;
    private final DeploymentRepository deploymentRepository;

    public DeploymentInfoDto create(CreateDeploymentRequestDto requestDto) {
        Deployment deployment = deploymentMapper.toEntity(requestDto);
        deploymentRepository.save(deployment);
        return deploymentMapper.toDeploymentInfoDto(deployment);
    }

    public DeploymentInfoDto getById(Long id) {
        Deployment deployment = deploymentRepository.findById(id)
                .orElseThrow(() -> new Errors(NOT_FOUND.value(), "notFound", "deployment not found")
                        .toEx());
        return deploymentMapper.toDeploymentInfoDto(deployment);
    }
}
