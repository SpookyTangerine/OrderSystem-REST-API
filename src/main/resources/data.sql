-- Insert data into the 'orders' table
INSERT INTO orders_table (customer_name, order_status) VALUES 
('zbigniew', 'in progress'), 
('imie2', 'commiettek');


-- Insert data into the 'line_items' table
INSERT INTO line_items (name, quantity, unit_price, order_id) VALUES 
('Apple', 3, 100, 1), 
('Orange', 5, 120, 2);

