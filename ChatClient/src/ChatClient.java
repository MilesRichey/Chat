import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ChatClient {
    private static String hostName = "localhost";
    private static int hostPort = 8817;
    private PrintWriter out;
    private Socket echoSocket;

    public ChatClient(String host, String port, String id) {
        this.hostName = host;
        this.hostPort = Integer.parseInt(port);
        try {
            this.echoSocket = new Socket(hostName, hostPort);
            this.out = new PrintWriter(echoSocket.getOutputStream(), true);
            out.println("id:" + id);
            new Thread(() -> {
                try {
                    String serverInput;
                    BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                    while ((serverInput = in.readLine()) != null) {
                        System.out.println(serverInput);
                        String[] splitChat = serverInput.split(":");
                        if(splitChat.length == 1) { // Server message
                            ChatApp.updateChat(splitChat[0]);
                            continue;
                        }
                        String user = splitChat[0];
                        String msg = splitChat[1];
                        ChatApp.updateChat(String.format("%s: %s", user, msg));
                    }
                } catch (SocketException ex) {
                    System.err.println("Socket Error, this is normal if in the process of exiting");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }).start();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    void stop() {
        try {
            echoSocket.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
