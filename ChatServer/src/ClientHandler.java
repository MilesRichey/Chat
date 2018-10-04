import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler extends Thread {
    public static List<InetAddress> users = new ArrayList<>();
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            users.add(clientSocket.getInetAddress());
            System.out.println("== Client Accepted(IP: " + clientSocket.getInetAddress().getHostAddress() + ") ==");
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
                System.out.println("Received input: " + inputLine);
            }
        } catch(SocketException ex) {
            System.out.println(clientSocket.getInetAddress().getHostAddress() + " has disconnected");
            users.remove(clientSocket.getInetAddress());
        } catch(IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                clientSocket.close();
            } catch (IOException e) {
                //TODO: Handle this IOException better
                e.printStackTrace();
            }
        }
    }
}
