
-- lab1
update orders set customer_id =2 where order_id = 1;

-- câu 1
select order_id, c.customer_id , c.customer_name  
from customers c inner join orders o
on c.customer_id = o.customer_id;

-- câu 2
select c.customer_id, customer_name order_id
from customers c left join orders o
on o.customer_id  = c.customer_id;

-- câu 3
select e.employee_id, concat(e.last_name ,' ',e.first_name) as 'Employee Name',
e2.employee_id as 'supervisor ID' ,concat(e2.last_name ,' ', e2.first_name ) as 'Supervisor Name'
from employees e inner join employees e2 
on e.supervisor_id = e2.employee_id;

-- câu 4
select A.customer_id, A.customer_name, A.country 
from customers A inner join customers B
on A.customer_id <> B.customer_id
where A.country = B.country;

-- câu 5
select order_id, customer_name,
CONCAT(E.first_name ,' ',E.last_name ) EmployeeName,
O.order_date 
from employees E inner join orders O
on E.employee_id = O.employee_id
inner join customers C
on C.customer_id = O.customer_id;

-- lab2

