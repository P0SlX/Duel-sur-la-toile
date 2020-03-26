-- Liste des parties en cours
select idPartie, pseudo
from PARTIE natural join JOUER natural join JOUEUR
where etatpartie > 0 and pseudo='iuto';
-- Message non lu
select idMessage, contenumessage, destinataire
from MESSAGE natural join COMMUNIQUER
where destinataire='iuto' and not messagelu;
-- Toutes les parties gagnés par IUT contre le joueur IUTC
select idPartie, pseudo, count(idPartie) as nbPartieGagnes
from PARTIE natural join JOUER
where adversaire='iutc' and etatpartie=-1 and nomJoueurCourant='iuto';
