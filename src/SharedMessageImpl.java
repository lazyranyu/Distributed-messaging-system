import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.text.SimpleDateFormat;
import java.io.Serializable;
/**
 * [一句话描述该类的功能]
 *
 * @author : [Lenovo]
 * @version : [v1.0]
 * @createTime : [2023/11/24 14:11]
 */
public class SharedMessageImpl extends UnicastRemoteObject implements SharedMessage,java.io.Serializable{
    private HashMap<String,String> users;
    private Vector<MessageInfo> messageInfos;
    // 创建 SimpleDateFormat 对象
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH:mm");
    protected SharedMessageImpl()throws RemoteException {
        super();
        users = new HashMap<>();
        messageInfos = new Vector<>();
    }

    @Override
    public boolean registerUser(String username, String password) throws RemoteException {
        if (users.containsKey(username))
        {
            System.out.println("注册失败：用户名已存在！");
            return false;
        }else {
            users.put(username, password);
            System.out.println("客户"+username+"注册成功！");
            return true;
        }

    }

    @Override
    public String showUsers() throws RemoteException {
        for (Map.Entry<String,String> entry:users.entrySet()) {
            System.out.println("用户:"+entry.getKey()+" 密码:"+entry.getValue());
        }
        String[] user_list = new String[users.size()];
        user_list = users.keySet().toArray(user_list);
        String out ="";
        for (String user:user_list) {
            out = out + user + "\n";
        }
        return out;
    }

    @Override
    public List<MessageInfo> checkMessage(String username, String password) throws RemoteException {
        List<MessageInfo> messages = new Vector<>();
        if (!users.get(username).equals(password)){
            String out = "搜索信息失败：用户名或密码错误！";
            System.out.println("搜索信息失败：用户名或密码错误");
            MessageInfo messageInfo = new MessageInfo("0", "0", "0","0");
            messages.add(messageInfo);
        }else {
            for (MessageInfo messageInfo:messageInfos) {
                if (messageInfo.getReceiverName().equals(username))
                {
                    messages.add(messageInfo);
                }
            }
        }
        return messages;
    }

    @Override
    public String leaveMessage(String username, String password, String receiver_name, String message_next) throws RemoteException {
        if (!users.get(username).equals(password))
        {
            String out = "发送信息失败：用户名或密码错误！";
            System.out.println("发送信息失败：用户名或密码错误");
            return out;
        }
        if (!users.containsKey(username)||!users.containsKey(receiver_name))
        {
            String out = "发送信息失败：用户不存在！";
            System.out.println("发送信息失败：用户不存在！");
            return out;
        }

        Date date =new Date();
        String formattedDate = sdf.format(date);
        MessageInfo messageInfo = new MessageInfo(username,receiver_name,formattedDate,message_next);
        messageInfos.add(messageInfo);
        System.out.println(username+"发向"+receiver_name+"的信息发送成功");
        String out = "发送信息成功！";
        return out;

    }

}
