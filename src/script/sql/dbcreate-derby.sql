-- CONNECT 'jdbc:derby://localhost:1527/st4db;create=true;user=test;password=test';

DROP TABLE orders_products;
DROP TABLE orders;
DROP TABLE productcharacteristic;
DROP TABLE products;
DROP TABLE characteristics;
DROP TABLE manufacturers;
DROP TABLE categories;
DROP TABLE statuses;
DROP TABLE users;
DROP TABLE roles;
DROP TABLE availability;


CREATE TABLE categories(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(10) NOT NULL UNIQUE
);

INSERT INTO categories VALUES(1, 'Headphones'); -- наушники
INSERT INTO categories VALUES(2, 'DACs'); -- ÷јѕы
INSERT INTO categories VALUES(3, 'Amplifiers'); -- усилители
INSERT INTO categories VALUES(4, 'Players'); -- плееры
INSERT INTO categories VALUES(5, 'Speakers'); -- плееры


CREATE TABLE manufacturers(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(50) NOT NULL
);

INSERT INTO manufacturers VALUES(1, 'Sennheiser');
INSERT INTO manufacturers VALUES(2, 'Denon');
INSERT INTO manufacturers VALUES(3, 'Ibasso');
INSERT INTO manufacturers VALUES(4, 'Sony');
INSERT INTO manufacturers VALUES(5, 'Hifiman');
INSERT INTO manufacturers VALUES(6, 'Audio-technica');
INSERT INTO manufacturers VALUES(7, 'Bowers&Wilkins');
INSERT INTO manufacturers VALUES(8, 'Bangs&Olufsen');
INSERT INTO manufacturers VALUES(9, 'KEF');
INSERT INTO manufacturers VALUES(10, 'Klipsch');
INSERT INTO manufacturers VALUES(11, 'Marantz');


CREATE TABLE availability(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(50) NOT NULL
);

INSERT INTO availability VALUES(0, 'Available');
INSERT INTO availability VALUES(1, 'On_request');
INSERT INTO availability VALUES(2, 'Not_available');

CREATE TABLE products(
	id INTEGER NOT NULL generated always AS identity PRIMARY KEY,
	manufacturer_id INTEGER NOT NULL REFERENCES manufacturers(id),
	name VARCHAR(50) NOT NULL,
	Description VARCHAR(500),
	price INTEGER NOT NULL,
	availability_id INTEGER NOT NULL REFERENCES availability(id),
	category_id INTEGER NOT NULL REFERENCES categories(id),
	img VARCHAR(50) 
);

-- наушники
INSERT INTO products VALUES(DEFAULT, 1,'Momentum', '', 400,0,1,'momentum_brown.jpg');
INSERT INTO products VALUES(DEFAULT, 1,'IE6', '', 160,0,1,'Sennheiser_IE6.jpg');
INSERT INTO products VALUES(DEFAULT, 1,'MM200', '', 130,1,1,'Sennheiser_MM200.jpg');
INSERT INTO products VALUES(DEFAULT, 2,'D600', '', 500,2,1,'denonD600.jpg');
INSERT INTO products VALUES(DEFAULT, 2,'D7100', '', 1000,0,1,'DenonD7100.jpg');

-- плееры
INSERT INTO products VALUES(DEFAULT, 3,'DX50', '', 200,0,4,'iBasso_DX50.jpg');
INSERT INTO products VALUES(DEFAULT, 3,'DX90', '', 200,0,4,'iBasso-DX90.jpg');
INSERT INTO products VALUES(DEFAULT, 3,'DX100', '', 200,0,4,'ibasso_dx100.jpg');
INSERT INTO products VALUES(DEFAULT, 5,'HM901', '', 200,0,4,'Hifiman_hm901.jpg');
INSERT INTO products VALUES(DEFAULT, 5,'HM901s', '', 200,0,4,'Hifiman HM901s.jpg');
INSERT INTO products VALUES(DEFAULT, 5,'HM601', '', 200,0,4,'Hifiman_HM601.jpg');


-- колонки
INSERT INTO products VALUES(DEFAULT, 7,'685', 'B&W 685 Ц пассивна€ акустическа€ система фазоинверторного типа, рассчитанна€ на полочную установку. ƒанна€ модель относитс€ к попул€рной 600-й серии, представл€ет собой комплект из двух компактных колонок, характеризуетс€ высоким качеством сборки. “ехническа€ составл€юща€ B&W 685 представлена 25-мм твитером с алюминиевым куполом и неодимовым магнитом, 165-мм Ќ„-динамиком с кевларовым диффузором, фирменным фазоинверторным портом Flowport.', 600,0,5,'BW_685.jpg');
INSERT INTO products VALUES(DEFAULT, 9,'Q100', '', 600,0,5,'kef_q100.jpg');

-- усилители
INSERT INTO products VALUES(DEFAULT, 2,'PMA-520AE', '', 250,0,3,'denon_PMA-520AE.jpg');
INSERT INTO products VALUES(DEFAULT, 11,'PM-15S2', '', 2200,0,3,'marantz_PM-15S2.jpg');

-- ÷јѕы
INSERT INTO products VALUES(DEFAULT, 11,'HD-DAC1', '', 900,0,2,'marantz_HD-DAC1.jpg');

