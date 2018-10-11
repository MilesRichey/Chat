import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    private static final String HOST_NAME = "localhost";
    private static final int PORT = 8817;
    private PrintWriter out;
    private Socket echoSocket;
    public void sendMessage(String msg) {
        out.println(msg);
    }
    public ChatClient(String id) {
        try {
            this.echoSocket = new Socket(HOST_NAME, PORT);
            this.out = new PrintWriter(echoSocket.getOutputStream(), true);
            out.println("id:" + id);
            new Thread(() -> {
                try {
                    String serverInput;
                    BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                    while ((serverInput = in.readLine()) != null) {
                        System.out.println(serverInput);
                    }
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
            }).start();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + HOST_NAME);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + HOST_NAME);
            System.exit(1);
        }
    }
}
