import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.List;

public class p_2_ClientConnection {
    private Socket  socket;
    private DataInputStream in;
    private DataOutputStream out;
    public p_2_ClientConnection(Socket socket){
        this.socket = socket;
        try{
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void sendFile() throws IOException {
        try {
            sendMenu();
            int index = getSelectedFileIndex();
            sendSelectedFile(index);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendSelectedFile(int index) throws IOException {
        File[] fileList = new File(p_2_Server.FILE_PATH).listFiles();
        //assert fileList != null;
        File selectedFile = fileList[index];
        List<String> fileLines = Files.readAllLines(selectedFile.toPath());
        String fileContent = String.join("\n", fileLines);
        out.writeUTF(fileContent);
    }

    private int getSelectedFileIndex() throws IOException{
        String input = in.readUTF();
        return Integer.parseInt(input)-1;
    }

    private void sendMenu() throws Exception{
        String menu = "*Files*\n";
        File[] fileList = new File(p_2_Server.FILE_PATH).listFiles();
        out.writeUTF(""+fileList.length);

        for (int i = 0; i < fileList.length; i++) {
            menu += String.format("* %d - %s\n", i+1, fileList[i].getName());

        }
        out.writeUTF(menu);
    }
}
