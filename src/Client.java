import javax.swing.*;

import org.json.simple.parser.ParseException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.Vector;
import java.util.Locale.Category;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Client {
    private Socket player;
    private String name;
    private boolean flag;
    private JTextArea chatText;
    private JButton send;
    private JPanel chatbox;
    private JTextField chatMessage;
    private ExecutorService Thread_ex;
    private BufferedReader client_buffered_reader;
    
    public Client(String IP, String name ) {
        this.name=name;
        chatMessage=new JTextField();
        chatText=new JTextArea();
        chatbox=new JPanel();
        send=new JButton("Send");
        chatbox.setSize(250,200);
        chatbox.setLayout(null);
        chatText.setBounds(25, 25, 200, 125);
        chatText.setEditable(false);
        chatMessage.setBounds(25, 150, 125, 25);
        send.setBounds(150,150,75,25);
        chatbox.add(send);
        chatbox.add(chatText);
        chatbox.add(chatMessage);
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!chatMessage.getText().trim().isEmpty()) {
                    sendMassage(chatMessage.getText().trim()+" CHAT");
                    chatMessage.setText(null);
                }
            }
        });
        chatMessage.addActionListener(send.getActionListeners()[0]);
        this.name = name;
        try {
            player = new Socket(InetAddress.getByName(IP),Finals.port_number);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Can't Create Client because can't get Server IP Address", "Error", JOptionPane.ERROR_MESSAGE);
        }
        try {
            if((new BufferedReader(new InputStreamReader(player.getInputStream())).readLine()).equalsIgnoreCase("Accepted"))
            {
            flag=true;
           Finals.terminal=new Command_executer();
           getMap();
            Base_finder bs=new Base_finder(getPlayernumber()); //It gets map and set Player Numbers
           Finals.nation=getnation()-1;
            new Game_frame(bs.get_HQ_points(),false);
            
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            player.getOutputStream().write((name+"\n").getBytes());
            player.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Runnable client_listener = new Runnable()//gets messages and send them to executor service
        {
            @Override
            public void run()
            {
                Client.this.getMessges();
            }
        };
        Thread_ex = Executors.newCachedThreadPool();
        Thread_ex.execute(client_listener);
    }
    private void getMessges() {
        Runnable runnable = new Runnable() {
            public void run() {
                while (player.isConnected()&&flag) {
                    try {
                        //String message=(new BufferedReader(new InputStreamReader(player.getInputStream())).readLine());/////should change to a executer service
                        String message = client_buffered_reader.readLine();//new change
                        if(message.endsWith("CHAT")){
                            message=message.substring(0,message.lastIndexOf("CHAT")).trim();
                            chatText.setText(chatText.getText()+"\n"+message);
                        }
                        else {
                            message = message.substring(message.indexOf(" : "),message.length()-1).trim();
                            Finals.terminal.commandExecutor(message);
                            System.out.println(message);
                        }

                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Can't Listen to Server", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
        new Thread(runnable).start();
    }
    public void sendMassage(String massage) {
        if(!flag)
            return;
        try {
            player.getOutputStream().write((massage+"\n").getBytes());
            player.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void close() {
        flag=false;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sendMassage("Qu!T");
        try {
            player.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getMap()
    {
        String message=null;
        try
        {
            client_buffered_reader = new BufferedReader(new InputStreamReader(player.getInputStream()));
        }
        catch (IOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try
        {
            //message=(new BufferedReader(new InputStreamReader(player.getInputStream())).readLine());
            message = client_buffered_reader.readLine();
            System.out.println(message);
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Can't get map from  Server", "Error", JOptionPane.ERROR_MESSAGE);
        }
        try
        {
            
           File_chooser_save_load.loadMap(message);
           
          
        }
        catch (ParseException e)
        {
            JOptionPane.showMessageDialog(null, "Can't Convert map which got from server", "Error", JOptionPane.ERROR_MESSAGE);
      
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Can't get map from  Server Error 2", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }
        
        
        
        
    public int getPlayernumber()    
    {
        String message=null;
        try
        {
            message=null;
            System.err.println("here2");

            //message=(new BufferedReader(new InputStreamReader(player.getInputStream())).readLine());
            message = client_buffered_reader.readLine();
            System.err.println("here");
            
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Can't get Players number from  Server", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return (int)Integer.parseInt(message.trim());
    }
    public int getnation()
    {
        String message=null;
        try
        {
            message = client_buffered_reader.readLine();
            //message=(new BufferedReader(new InputStreamReader(player.getInputStream())).readLine());
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Can't get Players number from  Server", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return Integer.parseInt(message);
    }
    public JPanel getChatbox(){return chatbox;}
//    public static void main(String[] args) {
//
//       Client c= new Client("localhost", "test");//////////////////////must change and get name and ip
//        JFrame test=new JFrame("test");
//        test.setBounds(100,100,500,500);
//        c.getChatbox().setLocation(0, 0);
//        test.add(c.getChatbox());
//        test.setVisible(true);
//        c.getMessges();
//
//    }
}















