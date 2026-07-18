package ru.yakovlev05.infra.docker.controller;

import com.github.dockerjava.api.DockerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yakovlev05.infra.docker.dto.CreateContainerDto;
import ru.yakovlev05.infra.docker.service.DockerService;

import java.util.concurrent.ThreadLocalRandom;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class DeploymentController {

    private final DockerService dockerService;
    private final DockerClient dockerClient;

    @PostMapping("/create-container")
    public void createContainer(@RequestBody CreateContainerDto createContainerDto) {
        dockerClient.createContainerCmd(createContainerDto.image())
                .withName("TO_DELETE_CONTAINER" + ThreadLocalRandom.current().nextInt(10000))
                .exec();
    }
}
