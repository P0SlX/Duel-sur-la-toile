public interface Invitation {
    int REFUSED  = -1;
    int PENDING  = 0;
    int ACCEPTED = 1;
    boolean INVGAME = true;
    boolean INVFRIEND = false;

    public Player getSender();
    public Player getReceiver();
    public java.sql.Date getEmissionDate();
    public int getEtatInv();
    public int getId();

    public void accept();
    public void decline();

}
