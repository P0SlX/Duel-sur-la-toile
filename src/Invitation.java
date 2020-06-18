public interface Invitation {
    int REFUSED  = -1;
    int PENDING  = 0;
    int ACCEPTED = 1;
    boolean INVGAME = true;
    boolean INVFRIEND = false;

    Player getSender();
    Player getReceiver();
    java.sql.Date getEmissionDate();
    int getEtatInv();
    int getId();

    void accept();
    void decline();

}
