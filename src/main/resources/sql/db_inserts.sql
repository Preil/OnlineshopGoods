-- ALL INSERT's only into clean DB!

insert into customers (login, password, name, address, phone, email, credit_card) values 
	('admin', 'admin', 'Administrator', 'City', '+854125487587', 'admin@onlineshop.com', '5555 5555 5555 5555'),
	('user', 'user', 'User', 'City1', '+987654321122', 'user@onlineshop.com', '6666 6666 6666 6666'),
	('user2', 'user2', 'User2', 'City2', '+123456789123', 'user2@onlineshop.com', '7777 7777 7777 7777');

insert into groups (group_name, group_parent_id) values 
	('Automotive', 0),
	('Beauty & Health', 0),
	('Books', 0),
	('Audio Books', 3),
	('Kindle books', 3),
	('Textbooks', 3),
	('Electronics', 0),
	('Computers', 0),
	('TV & Video', 7),
	('Camera, Photo & Video', 7),
	('Video Games', 7),
	('Laptops & Tablets', 8),
	('Desktops & Monitors', 8),
	('Computer Parts & Components', 8),
	('Software', 8),
	('Sports', 0),
	('Football', 16),
	('Golf', 16),
	('Athletic Clothing', 16),
	('Automotive Parts & Accessories', 1),
	('Tools & Equipment', 1),
	('Tires & Wheels', 1),
	('All Beauty', 2),
	('Luxury Beauty', 2),
	('Specialty Diets', 2),
	('Wine', 2);

insert into goods (goods_name, price, amount, group_id) values 
	('LG Electronics 65UF9500', 279799, 3, 9),
	('Acer H6510BD 3D Home Theater Projector', 58999, 5, 9),
	('Sylvania SDVD7027 7-Inch Portable DVD Player', 4999, 10, 9),
	('Olympus OM-D E-M10 Mark II', 79900, 20, 10),
	('DxO ONE 20.2MP Digital Connected Camera', 59900, 25, 10),
	('Sony 100mm f/2.8 Macro Lens', 74800, 4, 10),
	('NHL 16 - PlayStation 4', 5988, 40, 11),
	('Skylanders SuperChargers Starter Pack - Wii U', 7499, 20, 11),
	('Tom Clancy''s Rainbow Six Siege (Gold Edition) - Xbox One', 8999, 4, 11),
	('Toshiba Satellite C55-C5241 15.6', 49999, 7, 12),
	('PaintShop Pro X8 Ultimate', 9999, 1000, 15);
