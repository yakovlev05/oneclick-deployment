package ru.yakovlev05.infra.docker.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.CreateNetworkResponse;
import com.github.dockerjava.api.command.CreateVolumeResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.exception.DockerException;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Volume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.infra.consts.GlobalConst;
import ru.yakovlev05.infra.deployment.entity.Deployment;
import ru.yakovlev05.infra.deployment.service.DeploymentService;
import ru.yakovlev05.infra.docker.dto.*;
import ru.yakovlev05.infra.docker.entity.DockerResource;
import ru.yakovlev05.infra.docker.entity.DockerResourceType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static ru.yakovlev05.infra.consts.GlobalConst.TRAEFIK_NETWORK;
import static ru.yakovlev05.infra.util.ThreadingUtil.callUninterruptibly;

@RequiredArgsConstructor
@Service
public class DockerResourceService {

    private final DockerClient dockerClient;
    private final DeploymentService deploymentService;
    private final DockerInspectService dockerInspectService;
    private final TraefikLabelsService traefikLabelsService;

    public ContainerInfoDto createContainer(CreateContainerRequestDto requestDto, Long deploymentId) {
        Deployment deployment = deploymentService.findByIdOrThrow(deploymentId);
        List<String> networks = new ArrayList<>(List.of(requestDto.getNetworkName()));
        Map<String, String> labels = new HashMap<>();

        if (requestDto.getDomainNamespace() != null) {
            networks.add(TRAEFIK_NETWORK);
            labels.putAll(traefikLabelsService.getLabels(deploymentId, requestDto.getDomainNamespace()));
        }

        callUninterruptibly(() ->
                dockerClient
                        .pullImageCmd(requestDto.getImage())
                        .exec(new PullImageResultCallback())
                        .awaitCompletion(120L, TimeUnit.SECONDS)
        );

        HostConfig hostConfig = HostConfig.newHostConfig()
                .withBinds(
                        requestDto.getVolumeBinding().stream()
                                .map(b -> new Bind(b.getHost(), new Volume(b.getTarget())))
                                .toList()
                )
                .withPortBindings(requestDto.getPorts().stream().map(PortBinding::parse).toList());

        CreateContainerResponse createContainerResponse = dockerClient
                .createContainerCmd(requestDto.getImage())
                .withName(nameWithPrefix(deployment.getId(), requestDto.getContainerName()))
                .withEnv(requestDto.getEnvironment())
                .withHostConfig(hostConfig)
                .withLabels(labels)
                .exec();

        DockerResource dockerResource = new DockerResource()
                .setDeploymentId(deployment.getId())
                .setType(DockerResourceType.CONTAINER)
                .setDockerId(createContainerResponse.getId())
                .setCreatedAt(LocalDateTime.now());
        deployment.addDockerResource(dockerResource);
        deploymentService.save(deployment);

        try {
            networks.forEach(network -> {
                dockerClient.connectToNetworkCmd()
                        .withContainerId(createContainerResponse.getId())
                        .withNetworkId(network)
                        .exec();
            });
            dockerClient.startContainerCmd(createContainerResponse.getId()).exec();
        } catch (DockerException e) {
            dockerClient.removeContainerCmd(createContainerResponse.getId()).exec();
            throw e;
        }

        return dockerInspectService.inspectContainer(createContainerResponse.getId());
    }

    public void deleteContainer(String containerId, Long deploymentId) {
        Deployment deployment = deploymentService.findByIdAndCheckAffiliationOrThrow(deploymentId, containerId);

        dockerClient.removeContainerCmd(containerId)
                .withForce(true)
                .exec();

        deployment.removeDockerResourceByDockerId(containerId);
        deploymentService.save(deployment);
    }

    public NetworkInfoDto createNetwork(CreateNetworkRequestDto requestDto, Long deploymentId) {
        Deployment deployment = deploymentService.findByIdOrThrow(deploymentId);

        CreateNetworkResponse createNetworkResponse = dockerClient.createNetworkCmd()
                .withName(nameWithPrefix(deployment.getId(), requestDto.getName()))
                .exec();

        DockerResource dockerResource = new DockerResource()
                .setDeploymentId(deployment.getId())
                .setType(DockerResourceType.NETWORK)
                .setDockerId(createNetworkResponse.getId())
                .setCreatedAt(LocalDateTime.now());
        deployment.addDockerResource(dockerResource);
        deploymentService.save(deployment);

        return dockerInspectService.inspectNetwork(createNetworkResponse.getId());
    }

    public void deleteNetwork(String networkId, Long deploymentId) {
        Deployment deployment = deploymentService.findByIdAndCheckAffiliationOrThrow(deploymentId, networkId);

        dockerClient.removeNetworkCmd(networkId).exec();

        deployment.removeDockerResourceByDockerId(networkId);
        deploymentService.save(deployment);
    }

    public VolumeInfoDto createVolume(CreateVolumeRequestDto requestDto, Long deploymentId) {
        Deployment deployment = deploymentService.findByIdOrThrow(deploymentId);

        CreateVolumeResponse createVolumeResponse = dockerClient.createVolumeCmd()
                .withName(nameWithPrefix(deployment.getId(), requestDto.getName()))
                .exec();

        DockerResource dockerResource = new DockerResource()
                .setDeploymentId(deployment.getId())
                .setType(DockerResourceType.VOLUME)
                .setDockerId(createVolumeResponse.getName())
                .setCreatedAt(LocalDateTime.now());
        deployment.addDockerResource(dockerResource);
        deploymentService.save(deployment);

        return dockerInspectService.inspectVolume(createVolumeResponse.getName());
    }

    public void deleteVolume(String volumeName, Long deploymentId) {
        Deployment deployment = deploymentService.findByIdAndCheckAffiliationOrThrow(deploymentId, volumeName);

        dockerClient.removeVolumeCmd(volumeName).exec();

        deployment.removeDockerResourceByDockerId(volumeName);
        deploymentService.save(deployment);
    }

    private String nameWithPrefix(Long deploymentId, String originalName) {
        return GlobalConst.DOCKER_NAMESPACE + "-" + deploymentId + "-" + originalName;
    }
}
