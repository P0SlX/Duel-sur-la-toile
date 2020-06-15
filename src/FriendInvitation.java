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
        /**
         * @return PLayer, the sender of the invitation
         */

        return this.emitter;
    }

    @Override
    public Player getReceiver() {
        /**
         * @return Player, the receiver of the invitation
         */

        return this.receiver;
    }

    @Override
    public java.sql.Date getEmissionDate() {
        /**
         * @return Date, the date of sending
         */

        return emissionDate;
    }

    @Override
    public int getEtatInv() {
        /**
         * @return int, the state of the invitation (-1, 0 or 1)
         */

        return this.etatInv;
    }

    @Override
    public int getId() {
        /**
         * @return int, the id of the invitation
         */

        return this.id;
    }

    @Override
    public void accept() {
        /**
         * Change the state of the invitation to 1
         */

        this.etatInv = ACCEPTED;
    }

    @Override
    public void decline() {
        /**
         * Change the state of the invitation to -1
         */

        this.etatInv = REFUSED;
    }
}
