import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

/**
 * [一句话描述该类的功能]
 *
 * @author : [Lenovo]
 * @version : [v1.0]
 * @createTime : [2023/11/24 15:01]
 */
public class Client {
    private static SharedMessage sharedMessage;
    private static Scanner scanner;
    public static void main(String[] args) {
        if (args.length<6)
        {
            System.out.println("参数不足！");
            return;
        }
//        String command = "RMIClient localhost 8000 register b a";
//        args = command.split(" ");
        String clientname = args[0];
        String servername = args[1];
        int portNumber = Integer.parseInt(args[2]);
        try {
            Registry registry = LocateRegistry.getRegistry(servername, portNumber);
            sharedMessage = (SharedMessage) registry.lookup("SharedMessage");
            String action = args[3];
            String username = args[4];
            String password = args[5];
            boolean tage = true;
            while (tage) {
                while (action.equals("register")) {
                    if (registerUser(username, password)) {
                        System.out.println(username + "注册成功");
                        tage = false;
                        break;
                    } else {
                        System.out.println("注册失败：用户名已存在\n");
                        System.out.println("请输入新的参数：");
                        Scanner sc1 = new Scanner(System.in);
                        String newArgs = sc1.nextLine();
                        args = newArgs.split(" ");
                        username = args[4];
                        password = args[5];
                    }
                }
                if (!action.equals("register")) {
                    System.out.println("请先注册：");
                    Scanner sc1 = new Scanner(System.in);
                    String newArgs = sc1.nextLine();
                    args = newArgs.split(" ");
                    action = args[3];
                    username = args[4];
                    password = args[5];
                }
            }
            scanner = new Scanner(System.in);

            displayMenu();
            while (true) {
                System.out.println("\n" + username + "Input an operation:");
                String choice = scanner.nextLine();
                switch (choice)
                {
                    case "1":
                        String[] ups0 = yanZhen();
                        System.out.println("你要发给谁：");
                        String receiver_name = scanner.nextLine();
                        System.out.println("信息内容是：");
                        String message_text = scanner.nextLine();
                        sendMessage(ups0[0],ups0[1],receiver_name,message_text);
                        break;
                    case "2":
                        String[] ups1 = yanZhen();
                        checkMessage(ups1[0],ups1[1]);
                        break;
                    case "3":
                        String[] ups2 = yanZhen();
                        showMessage();
                        break;
                    case "4":
                        help();
                        break;
                    case "5":
                        System.out.println("系统退出");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("命令错误");
                        break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

   private static void displayMenu() {
        System.out.println("Message Menu:");
        System.out.println("\t\t1.leaveMessage\n\t\t\t<argument>: <username> <password> <receiver_name> <message_text>");
        System.out.println("\t\t2.checkMessages\n\t\t\t<argument>: <username> <password>");
        System.out.println("\t\t3.showUsers\n\t\t\t<argument>: no args");
        System.out.println("\t\t4.help\n\t\t\t<argument>: no args");
        System.out.println("\t\t5.quit\n\t\t\t<argument>: no args");
    }

  private static boolean registerUser(String username, String password) {
        try {
            //调用sharedMessage的registerUser方法，注册用户
            boolean registerResult = sharedMessage.registerUser(username,password );
            return registerResult;
        } catch (Exception e) {
            System.err.println("注册用户异常！");
            e.printStackTrace();
        }
        return false;
    }
    private static void sendMessage(String username, String password, String receiverName, String messageText) throws RemoteException {
       String result = sharedMessage.leaveMessage(username, password, receiverName, messageText);
        if (result.startsWith("发送信息成功"))
       {
           System.out.println(result);
       }else {
           System.out.println(result);
       }
    }
    private static void checkMessage(String username, String password) throws RemoteException {
        List<MessageInfo> messages = new ArrayList<>();
        messages = sharedMessage.checkMessage(username, password);
        if (messages.size() == 0) {
            System.out.println("暂无消息！");
        } else if (messages.size() ==1&&messages.get(0).getDate().equals("0")) {
            System.out.println("搜索信息失败：用户名或密码错误！");
        }else {
            for (MessageInfo messageInfo:messages) {
                System.out.println(messageInfo.toString());
            }
        }
    }
    private static void showMessage() throws RemoteException {
        String users = sharedMessage.showUsers();
        System.out.println("已注册的用户有：");
        System.out.println(users);
    }
    private static void help() {
        System.out.println("leaveMessage\t\t\t\t用于给其他用户留言，首先需校验用户名和密码是否正确。若不正确，请给出相应的提示，留言不成功；接着需校验接收者用户名是否存在，若不存在，请给出相应的提示，留言不成功；若以上校验均正确，则系统记录留言的日期和时间、留言内容等信息。");
        System.out.println("checkMessages\t\t\t打印用户的所有留言，留言信息包括：留言者、日期和时间、留言内容。注意，如果用户名和密码不对，应有相应的提示信息；如果该用户没有任何留言，也应该有提示。");
        System.out.println("showUsers\t\t\t用于显示所有注册的用户。");
    }
    private static String[] yanZhen(){
        System.out.println("请输入账号密码，验证身份：");
        String up = scanner.nextLine();
        String[] ups = up.split(" ");
        return ups;
    }
}
