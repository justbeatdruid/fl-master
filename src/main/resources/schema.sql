CREATE DATABASE IF NOT EXISTS `federated_learning`;

use `federated_learning`;

CREATE TABLE IF NOT EXISTS `tb_federation`(
    `id` INT UNSIGNED AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `uuid` VARCHAR(40) NOT NULL,
    `created_at` DATE,
    `type` TINYINT,
    `description` TEXT,
    `guest` TEXT,
    `hosts` TEXT,
    `status` TINYINT,
    `data_format` TEXT,
    `algorithm_id` INT,
    `param` TEXT,
    PRIMARY KEY ( `id` )
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
