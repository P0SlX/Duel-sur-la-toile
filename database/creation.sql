-- create database if not exists DUEL_SUR_LA_TOILE;
-- USE DUEL_SUR_LA_TOILE;

drop table INVITER;
drop table INVITATION;
drop table ETREAMIS;
drop table JOUER;
drop table COMMUNIQUER;
drop table MESSAGE;
drop table PARTIE;
drop table JOUEUR;


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
    etatinv int,
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
    gameID int unique,
    nomJeu varchar(20),
    plate varchar(2048),
    currentPlayer varchar(20),
    state int,
    startTime date,
    finishTime date,
    elementPlaced int,
    winner varchar(20),
    looser varchar(20),
    PRIMARY KEY (gameID)
);

create table JOUER (
    pseudo varchar(30),
    adversaire varchar(30),
    gameID int,
    score int,
    PRIMARY KEY (pseudo, adversaire, gameID)
);

create table COMMUNIQUER (
    pseudo varchar(30),
    destinataire varchar(30),
    idMessage int
);

alter table ETREAMIS add foreign key (amis) references JOUEUR (pseudo);
alter table PARTIE add foreign key (currentPlayer) references JOUEUR (pseudo);
alter table ETREAMIS add foreign key (pseudo) references JOUEUR (pseudo);
alter table INVITER add foreign key (expediteurInvit) references JOUEUR (pseudo);
alter table INVITER add foreign key (destinataireInvit) references JOUEUR (pseudo);
alter table INVITER add foreign key (idinv) references INVITATION (idinv);
alter table JOUER add foreign key (pseudo) references JOUEUR (pseudo);
alter table JOUER add foreign key (adversaire) references JOUEUR (pseudo);
alter table JOUER add foreign key (gameID) references PARTIE (gameID);
alter table COMMUNIQUER add foreign key (pseudo) references JOUEUR (pseudo);
alter table COMMUNIQUER add foreign key (destinataire) references JOUEUR (pseudo);
alter table COMMUNIQUER add foreign key (idMessage) references MESSAGE (idmessage);
