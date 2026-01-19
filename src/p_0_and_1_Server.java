import java.io.*;
import java.net.*;
import java.util.*;

public class p_0_and_1_Server implements Runnable{
    Socket socket;
    public static Vector<Object> client = new Vector<>();
    BufferedWriter writer;
    public p_0_and_1_Server(Socket socket){
        try {
            this.socket = socket;
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void run(){//used for Multithreading
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            client.add(writer);

            while (true){
                String data = reader.readLine().trim();
                System.out.println("Received: "+data);

                for (int i = 0; i < client.size(); i++) {
                    try {
                        BufferedWriter bw = (BufferedWriter) client.get(i);
                        if (bw != writer) {
                            bw.write(data);
                            bw.write("\r\n");
                            bw.flush();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            ServerSocket s = new ServerSocket(2003);
            while (true) {
                Socket socket = s.accept();
                p_0_and_1_Server server = new p_0_and_1_Server(socket);
                Thread thread = new Thread(server);
                thread.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
