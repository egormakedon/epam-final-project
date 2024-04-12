-- MySQL Workbench Forward Engineering

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

USE `selection_committee`;

-- university
INSERT INTO university(u_name) VALUE('БГУИР');	-- 1

-- faculty
INSERT INTO faculty(u_id,f_name) VALUE(1,'Факультет компьютерного проектирования');	-- 1
INSERT INTO faculty(u_id,f_name) VALUE(1,'Факультет информационных технологий и управления');	-- 2
INSERT INTO faculty(u_id,f_name) VALUE(1,'Факультет радиотехники и электроники');	-- 3
INSERT INTO faculty(u_id,f_name) VALUE(1,'Факультет компьютерных систем и сетей');	-- 4
INSERT INTO faculty(u_id,f_name) VALUE(1,'Факультет инфокоммуникаций');	-- 5
INSERT INTO faculty(u_id,f_name) VALUE(1,'Инженерно-экономический факультет');	-- 6
INSERT INTO faculty(u_id,f_name) VALUE(1,'Военный факультет');	-- 7

-- speciality
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(1,'Проектирование и производство программно-управляемых электронных средств',10); -- 1
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(1,'Моделирование и компьютерное проектирование радиоэлектронных средств',10); -- 2
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(1,'Программируемые мобильные системы',10); -- 3
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(1,'Программно-управляемые электронно-оптические системы',10); -- 4
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(1,'Медицинская электроника',10); -- 5
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(1,'Инженерно-психологическое обеспечение информационных технологий',10); -- 6
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(1,'Электронные системы безопасности',10); -- 7
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(1,'Информационные системы и технологии (в обеспечении промышленной безопасности)',10); -- 8
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(1,'Информационные системы и технологии (в бизнес-менеджменте)',10); -- 9
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(2,'Автоматизированные системы обработки информации',10); -- 10
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(2,'Искусственный интеллект',10); -- 11
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(2,'Информационные технологии и управление в технических системах',10); -- 12
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(2,'Промышленная электроника',10); -- 13
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(2,'Информационные системы и технологии (в игровой индустрии)',10); -- 14
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(3,'Микро- и наноэлектронные технологии и системы',10); -- 15
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(3,'Квантовые информационные системы',10); -- 16
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(3,'Нанотехнологии и наноматериалы в электронике',10); -- 17
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(3,'Радиотехника (программируемые радиоэлектронные средства)',10); -- 18
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(3,'Радиоэлектронные системы',10); -- 19
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(3,'Радиоинформатика',10); -- 20
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(3,'Радиоэлектронная защита информации',10); -- 21
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(3,'Электронные и информационно-управляющие системы физических установок',10); -- 22
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(3,'Профессиональное образование (информатика)',10); -- 23
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(4,'Вычислительные мышины, системы и сети',10); -- 24
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(4,'Программное обеспечение информационных технологий',10); -- 25
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(4,'Информатика и технологии программирования',10); -- 26
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(4,'Электронные вычислительные средства',10); -- 27
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(5,'Инфокоммуникационные технологии (системы телекоммуникаций)',10); -- 28
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(5,'Инфокоммуникационные технологии (сети инфокоммуникаций)',10); -- 29
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(5,'Инфокоммуникационные технологии (системы распределения мультимедийной информации)',10); -- 30
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(5,'Инфокоммуникационные системы (стандартизация, сертификация и контроль параметров)',10); -- 31
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(5,'Защита информации в телекоммуникациях',10); -- 32
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(6,'Информационные системы и технологии (в экономике)',10); -- 33
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(6,'Информационные системы и технологии (в логистике)',10); -- 34
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(6,'Электронный маркетинг',10); -- 35
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(6,'Экономика электронного бизнеса',10); -- 36
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(7,'Инфокуммуникационные технологии',10); -- 37
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(7,'Радиотехника',10); -- 38
INSERT INTO speciality(f_id,s_name,number_of_seats) VALUE(7,'Вычислительные машины, системы и сети (специального назначения)',10); -- 39

-- user
INSERT INTO user(email,username,password,type) VALUE('admin@gmail.com','admin',SHA1('admin'),'admin');
