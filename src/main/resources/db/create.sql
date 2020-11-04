SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS departments (
     id int PRIMARY KEY auto_increment,
     departmentName VARCHAR,
     description VARCHAR,
     totalUsers int
);

CREATE TABLE IF NOT EXISTS users (
     id int PRIMARY KEY auto_increment,
     fullName VARCHAR,
     position VARCHAR,
     role VARCHAR,
     departmentId int
);

CREATE TABLE IF NOT EXISTS news (
     id int PRIMARY KEY auto_increment,
     title VARCHAR,
     content VARCHAR,
     newsType VARCHAR,
     userId int,
     departmentId int
);