INSERT INTO bank (bankurl, name) VALUES ('/bank', 'Intesa');
INSERT INTO bank (bankurl, name) VALUES ('/bank', 'Komercijalna banka');
INSERT INTO bank (bankurl, name) VALUES ('/bank', 'Vojvodjanska banka');
INSERT INTO bank (bankurl, name) VALUES ('/bank', 'Postanska Stedionica');


INSERT INTO account (card_holder_name, date, pan, security_code, bank_id, balance) VALUES
('Urban Jovicic', '2022-02-02', '$2y$12$Z1k/f/ptlVflOINQNevveuDGArJbhcW935qIRYJw1oDnrRLwza4dq', '$2y$12$Z1k/f/ptlVflOINQNevveuDGArJbhcW935qIRYJw1oDnrRLwza4dq', 2, 10000),
('Pera Peric', '2022-02-02', '$2y$12$Z1k/f/ptlVflOINQNevveuDGArJbhcW935qIRYJw1oDnrRLwza4dq', '$2y$12$Z1k/f/ptlVflOINQNevveuDGArJbhcW935qIRYJw1oDnrRLwza4dq', 1, 5000),
('Vera Peric', '2022-08-02', '$2y$12$Z1k/f/ptlVflOINQNevveuDGArJbhcW935qIRYJw1oDnrRLwza4dq', '$2y$12$Z1k/f/ptlVflOINQNevveuDGArJbhcW935qIRYJw1oDnrRLwza4dq', 2, 60000);

INSERT INTO issuer (username, account_id) VALUES ('taca', 1), ('pera', 2), ('vera', 3);

