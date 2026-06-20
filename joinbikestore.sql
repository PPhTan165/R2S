-- câu 1
select product_name, list_price, b.brand_name  
from products p left join brands b 
on p.brand_id = b.brand_id 
where p.list_price > 1000;

-- câu 2
select c.customer_id ,c.first_name ,c.last_name ,o.order_id ,o.order_status 
from customers c left join orders o
on c.customer_id =o.customer_id 
where o.order_status = 4;

-- câu 3
select s2.first_name employee_first_name, 
s2.last_name employee_last_name, s2.email employee_email,
s1.first_name  manager_first_name, s1.last_name manager_last_name, s1.email manager_email 
from staffs s1 right join staffs s2
on s1.staff_id = s2.manager_id;


-- câu 4
select p.product_name , b.brand_name  
from products p left join brands b 
on p.brand_id = b.brand_id; 

-- câu 5
select p.product_name ,p.model_year  ,b.brand_name  
from products p left join brands b 
on p.brand_id = b.brand_id
where p.model_year <= 2016;

-- câu 6
select order_id, product_name, quantity
from order_items oi left join products p 
on oi.product_id = p.product_id;

-- câu 7
select p.product_name ,c.category_name 
from products p left join categories c 
on p.category_id =c.category_id
where c.category_name ='Mountain Bikes';

-- câu 8
select product_name, list_price, category_name, brand_name
from products p left join categories c 
on p.category_id = c.category_id
left join brands b 
on p.brand_id =b.brand_id
where p.list_price > 500 and c.category_name = 'Electric Bikes';

-- câu 9
select c.customer_id, c.first_name, c.last_name, o.order_id,o.shipped_date
from customers c left join orders o
on c.customer_id = o.customer_id
and o.shipped_date is null;

-- câu 10
select store_name, count(order_id) order_count
from stores s left join orders o 
on s.store_id = o.store_id 
group by s.store_id, s.store_name ;

-- câu 11
select o.order_id ,s2.first_name ,s2.last_name , o.order_date 
from orders o 
left join stores s 
on o.store_id = s.store_id
inner join staffs s2 on s2.staff_id =o.staff_id
where s.store_id = 1
order by o.order_id desc;

-- câu 12
select CONCAT(first_name, ' ',last_name) as customer_name, order_id, order_date
from orders o left join customers c 
on c.customer_id = o.customer_id
where YEAR(order_date) = 2016
order by o.order_date desc;

-- câu 13
select first_name, last_name, order_id, order_date
from staffs s left join orders o 
on s.staff_id = o.staff_id
where o.order_date >= DATE_SUB(CURDATE(), interval 12 month)
;
