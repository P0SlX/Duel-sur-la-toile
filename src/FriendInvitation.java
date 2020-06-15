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
        // Return a Player which is the sender of the invitation

        return this.emitter;
    }

    @Override
    public Player getReceiver() {
        // Return a Player which is the receiver of the invitation

        return this.receiver;
    }

    @Override
    public java.sql.Date getEmissionDate() {
        // Return a Date which is the sending date

        return emissionDate;
    }

    @Override
    public int getEtatInv() {
        // Return an int which is the state of the invitation (-1, 0 or 1)

        return this.etatInv;
    }

    @Override
    public int getId() {
        // Return an int which is the id of the invitation

        return this.id;
    }

    @Override
    public void accept() {
        // Change the state of the invitation to 1

        this.etatInv = ACCEPTED;
    }

    @Override
    public void decline() {
        // Change the state of the invitation to -1

        this.etatInv = REFUSED;
    }
}
