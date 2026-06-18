-- =========================
-- LIVRES
-- =========================

INSERT INTO livres (id, titre, auteur, isbn, categorie) VALUES
(1, 'Clean Code', 'Robert C. Martin', '9780132350884', 'Informatique'),
(2, 'Effective Java', 'Joshua Bloch', '9780134685991', 'Informatique'),
(3, 'Spring in Action', 'Craig Walls', '9781617297571', 'Informatique'),
(4, 'Le Petit Prince', 'Antoine de Saint-Exupéry', '9782070612758', 'Roman'),
(5, '1984', 'George Orwell', '9780451524935', 'Roman'),
(6, 'L''Étranger', 'Albert Camus', '9782070360024', 'Roman'),
(7, 'Harry Potter à l''école des sorciers', 'J.K. Rowling', '9782070643028', 'Fantasy'),
(8, 'Le Seigneur des Anneaux', 'J.R.R. Tolkien', '9782266282361', 'Fantasy'),
(9, 'Sapiens', 'Yuval Noah Harari', '9782226257017', 'Histoire'),
(10, 'Atomic Habits', 'James Clear', '9780735211292', 'Développement Personnel');

-- =========================
-- MEMBRES
-- =========================

INSERT INTO membres (id, nom, email, date_inscription) VALUES
(1, 'Jean Dupont', '[jean.dupont@mail.com](mailto:jean.dupont@mail.com)', '2026-01-10'),
(2, 'Marie Kossi', '[marie.kossi@mail.com](mailto:marie.kossi@mail.com)', '2026-01-15'),
(3, 'Pierre Mensah', '[pierre.mensah@mail.com](mailto:pierre.mensah@mail.com)', '2026-02-01'),
(4, 'Awa Johnson', '[awa.johnson@mail.com](mailto:awa.johnson@mail.com)', '2026-02-10'),
(5, 'Luc Martin', '[luc.martin@mail.com](mailto:luc.martin@mail.com)', '2026-03-01'),
(6, 'Sophie Bernard', '[sophie.bernard@mail.com](mailto:sophie.bernard@mail.com)', '2026-03-12'),
(7, 'David Lawson', '[david.lawson@mail.com](mailto:david.lawson@mail.com)', '2026-04-01'),
(8, 'Emma Agbo', '[emma.agbo@mail.com](mailto:emma.agbo@mail.com)', '2026-04-15');

-- =========================
-- EXEMPLAIRES
-- =========================

INSERT INTO exemplaires (id, code_barre, etat, statut, livre_id) VALUES
(1, 'EX001', 'NEUF', 'DISPONIBLE', 1),
(2, 'EX002', 'BON', 'EMPRUNTE', 1),

(3, 'EX003', 'NEUF', 'DISPONIBLE', 2),
(4, 'EX004', 'BON', 'DISPONIBLE', 2),

(5, 'EX005', 'NEUF', 'EMPRUNTE', 3),
(6, 'EX006', 'BON', 'DISPONIBLE', 3),

(7, 'EX007', 'BON', 'DISPONIBLE', 4),
(8, 'EX008', 'BON', 'DISPONIBLE', 5),

(9, 'EX009', 'USE', 'EMPRUNTE', 6),
(10, 'EX010', 'BON', 'DISPONIBLE', 7),

(11, 'EX011', 'NEUF', 'DISPONIBLE', 8),
(12, 'EX012', 'BON', 'DISPONIBLE', 9),

(13, 'EX013', 'NEUF', 'EMPRUNTE', 10),
(14, 'EX014', 'BON', 'DISPONIBLE', 4),

(15, 'EX015', 'BON', 'DISPONIBLE', 5),
(16, 'EX016', 'NEUF', 'DISPONIBLE', 6),

(17, 'EX017', 'BON', 'DISPONIBLE', 7),
(18, 'EX018', 'BON', 'DISPONIBLE', 8),

(19, 'EX019', 'NEUF', 'DISPONIBLE', 9),
(20, 'EX020', 'BON', 'DISPONIBLE', 10);

-- =========================
-- EMPRUNTS
-- =========================

INSERT INTO emprunts
(id, code_barre, membre_id, date_emprunt, date_retour_prevue, date_retour)
VALUES

(1, 'EX002', 1, '2026-05-01', '2026-05-15', '2026-05-12'),

(2, 'EX005', 2, '2026-05-10', '2026-05-24', NULL),

(3, 'EX009', 3, '2026-05-15', '2026-05-29', NULL),

(4, 'EX013', 4, '2026-05-20', '2026-06-03', NULL),

(5, 'EX001', 5, '2026-04-01', '2026-04-15', '2026-04-10'),

(6, 'EX003', 6, '2026-04-05', '2026-04-19', '2026-04-18'),

(7, 'EX007', 7, '2026-04-08', '2026-04-22', '2026-04-20'),

(8, 'EX008', 8, '2026-04-10', '2026-04-24', '2026-04-23'),

(9, 'EX011', 1, '2026-04-12', '2026-04-26', '2026-04-24'),

(10, 'EX012', 2, '2026-04-15', '2026-04-29', '2026-04-27');
-- Exemplaire en maintenance
INSERT INTO exemplaires
(id, code_barre, etat, statut, livre_id)
VALUES
(21, 'EX021', 'ABIME', 'MAINTENANCE', 2);

-- Exemplaire réservé
INSERT INTO exemplaires
(id, code_barre, etat, statut, livre_id)
VALUES
(22, 'EX022', 'BON', 'RESERVE', 7);

-- Exemplaire perdu
INSERT INTO exemplaires
(id, code_barre, etat, statut, livre_id)
VALUES
(23, 'EX023', 'PERDU', 'MAINTENANCE', 4);