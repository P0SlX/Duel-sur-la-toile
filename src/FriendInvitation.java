public class FriendInvitation implements Invitation {

    private Player emitter;
    private Player receiver;
    private java.sql.Date emissionDate;
    private int etatInv;
    private int id;

    public FriendInvitation(Player emitter, Player receiver, java.sql.Date emissionDate, int etatInv, int id) {
        this.emitter = emitter;
        this.receiver = receiver;
        this.emissionDate = emissionDate;
        this.etatInv = etatInv;
        this.id = id;
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
        this.etatInv = ACCEPTED;
    }

    @Override
    public void decline() {
        this.etatInv = REFUSED;
    }
}
