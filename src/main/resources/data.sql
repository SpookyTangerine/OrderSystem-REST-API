-- Insert data into the 'orders' table
INSERT INTO orders_table (customer_name, order_status) VALUES 
('Jurek', 'IN PREPARATION'), 
('Stefan', 'IN PREPARATION');


-- Insert data into the 'line_items' table
INSERT INTO line_items (name, quantity, unit_price, order_id) VALUES 
('Apple', 3, 100, 1), 
('Orange', 5, 120, 2);

