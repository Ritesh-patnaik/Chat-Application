import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class p_1_UserTwo implements ActionListener, Runnable {
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    BufferedReader reader;
    BufferedWriter writer;
    String name = "Patnaik";
    p_1_UserTwo(){   //constructor
        f.setLayout(null);
        JPanel p1 = new JPanel();//upper part of Layout
        p1.setBackground(new Color(7,94,84));//custom color
        p1.setBounds(0,0,450,70);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons2gc/3 (1).png"));
        Image i2 = i1.getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT);

        ImageIcon i3 = new ImageIcon(i2);

        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons2gc/mirzapur.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);

        JLabel profile = new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);



        //Set name of the person
        JLabel name = new JLabel("User Two");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.WHITE);
        name.setFont((new Font("SAN_SERIF",Font.BOLD,18)));
        p1.add(name);

        /*JLabel status = new JLabel("Active Now");
        status.setBounds(110,35,100,18);
        status.setForeground(Color.WHITE);
        status.setFont((new Font("SAN_SERIF",Font.BOLD,14)));
        p1.add(status);*/

        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        f.add(a1);

        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text);

        //Send Button
        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        //action at click
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));

        f.add(send);


        f.setSize(450,700);
        f.setLocation(490,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);

        f.setVisible(true);

        try{
            Socket socket = new Socket("localhost", 2003);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void actionPerformed(ActionEvent e){
        try {
            String out = "<html><p>"+name+"</p><p>"+text.getText()+"</p></html>";

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            a1.add(vertical, BorderLayout.PAGE_START);


            try {
                writer.write(out);
                writer.write("\r\n");
                writer.flush();
            }catch (Exception e1){
                e1.printStackTrace();
            }

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        }catch (Exception e1){
            e1.printStackTrace();
        }

    }
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">"+out+"</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(0,15,0,50));

        panel.add(output);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        panel.add(output);
        return panel;
    }
    public void run(){
        try {
            String msg = "";
            while (true){
                msg = reader.readLine();

                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.setBackground(Color.WHITE);
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);

                a1.add(vertical, BorderLayout.PAGE_START);

                f.repaint();
                f.invalidate();
                f.validate();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        p_1_UserTwo two = new p_1_UserTwo();
        Thread t2 = new Thread(two);
        t2.start();

    }
}
