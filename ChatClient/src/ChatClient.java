import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    private static final String HOST_NAME = "srv.raring.co";
    private static final int PORT = 8817;

    public static void main(String[] args) {
        try (
                Socket echoSocket = new Socket(HOST_NAME, PORT);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("==== CLIENT STARTED ====");
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                if(userInput.equalsIgnoreCase("quit")) {
                    System.out.println("Quitting...");
                    out.close();
                    in.close();
                    echoSocket.close();
                    break;
                }
                out.println(userInput);
                out.flush();
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + HOST_NAME);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + HOST_NAME);
            System.exit(1);
        }
    }
}
