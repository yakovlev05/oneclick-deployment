package ru.yakovlev05.infra.docker.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.CreateNetworkResponse;
import com.github.dockerjava.api.command.CreateVolumeResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.InspectVolumeResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.api.model.PortBinding;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.infra.docker.dto.ContainerInfoDto;
import ru.yakovlev05.infra.docker.dto.CreateContainerRequestDto;
import ru.yakovlev05.infra.docker.dto.CreateNetworkRequestDto;
import ru.yakovlev05.infra.docker.dto.CreateVolumeRequestDto;
import ru.yakovlev05.infra.docker.dto.NetworkInfoDto;
import ru.yakovlev05.infra.docker.dto.VolumeInfoDto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static ru.yakovlev05.infra.util.ThreadingUtil.callUninterruptibly;

@RequiredArgsConstructor
@Service
public class DockerResourceService {

    private final DockerClient dockerClient;

    public ContainerInfoDto createContainer(CreateContainerRequestDto requestDto) {
        callUninterruptibly(() ->
                dockerClient
                        .pullImageCmd(requestDto.getImage())
                        .exec(new PullImageResultCallback())
                        .awaitCompletion(120L, TimeUnit.SECONDS)
        );

        HostConfig hostConfig = HostConfig.newHostConfig()
                .withPortBindings(requestDto.getPorts().stream().map(PortBinding::parse).toList());

        CreateContainerResponse createContainerResponse = dockerClient
                .createContainerCmd(requestDto.getImage())
                .withName(requestDto.getContainerName())
                .withEnv(requestDto.getEnvironment())
                .withHostConfig(hostConfig)
                .exec();

        dockerClient.startContainerCmd(createContainerResponse.getId()).exec();
        return inspectContainer(createContainerResponse.getId());
    }

    public ContainerInfoDto inspectContainer(String containerId) {
        InspectContainerResponse inspectContainerResponse = dockerClient.inspectContainerCmd(containerId).exec();

        return new ContainerInfoDto()
                .setImage(inspectContainerResponse.getConfig().getImage())
                .setContainerName(inspectContainerResponse.getName())
                .setEnvironment(Optional.ofNullable(inspectContainerResponse.getConfig().getEnv())
                        .map(Arrays::asList)
                        .orElse(List.of()))
                .setPorts(inspectContainerResponse.getNetworkSettings().getPorts().getBindings().entrySet().stream()
                        .map(e -> e.getValue()[0].getHostPortSpec() + ":" + e.getKey().getPort())
                        .toList());
    }

    public NetworkInfoDto createNetwork(CreateNetworkRequestDto requestDto) {
        CreateNetworkResponse createNetworkResponse = dockerClient.createNetworkCmd()
                .withName(requestDto.getName())
                .exec();
        return inspectNetwork(createNetworkResponse.getId());
    }

    public NetworkInfoDto inspectNetwork(String networkId) {
        Network network = dockerClient.inspectNetworkCmd()
                .withNetworkId(networkId)
                .exec();

        return new NetworkInfoDto()
                .setName(network.getName());
    }

    public VolumeInfoDto createVolume(CreateVolumeRequestDto requestDto) {
        CreateVolumeResponse createVolumeResponse = dockerClient.createVolumeCmd()
                .withName(requestDto.getName())
                .exec();
        return inspectVolume(createVolumeResponse.getName());
    }

    public VolumeInfoDto inspectVolume(String volumeName) {
        InspectVolumeResponse volume = dockerClient.inspectVolumeCmd(volumeName).exec();

        return new VolumeInfoDto()
                .setName(volume.getName());
    }
}
