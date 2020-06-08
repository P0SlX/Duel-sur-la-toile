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
    -1,
    CURDATE(),
    CURDATE(),
    12  -- nb coup jouées
);

insert into PARTIE values (
    69,
    "Puissance 4",
    "contenuGrille",
    "Coco",
    -1,
    CURDATE(),
    CURDATE(),
    12  -- nb coup jouées
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

insert into MESSAGE values (
    12, -- idMessage
    CURDATE(),
    "Bonjour",
    false -- Lu ?
);

insert into COMMUNIQUER values (
    "Anatole",
    "iuto",
     12
);

insert into PARTIE values (
    542,
    "Puissance 4",
    "dfsdsf",
    "iuto",
    19,
    CURDATE(), CURDATE(),
    12
);

insert into JOUER values (
    "iuto",
    "iutc",
    542,
    8
);

insert into JOUER values (
    "iutc",
    "iuto",
    542,
    8
);
insert into PARTIE values (
    543,
    "Puissance 4",
    "dfsdsf",
    "iuto",
    -1,
    CURDATE(), CURDATE(),
    12
);

insert into JOUER values (
    "iuto",
    "iutc",
    543,
    8
);

insert into JOUER values (
    "iutc",
    "iuto",
    543,
    2
);
