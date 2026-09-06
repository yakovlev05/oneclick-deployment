package ru.yakovlev05.infra.docker.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yakovlev05.infra.docker.dto.DockerResourceInfo;
import ru.yakovlev05.infra.docker.dto.DockerResourceInfoWrapper;
import ru.yakovlev05.infra.docker.entity.DockerResource;
import ru.yakovlev05.infra.docker.service.DockerInspectService;

@RequiredArgsConstructor
@Component
public class DockerResourceMapper {

    private final DockerInspectService dockerInspectService;

    public DockerResourceInfoWrapper toDockerResourceInfoWrapper(DockerResource dockerResource) {
        return new DockerResourceInfoWrapper()
                .setType(dockerResource.getType())
                .setInfo(toDockerResourceInfo(dockerResource));
    }

    private DockerResourceInfo toDockerResourceInfo(DockerResource dockerResource) {
        return switch (dockerResource.getType()) {
            case CONTAINER -> dockerInspectService.inspectContainer(dockerResource.getDockerId());
            case NETWORK -> dockerInspectService.inspectNetwork(dockerResource.getDockerId());
            case VOLUME -> dockerInspectService.inspectVolume(dockerResource.getDockerId());
        };
    }
}
