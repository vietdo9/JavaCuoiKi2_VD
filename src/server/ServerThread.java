package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ServerThread implements Runnable {

    private Socket socketOfServer;
    private int clientNumber;
    private BufferedReader is;
    private BufferedWriter os;
    private boolean isClosed;
    private String userName;

    public BufferedReader getIs() {
        return is;
    }

    public BufferedWriter getOs() {
        return os;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public String getUserName() {
        return userName;
    }

    public ServerThread(Socket socketOfServer, int clientNumber) {
        this.socketOfServer = socketOfServer;
        this.clientNumber = clientNumber;
        System.out.println("Server thread number " + clientNumber + " Started");
        isClosed = false;
    }

    @Override
    public void run() {
        try {
            is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
            System.out.println("Khởi động luồng mới thành công, ID là: " + clientNumber);
            write("get-id" + "," + this.clientNumber);

            String nameMessage = is.readLine();
            if (nameMessage.startsWith("set-name")) {
                String[] nameSplit = nameMessage.split(",");
                userName = nameSplit[1];
                
                write("get-username" + "," + userName);
                
                System.out.println(userName);
            }

            Server.serverThreadBus.sendOnlineList();
            Server.serverThreadBus.mutilCastSend("global-message" + "," + "---Client " + userName + " đã đăng nhập---");
            String message;
            while (!isClosed) {
                message = is.readLine();
                if (message == null) {
                    break;
                }
                String[] messageSplit = message.split(",");
                if (messageSplit[0].equals("send-to-global")) {
                    Server.serverThreadBus.boardCast(this.getClientNumber(), "global-message" + "," + "Client " + userName + ": " + messageSplit[1]);
                }
                if (messageSplit[0].equals("send-to-person")) {
                    Server.serverThreadBus.sendMessageToPersion(messageSplit[4],"Client " + userName + " (tới bạn): " + messageSplit[1]);
                    
                    System.out.println(messageSplit[4]);
                }
            }
        } catch (IOException e) {
            isClosed = true;
            Server.serverThreadBus.remove(clientNumber);
            System.out.println(this.clientNumber + " đã thoát");
            Server.serverThreadBus.sendOnlineList();
            Server.serverThreadBus.mutilCastSend("global-message" + "," + "---Client " + userName + " đã thoát---");
        }
    }

    public void write(String message) throws IOException {
        os.write(message);
        os.newLine();
        os.flush();
    }
}