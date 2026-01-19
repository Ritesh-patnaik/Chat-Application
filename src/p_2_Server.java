import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class p_2_Server {
    public static final String FILE_PATH = "./TextFiles";
    //public static int FILE_PATH= Integer.parseInt("./TextFiles");
    private ServerSocket serverSocket;
    public static final int PORT = 3030;

    public p_2_Server(){
        try {
            serverSocket = new ServerSocket(PORT);
            acceptConnections();
        }catch (IOException e){e.printStackTrace();}

    }
    private void acceptConnections() throws IOException {
        while (true){
            Socket clientSocket = serverSocket.accept();
            if (clientSocket.isConnected())
                new Thread(()->{
                    p_2_ClientConnection client = new p_2_ClientConnection(clientSocket);
                    try {
                        client.sendFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    p_2_Client.close();
                }).start();
        }
    }

    public static void main(String[] args) {new p_2_Server();}
}
