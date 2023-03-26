CREATE DATABASE IF NOT EXISTS weatherDB
    COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS testDB
    COLLATE utf8mb4_unicode_ci;

USE weatherDB;

DROP TABLE IF EXISTS weather;

CREATE TABLE weather
(
    id             BIGINT(20)  NOT NULL AUTO_INCREMENT,
    location       VARCHAR(50) NULL,
    temperature    FLOAT NULL,
    wind_speed     FLOAT NULL,
    pressure       INT NULL,
    humidity       INT NULL,
    condition_text VARCHAR(100) NULL,
    `date`         DATETIME NULL,
    PRIMARY KEY (id)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;
