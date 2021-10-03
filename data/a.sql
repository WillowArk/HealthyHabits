DROP DATABASE Statefarm;
CREATE DATABASE Statefarm;
USE Statefarm;




CREATE TABLE Users (ID int NOT NULL AUTO_INCREMENT, username varchar(255) NOT NULL, DOB varchar(10), Constraint PK_User PRIMARY KEY (ID));

INSERT INTO Users (username, DOB) VALUES ('user1', '01/01/2000');
INSERT INTO Users (username, DOB) VALUES ('user2', '02/02/2000');




CREATE TABLE Steps (ID int NOT NULL AUTO_INCREMENT, ID_user int NOT NULL, date varchar(10) NOT NULL, nSteps int NOT NULL, CONSTRAINT PK_Step PRIMARY KEY (ID), CONSTRAINT FK_Step FOREIGN KEY (ID_user) REFERENCES Users(ID) );

INSERT INTO Steps (ID_user, date, nSteps) VALUES (1, '09/01/2021', 1);
INSERT INTO Steps (ID_user, date, nSteps) VALUES (1, '09/02/2021', 2);
INSERT INTO Steps (ID_user, date, nSteps) VALUES (1, '09/03/2021', 3);

INSERT INTO Steps (ID_user, date, nSteps) VALUES (2, '09/01/2021', 2);
INSERT INTO Steps (ID_user, date, nSteps) VALUES (2, '09/02/2021', 3);
INSERT INTO Steps (ID_user, date, nSteps) VALUES (2, '09/03/2021', 1);




CREATE TABLE Food (ID int NOT NULL AUTO_INCREMENT, ID_user int NOT NULL, date varchar(10) NOT NULL, rate int NOT NULL, CONSTRAINT PK_Food PRIMARY KEY (ID), CONSTRAINT FK_Food FOREIGN KEY (ID_user) REFERENCES Users(ID) );

INSERT INTO Food (ID_user, date, rate) VALUES (1, '09/01/2021', 1);
INSERT INTO Food (ID_user, date, rate) VALUES (1, '09/01/2021', 5);
INSERT INTO Food (ID_user, date, rate) VALUES (1, '09/01/2021', 10);
INSERT INTO Food (ID_user, date, rate) VALUES (1, '09/02/2021', 2);
INSERT INTO Food (ID_user, date, rate) VALUES (1, '09/02/2021', 3);
INSERT INTO Food (ID_user, date, rate) VALUES (1, '09/02/2021', 6);
INSERT INTO Food (ID_user, date, rate) VALUES (1, '09/03/2021', 4);
INSERT INTO Food (ID_user, date, rate) VALUES (1, '09/03/2021', 7);
INSERT INTO Food (ID_user, date, rate) VALUES (1, '09/03/2021', 8);

INSERT INTO Food (ID_user, date, rate) VALUES (2, '09/01/2021', 2);
INSERT INTO Food (ID_user, date, rate) VALUES (2, '09/01/2021', 4);
INSERT INTO Food (ID_user, date, rate) VALUES (2, '09/01/2021', 6);
INSERT INTO Food (ID_user, date, rate) VALUES (2, '09/02/2021', 1);
INSERT INTO Food (ID_user, date, rate) VALUES (2, '09/02/2021', 3);
INSERT INTO Food (ID_user, date, rate) VALUES (2, '09/02/2021', 9);
INSERT INTO Food (ID_user, date, rate) VALUES (2, '09/03/2021', 4);
INSERT INTO Food (ID_user, date, rate) VALUES (2, '09/03/2021', 10);
INSERT INTO Food (ID_user, date, rate) VALUES (2, '09/03/2021', 8);





CREATE TABLE MinutesInGym (ID int NOT NULL AUTO_INCREMENT, ID_user int NOT NULL, date varchar(10) NOT NULL, minutes int NOT NULL, CONSTRAINT PK_Gym PRIMARY KEY (ID), CONSTRAINT FK_Gym FOREIGN KEY (ID_user) REFERENCES Users(ID) );

INSERT INTO MinutesInGym (ID_user, date, minutes) VALUES (1, '09/01/2021', 45);
INSERT INTO MinutesInGym (ID_user, date, minutes) VALUES (1, '09/02/2021', 60);
INSERT INTO MinutesInGym (ID_user, date, minutes) VALUES (1, '09/03/2021', 30);

INSERT INTO MinutesInGym (ID_user, date, minutes) VALUES (2, '09/01/2021', 60);
INSERT INTO MinutesInGym (ID_user, date, minutes) VALUES (2, '09/02/2021', 45);
INSERT INTO MinutesInGym (ID_user, date, minutes) VALUES (2, '09/03/2021', 30);




CREATE TABLE HoursSleep (ID int NOT NULL AUTO_INCREMENT, ID_user int NOT NULL, date varchar(10) NOT NULL, hours int NOT NULL, CONSTRAINT PK_Sleep PRIMARY KEY (ID), CONSTRAINT FK_Sleep FOREIGN KEY (ID_user) REFERENCES Users(ID) );

INSERT INTO HoursSleep (ID_user, date, hours) VALUES (1, '09/01/2021', 7);
INSERT INTO HoursSleep (ID_user, date, hours) VALUES (1, '09/02/2021', 6);
INSERT INTO HoursSleep (ID_user, date, hours) VALUES (1, '09/03/2021', 8);

INSERT INTO HoursSleep (ID_user, date, hours) VALUES (2, '09/01/2021', 7);
INSERT INTO HoursSleep (ID_user, date, hours) VALUES (2, '09/02/2021', 8);
INSERT INTO HoursSleep (ID_user, date, hours) VALUES (2, '09/03/2021', 6);