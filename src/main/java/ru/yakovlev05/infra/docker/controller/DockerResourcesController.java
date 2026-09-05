package ru.yakovlev05.infra.docker.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yakovlev05.infra.docker.dto.ContainerInfoDto;
import ru.yakovlev05.infra.docker.dto.CreateContainerRequestDto;
import ru.yakovlev05.infra.docker.dto.CreateNetworkRequestDto;
import ru.yakovlev05.infra.docker.dto.CreateVolumeRequestDto;
import ru.yakovlev05.infra.docker.dto.NetworkInfoDto;
import ru.yakovlev05.infra.docker.dto.VolumeInfoDto;
import ru.yakovlev05.infra.docker.service.DockerResourceService;
import ru.yakovlev05.infra.error.model.Errors;

@Tag(name = "docker-resources")
@RequestMapping("/api/docker-resources")
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
    public ContainerInfoDto createContainer(@RequestBody @Valid CreateContainerRequestDto requestDto) {
        return dockerResourceService.createContainer(requestDto);
    }

    @Operation(summary = "Создать Docker-сеть")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Docker сеть успешно создана",
                    content = @Content(schema = @Schema(implementation = NetworkInfoDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка создания. Валидация или ошибка от Docker",
                    content = @Content(schema = @Schema(implementation = Errors.class)))
    })
    @PostMapping("/network")
    public NetworkInfoDto createNetwork(@RequestBody @Valid CreateNetworkRequestDto requestDto) {
        return dockerResourceService.createNetwork(requestDto);
    }

    @Operation(summary = "Создать Docker-том")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Docker том успешно создан",
                    content = @Content(schema = @Schema(implementation = VolumeInfoDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка создания. Валидация или ошибка от Docker",
                    content = @Content(schema = @Schema(implementation = Errors.class)))
    })
    @PostMapping("/volume")
    public VolumeInfoDto createVolume(@RequestBody @Valid CreateVolumeRequestDto requestDto) {
        return dockerResourceService.createVolume(requestDto);
    }

}
