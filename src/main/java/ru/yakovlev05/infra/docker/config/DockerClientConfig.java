package ru.yakovlev05.infra.docker.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DockerClientConfig {

    private static final String DOCKER_SOCKET = "unix:///var/run/docker.sock";

    @Bean
    public com.github.dockerjava.core.DockerClientConfig localDockerClientConfig() {
        return DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(DOCKER_SOCKET)
                .build();
    }

    @Bean
    public DockerHttpClient dockerHttpClient(com.github.dockerjava.core.DockerClientConfig localDockerClientConfig) {
        return new ApacheDockerHttpClient.Builder()
                .dockerHost(localDockerClientConfig.getDockerHost())
                .build();
    }

    @Bean
    public DockerClient dockerClient(com.github.dockerjava.core.DockerClientConfig config, DockerHttpClient dockerHttpClient) {
        return DockerClientImpl.getInstance(config, dockerHttpClient);
    }

}
