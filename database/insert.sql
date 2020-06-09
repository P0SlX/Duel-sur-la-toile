-- Creation des Joueurs
insert into JOUEUR values (
    "p0slx",
    "florian.savoure@gmail.com",
    "unMotDePasseSecure",
    "sah.png",
    0,
    false,
    false
);

insert into JOUEUR values (
    "L'ananas",
    "lananas@gmail.com",
    "jaimeLesPommes",
    "ananas.png",
    0,
    false,
    false
);

insert into JOUEUR values (
    "Coco",
    "cocolastico@gmail.com",
    "cocopops",
    "mouton.png",
    0,
    false,
    false
);

insert into JOUEUR values (
    "Jordan",
    "jordan07@gmail.com",
    "alphawann",
    "wann.png",
    0,
    false,
    false
);

insert into JOUEUR values (
    "Le Chef",
    "gendarmerie@gmail.com",
    "jaimelespates",
    "joker.png",
    0,
    false,
    true
);

insert into JOUEUR values (
    "Lanka",
    "ghost@gmail.com",
    "jsuispaslalalalalala",
    "ghost.png",
    0,
    true,
    false
);


-- Creation amis
insert into ETREAMIS values (
    "p0slx",
    "L'ananas"
);

insert into ETREAMIS values (
    "p0slx",
    "Coco"
);

insert into ETREAMIS values (
    "Coco",
    "Le chef"
);

insert into ETREAMIS values (
    "L'ananas",
    "p0slx"
);

insert into ETREAMIS values (
    "Le chef",
    "L'ananas"
);


insert into ETREAMIS values (
    "L'ananas",
    "Jordan"
);


-- Creation parties
insert into PARTIE values (
    420,
    "Puissance 4",
    "contenuGrille",
    "p0slx",
    1,
    CURDATE(),
    CURDATE(),
    24  -- nb coup jouées
);

insert into PARTIE values (
    69,
    "Puissance 4",
    "contenuGrille",
    "Coco",
    1,
    CURDATE(),
    CURDATE(),
    128  -- nb coup jouées
);

insert into JOUER values (
    "p0slx",
    "Coco",
    420,
    16
);

insert into JOUER values (
    "Coco",
    "L'ananas",
    69,
    420
);



-- Creation messages
insert into MESSAGE values (
    12, -- idMessage
    CURDATE(),
    "Bonjour",
    false -- Lu ?
);

insert into COMMUNIQUER values (
    "L'ananas",
    "p0slx",
     12
);




-- Creation invitations

insert into INVITATION values (
    1,
    CURDATE(),
    0
);

insert into INVITATION values (
    2,
    CURDATE(),
    1
);

insert into INVITER values (
    "L'ananas",
    "Coco",
    1
);

insert into INVITER values (
    "p0slx",
    "Coco",
    2
);
