CREATE TABLE IF NOT EXISTS purchase (
    id SERIAL PRIMARY KEY,
    customer_id INTEGER NOT NULL,
    duck_id INTEGER NOT NULL,
    price NUMERIC(8) NOT NULL,

    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (duck_id) REFERENCES duck(id)
);