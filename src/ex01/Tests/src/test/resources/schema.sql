CREATE SCHEMA IF NOT EXISTS shop;

CREATE TABLE IF NOT EXISTS shop.prices (
    identifier INTEGER,
    name VARCHAR(20),
    price INTEGER
)