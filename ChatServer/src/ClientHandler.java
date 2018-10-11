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
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String identifier;
    private boolean active = false;
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    public void sendMessage(String msg) {
        out.println(msg);
    }
    public String getIdentifier() {
        return this.identifier;
    }
    @Override
    public void run() {
        try {
            System.out.println("== Client Accepted(IP: " + clientSocket.getInetAddress().getHostAddress() + ") ==");
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if(active) {
                    for (ClientHandler ch : ChatServer.USERS) {
                        //Relaying message to each individual client
                        ch.sendMessage(this.getIdentifier() + ":" + inputLine);
                    }
                } else {
                    if(inputLine.startsWith("id:")) {
                        this.identifier = inputLine.split("id:")[1];
                        this.active = true;
                    }
                }
            }
        } catch(SocketException ex) {
            System.out.println(clientSocket.getInetAddress().getHostAddress() + " has disconnected");
            ChatServer.USERS.remove(this);
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
