DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS users;

CREATE TABLE users(
    user_id SERIAL PRIMARY KEY,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    login VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    CONSTRAINT users_login_uk UNIQUE (login)
);

CREATE TABLE message(
    message_id SERIAL PRIMARY KEY,
    author_id SERIAL NOT NULL,
    destination_id SERIAL NOT NULL,
    posted_date TIMESTAMPTZ NOT NULL,
    message_text TEXT NOT NULL,
    CONSTRAINT message_users_author_fk FOREIGN KEY (author_id) REFERENCES users (user_id) ON DELETE NO ACTION,
    CONSTRAINT message_users_dess_fk FOREIGN KEY (destination_id) REFERENCES users (user_id) ON DELETE NO ACTION
);

INSERT INTO users (first_name, last_name, login, password) VALUES
    ('Jora', 'Kornev', 'kornev', '1'),
    ('Ivan', 'Lebedev', 'lebedev', '1');

INSERT INTO message (author_id, destination_id, posted_date, message_text) VALUES
    (1, 2, '2017-04-06 13:35', 'Hello!'),
    (2, 1, '2017-04-06 13:36', 'Hi!  How are you?'),
    (1, 2, '2017-04-06 13:37', 'I am fine, thanks. And how are you?'),
    (2, 1, '2017-04-06 13:37', 'I am good.');