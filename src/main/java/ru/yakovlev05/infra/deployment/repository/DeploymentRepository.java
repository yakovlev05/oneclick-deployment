package ru.yakovlev05.infra.deployment.repository;

import org.springframework.data.repository.CrudRepository;
import ru.yakovlev05.infra.deployment.entity.Deployment;

public interface DeploymentRepository extends CrudRepository<Deployment, Long> {

}
