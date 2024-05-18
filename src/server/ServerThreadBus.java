package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerThreadBus {
    private List<ServerThread> listServerThreads;

    public List<ServerThread> getListServerThreads() {
        return listServerThreads;
    }

    public ServerThreadBus() {
        listServerThreads = new ArrayList<>();
    }

    public void add(ServerThread serverThread) {
        listServerThreads.add(serverThread);
    }

    public void mutilCastSend(String message) {
        for (ServerThread serverThread : getListServerThreads()) {
            try {
                serverThread.write(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void boardCast(int id, String message) {
        for (ServerThread serverThread : getListServerThreads()) {
            if (serverThread.getClientNumber() == id) {
                continue;
            } else {
                try {
                    serverThread.write(message);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public int getLength() {
        return listServerThreads.size();
    }

    public void sendOnlineList() {
        StringBuilder res = new StringBuilder();
        for (ServerThread serverThread : listServerThreads) {
            res.append(serverThread.getUserName()).append("-");
        }
        mutilCastSend("update-online-list," + res.toString());
    }

    public void sendMessageToPersion(String username, String message) {
        for (ServerThread serverThread : listServerThreads) {
            if (serverThread.getUserName().equals(username)) {
                try {
                    serverThread.write("global-message," + message);
                    break;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void remove(int id) {
        listServerThreads.removeIf(serverThread -> serverThread.getClientNumber() == id);
    }
}