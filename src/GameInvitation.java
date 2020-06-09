public class GameInvitation implements Invitation {
    public static final int REFUSED  = -1;
    public static final int PENDING  = 0;
    public static final int ACCEPTED = 1;

    private Player emitter;
    private Player receiver;
    private String emissionDate;
    private int etatInv;
    private int id;

    public GameInvitation(Player emitter, Player receiver, String emissionDate) {
        this.emitter = emitter;
        this.receiver = receiver;
        this.emissionDate = emissionDate;
        this.etatInv = 0;
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
    public String getEmissionDate() {
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
        this.etatInv = 2;
    }
}
