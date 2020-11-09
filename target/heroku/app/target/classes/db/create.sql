--SET MODE PostgreSQL;
--
--CREATE TABLE IF NOT EXISTS departments (
--     id int PRIMARY KEY auto_increment,
--     departmentName VARCHAR,
--     description VARCHAR,
--     totalUsers int
--);
--
--CREATE TABLE IF NOT EXISTS users (
--     id int PRIMARY KEY auto_increment,
--     fullName VARCHAR,
--     position VARCHAR,
--     role VARCHAR,
--     departmentId int
--);
--
--CREATE TABLE IF NOT EXISTS news (
--     id int PRIMARY KEY auto_increment,
--     title VARCHAR,
--     content VARCHAR,
--     newsType VARCHAR,
--     userId int,
--     departmentId int
--);


CREATE DATABASE orgapi;

\c orgapi;

CREATE TABLE departments (
     id SERIAL PRIMARY KEY,
     departmentName VARCHAR,
     description VARCHAR,
     totalUsers int
);

CREATE TABLE users (
     id SERIAL PRIMARY KEY,
     fullName VARCHAR,
     position VARCHAR,
     role VARCHAR,
     departmentId int
);

CREATE TABLE news (
     id SERIAL PRIMARY KEY,
     title VARCHAR,
     content VARCHAR,
     newsType VARCHAR,
     userId int,
     departmentId int
);

CREATE DATABASE orgapitest WITH TEMPLATE orgapi;