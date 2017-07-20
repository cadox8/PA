CREATE TABLE IF NOT EXISTS `pa_datos` (
  `id`          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(32)      NOT NULL DEFAULT '',
  `uuid`        VARCHAR(36)      NOT NULL,
  `timeJoin`    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `grupo`       INT(2) UNSIGNED  NOT NULL DEFAULT '0',
  `god`         BIT(1)           NOT NULL DEFAULT b'0',
  `lastConnect` TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `coins`       INT(11)          NOT NULL DEFAULT '0',
  `ip`          VARCHAR(45)      NOT NULL DEFAULT '127.0.0.0',
  `nick`        VARCHAR(32)      NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `pa_antium` (
  `id`    INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `uuid`  VARCHAR(36)      NOT NULL,
  `name`  VARCHAR(32)      NOT NULL DEFAULT '',
  `pass`  VARCHAR(32)      NOT NULL DEFAULT '',
  `email` VARCHAR(32)      NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;