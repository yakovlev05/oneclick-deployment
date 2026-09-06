package ru.yakovlev05.infra.docker.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "docker_resource")
public class DockerResource {

    @Id
    private Long id;
    private Long deploymentId;
    private DockerResourceType type;
    private String dockerId;
    private LocalDateTime createdAt;

}
