CREATE TABLE IF NOT EXISTS order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    price_at_purchase_time NUMERIC(19, 2) NOT NULL,

    created_at TIMESTAMP,
    modified_at TIMESTAMP,

    created_by VARCHAR(255),
    modified_by VARCHAR(255),

    CONSTRAINT fk_order
        FOREIGN KEY (order_id)
        REFERENCES orders(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_order_id ON order_items(order_id);
CREATE INDEX IF NOT EXISTS idx_product_id ON order_items(product_id);
