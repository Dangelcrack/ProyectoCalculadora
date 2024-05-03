CREATE DATABASE IF NOT EXISTS pokedex;
USE pokedex;
CREATE TABLE IF NOT EXISTS Pokemon (
    PokemonName VARCHAR(50) PRIMARY KEY NOT NULL,
    FirstType VARCHAR(20) NOT NULL,
    SecondType VARCHAR(20),
    Photo VARCHAR(50) NOT NULL,
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
    EV_Speed INT NOT NULL
);
CREATE TABLE IF NOT EXISTS Moves (
    Name VARCHAR(50) PRIMARY KEY NOT NULL,
    Type VARCHAR(20) NOT NULL,
    Category VARCHAR(20) NOT NULL,
    Power INT NOT NULL
);
CREATE TABLE IF NOT EXISTS Objects (
    Name VARCHAR(50) PRIMARY KEY NOT NULL,
    Description LONGTEXT
);
CREATE TABLE IF NOT EXISTS PokemonMoves (
    PokemonName VARCHAR(50),
    MoveName VARCHAR(50),
    PRIMARY KEY (PokemonName, MoveName),
    CONSTRAINT Fk_PokemonName_Moves FOREIGN KEY (PokemonName) REFERENCES Pokemon(PokemonName),
    CONSTRAINT Fk_MoveName_Pokemon FOREIGN KEY (MoveName) REFERENCES Moves(Name)

);
CREATE TABLE IF NOT EXISTS PokemonObjects (
    PokemonName VARCHAR(50),
    ObjectName VARCHAR(50),
    PRIMARY KEY (PokemonName, ObjectName),
    CONSTRAINT Fk_PokemonName_Objects FOREIGN KEY (PokemonName) REFERENCES Pokemon(PokemonName),
    CONSTRAINT Fk_ObjectName_Pokemon FOREIGN KEY (ObjectName) REFERENCES Objects(Name)
);
