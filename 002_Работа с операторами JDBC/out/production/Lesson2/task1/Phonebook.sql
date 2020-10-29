DROP TABLE Phonebook;
CREATE TABLE Phonebook (IdPhoneBook MEDIUMINT AUTO_INCREMENT, LastName VARCHAR(30), Phone BIGINT, PRIMARY KEY (IdPhoneBook));
INSERT INTO Phonebook (LastName, Phone) VALUES ('Иванов', 9268713311);
INSERT INTO Phonebook (LastName, Phone) VALUES ('Сидоров', 9161449589);
INSERT INTO Phonebook (LastName, Phone) VALUES ('Петров', 9027418877);
INSERT INTO Phonebook (LastName, Phone) VALUES ('Астафьев', 9091234567);
INSERT INTO Phonebook (LastName, Phone) VALUES ('Астахов', 9639874562);
INSERT INTO Phonebook (LastName, Phone) VALUES ('Тарасов', 9261786541);
