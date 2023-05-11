create table CATEGORIES
(
    no_categorie int IDENTITY(1,1) not null,
    libelle      varchar(30) not null
);
alter table CATEGORIES add constraint categories_pk primary key (no_categorie)

create table UTILISATEURS
(
    no_utilisateur int identity(1,1) not null,
    pseudo         varchar(30) not null,
    nom            varchar(30) not null,
    prenom         varchar(30) not null,
    email          varchar(50) not null,
    telephone      varchar(17) null,
    rue            varchar(30) not null,
    code_postal    varchar(10) not null,
    ville          varchar(30) not null,
    mot_de_passe   varchar(30) not null,
    credit         int         not null,
    administrateur bit         not null,
);
alter table UTILISATEURS add constraint utilisateurs_pk primary key (no_utilisateur)
alter table UTILISATEURS add constraint utilisateurs_pseudo_uq unique (pseudo)
alter table UTILISATEURS add constraint utilisateurs_email_uq unique (email) 

create table ARTICLES_VENDUS
(
    no_article         int identity(1,1) not null,
    nom_article        varchar(30)          not null,
    description        varchar(300)         not null,
    date_debut_enchere datetime             not null,
    date_fin_enchere   datetime             not null,
    prix_initial       int                  null,
    prix_vente         int                  null,
    no_utilisateur     int                  not null,
    no_categorie       int                  not null,
    etat_vente         char(2) default 'CR' not null,
);
alter table ARTICLES_VENDUS add constraint articles_vendus_pk primary key (no_article)
--CREER("CR"),EN_COURS("EC"),VENDU("VD"),RETIRER("RT");
alter table ARTICLES_VENDUS add constraint articles_vendus_etat_vente_ck CHECK (etat_vente IN ('CR','EC','VD','RT'))

create table ENCHERES
(
    no_enchere      int identity(1,1) not null,
    no_utilisateur  int      not null,
    no_article      int      not null,
    date_enchere    datetime not null,
    montant_enchere int      not null,
);
alter table ENCHERES add constraint encheres_pk primary key (no_enchere)

create table IMAGES
(
    no_article int not null,
    image      varchar(max) null,
);
alter table IMAGES add constraint image_pk primary key  (no_article)

create table RETRAITS
(
    no_article  int         not null,
    rue         varchar(30) not null,
    code_postal varchar(15) not null,
    ville       varchar(30) not null,
);
alter table RETRAITS add constraint retraits_pk primary key  (no_article)

alter table ARTICLES_VENDUS
    add constraint articles_utilisateurs_fk foreign key ( no_utilisateur )
	    references UTILISATEURS ( no_utilisateur )
	on delete cascade 
    on update no action
    
alter table ARTICLES_VENDUS
    add constraint articles_vendus_categories_fk foreign key ( no_categorie )
        references categories ( no_categorie )
	on delete no action 
    on update no action
    
alter table ENCHERES
    add constraint encheres_articles_vendus_fk foreign key ( no_article )
        references ARTICLES_VENDUS ( no_article )
	on delete no action
    on update no action
    
alter table ENCHERES
    add constraint ventes_utilisateurs_fk foreign key ( no_utilisateur )
        references utilisateurs ( no_utilisateur )
	on delete no action
    on update no action

alter table RETRAITS
    add constraint retraits_articles_vendus_fk foreign key ( no_article )
        references ARTICLES_VENDUS ( no_article )
	on delete cascade  --A LA SUPPRESSION DE L'ARTICLE, SUPPRESSION DU RETRAIT LIES
    on update no action

alter table IMAGES
	add constraint image_article_fk foreign key ( no_article )
		references ARTICLES_VENDUS ( no_article )
	on delete cascade 
	on update no action