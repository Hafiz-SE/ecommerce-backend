CREATE TABLE wish_list_items (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,

    created_at TIMESTAMP,
    modified_at TIMESTAMP,

    created_by VARCHAR(255),
    modified_by VARCHAR(255),

    CONSTRAINT uc_customer_product UNIQUE (user_id, product_id),

    CONSTRAINT fk_wish_list_items_user FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_wish_list_items_product FOREIGN KEY (product_id)
        REFERENCES products (id)
        ON DELETE CASCADE
);

CREATE INDEX customer_index ON wish_list_items (user_id);
