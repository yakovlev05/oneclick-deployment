package ru.yakovlev05.infra.docker.service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.InspectVolumeResponse;
import com.github.dockerjava.api.model.Network;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakovlev05.infra.docker.dto.ContainerInfoDto;
import ru.yakovlev05.infra.docker.dto.NetworkInfoDto;
import ru.yakovlev05.infra.docker.dto.VolumeInfoDto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DockerInspectService {

    private final DockerClient dockerClient;

    public ContainerInfoDto inspectContainer(String containerId) {
        InspectContainerResponse inspectContainerResponse = dockerClient.inspectContainerCmd(containerId).exec();

        return new ContainerInfoDto()
                .setImage(inspectContainerResponse.getConfig().getImage())
                .setContainerName(inspectContainerResponse.getName())
                .setContainerId(inspectContainerResponse.getId())
                .setEnvironment(Optional.ofNullable(inspectContainerResponse.getConfig().getEnv())
                        .map(Arrays::asList)
                        .orElse(List.of()))
                .setPorts(inspectContainerResponse.getNetworkSettings().getPorts().getBindings().entrySet().stream()
                        .map(e -> e.getValue()[0].getHostPortSpec() + ":" + e.getKey().getPort())
                        .toList());
    }

    public NetworkInfoDto inspectNetwork(String networkId) {
        Network network = dockerClient.inspectNetworkCmd()
                .withNetworkId(networkId)
                .exec();

        return new NetworkInfoDto()
                .setName(network.getName())
                .setNetworkId(network.getId());
    }

    public VolumeInfoDto inspectVolume(String volumeName) {
        InspectVolumeResponse volume = dockerClient.inspectVolumeCmd(volumeName).exec();

        return new VolumeInfoDto()
                .setName(volume.getName());
    }
}
