DROP TABLE IF EXISTS city CASCADE;
DROP TABLE IF EXISTS country CASCADE;
DROP TABLE IF EXISTS countrylanguage CASCADE;

-- your code here which creates the tables

CREATE TABLE city
(
    id          INTEGER PRIMARY KEY NOT NULL UNIQUE,
    name        VARCHAR(255)        NOT NULL,
    countrycode VARCHAR(3)          NOT NULL,
    orig_name   VARCHAR(255)        NOT NULL,
    population  INTEGER             NOT NULL
);

CREATE TABLE country
(
    code                 VARCHAR(3) PRIMARY KEY NOT NULL UNIQUE,
    name                 VARCHAR(255)           NOT NULL,
    continent            VARCHAR(255)           NOT NULL,
    region               VARCHAR(255)           NOT NULL,
    surfacearea          decimal                NOT NULL,
    independence_year    int8,
    first_numeric_value  decimal                NOT NULL,
    second_numeric_value decimal,
    third_numeric_value  decimal                NOT NULL,
    fourth_numeric_value decimal,
    original_name        VARCHAR(255)           NOT NULL,
    governmentform       VARCHAR(255)           NOT NULL,
    ruler                VARCHAR(255),
    capital              INTEGER references city (id),
    national_currency    VARCHAR(30)            NOT NULL,
    CONSTRAINT continent_exists CHECK ( continent = ANY (ARRAY ['Antarctica', 'Asia', 'Europe', 'North America', 'Africa', 'South America', 'Oceania']))
);

CREATE TABLE countrylanguage
(
    countrycode VARCHAR(3) references country (code) NOT NULL,
    language    VARCHAR(255)                         NOT NULL,
    isOfficial  BOOLEAN                              NOT NULL,
    percentage  real                                 NOT NULL,
    UNIQUE (countrycode, language)
);

TRUNCATE TABLE city CASCADE;
TRUNCATE TABLE country CASCADE;
TRUNCATE TABLE countrylanguage CASCADE;

-- COPY countrylanguage FROM
--     '/mnt/7d45c543-fc06-4310-b70a-2a9aa2e43a54/Projects/codecool/java/word-sql/src/test/resources/countrylanguage_data.txt';


EXPLAIN SELECT * FROM city
WHERE name = 'Tallahassee';

CREATE INDEX city_in_a_snow_globe_idx ON city USING btree(name);


EXPLAIN SELECT * FROM city
        WHERE name = 'Tallahassee';


DROP INDEX city_in_a_snow_globe_idx;

