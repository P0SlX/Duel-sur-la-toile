public interface Invitation {
    public Player getSender();
    public Player getReceiver();
    public String getEmissionDate();

    public void accept();
    public void decline();

}
