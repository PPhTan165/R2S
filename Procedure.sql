CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL
);

CREATE TABLE employees (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    employee_name VARCHAR(255) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    supervisor_id INT,
    FOREIGN KEY (supervisor_id) REFERENCES employees(employee_id)
);

CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    list_price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    order_date DATETIME NOT NULL,
    customer_id INT NOT NULL,
    employee_id INT NOT NULL,
    total DECIMAL(10, 2),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id)
);

CREATE TABLE line_items (
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2),
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);

insert into customers(customer_name) values 
('Nguyễn văn A'),('Lê Thị B'),('Phan Văn C');

insert into employees (employee_name, salary ,supervisor_id) values
('Quản lý 1',10000,null),
('Nhân Viên 1',5000,1),
('Nhân Viên 2',5000,1);

insert into products (product_name, list_price ) values
('Iphone 16 Pro Max', 40000),
('Iphone 17 Pro',42000),
('Mac M4 Pro',47000),
('Mac M5',45000);

insert into orders (order_date,customer_id ,employee_id,total) values
('2026-06-23 10:00:00',1,2,40000),
('2026-06-20 15:00:00',2,3,42000),
('2026-06-21 09:30:00',3,2,45000),
('2026-05-20 10:00:00',1,2,80000);

insert into line_items (order_id,product_id ,quantity,price ) values
(1,1,1,40000),
(2,2,1,42000),
(3,4,1,45000),
(4,1,2,40000);

select * from orders;
select * from line_items li;
select * from customers;

-- câu 1
DELIMITER //

CREATE PROCEDURE get_all_customer_with_order()
BEGIN
    SELECT 
        o.customer_id,
        c.customer_name
    FROM customers c
    INNER JOIN orders o 
        ON c.customer_id = o.customer_id;
END //

DELIMITER ;

call get_all_customer_with_order();

-- câu 2
DROP PROCEDURE IF EXISTS get_all_order_with_customer_id;

DELIMITER //

create pro0cedure get_all_order_with_customer_id(in customer_id int)
begin
	select *
	from orders o
	where o.customer_id = customer_id;
end //

DELIMITER ;

call get_all_order_with_customer_id(1);

-- câu 3
DELIMITER //

create procedure get_all_line_items_for_order(in order_id int)
begin
	select *
	from line_items
	where line_items.order_id = order_id;
end //

DELIMITER ;

call get_all_line_items_for_order(1);


-- câu 4
DELIMITER //

create function get_compute_order_total(order_id int)
returns decimal(10,2)
begin
	declare order_total decimal(10,2);

	select sum(quantity * price) into order_total
	from line_items
	where line_items.order_id = order_id;
	
	return order_total;
end //

DELIMITER ;

select get_compute_order_total(4);

-- câu 5
DELIMITER //

create procedure add_customer(in customer_name varchar(255))
begin
	insert into customers (customer_name) values (customer_name);
end //

DELIMITER ;

call add_customer('Trần Văn Quốc D');

-- câu 6
DELIMITER //

create procedure delete_customer(in sp_customer_id int)
begin
	delete from line_items where order_id in (select order_id from orders o where customer_id = sp_customer_id);
	delete from orders  where customer_id = sp_customer_id;
	delete from customers where customer_id = sp_customer_id;
end //

DELIMITER ;

call delete_customer(1);

-- câu 7
DELIMITER // 
create procedure update_customer(in sp_customer_id int,in sp_customer_name varchar(255))
begin
	update customers set customer_name = sp_customer_name where customer_id = sp_customer_id;
end //

DELIMITER ;

call update_customer(3,'Lê Thị M');

-- câu 8
DELIMITER //
create procedure create_order(in sp_order_date datetime, in sp_customer_id int, in sp_employee_id int, in sp_total decimal(10,2))
begin 
	insert into orders (order_date,customer_id,employee_id,total) values(sp_order_date, sp_customer_id, sp_employee_id, sp_total);	
end //

DELIMITER ;
call create_order('2026-07-15 18:30:00',5,1,87000.00);

drop trigger if exists tr_order_date_greater_current_date;

DELIMITER //

CREATE TRIGGER tr_order_date_greater_current_date
AFTER INSERT ON orders
FOR EACH ROW
BEGIN
    DECLARE v_order_date DATETIME;

    SET v_order_date = NEW.order_date;

    IF v_order_date > NOW() THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'order date greater than the current date';
    END IF;
end //

DELIMITER ;

-- câu 9
DELIMITER //

create procedure create_line_items(in sp_order_id int, in sp_product_id int, in sp_quantity int, in sp_price decimal(10,2))
begin
	insert into line_items (order_id, product_id,quantity,price) values (sp_order_id,sp_product_id,sp_quantity,sp_price);
end //

DELIMITER ;

select * from line_items li ;
select * from orders;

call create_line_items(5,4,1,42000);

-- câu 10
drop procedure if exists update_total_order;
DELIMITER //

create procedure update_total_order(in sp_order_id int)
begin
	update orders set total = (select sum(quantity * price) from line_items where order_id = sp_order_id) 
	where order_id = sp_order_id;
end //

DELIMITER ;

call update_total_order(5);

select sum(quantity * price) from line_items where order_id = 5;
