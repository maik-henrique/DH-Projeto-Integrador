INSERT INTO `meli-frescos`.warehouse
(id, name)
VALUES(1, 'Warehouse 1');

INSERT INTO `meli-frescos`.agent
(agent_id, name, password)
VALUES(1, 'Pedro', '123');

INSERT INTO `meli-frescos`.category
(id, maximum_temperature, minimum_temperature, name)
VALUES(1, 5, -10, 'FF');


INSERT INTO `meli-frescos`.`section`
(id, capacity, name, category, warehouse)
VALUES(1, 10, 'Section 1', 1, 1);

INSERT INTO `meli-frescos`.seller
(id, name)
VALUES(1, 'Seller 1');

INSERT INTO `meli-frescos`.product
(id, name, price, volume, category_id, seller_id)
VALUES(1, 'Frango', 20, 2, 1, 1);
