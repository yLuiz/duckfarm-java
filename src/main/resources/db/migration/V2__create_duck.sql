CREATE TABLE IF NOT EXISTS duck (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price NUMERIC(8, 2) NOT NULL,
    mother_id INTEGER,
    customer_id INTEGER,
    FOREIGN KEY (mother_id) REFERENCES duck(id),
    FOREIGN KEY (customer_id) REFERENCES customer(id)

);