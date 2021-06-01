--drop database if exists blogdb;
create  database if not exists blogdb;

--drom user null if exists 'bloguser'@'localhost';
create user if not exists 'bloguser'@'localhost' identified by 'justGo';
grant all privileges on blogdb.* to 'bloguser'@'localhost';
flush privileges ;