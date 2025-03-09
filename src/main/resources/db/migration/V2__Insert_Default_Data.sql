INSERT INTO users (email, password) VALUES
('user1@example.com', '$2a$10$FdttG2Qjg/.7Tx16gWB0xuYfw8vdJeO9IOBrxriexWsnqGp.UkFdO'), -- Hash "password123"
('user2@example.com', '$2a$10$FdttG2Qjg/.7Tx16gWB0xuYfw8vdJeO9IOBrxriexWsnqGp.UkFdO'); -- Hash "password123"

INSERT INTO places (name, place_type) VALUES
('Pizza Palace', 'RESTAURANT'),
('Burger Heaven', 'RESTAURANT'),
('Tech Store', 'SHOP'),
('Book Haven', 'SHOP');

INSERT INTO feedbacks (title, comment, score, user_id, place_id) VALUES
('Awesome Pizza!', 'Loved the pizza.', 9, 1, 1),
('Best Burger in Town', 'Juicy and delicious.', 8, 2, 2),
('Great Selection of Gadgets', 'Loved their collection.', 10, 1, 3),
('Friendly Staff', 'The bookstore was cozy!', 7, 2, 4);
