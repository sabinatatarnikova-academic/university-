CREATE TABLE user_token
(
    token_id     VARCHAR(255) PRIMARY KEY,
    token        VARCHAR(50),
    user_id      VARCHAR(255) REFERENCES users (user_id) ON DELETE SET NULL,
    created_time TIMESTAMP,
    expires_time TIMESTAMP
);