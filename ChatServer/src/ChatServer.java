import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    private static final int PORT = 8817;

    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.printf("Unable to establish a connection on port: %d\n", PORT);
            e.printStackTrace();
            return;
        }
        System.out.printf("Listening for connections on %s:%d...\n", serverSocket.getInetAddress().getHostAddress(), PORT);
        try (Socket clientSocket = serverSocket.accept();
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            //System.out.println("==== Client Accepted ====");
            ClientHandler cliHandle = new ClientHandler(clientSocket, out, in);
            cliHandle.run();
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + PORT + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}