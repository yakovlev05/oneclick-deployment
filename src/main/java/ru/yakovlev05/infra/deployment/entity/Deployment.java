package ru.yakovlev05.infra.deployment.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

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

}
