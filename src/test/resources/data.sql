-- product
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Harry Potter and the Philosopher Stone', 'E_BOOK', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.hpfl.net%2Fnews%2Farticle%2F2391&psig=AOvVaw1jzvI7LbwV9J0ElZZAdTr3&ust=1738404458907000&source=images&cd=vfe&opi=89978449&ved=2ahUKEwi6zaGz25-LAxXrX_UHHU3vJ7EQjRx6BAgAEBk', 300, 10, null, '2022-03-01 02:41:28', '2025-01-31 21:36:17');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Harry Potter and the Chamber of Secrets', 'E_BOOK', 'https://media.harrypotterfanzone.com/chamber-of-secrets-ebook-cover.jpg', 280, 12, null, '2025-01-31 18:24:55', '2025-01-31 21:38:12');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Harry Potter and the Prisoner of Azkaban', 'E_BOOK', 'https://media.harrypotterfanzone.com/prisoner-of-azkaban-ebook-cover.jpg', 290, 15, null, '2025-01-31 21:39:18', '2025-01-31 21:39:18');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Harry Potter and the Goblet of Fire', 'E_BOOK', 'https://cdn.club.be/product/9781781105672/front-medium-204724580.jpg', 270, 17, null, '2025-01-31 21:40:18', '2025-01-31 21:40:18');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Harry Potter and the Order of the Phoenix', 'E_BOOK', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-1YJi7UKRy8Rx4G4Ac-BHgIhEjuqEmCrfzg&s', 308, 6, null, '2025-01-31 21:40:59', '2025-01-31 21:40:59');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Harry Potter and the Half-Blood Prince', 'E_BOOK', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTGK8ftaGUIN6HXYvoGiCMd3McKbqwG9T7vBw&s', 310, 3, null, '2025-01-31 21:42:01', '2025-01-31 21:42:01');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Harry Potter and the Deathly Hallows', 'E_BOOK', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQatSbxdepo61SM_Sy5oZ2cmqOo9r78zAUU6g&s', 305, 8, null, '2025-01-31 21:43:16', '2025-01-31 21:43:16');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Tesla', 'CAR', 'https://cdn.pixabay.com/photo/2021/01/15/16/49/tesla-5919764_1280.jpg', 450000, 5, '世界最暢銷的充電式汽車', '2022-03-21 23:30:00', '2022-03-21 23:30:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Toyota', 'CAR', 'https://cdn.pixabay.com/photo/2014/05/18/19/13/toyota-347288_1280.jpg', 100000, 5, null, '2022-03-20 09:20:00', '2022-03-20 09:20:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('BMW', 'CAR', 'https://cdn.pixabay.com/photo/2018/02/21/03/15/bmw-m4-3169357_1280.jpg', 500000, 3, '渦輪增壓，直列4缸，DOHC雙凸輪軸，16氣門', '2022-03-20 12:30:00', '2022-03-20 12:30:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Benz', 'CAR', 'https://cdn.pixabay.com/photo/2017/03/27/14/56/auto-2179220_1280.jpg', 600000, 2, null, '2022-03-21 20:10:00', '2022-03-22 10:50:00');

-- user
INSERT INTO `user` (email, password, created_date, last_modified_date) VALUES ('user1@gmail.com', '202cb962ac59075b964b07152d234b70', '2022-06-30 10:30:00', '2022-06-30 10:30:00');
INSERT INTO `user` (email, password, created_date, last_modified_date) VALUES ('user2@gmail.com', '202cb962ac59075b964b07152d234b70', '2022-06-30 10:40:00', '2022-06-30 10:40:00');

-- order, order_item
INSERT INTO `order` (user_id, total_cost, created_date, last_modified_date) VALUES (1, 500690, '2022-06-30 11:10:00', '2022-06-30 11:10:00');
INSERT INTO order_item (order_id, product_id, quantity, cost) VALUES (1, 1, 3, 90);
INSERT INTO order_item (order_id, product_id, quantity, cost) VALUES (1, 2, 2, 600);
INSERT INTO order_item (order_id, product_id, quantity, cost) VALUES (1, 5, 1, 500000);

INSERT INTO `order` (user_id, total_cost, created_date, last_modified_date) VALUES (1, 100000, '2022-06-30 12:03:00', '2022-06-30 12:03:00');
INSERT INTO order_item (order_id, product_id, quantity, cost) VALUES (2, 4, 1, 100000);
