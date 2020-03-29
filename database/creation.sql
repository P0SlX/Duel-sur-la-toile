create database if not exists DUEL_SUR_LA_TOILE;
USE DUEL_SUR_LA_TOILE;

create table JOUEUR (
    pseudo varchar(30) unique,
    email varchar(50) unique,
    mdp varchar(50),
    avatar varchar(100),
    etat int,
    desactive boolean,
    admin boolean,
    PRIMARY KEY (pseudo)
);

create table ETREAMIS (
    pseudo varchar(20),
    amis varchar(20),
    PRIMARY KEY (pseudo, amis)
);

create table INVITATION (
    idinv int unique,
    dateinv date,
    etatinv boolean,
    pseudo varchar(20),
    PRIMARY KEY (idinv)
);

create table INVITER (
    expediteurInvit varchar(20),
    destinataireInvit varchar(20),
    idinv int,
    PRIMARY KEY (expediteurInvit, destinataireInvit, idinv)
);

create table MESSAGE (
    idmessage int unique,
    datemessage date,
    contenumessage text(280),
    messagelu boolean,
    PRIMARY KEY (idmessage)
);

create table PARTIE (
    idpartie int unique,
    typeJeu varchar(20),
    contenugrillejeu varchar(2048),
    nomJoueurCourant varchar(30),
    etatpartie int,
    datedebut date,
    datefin date,
    nbcoupjoue int,
    PRIMARY KEY (idpartie)
);

create table JOUER (
    pseudo varchar(30),
    adversaire varchar(30),
    idPartie int,
    score int,
    PRIMARY KEY (pseudo, adversaire, idPartie)
);

create table COMMUNIQUER (
    pseudo varchar(30),
    destinataire varchar(30),
    idMessage int
);

alter table ETREAMIS add foreign key (amis) references JOUEUR (pseudo);
alter table PARTIE add foreign key (nomJoueurCourant) references JOUEUR (pseudo);
alter table ETREAMIS add foreign key (pseudo) references JOUEUR (pseudo);
alter table INVITATION add foreign key (pseudo) references JOUEUR (pseudo);
alter table INVITER add foreign key (expediteurInvit) references JOUEUR (pseudo);
alter table INVITER add foreign key (destinataireInvit) references JOUEUR (pseudo);
alter table INVITER add foreign key (idinv) references INVITATION (idinv);
alter table JOUER add foreign key (pseudo) references JOUEUR (pseudo);
alter table JOUER add foreign key (adversaire) references JOUEUR (pseudo);
alter table JOUER add foreign key (idpartie) references PARTIE (idpartie); 
alter table COMMUNIQUER add foreign key (pseudo) references JOUEUR (pseudo);
alter table COMMUNIQUER add foreign key (destinataire) references JOUEUR (pseudo);
alter table COMMUNIQUER add foreign key (idMessage) references MESSAGE (idmessage);
