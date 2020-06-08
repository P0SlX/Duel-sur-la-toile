insert into JOUEUR values (
    "p0slx",
    "florian.savoure@gmail.com",
    "unMotDePasseSecure",
    "truc.png",
    0,
    false,
    false
);

insert into JOUEUR values (
    "L'ananas",
    "lananas@gmail.com",
    "jaimeLesPommes",
    "ananas.png",
    1,
    false,
    false
);

insert into JOUEUR values (
    "Coco",
    "cocolastico@gmail.com",
    "cocopops",
    "oui.png",
    2,
    false,
    false
);

insert into ETREAMIS values (
    "Coco",
    "L'ananas"
);

insert into ETREAMIS values (
    "L'ananas",
    "p0slx"
);

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
