package ru.yakovlev05.infra.docker.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Schema(description = """
        Позволяет открыть доступ к контейнеру для доступа из браузера: домен:443.
        Автоматически выдает TLS сертификаты.
        
        Как устроено.
        Есть фиксированная часть домена. В запросе при создании контейнера можно выбрать поддомены, которые
        будут использоваться. Например
        1. postgres - это значит, что будут выданы сертификаты: postgres.domain, *.postgres.domain
        2. admin.postgres - это значит, что будут выданы сертификаты: admin.postgres + *.admin.postgres
        
        Пример: можно поднять nginx контейнер с настроенным конфигом роутинга между сервисами (фронт, бэк).
        А здесь открыть доступ наружу
        """)
@Getter
public class DomainBindingInfoDto {

    @NotNull
    @Schema(description = "Основной поддомен. Он и все его поддомены будут направлять трафик к контейнеру",
            example = "postgres")
    private String mainSubdomain;

    @NotEmpty
    @ArraySchema(
            schema = @Schema(example = "admin.postgres"),
            arraySchema = @Schema(description = "Список поддоменов для получения сертификатов.")
    )
    private List<String> subdomains;

    @NotNull
    @Schema(description = "Порт контейнера, куда будет идти трафик", example = "80")
    private Integer containerPort;
}
