DROP TABLE IF EXISTS customer;

CREATE TABLE IF NOT EXISTS customer
(
    id         INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);