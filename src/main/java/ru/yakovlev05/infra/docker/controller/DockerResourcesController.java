package ru.yakovlev05.infra.docker.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yakovlev05.infra.docker.dto.*;
import ru.yakovlev05.infra.docker.service.DockerResourceService;
import ru.yakovlev05.infra.error.model.Errors;

@Tag(name = "docker-resources")
@RequestMapping("/api/deployment/{deploymentId}/docker-resources")
@RestController
@RequiredArgsConstructor
public class DockerResourcesController {

    private final DockerResourceService dockerResourceService;

    @Operation(summary = "Создать Docker-контейнер")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Docker контейнер успешно создан и запущен",
                    content = @Content(schema = @Schema(implementation = ContainerInfoDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка создания. Валидация или ошибка от Docker",
                    content = @Content(schema = @Schema(implementation = Errors.class)))
    })
    @PostMapping("/container")
    public ContainerInfoDto createContainer(
            @PathVariable Long deploymentId,
            @RequestBody @Valid CreateContainerRequestDto requestDto
    ) {
        return dockerResourceService.createContainer(requestDto, deploymentId);
    }

    @Operation(summary = "Удалить Docker-контейнер")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Docker контейнер успешно остановлен и удален"),
            @ApiResponse(responseCode = "400", description = "Ошибка удаления. Валидация или ошибка от Docker",
                    content = @Content(schema = @Schema(implementation = Errors.class)))
    })
    @DeleteMapping("/container/{containerId}")
    public void deleteContainer(@PathVariable Long deploymentId, @PathVariable String containerId) {
        dockerResourceService.deleteContainer(containerId, deploymentId);
    }

    @Operation(summary = "Создать Docker-сеть")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Docker сеть успешно создана",
                    content = @Content(schema = @Schema(implementation = NetworkInfoDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка создания. Валидация или ошибка от Docker",
                    content = @Content(schema = @Schema(implementation = Errors.class)))
    })
    @PostMapping("/network")
    public NetworkInfoDto createNetwork(
            @PathVariable Long deploymentId,
            @RequestBody @Valid CreateNetworkRequestDto requestDto
    ) {
        return dockerResourceService.createNetwork(requestDto, deploymentId);
    }

    @Operation(summary = "Удалить Docker-сеть")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Docker сеть успешно удалена"),
            @ApiResponse(responseCode = "400", description = "Ошибка удаления. Валидация или ошибка от Docker",
                    content = @Content(schema = @Schema(implementation = Errors.class)))
    })
    @DeleteMapping("/network/{networkId}")
    public void deleteNetwork(@PathVariable Long deploymentId, @PathVariable String networkId) {
        dockerResourceService.deleteNetwork(networkId, deploymentId);
    }

    @Operation(summary = "Создать Docker-том")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Docker том успешно создан",
                    content = @Content(schema = @Schema(implementation = VolumeInfoDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка создания. Валидация или ошибка от Docker",
                    content = @Content(schema = @Schema(implementation = Errors.class)))
    })
    @PostMapping("/volume")
    public VolumeInfoDto createVolume(
            @PathVariable Long deploymentId,
            @RequestBody @Valid CreateVolumeRequestDto requestDto
    ) {
        return dockerResourceService.createVolume(requestDto, deploymentId);
    }

    @Operation(summary = "Удалить Docker-том")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Docker том успешно удален"),
            @ApiResponse(responseCode = "400", description = "Ошибка удаления. Валидация или ошибка от Docker",
                    content = @Content(schema = @Schema(implementation = Errors.class)))
    })
    @DeleteMapping("/volume/{volumeName}")
    public void deleteVolume(@PathVariable Long deploymentId, @PathVariable String volumeName) {
        dockerResourceService.deleteVolume(volumeName, deploymentId);
    }

}
