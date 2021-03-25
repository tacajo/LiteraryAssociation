INSERT INTO role (name) VALUES ('ROLE_WRITER');
INSERT INTO role (name) VALUES ('ROLE_READER');
INSERT INTO role (name) VALUES ('ROLE_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_COMMITTEEMEMBER');
INSERT INTO role (name) VALUES ('ROLE_EDITOR');

INSERT INTO user (user_type, city, country, email, enabled, first_name,last_name, last_password_reset_date, password, username, beta_reader,  waiting_for_votes, membership_fee_paid, member, points)
VALUES ("reader", "Novi Sad", "Srbija", "tacajovicic@gmail.com", true, "Tamara", "Jovicic", "2020-01-01", "$2y$12$BMU4WzOqe4LzpF1ogvS6suBO8YeOWsNiEYq/vW1FE0M6FWEVu8muW", "taca", true, false, true, true, 0),
("reader", "Novi Sad", "Srbija", "tacajovicic@gmail.com", true, "Pera", "Jovicic", "2020-01-01", "$2y$12$BMU4WzOqe4LzpF1ogvS6suBO8YeOWsNiEYq/vW1FE0M6FWEVu8muW", "pera", true, false, true, true, 0 ),
("writer", "Novi Sad", "Srbija", "urb.saska@gmail.com", true, "Vera", "Jovicic", "2020-01-01", "$2y$12$BMU4WzOqe4LzpF1ogvS6suBO8YeOWsNiEYq/vW1FE0M6FWEVu8muW", "vera", true, false, true, true, 0 ),
("committeeMember", "Novi Sad", "Srbija", "tacajovicic@gmail.com", true, "Mika",  "Jovicic", "2020-01-01", "$2y$12$BMU4WzOqe4LzpF1ogvS6suBO8YeOWsNiEYq/vW1FE0M6FWEVu8muW", "mika", null, false, true, true, 0 ),
("committeeMember", "Novi Sad", "Srbija", "tacajovicic@gmail.com", true, "Mika", "Jovicic", "2020-01-01", "$2y$12$BMU4WzOqe4LzpF1ogvS6suBO8YeOWsNiEYq/vW1FE0M6FWEVu8muW", "urb", null, false, true, true, 0 ),
("editor", "Novi Sad", "Srbija", "urb.saska@gmail.com", true, "Ana",  "Jovicic", "2020-01-01", "$2y$12$BMU4WzOqe4LzpF1ogvS6suBO8YeOWsNiEYq/vW1FE0M6FWEVu8muW", "ana", null, false, true, true, 0 ),
("editor", "Novi Sad", "Srbija", "urb.saska@gmail.com", true, "Ana",  "Jovicic", "2020-01-01", "$2y$12$BMU4WzOqe4LzpF1ogvS6suBO8YeOWsNiEYq/vW1FE0M6FWEVu8muW", "ana1", null, false, true, true, 0 ),
("writer", "Novi Sad", "Srbija", "urb.saska@gmail.com", true, "Fredrik",  "Bakman", "2020-01-01", "$2y$12$BMU4WzOqe4LzpF1ogvS6suBO8YeOWsNiEYq/vW1FE0M6FWEVu8muW", "ana2", null, false, true, true, 0 ),
("writer", "Novi Sad", "Srbija", "urb.saska@gmail.com", true, "Svetislav",  "Basara", "2020-01-01", "$2y$12$BMU4WzOqe4LzpF1ogvS6suBO8YeOWsNiEYq/vW1FE0M6FWEVu8muW", "ana3", null, false, true, true, 0 ),
("writer", "Novi Sad", "Srbija", "urb.saska@gmail.com", true, "Dzejn",  "Ostin", "2020-01-01", "$2y$12$BMU4WzOqe4LzpF1ogvS6suBO8YeOWsNiEYq/vW1FE0M6FWEVu8muW", "ana4", null, false, true, true, 0 ),
("writer", "Novi Sad", "Srbija", "tacajovicic@gmail.com", true, "Bojan", "Ljubenović", "2020-01-01", "$2y$12$BMU4WzOqe4LzpF1ogvS6suBO8YeOWsNiEYq/vW1FE0M6FWEVu8muW", "zex", null, false, true, true, 0 );

INSERT INTO user_roles (user_id, role_id) VALUES (1,3), (2,2), (3,1), (4,4), (5,4), (6, 5), (7, 5), (8, 5), (9, 5), (10, 5), (11, 5);

INSERT INTO merchant (registered) VALUES (false);

INSERT INTO genre (name) VALUES ("Drama"), ("Triler"), ("Horor"), ("Autobiografija"), ("Komedija"), ("Naucna fantastika");

INSERT INTO user_genres (genre_id, user_id) VALUES (1, 3), (2, 2), (3, 3);
INSERT INTO user_interested_genres (reader_id, interested_genres_id) VALUES (1, 1), (2, 1);

INSERT INTO book (city,isbn,page_number,synopsis,title,year, accept, original, print, genre_id, writer_id, price) VALUES ("Novi Sad","132","132","","Gordost i predrasude","1887", false, false, true, 1, 10, 9.99);
INSERT INTO book (city,isbn,page_number,synopsis,title,year, accept, original, print, genre_id, writer_id, price) VALUES ("Novi Sad","524","524","","Kontraendorfin","2020", false, false, true, 1, 9, 3.59);
INSERT INTO book (city,isbn,page_number,synopsis,title,year, accept, original, print, genre_id, writer_id, price) VALUES ("Novi Sad","256","256","","Da je bolje, ne bi valjalo","2012", false, false, true, 1, 11, 8.79);
INSERT INTO book (city,isbn,page_number,synopsis,title,year, accept, original, print, genre_id, writer_id, price) VALUES ("Novi Sad","320","320","","Medvedgrad","2003", false, false, true, 1, 8, 5.99);
INSERT INTO book (city,isbn,page_number,synopsis,title,year, accept, original, print, genre_id, writer_id, price) VALUES ("Novi Sad","200","200","","Čovek po imenu Uve","1999", false, false, true, 1,  8, 11.39);
INSERT INTO book (city,isbn,page_number,synopsis,title,year, accept, original, print, genre_id, writer_id, price) VALUES ("Novi Sad","512","512","","Kinesko pismo","2009", false, false, true, 1, 11, 7.99);