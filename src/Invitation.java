public interface Invitation {
    public Player getSender();
    public String getEmissionDate();

    public void accept();
    public void decline();

}
