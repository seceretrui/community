CREATE TABLE USER
(
    ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    NAME varchar(100),
    ACCOUNT_ID varchar(50),
    TOKEN varchar(36),
    GMT_CREATE bigint,
    GMT_MODIFIED bigint
);