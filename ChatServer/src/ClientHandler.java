import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    private static List<InetAddress> users = new ArrayList<>();
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    public ClientHandler(Socket clientSocket, PrintWriter out, BufferedReader in) {
        this.clientSocket = clientSocket;
        this.out = out;
        this.in = in;
    }
    @Override
    public void run() {
        try {
            users.add(clientSocket.getInetAddress());
            System.out.println("IP: " + clientSocket.getInetAddress().getHostAddress());
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
                System.out.println("Received input: " + inputLine);
            }
        } catch(SocketException ex) {
            System.out.println(clientSocket.getInetAddress().getHostAddress() + " has disconnected");
            users.remove(clientSocket.getInetAddress());
            // Clean up after a user disconnects
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                //TODO: Handle this IOException better
                e.printStackTrace();
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
