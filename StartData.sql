INSERT INTO `meli-frescos`.warehouse
(id, name)
VALUES(1, "Warehouse 1");

INSERT INTO `meli-frescos`.agent
(agent_id, name, fk_user_id)
VALUES(1, "Pedro", 1);

INSERT INTO `meli-frescos`.category
(id, maximum_temperature, minimum_temperature, name)
VALUES(1, 5, -10, "FF"),
VALUES(1, 15, 0, "FS"),;

INSERT INTO `meli-frescos`.`section`
(id, capacity, name, category_id, warehouse)
VALUES(1, 10, "Section 1", 1, 1),
VALUES(2, 10, "Section 1", 2, 1);

INSERT INTO `meli-frescos`.seller
(id, name)
VALUES(1, "Seller 1");

INSERT INTO `meli-frescos`.product
(id, name, price, volume, category_id, seller_id)
VALUES(1, "Frango", 20, 2, 1, 1),
VALUES(1, "Alface", 10, 2, 2, 1);

INSERT INTO `meli-frescos`.buyer
(email, name, status, fk_user_id)
VALUES("buyer@gmail.com", "buyer", "ATIVADO", 3);

INSERT INTO `meli-frescos`.purchase_order
(`date`, status, buyer_id)
VALUES("2022-05-14", "ABERTO", 1)
VALUES("2022-05-14", "FINALIZADO", 1);

INSERT INTO `meli-frescos`.cart_product
(quantity, product_id, purchase_order_id)
VALUES(2, 1, 1)
VALUES(3, 2, 2);

INSERT INTO `meli-frescos`.inbound_order
(order_date, agent_id, section_id)
VALUES("2022-05-01", 1, 1)
VALUES("2022-05-01", 1, 2);

INSERT INTO `meli-frescos`.batch_stock
(batch_number, current_quantity, current_temperature, due_date, initial_quantity, manufacturing_date, manufacturing_time, minimum_temperature, order_number, product_id)
VALUES(1, 10, 0, "2022-06-20", 15, "2022-04-10", '2022-04-10 10:00', -10, 1, 1)
VALUES(2, 10, 20, "2022-06-20", 15, "2022-04-10", '2022-04-10 10:00', 18, 2, 2);


