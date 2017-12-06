CREATE DATABASE `olimp` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `olimp`.`cidade` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(60) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

CREATE TABLE `olimp`.`estadio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `idcidade` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cidade_fk_idx` (`idcidade`),
  CONSTRAINT `cidade_fk` FOREIGN KEY (`idcidade`) REFERENCES `cidade` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

 
INSERT INTO `olimp`.`cidade` (`nome`) VALUES ('Rio de Janeiro');
INSERT INTO `olimp`.`cidade` (`nome`) VALUES ('Porto Alegre');

INSERT INTO `olimp`.`estadio` (`nome`, `idcidade`) VALUES ('Maracanã', '1');
INSERT INTO `olimp`.`estadio` (`nome`, `idcidade`) VALUES ('Engenhão', '1');
INSERT INTO `olimp`.`estadio` (`nome`, `idcidade`) VALUES ('Beira-Rio', '2');