CREATE TABLE IF NOT EXISTS `pa_datos` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '',
  `uuid` varchar(36) NOT NULL,
  `timeJoin` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `grupo` int(2) unsigned NOT NULL DEFAULT '0',
  `god` bit(1) NOT NULL DEFAULT b'0',
  `lastConnect` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `coins` int(11) NOT NULL DEFAULT '0',
  `ip` varchar(45) NOT NULL DEFAULT '127.0.0.0',
  `nick` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `pa_antium` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `uuid` varchar(36) NOT NULL,
  `name` varchar(32) NOT NULL DEFAULT '',
  `pass` varchar(32) NOT NULL DEFAULT '',
  `email` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;