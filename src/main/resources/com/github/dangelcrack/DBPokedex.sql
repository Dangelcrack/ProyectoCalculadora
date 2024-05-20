CREATE DATABASE IF NOT EXISTS pokedex;
USE pokedex;
CREATE TABLE IF NOT EXISTS Objects (
    Name VARCHAR(50) PRIMARY KEY,
    Description LONGTEXT,
    Photo VARCHAR(255) NOT NULL,
    BoostType VARCHAR(20),
    BoostCategory VARCHAR(20),
    BoostAttack INT,
    BoostDefense INT,
    BoostSpAttack INT,
    BoostSpDefense INT,
    BoostSpeed INT
);
CREATE TABLE IF NOT EXISTS Pokemon (
    PokemonName VARCHAR(50) PRIMARY KEY NOT NULL,
    FirstType VARCHAR(20) NOT NULL,
    SecondType VARCHAR(20),
    Nature VARCHAR(20) NOT NULL,
    Photo VARCHAR(255) NOT NULL,
    LEVELCAP INT NOT NULL,
    HP INT NOT NULL,
    Attack INT NOT NULL,
    Defense INT NOT NULL,
    SpAttack INT NOT NULL,
    SpDefense INT NOT NULL,
    Speed INT NOT NULL,
    IV_HP INT NOT NULL,
    IV_Attack INT NOT NULL,
    IV_Defense INT NOT NULL,
    IV_SpAttack INT NOT NULL,
    IV_SpDefense INT NOT NULL,
    IV_Speed INT NOT NULL,
    EV_HP INT NOT NULL,
    EV_Attack INT NOT NULL,
    EV_Defense INT NOT NULL,
    EV_SpAttack INT NOT NULL,
    EV_SpDefense INT NOT NULL,
    EV_Speed INT NOT NULL,
    Object_Name VARCHAR(50) DEFAULT NULL,
    KEY Object_Name(Object_Name),
    CONSTRAINT FK_ObjectName FOREIGN KEY (Object_Name) REFERENCES Objects(Name) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Moves (
    Name VARCHAR(50) PRIMARY KEY NOT NULL,
    Type VARCHAR(20) NOT NULL,
    Category VARCHAR(20) NOT NULL,
    Power INT NOT NULL
);

CREATE TABLE IF NOT EXISTS PokemonMoves (
    PokemonName VARCHAR(50),
    MoveName VARCHAR(50),
    PRIMARY KEY (PokemonName, MoveName),
    CONSTRAINT Fk_PokemonName_Moves FOREIGN KEY (PokemonName) REFERENCES Pokemon(PokemonName),
    CONSTRAINT Fk_MoveName_Pokemon FOREIGN KEY (MoveName) REFERENCES Moves(Name)
);
