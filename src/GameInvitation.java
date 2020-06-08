public class GameInvitation implements Invitation {

    private Player emitter;
    private Player receiver;
    private String emissionDate;

    public GameInvitation(Player emitter, Player receiver, String emissionDate) {
        this.emitter = emitter;
        this.receiver = receiver;
        this.emissionDate = emissionDate;
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
    public void accept() {
        // TODO
    }

    @Override
    public void decline() {
        // TODO
    }
}
