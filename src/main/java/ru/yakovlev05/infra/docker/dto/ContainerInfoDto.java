package ru.yakovlev05.infra.docker.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Schema(description = "Информация о Docker контейнере")
@Accessors(chain = true)
@Getter
@Setter
public class ContainerInfoDto {

    @Pattern(regexp = "^.+:.+$")
    @Schema(description = "Образ в Docker", example = "postgres:18")
    private String image;

    @Schema(description = "Название контейнера", example = "app-backend")
    private String containerName;

    @ArraySchema(
            schema = @Schema(example = "POSTGRES_USER=alexey"),
            arraySchema = @Schema(description = "Список переменных окружения для контейнера")
    )
    private List<@Pattern(regexp = "^\\w+=\\w+$") String> environment;


    @ArraySchema(
            schema = @Schema(example = "8080:8080"),
            arraySchema = @Schema(description = "Список открытых портов")
    )
    private List<@Pattern(regexp = "^\\d+:\\d+$") String> ports;

}
