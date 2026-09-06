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

    public DeploymentInfoDto getInfoById(Long id) {
        Deployment deployment = findByIdOrThrow(id);
        return deploymentMapper.toDeploymentInfoDto(deployment);
    }

    public Deployment findByIdOrThrow(Long id) {
        return deploymentRepository.findById(id)
                .orElseThrow(() -> new Errors(NOT_FOUND.value(), "notFound", "deployment not found")
                        .toEx());
    }

    public Deployment findByIdAndCheckAffiliationOrThrow(Long id, String dockerId) {
        Deployment deployment = findByIdOrThrow(id);

        boolean belongsToDeployment = deployment.getDockerResources().stream()
                .anyMatch(resource -> resource.getDockerId().equals(dockerId));

        if (!belongsToDeployment) {
            throw new Errors(NOT_FOUND.value(), "notFound", "docker resource not found in deployment")
                    .toEx();
        }

        return deployment;
    }

    public Deployment save(Deployment deployment) {
        return deploymentRepository.save(deployment);
    }
}
