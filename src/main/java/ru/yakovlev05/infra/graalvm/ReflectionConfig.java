package ru.yakovlev05.infra.graalvm;

import com.github.dockerjava.core.DockerConfigFile;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;

// Другие конфиги смотри в мавене
@Configuration
@RegisterReflectionForBinding({
        DockerConfigFile.class,
})
public class ReflectionConfig {
}
