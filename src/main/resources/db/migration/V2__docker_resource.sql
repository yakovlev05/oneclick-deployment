CREATE TABLE docker_resource
(
    id            BIGSERIAL PRIMARY KEY,
    deployment_id BIGINT       NOT NULL REFERENCES deployment (id) ON DELETE CASCADE,
    type          VARCHAR(255) NOT NULL,
    docker_id     VARCHAR(255) NOT NULL,
    created_at    timestamp    NOT NULL
);
