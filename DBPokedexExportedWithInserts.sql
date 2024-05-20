-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         11.3.2-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para pokedex
DROP DATABASE IF EXISTS `pokedex`;
CREATE DATABASE IF NOT EXISTS `pokedex` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;
USE `pokedex`;

-- Volcando estructura para tabla pokedex.moves
DROP TABLE IF EXISTS `moves`;
CREATE TABLE IF NOT EXISTS `moves` (
  `Name` varchar(50) NOT NULL,
  `Type` varchar(20) NOT NULL,
  `Category` varchar(20) NOT NULL,
  `Power` int(11) NOT NULL,
  PRIMARY KEY (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla pokedex.moves: ~4 rows (aproximadamente)
REPLACE INTO `moves` (`Name`, `Type`, `Category`, `Power`) VALUES
	('Abocajarro', 'FIGHTING', 'PHYSICAL', 120),
	('Envite Ígneo', 'FIRE', 'PHYSICAL', 92),
	('Tajo Aéreo', 'FLYING', 'SPECIAL', 74),
	('Water', 'WATER', 'SPECIAL', 80);

-- Volcando estructura para tabla pokedex.objects
DROP TABLE IF EXISTS `objects`;
CREATE TABLE IF NOT EXISTS `objects` (
  `Name` varchar(50) NOT NULL,
  `Description` longtext DEFAULT NULL,
  `Photo` varchar(255) NOT NULL,
  `BoostType` varchar(20) DEFAULT NULL,
  `BoostCategory` varchar(20) DEFAULT NULL,
  `BoostAttack` int(11) DEFAULT NULL,
  `BoostDefense` int(11) DEFAULT NULL,
  `BoostSpAttack` int(11) DEFAULT NULL,
  `BoostSpDefense` int(11) DEFAULT NULL,
  `BoostSpeed` int(11) DEFAULT NULL,
  PRIMARY KEY (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla pokedex.objects: ~2 rows (aproximadamente)
REPLACE INTO `objects` (`Name`, `Description`, `Photo`, `BoostType`, `BoostCategory`, `BoostAttack`, `BoostDefense`, `BoostSpAttack`, `BoostSpDefense`, `BoostSpeed`) VALUES
	('Cinta fuerte', '', 'Cinta_fuerte_EP.png', NULL, 'PHYSICAL', 1, 0, 0, 0, 0),
	('Cinta fuerte 2', '', 'Cinta_fuerte_EP.png', 'NORMAL', NULL, 0, 0, 0, 0, 0);

-- Volcando estructura para tabla pokedex.pokemon
DROP TABLE IF EXISTS `pokemon`;
CREATE TABLE IF NOT EXISTS `pokemon` (
  `PokemonName` varchar(50) NOT NULL,
  `FirstType` varchar(20) NOT NULL,
  `SecondType` varchar(20) DEFAULT NULL,
  `Nature` varchar(20) NOT NULL,
  `Photo` varchar(255) NOT NULL,
  `LEVELCAP` int(11) NOT NULL,
  `HP` int(11) NOT NULL,
  `Attack` int(11) NOT NULL,
  `Defense` int(11) NOT NULL,
  `SpAttack` int(11) NOT NULL,
  `SpDefense` int(11) NOT NULL,
  `Speed` int(11) NOT NULL,
  `IV_HP` int(11) NOT NULL,
  `IV_Attack` int(11) NOT NULL,
  `IV_Defense` int(11) NOT NULL,
  `IV_SpAttack` int(11) NOT NULL,
  `IV_SpDefense` int(11) NOT NULL,
  `IV_Speed` int(11) NOT NULL,
  `EV_HP` int(11) NOT NULL,
  `EV_Attack` int(11) NOT NULL,
  `EV_Defense` int(11) NOT NULL,
  `EV_SpAttack` int(11) NOT NULL,
  `EV_SpDefense` int(11) NOT NULL,
  `EV_Speed` int(11) NOT NULL,
  `Object_Name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`PokemonName`),
  KEY `Object_Name` (`Object_Name`),
  CONSTRAINT `FK_ObjectName` FOREIGN KEY (`Object_Name`) REFERENCES `objects` (`Name`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla pokedex.pokemon: ~3 rows (aproximadamente)
REPLACE INTO `pokemon` (`PokemonName`, `FirstType`, `SecondType`, `Nature`, `Photo`, `LEVELCAP`, `HP`, `Attack`, `Defense`, `SpAttack`, `SpDefense`, `Speed`, `IV_HP`, `IV_Attack`, `IV_Defense`, `IV_SpAttack`, `IV_SpDefense`, `IV_Speed`, `EV_HP`, `EV_Attack`, `EV_Defense`, `EV_SpAttack`, `EV_SpDefense`, `EV_Speed`, `Object_Name`) VALUES
	('Chimchar', 'FIRE', NULL, 'ADAMANT', 'Chimchar.png', 50, 44, 58, 44, 58, 44, 61, 14, 30, 31, 30, 31, 31, 4, 56, 0, 216, 4, 188, 'Cinta fuerte 2'),
	('INFERNAPE', 'FIRE', 'FIGHTING', 'BRAVE', 'infernape.jpg', 50, 76, 104, 71, 104, 71, 108, 31, 31, 31, 31, 31, 31, 0, 64, 0, 252, 0, 192, NULL),
	('Rayquaza', 'DRAGON', 'FLYING', 'NAUGHTY', 'Rayquaza.png', 50, 104, 150, 90, 150, 90, 95, 31, 31, 31, 31, 31, 31, 252, 252, 0, 0, 0, 4, NULL);

-- Volcando estructura para tabla pokedex.pokemonmoves
DROP TABLE IF EXISTS `pokemonmoves`;
CREATE TABLE IF NOT EXISTS `pokemonmoves` (
  `PokemonName` varchar(50) NOT NULL,
  `MoveName` varchar(50) NOT NULL,
  PRIMARY KEY (`PokemonName`,`MoveName`),
  KEY `Fk_MoveName_Pokemon` (`MoveName`),
  CONSTRAINT `Fk_MoveName_Pokemon` FOREIGN KEY (`MoveName`) REFERENCES `moves` (`Name`),
  CONSTRAINT `Fk_PokemonName_Moves` FOREIGN KEY (`PokemonName`) REFERENCES `pokemon` (`PokemonName`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla pokedex.pokemonmoves: ~5 rows (aproximadamente)
REPLACE INTO `pokemonmoves` (`PokemonName`, `MoveName`) VALUES
	('INFERNAPE', 'Abocajarro'),
	('Chimchar', 'Envite Ígneo'),
	('INFERNAPE', 'Envite Ígneo'),
	('Rayquaza', 'Tajo Aéreo'),
	('Chimchar', 'Water');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
