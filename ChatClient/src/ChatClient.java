import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    private static final String HOST_NAME = "localhost";
    private static final String IDENTIFIER = "comp1";
    private static final int PORT = 8817;

    public static void main(String[] args) {
        try {
            Socket echoSocket = new Socket(HOST_NAME, PORT);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            out.println("id:" + IDENTIFIER);
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
            new Thread(() -> {
                try {
                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                    String userInput;
                    while ((userInput = stdIn.readLine()) != null) {
                        if (userInput.equalsIgnoreCase("quit")) {
                            System.out.println("Quitting...");
                            break;
                        } else {
                            out.println(userInput);
                        }
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
