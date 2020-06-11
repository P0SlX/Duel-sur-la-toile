public interface Invitation {
    public static final int REFUSED  = -1;
    public static final int PENDING  = 0;
    public static final int ACCEPTED = 1;
    public static final boolean INVGAME = true;
    public static final boolean INVFRIEND = false;

    public Player getSender();
    public Player getReceiver();
    public java.sql.Date getEmissionDate();
    public int getEtatInv();
    public int getId();

    public void accept();
    public void decline();

}
