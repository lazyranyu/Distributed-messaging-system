import java.util.Date;

/**
 * [一句话描述该类的功能]
 *
 * @author : [Lenovo]
 * @version : [v1.0]
 * @createTime : [2023/11/24 14:31]
 */
public class MessageInfo implements java.io.Serializable{
    String username;
    String receiverName;
    String date;
    String message;

    public MessageInfo(){};

    public MessageInfo(String username, String receiverName, String date, String message) {
        this.username = username;
        this.receiverName = receiverName;
        this.date = date;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageInfo{" +
                "username='" + username + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", date='" + date + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
