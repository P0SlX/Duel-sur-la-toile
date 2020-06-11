-- Creation des Joueurs
insert into JOUEUR values (
    "p0slx",
    "florian.savoure@gmail.com",
    "unMotDePasseSecure",
    'img/logo.png',
    0,
    false,
    false
);

insert into JOUEUR values (
    "L'ananas",
    "lananas@gmail.com",
    "jaimeLesPommes",
    null,
    0,
    false,
    false
);

insert into JOUEUR values (
    "Coco",
    "cocolastico@gmail.com",
    "cocopops",
    null,
    0,
    false,
    false
);

insert into JOUEUR values (
    "Jordan",
    "jordan07@gmail.com",
    "alphawann",
    null,
    0,
    false,
    false
);

insert into JOUEUR values (
    "admin",
    "admin@gmail.com",
    "admin",
    null,
    0,
    false,
    True
);

insert into JOUEUR values (
    "Le Chef",
    "gendarmerie@gmail.com",
    "password",
    null,
    0,
    false,
    true
);

insert into JOUEUR values (
    "Lanka",
    "ghost@gmail.com",
    "jsuispaslalalalalala",
    null,
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

insert into ETREAMIS values (
    "admin",
    "L'ananas"
);

insert into ETREAMIS values (
    "admin",
    "Jordan"
);

insert into ETREAMIS values (
    "admin",
    "Le Chef"
);

insert into ETREAMIS values (
    "admin",
    "p0slx"
);

insert into ETREAMIS values (
    "admin",
    "Coco"
);


-- Creation parties
insert into PARTIE values (
    420,
    "Puissance 4",
    "contenuGrille",
    "p0slx",
    0,
    CURDATE(),
    CURDATE(),
    7,
    null,
    null
);

insert into PARTIE values (
    69,
    "Puissance 4",
    "contenuGrille",
    "Coco",
    1,
    CURDATE(),
    CURDATE(),
    18,
    "Coco",
    "L'ananas"
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


insert into MESSAGE values (
    13, -- idMessage
    CURDATE(),
    "t la ?",
    false -- Lu ?
);

insert into COMMUNIQUER values (
    "L'ananas",
    "p0slx",
     13
);
insert into MESSAGE values (
    14, -- idMessage
    CURDATE(),
    "Bon anniversaire !!!!!!!",
    false -- Lu ?
);

insert into COMMUNIQUER values (
    "L'ananas",
    "Le chef",
     14
);
insert into MESSAGE values (
    15, -- idMessage
    CURDATE(),
    "Bonjour",
    false -- Lu ?
);

insert into COMMUNIQUER values (
    "Jordan",
    "p0slx",
     15
);


-- Creation invitations

insert into INVITATION values (
    1,
    CURDATE(),
    0,
    False
);

insert into INVITATION values (
    2,
    CURDATE(),
    1,
    False
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
