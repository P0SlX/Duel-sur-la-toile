public class Message{

    private String content;
    private String date;
    private String senderPseudo;
    private String receiverPseudo;

    public Message(String content, String date, String senderPseudo, String receiverPseudo){
        this.content = content;
        this.date = date;
        this.senderPseudo = senderPseudo;
        this.receiverPseudo = receiverPseudo;
    }

    public Message(String content) {
        this.content = content;
        this.date = new String();
    }

    public String getContent() {
        return this.content;
    }

    public String getDate() {
        return this.date;
    }

    public String getSenderPseudo() {
        return senderPseudo;
    }

    public void setSenderPseudo(String senderPseudo) {
        this.senderPseudo = senderPseudo;
    }

    public String getReceiverPseudo() {
        return receiverPseudo;
    }

    public void setReceiverPseudo(String receiverPseudo) {
        this.receiverPseudo = receiverPseudo;
    }
}
