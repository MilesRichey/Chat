import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatServer {
    private static final int PORT = 8817;
    private static boolean RUNNING = true;
    public static List<ClientHandler> USERS = new ArrayList<>();

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
        while(RUNNING) {
            Socket client;
            try {
                client = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Exception caught when trying to listen on port " + PORT + " or listening for a connection");
                System.out.println(e.getMessage());
                continue;
            }
            ChatServer.USERS.add(new ClientHandler(client));
            ChatServer.USERS.get(USERS.size()-1).start();
        }
    }
}