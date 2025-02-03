CREATE TABLE IF NOT EXISTS product
(
    product_id         INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    product_name       varchar(128)  not null,
    category           varchar(32)   not null,
    image_url          varchar(256)  not null,
    price              int           not null,
    stock              int           not null,
    description        varchar(1024),
    created_date       timestamp     not null,
    last_modified_date timestamp     not null
);

CREATE TABLE IF NOT EXISTS `user`
(
    user_id            INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email              VARCHAR(256) NOT NULL UNIQUE,
    password           VARCHAR(256) NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
);


