


DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `role_id` bigint(20) NOT NULL,
    `is_default` bit(1) DEFAULT NULL,
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `settings`;
CREATE TABLE `settings` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `theme_name` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `age` int(11) DEFAULT NULL,
    `address_city` varchar(255) DEFAULT NULL,
    `created_at` datetime DEFAULT NULL,
    `email` varchar(255) NOT NULL,
    `first_name` varchar(255) DEFAULT NULL,
    `is_deleted` bit(1) NOT NULL,
    `last_name` varchar(255) DEFAULT NULL,
    `address_lat` double DEFAULT NULL,
    `address_lng` double DEFAULT NULL,
    `login` varchar(255) NOT NULL,
    `password_hash` varchar(255) NOT NULL,
    `address_street` varchar(255) DEFAULT NULL,
    `updated_at` datetime DEFAULT NULL,
    `address_zip_code` varchar(255) DEFAULT NULL,
    `settings_id` bigint(20) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `FKexjamu52wpct0e0y77vawpcjo` (`settings_id`),
    CONSTRAINT `FKexjamu52wpct0e0y77vawpcjo` FOREIGN KEY (`settings_id`) REFERENCES `settings` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `image`;
CREATE TABLE `image` (
     `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
     `image` blob DEFAULT NULL,
     PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
      `user_id` bigint(20) NOT NULL,
      `role_id` bigint(20) NOT NULL,
      PRIMARY KEY (`user_id`,`role_id`),
      KEY `FKrhfovtciq1l558cw6udg0h0d3` (`role_id`),
      CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `account` (`id`),
      CONSTRAINT `FKrhfovtciq1l558cw6udg0h0d3` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `restore_password`;
CREATE TABLE `restore_password` (
    `id` bigint(20) NOT NULL,
    `expires_in` datetime NOT NULL,
    `token` varchar(255) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `UK_fn9iq5rdbswa304p0an04lxoc` (`token`),
    KEY `FKdmhk6ivh741n4ucx6hpbmb9yq` (`user_id`),
    CONSTRAINT `FKdmhk6ivh741n4ucx6hpbmb9yq` FOREIGN KEY (`user_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



-- ========== Insert sample data ==========

INSERT INTO role (id, id, name, is_default) VALUES
(101, 101,'USER', 1), (102, 102,'ADMIN', 0), (103, 103,'PUBLIC', 0), (104, 104,'NHANCONG', 0)
, (1, 1, 'Nhà sản xuất', 0)
, (2, 2, 'Cấp huyện', 0)
, (3, 3, 'Cấp chi cục chuyên ngành', 0)
, (4, 4, 'Cấp tỉnh', 0)
, (5, 5, 'Cấp cục', 0)
;


INSERT INTO settings (id, theme_name) VALUES
(1, 'default'), (2, 'cosmic'), (3, 'default'), (4, 'default');
INSERT INTO account (id, first_name, last_name, login, email, password_hash, is_deleted, settings_id) VALUES
-- Hashed "password"
(1, 'Admin', 'Admin', 'admin', 'admin@admin.com', '$2a$10$ZUw7TUg/cKEJw4XlSS/6Wu0Pp05yi5kUO3cBYs5ewInpKXOW/US6G', false, 1),
-- Hashed "password1"
(2, 'Account', 'Account', 'account', 'account@account.com', '$2a$10$6IDH7YBMlz3B2W9GiHdEI.sm6tlVRDYGmA9eWzUDucYYnqQVvmR66', false, 2)
;
-- New password encoder:
-- password 	5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8
-- password1 	0b14d501a594442a01c6859541bcb3e8164d183d32937b851835442f69d5c94e
-- 123456a@ 	cb9a7f035d23c91b81d8f9981405d2e566267e4e10ef8ff0722d3d5a61611b52

INSERT INTO image (user_id, image) VALUES
-- Bytes of the default picture
(1, null),
(2, null);


INSERT INTO user_roles (user_id, role_id) VALUES
(1, 102), (2, 101), (3, 103), (4, 104), (4, 103);


alter table account add column is_deleted bit(1) NOT null default false;