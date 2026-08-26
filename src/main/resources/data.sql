INSERT INTO customers (id, name) VALUES (1, 'Alice Johnson');
INSERT INTO customers (id, name) VALUES (2, 'Bob Smith');

INSERT INTO purchases (customer_id, purchase_date, amount) VALUES (1, DATEADD('MONTH', -2, CURRENT_DATE), 120.00);
INSERT INTO purchases (customer_id, purchase_date, amount) VALUES (1, DATEADD('DAY', 1, DATEADD('MONTH', -2, CURRENT_DATE)), 75.00);
INSERT INTO purchases (customer_id, purchase_date, amount) VALUES (1, DATEADD('MONTH', -1, CURRENT_DATE), 200.00);
INSERT INTO purchases (customer_id, purchase_date, amount) VALUES (1, CURRENT_DATE, 50.00);
INSERT INTO purchases (customer_id, purchase_date, amount) VALUES (2, DATEADD('MONTH', -2, CURRENT_DATE), 49.99);
INSERT INTO purchases (customer_id, purchase_date, amount) VALUES (2, DATEADD('MONTH', -1, CURRENT_DATE), 150.00);
INSERT INTO purchases (customer_id, purchase_date, amount) VALUES (2, CURRENT_DATE, 99.99);
