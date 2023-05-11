insert into UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur)
values
    ('admin', 'Dupont', 'Jean', 'jean.dupont@example.com', '+33 6 12 34 56 78', '15 rue de l''Admin', '75008', 'Paris', 'motdepasseadmin', 1000, 1),
    ('user1', 'Martin', 'Marie', 'marie.martin@example.com', '+33 6 23 45 67 89', '42 avenue des Lilas', '75020', 'Paris', 'motdepasse1', 300, 0),
    ('user2', 'Dubois', 'Julien', 'julien.dubois@example.com', '+33 6 12 34 56 78', '5 rue du Commerce', '69009', 'Lyon', 'motdepasse2', 600, 0),
    ('user3', 'Lefebvre', 'Sophie', 'sophie.lefebvre@example.com', '+33 6 34 56 78 90', '22 rue du Bac', '44000', 'Nantes', 'motdepasse3', 200, 0),
    ('user4', 'Pierre', 'Luc', 'luc.pierre@example.com', '+33 6 45 67 89 01', '1 rue du Moulin', '67000', 'Strasbourg', 'motdepasse4', 300, 0);
INSERT INTO categories (libelle)
VALUES
('Informatique'),
('Ameublement'),
('Vêtement'),
('Sport & Loisir');
insert into ARTICLES_VENDUS (nom_article, description, date_debut_enchere, date_fin_enchere, prix_initial, prix_vente, no_utilisateur, no_categorie, etat_vente)
values
('PC Portable Asus', 'PC portable de marque Asus, en très bon état', DATEADD(DAY, 1, GETDATE()), DATEADD(WEEK, 1, GETDATE()), 600, null,1, 1, 'CR'),
('Imprimante Canon', 'Imprimante de marque Canon, en bon état', GETDATE(), DATEADD(WEEK, 1, GETDATE()), 80, null,1, 1, 'EC'),
('Air pods', 'Air pods quasiment neuf', DATEADD(WEEK, -1, GETDATE()), GETDATE(), 500,700, 1, 1, 'VD'),
('Chemise blanche', 'Chemise blanche pour homme, taille M', DATEADD(WEEK, -1, GETDATE()), DATEADD(DAY, -1, GETDATE()), 25, 125,3, 3, 'VD'),
('Tapis de yoga', 'Tapis de yoga noir, épaisseur 6mm', DATEADD(WEEK, 1, GETDATE()), DATEADD(DAY, 1, GETDATE()), 100,150, 2, 2, 'RT'),
('Souris sans fil', 'Souris de marque logitech', GETDATE(), DATEADD(WEEK, 1, GETDATE()), 80, null,2, 1, 'EC');

insert into ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere)
values (2, 2, DATEADD(DAY, 1, GETDATE()), 700),
       (4, 2, DATEADD(DAY, 1, GETDATE()), 750),
       (3, 2, DATEADD(DAY, 1, GETDATE()), 800),
       (2, 3, DATEADD(DAY, -1, GETDATE()), 600),
       (3, 3, DATEADD(DAY, -1, GETDATE()), 650),
       (4, 3, DATEADD(DAY, -1, GETDATE()), 700),
       (4, 4, DATEADD(DAY, -1, GETDATE()), 50),
       (2, 4, DATEADD(DAY, -1, GETDATE()), 75),
       (1, 4, DATEADD(DAY, -1, GETDATE()), 125),
	   (1, 5, DATEADD(DAY, -1, GETDATE()), 105),
	   (3, 5, DATEADD(DAY, -1, GETDATE()), 125),
	   (1, 5, DATEADD(DAY, -1, GETDATE()), 150),
	   (1, 6, DATEADD(DAY, -1, GETDATE()), 90),
	   (3, 6, DATEADD(DAY, -1, GETDATE()), 95);

insert into IMAGES (no_article, image)
values (1, 'pc_portabel_asus.jpg'),
	   (2, 'canon_imprimante.jpg'),
	   (3, 'air_pods.jpg'),
	   (4, 'chemise_blanche.jpg'),
	   (5, 'tapis_yoga.jpg'),
	   (6, 'souris.jpg');
