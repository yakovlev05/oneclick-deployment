package ru.yakovlev05.infra.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppDockerClientConfig {

    private static final String DOCKER_SOCKET = "unix:///var/run/docker.sock";

    @Bean
    public DockerClientConfig dockerClientConfig() {
        return DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(DOCKER_SOCKET)
                .build();
    }

    @Bean
    public DockerHttpClient dockerHttpClient(DockerClientConfig dockerClientConfig) {
        return new ApacheDockerHttpClient.Builder()
                .dockerHost(dockerClientConfig.getDockerHost())
                .build();
    }

    @Bean
    public DockerClient dockerClient(DockerClientConfig config, DockerHttpClient dockerHttpClient) {
        return DockerClientImpl.getInstance(config, dockerHttpClient);
    }

}
