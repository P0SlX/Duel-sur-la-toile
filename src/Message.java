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
        /** Return a String indicating the content of the message */

        return this.content;
    }

    public String getDate() {
        /** Return a String indicating the sending date of the message */

        return this.date;
    }

    public String getSenderPseudo() {
        /** Return a String indicating the sender pseudo */

        return senderPseudo;
    }

    public void setSenderPseudo(String senderPseudo) {
        /** Take a String in paramater and set it as the sender pseudo */

        this.senderPseudo = senderPseudo;
    }

    public String getReceiverPseudo() {
        /** Return a String indicating the receiver pseudo */

        return receiverPseudo;
    }

    public void setReceiverPseudo(String receiverPseudo) {
        /** Take a String in paramater and set it as the receiver pseudo */

        this.receiverPseudo = receiverPseudo;
    }
}
