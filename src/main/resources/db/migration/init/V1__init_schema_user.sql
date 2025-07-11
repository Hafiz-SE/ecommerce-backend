CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    user_type VARCHAR(50),
    created_at TIMESTAMP,
    modified_at TIMESTAMP,
    created_by VARCHAR(255),
    modified_by VARCHAR(255)
);

CREATE INDEX IF NOT EXISTS idx_user_type ON users(user_type);
