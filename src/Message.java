public class Message{

  private String content;
  private String date;

  public Message(String content, String date){
    this.content = content;
    this.date = date;
  }

  public String getContent(){ return this.content; }

  public String getDate(){ return this.date; }

}
