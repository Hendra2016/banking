-- Create databases

CREATE DATABASE customerdb;
CREATE DATABASE loandb;
CREATE DATABASE riskdb;
CREATE DATABASE slikdb;
CREATE DATABASE authdb;

USE authdb;

INSERT INTO users(username,password,role)
VALUES
('customer1','password','CUSTOMER'),
('analyst1','password','ANALYST'),
('admin1','password','ADMIN');