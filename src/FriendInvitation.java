public class FriendInvitation implements Invitation {

    private Player emitter;
    private Player receiver;
    private java.sql.Date emissionDate;
    private int etatInv;
    private int id;

    public FriendInvitation(Player emitter, Player receiver, java.sql.Date emissionDate) {
        this.emitter = emitter;
        this.receiver = receiver;
        this.emissionDate = emissionDate;
        this.etatInv = 0;
        this.id = 0;                // TODO setId()
    }

    @Override
    public Player getSender() {
        return this.emitter;
    }

    @Override
    public Player getReceiver() {
        return this.receiver;
    }

    @Override
    public java.sql.Date getEmissionDate() {
        return emissionDate;
    }

    @Override
    public int getEtatInv() {
        return this.etatInv;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void accept() {
        this.etatInv = 1;
    }

    @Override
    public void decline() {
        this.etatInv = -1;
    }
}
