DROP TABLE IF EXISTS `gift_certificate`;
CREATE TABLE `gift_certificate` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(90) NOT NULL,
  `description` VARCHAR(90) NOT NULL,
  `price` DECIMAL(10,0) NOT NULL,
  `duration` INT NOT NULL,
  `create_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 31;

DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id_tag` INT NOT NULL AUTO_INCREMENT,
  `name_tag` VARCHAR(90) NOT NULL,
  PRIMARY KEY (`id_tag`),
  UNIQUE INDEX `name_tag_UNIQUE` (`name_tag` ASC));

DROP TABLE IF EXISTS `gift_certificate_has_tag`;
CREATE TABLE `gift_certificate_has_tag`
(
 `gift_certificate_id_gift_certificate` INT NOT NULL,
  `tag_id_tag` INT NOT NULL,
  PRIMARY KEY (`gift_certificate_id_gift_certificate`, `tag_id_tag`),
  CONSTRAINT `fk_gift_certificate_has_tag_gift_certificate`
    FOREIGN KEY (`gift_certificate_id_gift_certificate`)
    REFERENCES `gift_certificate` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_gift_certificate_has_tag_tag`
    FOREIGN KEY (`tag_id_tag`)
    REFERENCES `tag` (`id_tag`)
    ON DELETE CASCADE);

INSERT INTO `gift_certificate` (`id`, `name`, `description`, `price`, `duration`, `create_date`, `last_update_date`)
VALUES ('1', 'name1', 'description1', '11', '11', '2021-01-22T11:11:11','2021-01-22T11:11:11');
INSERT INTO `gift_certificate` (`id`, `name`, `description`, `price`, `duration`, `create_date`, `last_update_date`)
VALUES ('2', 'name2', 'description2', '22', '22','2021-01-22T22:22:22','2021-01-22T22:22:22');
INSERT INTO `tag` (`id_tag`, `name_tag`)
VALUES ('1', 'nameTag1');
INSERT INTO `tag` (`id_tag`, `name_tag`)
VALUES ('2', 'nameTag2');
INSERT INTO `gift_certificate_has_tag` (`gift_certificate_id_gift_certificate`, `tag_id_tag`)
VALUES ('1', '1');
INSERT INTO `gift_certificate_has_tag` (`gift_certificate_id_gift_certificate`, `tag_id_tag`)
VALUES ('2', '2');