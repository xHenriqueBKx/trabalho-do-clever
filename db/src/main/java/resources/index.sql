CREATE TYPE GROUP_VEHICLE   AS ENUM('BASICO', 'PADRAO', 'PREMIUM');
CREATE TYPE STATUS_LOCATION AS ENUM('DISPONIVEL', 'LOCADO', 'RESERVADO', 'INDISPONIVEL', 'INDISPONIVEL');
CREATE TYPE STATUS_RENT AS ENUM('ACTIVE', 'CANCELED', 'NO_SHOW', 'EFFECTED', 'CONCLUDED');



CREATE TABLE Users (
    CPF                 CHAR(11)            PRIMARY KEY,
    Name                VARCHAR(255)        NOT NULL,
    BirthDate           CHAR(8)             NOT NULL,
    Email               VARCHAR(255)        NOT NULL UNIQUE,
    MobileNumber        VARCHAR(11)         NOT NULL UNIQUE
);

CREATE INDEX indexCPF ON users USING HASH (CPF);


CREATE TABLE Vehicle (
    LicensePlate        CHAR(7)             PRIMARY KEY,
    Builder             VARCHAR(255)        NOT NULL,
    Model               VARCHAR(255)        NOT NULL,
    Colour              VARCHAR(255)        NOT NULL,
    YearFabrication     CHAR(4)             NOT NULL,
    GroupVehicle        GROUP_VEHICLE       DEFAULT 'BASICO',
    Status              STATUS_LOCATION     DEFAULT 'DISPONIVEL'
);



CREATE TABLE Registry (
    Id                  SERIAL              PRIMARY KEY,
    CPF                 CHAR(11)            NOT NULL REFERENCES Users(CPF),
    LicensePlate        CHAR(7)             NOT NULL REFERENCES Vehicle(LicensePlate),
    TakeDate            CHAR(8)             NOT NULL,
    ReturnDate          CHAR(8)             NOT NULL,
    RentValue           INT                 NOT NULL,
    RentStatus          STATUS_RENT         NOT NULL
)