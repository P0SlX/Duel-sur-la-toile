public class Message{

    private final String content;
    private final String date;
    private String senderPseudo;
    private String receiverPseudo;
    private int id;
    private boolean isRead;

    public Message(String content, String date, String senderPseudo, String receiverPseudo, int id, boolean isRead){
        this.content = content;
        this.date = date;
        this.senderPseudo = senderPseudo;
        this.receiverPseudo = receiverPseudo;
        this.id = id;
        this.isRead = isRead;
    }

    /**
     * builder of message
     * @param content, String content of the message
     */
    public Message(String content) {
        this.content = content;
        this.date = "";
    }

    /**
     * @return String, the content of the message
     */
    public String getContent() {
        return this.content;
    }

    /**
     * @return String, the send date of the message
     */
    public String getDate() {
        return this.date;
    }

    /**
     * @return String, the pseudo of the sender
     */
    public String getSenderPseudo() {
        return this.senderPseudo;
    }

    /**
     * Allow to change the pseudo of the sender
     * @param senderPseudo String, the pseudo of the new sender
     */
    public void setSenderPseudo(String senderPseudo) {
        this.senderPseudo = senderPseudo;
    }

    /**
     * @return String, the pseudo of the receiver
     */
    public String getReceiverPseudo() {
        return this.receiverPseudo;
    }

    /**
     * Allow to change the receiver's pseudo
     * @param receiverPseudo String, the pseudo of the new receiver
     */
    public void setReceiverPseudo(String receiverPseudo) {
        this.receiverPseudo = receiverPseudo;
    }
}
