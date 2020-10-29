DROP TABLE Books1;
CREATE TABLE Books1 (bookId MEDIUMINT NOT NULL AUTO_INCREMENT, name VARCHAR(30) NOT NULL, price DOUBLE, PRIMARY KEY (bookId));
INSERT INTO Books1 (name, price) VALUES ('Inferno', 45.0);
INSERT INTO Books1 (name, price) VALUES ('Harry Potter', 45.5);
INSERT INTO Books1 (name, price) VALUES ('It', 25.0);
INSERT INTO Books1 (name, price) VALUES ('Spartacus', 55.0);
INSERT INTO Books1 (name, price) VALUES ('Green mile', 20.6);
INSERT INTO Books1 (name, price) VALUES ('Solomon key', 5.0);
UPDATE Books1 SET price = price + 5 WHERE price < 30;