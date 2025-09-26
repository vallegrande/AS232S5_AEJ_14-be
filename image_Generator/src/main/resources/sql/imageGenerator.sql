CREATE TABLE IF NOT EXISTS image_generator_requests (
    id SERIAL PRIMARY KEY NOT NULL,
    prompt VARCHAR(500) NOT NULL,
    style_id INT NOT NULL,
    size VARCHAR(20) NOT NULL,
    creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date TIMESTAMP
);
