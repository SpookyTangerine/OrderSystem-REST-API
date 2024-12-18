-- Insert data into the 'orders' table
INSERT INTO orders_table (customer_name, order_status, checkout_date) VALUES 
('zbigniew', 'in progress', '2024-12-17T05:15:52'), 
('imie2', 'commietted', '2024-12-01T23:37:52');


-- Insert data into the 'line_items' table
INSERT INTO line_items (name, quantity, unit_price, order_id) VALUES 
('Apple', 3, 100, 1), 
('Orange', 5, 120, 2);

