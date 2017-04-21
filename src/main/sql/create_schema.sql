CREATE DATABASE `car_seller` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `attributes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `sec_roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `sec_users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '0',
  `first_name` varchar(80) NOT NULL,
  `last_name` varchar(80) NOT NULL,
  `login` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `phone_number` varchar(30) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_cfu0ko0i9l8afdu520rvtf318` (`email`),
  KEY `ROLE_ID_FK` (`role_id`),
  CONSTRAINT `ROLE_ID_FK` FOREIGN KEY (`role_id`) REFERENCES `sec_roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `advertisements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created` datetime NOT NULL,
  `description` longtext,
  `engine_volume` int(11) NOT NULL,
  `horse_powers` int(11) DEFAULT NULL,
  `model_name` varchar(50) NOT NULL,
  `odometer` int(11) NOT NULL,
  `photo` blob,
  `price` float NOT NULL,
  `year_produced` int(11) NOT NULL,
  `car_body` int(11) NOT NULL,
  `engine_type` int(11) NOT NULL,
  `owner_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL,
  `transmission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `CAR_BODY_ID_FK` (`car_body`),
  KEY `ENGINE_TYPE_ID_FK` (`engine_type`),
  KEY `OWNER_ID_FK` (`owner_id`),
  KEY `STATUS_ID_FK` (`status_id`),
  KEY `TRANSMISSION_ID_FK` (`transmission_id`),
  CONSTRAINT `CAR_BODY_ID_FK` FOREIGN KEY (`car_body`) REFERENCES `attributes` (`id`),
  CONSTRAINT `ENGINE_TYPE_ID_FK` FOREIGN KEY (`engine_type`) REFERENCES `attributes` (`id`),
  CONSTRAINT `OWNER_ID_FK` FOREIGN KEY (`owner_id`) REFERENCES `sec_users` (`id`),
  CONSTRAINT `STATUS_ID_FK` FOREIGN KEY (`status_id`) REFERENCES `attributes` (`id`),
  CONSTRAINT `TRANSMISSION_ID_FK` FOREIGN KEY (`transmission_id`) REFERENCES `attributes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `body` varchar(255) NOT NULL,
  `created` bigint(20) NOT NULL,
  `advertisement_id` int(11) NOT NULL,
  `author_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ADV_ID_FK` (`advertisement_id`),
  KEY `AUTHOR_ID_FK` (`author_id`),
  CONSTRAINT `ADV_ID_FK` FOREIGN KEY (`advertisement_id`) REFERENCES `advertisements` (`id`),
  CONSTRAINT `AUTHOR_ID_FK` FOREIGN KEY (`author_id`) REFERENCES `sec_users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

