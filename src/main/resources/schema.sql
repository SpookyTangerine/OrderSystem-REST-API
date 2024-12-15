CREATE TABLE orders_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL
);

-- Tworzenie tabeli 'line_items'
CREATE TABLE line_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    unit_price INT NOT NULL,
    order_id BIGINT,
    FOREIGN KEY (order_id) REFERENCES orders_table(id) ON DELETE CASCADE
);