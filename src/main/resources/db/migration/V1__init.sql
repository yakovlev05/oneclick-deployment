CREATE TABLE deployment
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    ttl         BIGINT       NOT NULL,
    created_at  timestamp    NOT NULL,
    updated_at  timestamp    NOT NULL
);