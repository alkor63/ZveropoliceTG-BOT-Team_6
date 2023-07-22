CREATE TABLE correspondence_with_owner
(
    id                BIGSERIAL PRIMARY KEY,
    chat_id           BIGINT    NOT NULL,
    message           TEXT      NOT NULL,

    message_date_time TIMESTAMP NOT NULL

);