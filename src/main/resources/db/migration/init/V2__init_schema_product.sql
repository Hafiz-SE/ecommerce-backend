CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,

    name VARCHAR(100) NOT NULL,

    description TEXT NOT NULL,

    price NUMERIC(19, 2) NOT NULL,

    created_at TIMESTAMP,
    modified_at TIMESTAMP,

    created_by VARCHAR(255),
    modified_by VARCHAR(255)
);
