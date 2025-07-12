ALTER TABLE order_items
    ADD COLUMN quantity INT NOT NULL DEFAULT 1;

ALTER TABLE order_items
    ADD CONSTRAINT uc_order_product UNIQUE (order_id, product_id);
