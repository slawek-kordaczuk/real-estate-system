-- DROP TYPE IF EXISTS estate_type;
-- CREATE TYPE estate_type AS ENUM ('DETACHED_HOUSE', 'FLAT', 'SEMI_DETACHED_HOUSE', 'TERRACED_HOUSE');

DROP TABLE IF EXISTS region CASCADE;

CREATE TABLE region
(
    id          INT PRIMARY KEY,
    region_code VARCHAR(16)  NOT NULL,
    description VARCHAR(500) NOT NULL
);

DROP TABLE IF EXISTS estate CASCADE;

CREATE TABLE estate
(
    id           UUID PRIMARY KEY,
    region_code  VARCHAR(16)    NOT NULL,
    price        NUMERIC(10, 2) NOT NULL,
    type         VARCHAR(30)    NOT NULL,
    area         FLOAT          NOT NULL,
    rooms        INT            NOT NULL,
    description  VARCHAR(500)   NOT NULL,
    created_date TIMESTAMP      NOT NULL
);

CREATE UNIQUE INDEX "estate_index" ON estate (id, region_code);
