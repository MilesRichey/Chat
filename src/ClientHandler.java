import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler implements Runnable {
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
            System.out.println("IP: " + clientSocket.getInetAddress().getHostName());
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
                System.out.println("Received input: " + inputLine);
            }
        } catch(SocketException ex) {
            System.out.println(clientSocket.getInetAddress().getHostName() + " has disconnected");
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
