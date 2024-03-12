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

INSERT INTO region VALUES (1, 'DLN_WROC_C', 'Dolnośląskie - Wrocław centrum');
INSERT INTO region VALUES (2, 'DLN_WROC_PC', 'Dolnośląskie - Wrocław poza centrum');
INSERT INTO region VALUES (3, 'DLN_POZA_WROC', 'Dolnośląskie - poza Wrocławiem');
INSERT INTO region VALUES (4, 'SL_POL', 'Śląskie - południe');
INSERT INTO region VALUES (5, 'SL_KATO', 'Śląskie - Katowice');
INSERT INTO region VALUES (6, 'SL_PN', 'Śląskie - północ');
INSERT INTO region VALUES (7, 'M_WAW_CE', 'Mazowieckie - Warszawa Centrum');
INSERT INTO region VALUES (8, 'M_WAW_W', 'Mazowieckie - Warszawa wschód');
INSERT INTO region VALUES (9, 'M_WAW_Z', 'Mazowieckie - Warszaawa - zachód');
INSERT INTO region VALUES (10, 'LUBL', 'Lubelskie - Lublin');
INSERT INTO region VALUES (11, 'LUBL_INNE', 'Lubelskie - poza Lublinem');
INSERT INTO region VALUES (12, 'ZPOM', 'Zachodniopomorskie');
INSERT INTO region VALUES (13, 'LUBSK', 'Lubuskie');

INSERT INTO estate VALUES('6d544a3e-60f7-478a-bacb-7d71203f3d67', 'SL_KATO', 666.99, 'FLAT', 50.6, 4, 'description', '2023-12-29');
INSERT INTO estate VALUES('f92f03f4-34e2-448f-80c6-6f2650f34013', 'SL_KATO', 544565.99, 'FLAT', 50.6, 4, 'description', '2023-12-29');
INSERT INTO estate VALUES('db621317-e22d-4002-ad99-1ec78fc271d0', 'SL_KATO', 99999.66, 'FLAT', 99.6, 5, 'description', '2023-12-29');
