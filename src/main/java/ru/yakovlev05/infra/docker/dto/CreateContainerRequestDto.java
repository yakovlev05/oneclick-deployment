package ru.yakovlev05.infra.docker.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.util.List;

@Schema(description = "Параметры, которые нужно передать для создания контейнера")
@Getter
public class CreateContainerRequestDto {

    @NotNull
    @Pattern(regexp = "^.+:.+$")
    @Schema(description = "Образ в Docker", example = "postgres:18")
    private String image;

    @NotNull
    @Schema(description = "Название контейнера", example = "app-backend")
    private String containerName;

    @ArraySchema(
            schema = @Schema(example = "POSTGRES_USER=alexey"),
            arraySchema = @Schema(description = "Список переменных окружения для контейнера")
    )
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<@Pattern(regexp = "^\\w+=\\w+$") String> environment;


    @ArraySchema(
            schema = @Schema(example = "8080:8080"),
            arraySchema = @Schema(description = "Список открытых портов")
    )
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private List<@Pattern(regexp = "^\\d+:\\d+$") String> ports;
}
