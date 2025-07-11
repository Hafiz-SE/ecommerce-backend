CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    total_price NUMERIC(19, 2) NOT NULL,

    created_at TIMESTAMP,
    modified_at TIMESTAMP,

    created_by VARCHAR(255),
    modified_by VARCHAR(255),

    CONSTRAINT fk_orders_user FOREIGN KEY (user_id)
        REFERENCES "users" (id)
        ON DELETE CASCADE
);

CREATE INDEX idx_orders_user_id ON orders (user_id);