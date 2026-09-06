package ru.yakovlev05.infra.deployment.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import ru.yakovlev05.infra.docker.entity.DockerResource;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Accessors(chain = true)
@Getter
@Setter
@Table(name = "deployment")
public class Deployment {

    @Id
    private Long id;
    private String name;
    private String description;
    private Long ttl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @MappedCollection(idColumn = "deployment_id")
    private Set<DockerResource> dockerResources = new HashSet<>();

    public Deployment addDockerResource(DockerResource resource) {
        dockerResources.add(resource);
        return this;
    }

    public Deployment removeDockerResourceByDockerId(String dockerId) {
        dockerResources.removeIf(r -> r.getDockerId().equals(dockerId));
        return this;
    }
}
