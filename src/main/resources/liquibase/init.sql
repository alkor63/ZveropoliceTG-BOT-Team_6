CREATE TABLE pet_owners
(
    owner_id           BIGSERIAL PRIMARY KEY,
    first_name     CHAR NOT NULL,
    last_name  CHAR NOT NULL,
    owner_phone        CHAR NOT NULL,
    adoption_start_date CHAR NOT NULL

);
