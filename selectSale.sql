INSERT INTO customers
(customer_name, contact_name, address, city, postal_code, country)
VALUES
('Trần Bình', 'Trọng', 'Quận 8', 'HCM', '70000', 'VN'),
('Tran Bao', 'An', 'Binh Thanh', 'HCM', '70000', 'VN'),
('Tasty', 'Finn', 'Streetroad 19B', 'Liverpool', 'L1 0AA', 'UK');


-- Câu 1
SELECT *
FROM customers;


-- Câu 2
SELECT DISTINCT country
FROM customers;


-- Câu 3
SELECT *
FROM customers
WHERE country = 'VN';


-- Câu 4
SELECT country, COUNT(customer_id) AS NumberOfCustomers
FROM customers
GROUP BY country;


-- Câu 5
SELECT country, COUNT(customer_id) AS NumberOfCustomers
FROM customers
GROUP BY country
HAVING COUNT(customer_id) >= 2;


-- Câu 6
SELECT customer_id, customer_name, country
FROM customers
ORDER BY customer_name ASC;