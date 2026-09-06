package ru.yakovlev05.infra.deployment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yakovlev05.infra.deployment.dto.CreateDeploymentRequestDto;
import ru.yakovlev05.infra.deployment.dto.DeploymentInfoDto;
import ru.yakovlev05.infra.deployment.service.DeploymentService;
import ru.yakovlev05.infra.error.model.Errors;

@Tag(name = "deployment")
@RequestMapping("/api/deployment")
@RestController
@RequiredArgsConstructor
public class DeploymentController {

    private final DeploymentService deploymentService;

    @Operation(summary = "Создание сессии деплоя")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Успешно создано"),
            @ApiResponse(responseCode = "400", description = "Неверный запрос, ошибка валидации",
                    content = @Content(schema = @Schema(implementation = Errors.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeploymentInfoDto create(@RequestBody @Valid CreateDeploymentRequestDto requestDto) {
        return deploymentService.create(requestDto);
    }

    @Operation(summary = "Получить информацию о сессии деплоя по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Информация получена"),
            @ApiResponse(responseCode = "404", description = "Сессия деплоя не найдена",
                    content = @Content(schema = @Schema(implementation = Errors.class)))
    })
    @GetMapping("/{id}")
    public DeploymentInfoDto getById(@PathVariable Long id) {
        return deploymentService.getInfoById(id);
    }

}
