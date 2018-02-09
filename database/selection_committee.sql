-- drop old database
DROP DATABASE selection_committee;

-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema selection_committee
-- -----------------------------------------------------
-- БД "Приемная комиссия". Абитуриент регистрируется на один из Факультетов с фиксированным планом набора, вводит баллы по соответствующим Предметам и аттестату.
-- Результаты Администратором регистрируются в Ведомости. Система подсчитывает сумму баллов и определяет Абитуриентов, зачисленных в учебное заведение.

-- -----------------------------------------------------
-- Schema selection_committee
--
-- БД "Приемная комиссия". Абитуриент регистрируется на один из Факультетов с фиксированным планом набора, вводит баллы по соответствующим Предметам и аттестату.
-- Результаты Администратором регистрируются в Ведомости. Система подсчитывает сумму баллов и определяет Абитуриентов, зачисленных в учебное заведение.
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `selection_committee` DEFAULT CHARACTER SET utf8 ;
USE `selection_committee` ;

-- -----------------------------------------------------
-- Table `selection_committee`.`university`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `selection_committee`.`university` (
  `u_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID учебного заведения.',
  `u_name` VARCHAR(100) NOT NULL COMMENT 'Имя учебного заведения.',
  PRIMARY KEY (`u_id`),
  UNIQUE INDEX `u_name_UNIQUE` (`u_name` ASC))
  ENGINE = InnoDB
  COMMENT = 'Таблица о высших учебных заведениях.';


-- -----------------------------------------------------
-- Table `selection_committee`.`faculty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `selection_committee`.`faculty` (
  `f_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID факультета.',
  `u_id` INT UNSIGNED NOT NULL COMMENT 'ID учебного заведения.',
  `f_name` VARCHAR(100) NOT NULL COMMENT 'Имя факультета.',
  PRIMARY KEY (`f_id`),
  INDEX `u_id_idx` (`u_id` ASC),
  CONSTRAINT `u_id`
  FOREIGN KEY (`u_id`)
  REFERENCES `selection_committee`.`university` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
  ENGINE = InnoDB
  COMMENT = 'Таблица о факультетах.';


-- -----------------------------------------------------
-- Table `selection_committee`.`speciality`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `selection_committee`.`speciality` (
  `s_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID специальности.',
  `f_id` INT UNSIGNED NOT NULL COMMENT 'ID факультета.',
  `s_name` VARCHAR(100) NOT NULL COMMENT 'Имя специальности.',
  `number_of_seats` SMALLINT UNSIGNED NOT NULL COMMENT 'Количество мест.',
  PRIMARY KEY (`s_id`),
  INDEX `f_id_idx` (`f_id` ASC),
  CONSTRAINT `f_id`
  FOREIGN KEY (`f_id`)
  REFERENCES `selection_committee`.`faculty` (`f_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
  ENGINE = InnoDB
  COMMENT = 'Таблица о специальностях.';


-- -----------------------------------------------------
-- Table `selection_committee`.`enrollee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `selection_committee`.`enrollee` (
  `passport_id` VARCHAR(10) NOT NULL COMMENT 'ID пасспорта.',
  `country_domen` CHAR(2) NOT NULL COMMENT 'Домен страны.',
  `e_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор.',
  `s_id` INT UNSIGNED NOT NULL COMMENT 'ID специальности.',
  `surname` VARCHAR(50) NOT NULL COMMENT 'Фамилия абитуриента.',
  `name` VARCHAR(50) NOT NULL COMMENT 'Имя абитуриента.',
  `second_name` VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'Второе имя абитуриента.',
  `phone` CHAR(12) NOT NULL COMMENT 'Контактный телефон.',
  `statement` ENUM('Зачислен', 'Не зачислен', 'В процессе') NOT NULL DEFAULT 'В процессе' COMMENT 'Ведомасть.',
  `russian_lang` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Балл по русскому языку.',
  `belorussian_lang` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Балл по белорусскому языку.',
  `physics` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Балл по физике.',
  `math` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Балл по математике.',
  `chemistry` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Балл по химии.',
  `biology` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Балл по биологии.',
  `foreign_lang` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Балл по иностранному языку.',
  `history_of_belarus` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Балл по истории Беларуси.',
  `social_studies` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Балл по обществоведению.',
  `geography` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Балл по географии.',
  `history` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Балл по истории.',
  `certificate` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT 'Балл аттестата.',
  `date` DATE NOT NULL COMMENT 'Дата регистрации',
  INDEX `s_id_idx` (`s_id` ASC),
  PRIMARY KEY (`passport_id`, `country_domen`),
  UNIQUE INDEX `e_id_UNIQUE` (`e_id` ASC),
  CONSTRAINT `s_id`
  FOREIGN KEY (`s_id`)
  REFERENCES `selection_committee`.`speciality` (`s_id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE)
  ENGINE = InnoDB
  COMMENT = 'Таблица об абитуриентах.';


-- -----------------------------------------------------
-- Table `selection_committee`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `selection_committee`.`user` (
  `user_id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID пользователя.',
  `email` VARCHAR(64) NOT NULL COMMENT 'Почта',
  `username` VARCHAR(16) NOT NULL COMMENT 'Имя пользователя.',
  `password` CHAR(40) NOT NULL COMMENT 'Пароль.',
  `type` ENUM('user', 'admin') NOT NULL DEFAULT 'user' COMMENT 'Тип пользователя.',
  `e_id` INT UNSIGNED NULL DEFAULT NULL COMMENT 'ID абитуриента.',
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `login_UNIQUE` (`username` ASC),
  UNIQUE INDEX `e_id_UNIQUE` (`e_id` ASC),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  CONSTRAINT `fk_user_1`
  FOREIGN KEY (`e_id`)
  REFERENCES `selection_committee`.`enrollee` (`e_id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
  ENGINE = InnoDB
  COMMENT = 'Таблица о пользователях.';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- university
INSERT INTO university(u_name) VALUE("БГУИР");	-- 1	

-- faculty
INSERT INTO faculty(u_id,f_name) VALUE(1,"Факультет компьютерного проектирования");	-- 1
INSERT INTO faculty(u_id,f_name) VALUE(1,"Факультет информационных технологий и управления");	-- 2
INSERT INTO faculty(u_id,f_name) VALUE(1,"Факультет радиотехники и электроники");	-- 3
INSERT INTO faculty(u_id,f_name) VALUE(1,"Факультет компьютерных систем и сетей");	-- 4
INSERT INTO faculty(u_id,f_name) VALUE(1,"Факультет инфокоммуникаций");	-- 5
INSERT INTO faculty(u_id,f_name) VALUE(1,"Инженерно-экономический факультет");	-- 6
INSERT INTO faculty(u_id,f_name) VALUE(1,"Военный факультет");	-- 7

-- speciality
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(1,"Проектирование и производство программно-управляемых электронных средств",10); -- 1
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(1,"Моделирование и компьютерное проектирование радиоэлектронных средств",10); -- 2
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(1,"Программируемые мобильные системы",10); -- 3
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(1,"Программно-управляемые электронно-оптические системы",10); -- 4
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(1,"Медицинская электроника",10); -- 5
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(1,"Инженерно-психологическое обеспечение информационных технологий",10); -- 6
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(1,"Электронные системы безопасности",10); -- 7
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(1,"Информационные системы и технологии (в обеспечении промышленной безопасности)",10); -- 8
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(1,"Информационные системы и технологии (в бизнес-менеджменте)",10); -- 9
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(2,"Автоматизированные системы обработки информации",10); -- 10
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(2,"Искусственный интеллект",10); -- 11
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(2,"Информационные технологии и управление в технических системах",10); -- 12
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(2,"Промышленная электроника",10); -- 13
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(2,"Информационные системы и технологии (в игровой индустрии)",10); -- 14
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(3,"Микро- и наноэлектронные технологии и системы",10); -- 15
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(3,"Квантовые информационные системы",10); -- 16
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(3,"Нанотехнологии и наноматериалы в электронике",10); -- 17
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(3,"Радиотехника (программируемые радиоэлектронные средства)",10); -- 18
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(3,"Радиоэлектронные системы",10); -- 19
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(3,"Радиоинформатика",10); -- 20
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(3,"Радиоэлектронная защита информации",10); -- 21
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(3,"Электронные и информационно-управляющие системы физических установок",10); -- 22
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(3,"Профессиональное образование (информатика)",10); -- 23
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(4,"Вычислительные мышины, системы и сети",10); -- 24
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(4,"Программное обеспечение информационных технологий",10); -- 25
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(4,"Информатика и технологии программирования",10); -- 26
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(4,"Электронные вычислительные средства",10); -- 27
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(5,"Инфокоммуникационные технологии (системы телекоммуникаций)",10); -- 28
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(5,"Инфокоммуникационные технологии (сети инфокоммуникаций)",10); -- 29
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(5,"Инфокоммуникационные технологии (системы распределения мультимедийной информации)",10); -- 30
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(5,"Инфокоммуникационные системы (стандартизация, сертификация и контроль параметров)",10); -- 31
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(5,"Защита информации в телекоммуникациях",10); -- 32
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(6,"Информационные системы и технологии (в экономике)",10); -- 33
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(6,"Информационные системы и технологии (в логистике)",10); -- 34
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(6,"Электронный маркетинг",10); -- 35
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(6,"Экономика электронного бизнеса",10); -- 36
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(7,"Инфокуммуникационные технологии",10); -- 37
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(7,"Радиотехника",10); -- 38
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(7,"Вычислительные машины, системы и сети (специального назначения)",10); -- 39

-- user (admin)
INSERT INTO user(email,username,password,type) VALUE("emakedonw@gmail.com","egormakedon",SHA1("Elizabeth147"),"admin");