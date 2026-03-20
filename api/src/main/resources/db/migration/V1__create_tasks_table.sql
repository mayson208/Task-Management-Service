CREATE TABLE IF NOT EXISTS tasks (
    id          VARCHAR(36)  NOT NULL,
    title       VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    status      VARCHAR(20)  NOT NULL,
    created_at  DATETIME(6)  NOT NULL,
    updated_at  DATETIME(6)  NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX idx_tasks_status ON tasks (status);
