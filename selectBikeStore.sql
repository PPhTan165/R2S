-- Bài 1

select first_name, last_name, email
from customers;

-- Bài 2

select * from customers
where state = 'CA';

-- Bài 3

select * from customers c
order by c.first_name;

-- Bài 4

select city, count(customer_id) customer_count
from customers c
where c.state = 'CA'
group by city;

-- Bài 5

select city
from customers c
where c.state = 'CA'
group by city
having count(c.customer_id) >10;

-- Bài 6

select product_name, p.model_year 
from products p
where p.list_price between 1000 and 2000;

-- Bài 7

select s.first_name, s.email from staffs s
where s.active =1;

-- Bài 8

select product_name, p.brand_id  from products p
where p.model_year = 2016 and p.list_price >= 1000;

-- Bài 9

select order_id, customer_id from orders o
where o.shipped_date is not null;

-- Bài 10

select product_id, list_price, quantity from order_items oi 
where oi.discount >=0 and quantity = 2;

-- Bài 11

select store_id, count(product_id) product_count from stocks s 
group by store_id
having count(product_id) > 5;

-- Bài 12
 
select concat(c.first_name , ' ',c.last_name ) customer_name , email from customers c 
where email like '%@yahoo.com';

-- Bài 13

select category_id, AVG(p.list_price ) avg_price from products p
group by category_id 
having avg_price > 500
order by avg_price desc;

-- Bài 14

select brand_id,count(product_id) total_product from products p 
group by brand_id
having total_product > 2
order by total_product desc;

