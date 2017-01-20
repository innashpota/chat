CREATE TABLE users(
    user_id SERIAL PRIMARY KEY,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    login VARCHAR(20) NOT NULL,
    password VARCHAR(10) NOT NULL,
    CONSTRAINT users_login_uk UNIQUE (login)
);


CREATE TABLE message(
    message_id SERIAL PRIMARY KEY,
    author_id SERIAL NOT NULL,
    destination_id SERIAL NOT NULL,
    posted_date DATE NOT NULL,
    message_text TEXT NOT NULL,
    CONSTRAINT message_users_author_fk FOREIGN KEY (author_id) REFERENCES users (user_id) ON DELETE NO ACTION,
    CONSTRAINT message_users_dess_fk FOREIGN KEY (destination_id) REFERENCES users (user_id) ON DELETE NO ACTION
);