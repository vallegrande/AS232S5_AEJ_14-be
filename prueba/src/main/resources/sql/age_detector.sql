CREATE TABLE IF NOT EXISTS age_detector (
    id SERIAL PRIMARY KEY NOT NULL,
    image_url VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    update_date TIMESTAMP
);