----------------------------------------------------------------
CREATE TABLE characteristics(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(20) NOT NULL,
	description VARCHAR(100)
);

INSERT INTO characteristics VALUES(1, 'Power','');
INSERT INTO characteristics VALUES(2, 'Weight','');


CREATE TABLE productcharacteristic(
id INTEGER NOT NULL generated always AS identity PRIMARY KEY,
product_id INTEGER NOT NULL REFERENCES products(id),
characteristic_id INTEGER NOT NULL REFERENCES characteristics(id),
value VARCHAR(20)
);

INSERT INTO productcharacteristic VALUES(DEFAULT,1, 1,'100');
INSERT INTO productcharacteristic VALUES(DEFAULT,1, 2,'200');


CREATE TABLE roles(

	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(10) NOT NULL UNIQUE
);
INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'client');
INSERT INTO roles VALUES(2, 'banned');


CREATE TABLE users(

	id INTEGER NOT NULL generated always AS identity PRIMARY KEY,
	login VARCHAR(10) NOT NULL UNIQUE,
	password VARCHAR(10) NOT NULL,
	first_name VARCHAR(20),
	last_name VARCHAR(20),
	email VARCHAR(30),
	phone VARCHAR(13),
	city VARCHAR(20),
	address VARCHAR(20),
	role_id INTEGER NOT NULL REFERENCES roles(id) 
		ON DELETE CASCADE 
		ON UPDATE RESTRICT
);

INSERT INTO users VALUES(DEFAULT, 'admin', 'admin', 'Ivan', NULL,NULL,NULL,NULL,NULL, 0);
INSERT INTO users VALUES(DEFAULT, 'client', 'client', 'Petr', NULL,NULL,NULL,NULL,NULL, 1);
INSERT INTO users VALUES(DEFAULT, 'client1', 'client1', 'Ivan', NULL,NULL,NULL,NULL,NULL, 1);
INSERT INTO users VALUES(DEFAULT, 'client2', 'client2', 'Vlad', NULL,NULL,NULL,NULL,NULL, 1);
INSERT INTO users VALUES(DEFAULT, 'петров', 'петров', '»ван', NULL,NULL,NULL,NULL,NULL, 1);

----------------------------------------------------------------
CREATE TABLE statuses(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(10) NOT NULL UNIQUE
);

----------------------------------------------------------------
INSERT INTO statuses VALUES(0, 'opened');
INSERT INTO statuses VALUES(1, 'confirmed');
INSERT INTO statuses VALUES(2, 'paid');
INSERT INTO statuses VALUES(3, 'closed');

----------------------------------------------------------------
CREATE TABLE orders(
	id INTEGER NOT NULL generated always AS identity PRIMARY KEY,
	bill INTEGER NOT NULL ,
	phone VARCHAR(13) NOT NULL,
	email VARCHAR(30) NOT NULL,
	city VARCHAR(20) NOT NULL,
	address VARCHAR(50) NOT NULL,
	user_id INTEGER NOT NULL REFERENCES users(id),
	status_id INTEGER NOT NULL REFERENCES statuses(id),
	date DATE
);

INSERT INTO orders VALUES(DEFAULT, 100,'0987654321','razor_vlad@mail.ru','Kharkiv','address sdc 234', 2, 0,'2013-10-20');
INSERT INTO orders VALUES(DEFAULT, 1000,'0123456789','razorvlad1992@gmail.com','Lviv','address asd 32', 2, 3,'2014-10-20');
INSERT INTO orders VALUES(DEFAULT, 1000,'0123456789','razorvlad1992@gmail.com','Lviv','address asd 32', 3, 3,'2015-10-20');
INSERT INTO orders VALUES(DEFAULT, 1000,'0123456789','razorvlad1992@gmail.com','Lviv','address asd 32', 4, 3,'2013-10-20');
INSERT INTO orders VALUES(DEFAULT, 1000,'0123456789','razorvlad1992@gmail.com','Lviv','address asd 32', 5, 3,'2012-10-20');

----------------------------------------------------------------

CREATE TABLE orders_products(
	product_id INTEGER NOT NULL REFERENCES products(id),
	order_id INTEGER NOT NULL REFERENCES orders(id),
	quantity INTEGER NOT NULL
);
INSERT INTO orders_products VALUES(1,1,1);
INSERT INTO orders_products VALUES(2,1,2);
INSERT INTO orders_products VALUES(3,1,2);
INSERT INTO orders_products VALUES(4,2,2);
INSERT INTO orders_products VALUES(5,2,2);
INSERT INTO orders_products VALUES(6,3,2);
INSERT INTO orders_products VALUES(7,3,2);
INSERT INTO orders_products VALUES(8,3,2);
INSERT INTO orders_products VALUES(9,3,2);
INSERT INTO orders_products VALUES(8,4,2);

SELECT * FROM orders_products;
SELECT * FROM products;
SELECT * FROM orders;
SELECT * FROM categories;
SELECT * FROM statuses;
SELECT * FROM users;
SELECT * FROM roles;
SELECT * FROM characteristics;
SELECT * FROM productcharacteristic;
SELECT * FROM manufacturers;

-- DISCONNECT;