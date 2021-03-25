
INSERT INTO user (email, enabled, first_name, last_name, last_password_reset_date, password, username) VALUES
 ('tacajovicic@gmail.com', true, 'Tamara', 'Jovicic', '2017-10-01', '$2y$12$Hw.03qivbwtDGxWNYL1f5O5RXvabBxCMhscUQCEXtumGHxEb5Wh2C', 'taca'),
 ("tacajovicic@gmail.com", true, "Pera",  "Jovicic", "2020-01-01", "$2y$12$BMU4WzOqe4LzpF1ogvS6suBO8YeOWsNiEYq/vW1FE0M6FWEVu8muW", "pera"),
 ("tacajovicic@gmail.com", true, "Vera",  "Jovicic", "2020-01-01", "$2y$12$BMU4WzOqe4LzpF1ogvS6suBO8YeOWsNiEYq/vW1FE0M6FWEVu8muW", "vera");


INSERT INTO role (name) VALUES ('ROLE_USER');
INSERT INTO role (name) VALUES ('ROLE_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_AGENT');

INSERT INTO user_roles (user_id, role_id) VALUES ( 1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES ( 2, 3);
INSERT INTO user_roles (user_id, role_id) VALUES ( 3, 2);
--INSERT INTO user_roles (user_id, role_id) VALUES ( 4, 1);
--INSERT INTO user_roles (user_id, role_id) VALUES ( 5, 1);