public interface Invitation {
    public Player getSender();
    public Player getReceiver();
    public String getEmissionDate();
    public int getEtatInv();
    public int getId();

    public void accept();
    public void decline();

}