insert into JOUEUR values (
    "iuto",
    "mail",
    "mdp",
    "avatrt",
    -1,
    false,
    false
);

insert into JOUEUR values (
    "iutc",
    "mailiutc",
    "mdp",
    "avatrt",
    -1,
    false,
    false
);

insert into JOUEUR values (
    "Anatole",
    "mail22",
    "mdp",
    "avatrt",
    -1,
    false,
    false
);

insert into ETREAMIS values (
    "iuto",
    "Anatole"
);

insert into ETREAMIS values (
    "Anatole",
    "iuto"
);

insert into PARTIE values (
    541,
    "Puissance 4",
    "dfsdsf",
    "Anatole",
    -1,
    CURDATE(), CURDATE(),
    12
);

insert into JOUER values (
    "Anatole", "iuto", 541, 16
);

insert into JOUER values (
    "iuto", "Anatole", 541, 4 
);

insert into MESSAGE values (
    12,
    CURDATE(),
    "Bonjour",
    false
);

insert into COMMUNIQUER values (
    "Anatole", "iuto", 12
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
