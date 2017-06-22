-- CONNECT 'jdbc:derby://localhost:1527/st4db;user=test;password=test';

SELECT * FROM users;
SELECT * FROM orders;
SELECT * FROM productCharacteristic;

SELECT p.id, p.manufacturer_id, p.name, p.description, p.price, p.category_id, op.quantity 
FROM orders_products op join orders o on op.order_id=o.id join products p on op.product_id=p.id 
WHERE op.order_id=11;


SELECT DISTINCT op.product_id as productId, p.name as productName, u.first_name as userFirstName,u.last_name as userLastName, u.id as id
FROM orders o join orders_products op on o.id=op.order_id join users u on o.user_id=u.id join products p on op.product_id=p.id;

-- DISCONNECT;