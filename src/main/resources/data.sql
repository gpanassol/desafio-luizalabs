INSERT INTO product (id, price, url, marca, title, review_score) VALUES (1, 1599.99, 'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ_zRJioBh7-wXdtLrC4petOj-tYiJxtObUIBJ2vk639vof8-TSgUpGexRLZD7dzDGMQteicOLb&usqp=CAc', 'LG', 'LG SMART TV PRO', 4.0);
INSERT INTO client (id, email, name) VALUES (1, 'gpanassol@gmail.com', 'Gabriel Panassol da Fonseca');
INSERT INTO product_client (id, client_id, product_id) VALUES (1, 1, 1);