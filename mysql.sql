CREATE TABLE IF NOT EXISTS `pa_datos` (
  `id`          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(36)      NOT NULL DEFAULT '',
  `timeJoin`    TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `grupo`       INT(2) UNSIGNED  NOT NULL DEFAULT '0',
  `god`         INT(1)           NOT NULL DEFAULT '0',
  `lastConnect` TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `coins`       INT(11)          NOT NULL DEFAULT '0',
  `ip`          VARCHAR(45)      NOT NULL DEFAULT '127.0.0.0',
  `nick`        VARCHAR(32)      NOT NULL DEFAULT '',

  `maxPiso`     INT(11)          NOT NULL DEFAULT '0',
  `exp`         INT(11)          NOT NULL DEFAULT '0',
  `lvl`         INT(11)          NOT NULL DEFAULT '0',
  `zeny`        INT(11)          NOT NULL DEFAULT '0',
  `kills`       INT(11)          NOT NULL DEFAULT '0',
  `deaths`      INT(11)          NOT NULL DEFAULT '0',
  `kit`         INT(2)           NOT NULL DEFAULT '-1',
  `karma`       INT(100)         NOT NULL DEFAULT '100',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `pa_antium` (
  `id`    INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`  VARCHAR(32)      NOT NULL DEFAULT '',
  `pass`  VARCHAR(32)      NOT NULL DEFAULT '',
  `email` VARCHAR(32)      NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `pa_logros` (
  `id`    INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user`  VARCHAR(32)      NOT NULL DEFAULT '',
  `logro` INT(5)           NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `pa_homes` (
  `id`    INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `owner`  VARCHAR(32)     NOT NULL DEFAULT 'none',
  `loc`  VARCHAR(500)      NOT NULL DEFAULT '',
  `tpLoc` VARCHAR(500)     NOT NULL DEFAULT '',
  `area` VARCHAR(500)      NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
