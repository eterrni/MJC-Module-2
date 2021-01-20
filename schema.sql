DROP TABLE IF EXISTS `gift_certificate`;
CREATE TABLE IF NOT EXISTS `gift_certificate`
(
    `id`               INT            NOT NULL AUTO_INCREMENT,
    `name`             VARCHAR(90)    NOT NULL,
    `description`      VARCHAR(90)    NOT NULL,
    `price`            DECIMAL(10, 0) NOT NULL,
    `duration`         INT            NOT NULL,
    `create_date`      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `last_update_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);
INSERT INTO `gift_certificate` (`id`, `name`, `description`, `price`, `duration`)
VALUES ('1', 'name1', 'description1', '11', '11');
INSERT INTO `gift_certificate` (`id`, `name`, `description`, `price`, `duration`)
VALUES ('2', 'name2', 'description2', '22', '22');