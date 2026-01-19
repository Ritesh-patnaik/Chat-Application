import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class p_2_Client {
    private Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;
    private Scanner sc;

    public p_2_Client(){
        try {
            socket = new Socket("127.0.0.1", p_2_Server.PORT);
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
            sc = new Scanner(System.in);

            getFile();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            in.close();
            out.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getFile() throws IOException {
        String filesLen = in.readUTF();
        int maxFiles = Integer.parseInt(filesLen);
        String menu = in.readUTF();
        System.out.println(menu);

        int userSelection = 1;
        boolean isSelectionCorrect = false;
        while (!isSelectionCorrect){
            System.out.println("Select a file Number");
            userSelection = sc.nextInt();
            isSelectionCorrect = userSelection>0 && userSelection<=maxFiles;
        }
        out.writeUTF(""+userSelection);
        String fileContent = in.readUTF();

        System.out.println("--FILE START--");
        System.out.println(fileContent);
        System.out.println("--FILE END--");
    }

    public static void main(String[] args) {
        new p_2_Client();
    }
}
