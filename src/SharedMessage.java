import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;

public interface SharedMessage extends Remote {
    boolean registerUser(String username,String password)throws RemoteException;
    String showUsers()throws RemoteException;
    List<MessageInfo> checkMessage(String username, String password)throws RemoteException;
    String leaveMessage(String username,String password,String receiver_name,String message_next)throws RemoteException;
}
