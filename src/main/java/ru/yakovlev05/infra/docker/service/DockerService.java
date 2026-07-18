package ru.yakovlev05.infra.docker.service;

import com.github.dockerjava.api.DockerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DockerService {

    private final DockerClient dockerClient;



}
