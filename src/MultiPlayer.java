import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.util.Vector;

public class MultiPlayer extends JPanel
{
    private JButton refresh = new JButton("Refresh");
    private JTable allServer = new JTable();
    private JComboBox<Integer> Selected_number_of_players;
    private DefaultTableModel model;
    private Font font;
    private File_chooser_save_load load_class;
    public MultiPlayer()
    {
        this.setLayout(null);
        
        
        font = new Font("Tahoma",20,20);
     
        refresh.setBounds(15, 15, 100, 25);
        refresh.setFont(font);
        refresh.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setTable();
            }
        });
       
        this.add(refresh);
        allServer.setBounds(15, 50, 450, 500);
        model = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable(int col, int row)
            {
                return false;
            }
        };
        model.addColumn("Server Name");
        model.addColumn("Players");
        model.addColumn("IP Address");
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) allServer.getDefaultRenderer(Object.class);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        renderer.setVerticalAlignment(SwingConstants.CENTER);
        allServer.setRowHeight(50);
        this.add(allServer);
        model.addRow(new Object[] { "Server Name", "Players", "IP Address" });
        allServer.setModel(model);
        JButton connect = new JButton("Connect");
        connect.setFont(font);
        connect.setBounds(365, 15, 100, 25);
        this.add(connect);
        connect.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
//                String host_name=(JOptionPane.showInputDialog(null,"Please enter host name")).replace(" ","");
//                if(host_name!=null){
//                    simple_client sc = new simple_client(host_name);
//                }
                System.out.println("c");
                if (allServer.getSelectedRow() == 0 || allServer.getSelectedRow() == -1)
                {
                    JOptionPane.showMessageDialog(null, "Please Select a net work", "Select Network",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                else
                {
                    int row_number=allServer.getSelectedRow();
                    String IP = (String) allServer.getModel().getValueAt(row_number, 2);
                    String name = Finals.player_name;
                    Client client = new Client(IP, name);
                }
            }
        });
        JButton creatServer = new JButton("Create Srever");
        creatServer.setFont(font);
        creatServer.setBounds(550, 15, 200, 100);
        creatServer.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                
                if(Selected_number_of_players.getSelectedItem()!=null){
                int player_number = (int) Selected_number_of_players.getSelectedItem();
//                simple_server ss = new simple_server(player_number);
//                try
//                {
//                    simple_client sc = new simple_client(InetAddress.getLocalHost().getHostName()+"");
//                }
//                catch (UnknownHostException e1)
//                {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                }
                Server server =new Server(player_number,load_class.get_selected_file());
                server.Broadcast();//it has a thread 
                server.getConnection();//it has not thread
                Client me=new Client("localhost", Finals.player_name);
                server.startIt();
                }
                else JOptionPane.showMessageDialog(null,"Please select number of players");
            }
        });
        Selected_number_of_players = new JComboBox();
        Selected_number_of_players.setFont(font);
        Selected_number_of_players.addItem(2);
        Selected_number_of_players.addItem(3);
        Selected_number_of_players.addItem(4);
        Selected_number_of_players.setBounds(creatServer.getLocation().x,creatServer.getLocation().y+creatServer.getSize().height,200,50);
        add(Selected_number_of_players);
        
        load_class = new File_chooser_save_load();
        JButton load_button =load_class.loadButton;//it is the worst thing I could do
        load_button.setFont(font);
        load_button.setLocation(Selected_number_of_players.getLocation().x,
                Selected_number_of_players.getLocation().y+Selected_number_of_players.getHeight());
        load_button.setSize(creatServer.getSize());
        Map_preview_for_loading_for_game map_preview =new Map_preview_for_loading_for_game(new Dimension(200,200),true);
        map_preview.setSize(200,200);
        map_preview.setLocation(load_button.getLocation().x,load_button.getLocation().y+load_button.getHeight());
        this.add(map_preview);
        this.add(load_button);
        this.add(creatServer);
    }

    private void setTable()
    {
        Vector<String> IPS = getIp();
        model = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable(int col, int row)
            {
                return false;
            }
        };
        model.addColumn("Server Name");
        model.addColumn("Players");
        model.addColumn("IP Address");
        model.addRow(new Object[] { "Server Name", "Players", "IP Address" });
        allServer.setModel(model);
        for (String s : IPS)
        {
            String name = s.substring(0, s.indexOf(" ")).trim();
            s = s.substring(s.indexOf(" ")).trim();
            String number = s.substring(0, s.indexOf(" ")).trim();
            s = s.substring(s.indexOf(" ")).trim();
            String ip = s.trim();
            model.addRow(new Object[] { name, number, ip });
        }
    }

    public Vector<String> getIp()
    {

        DatagramSocket socket = null;
        try
        {
            try
            {
                socket = new DatagramSocket(1119, InetAddress.getByName("0.0.0.0"));// ///////////should get from finals
            }
            catch (UnknownHostException e)
            {
                e.printStackTrace();
            }
        }
        catch (SocketException e)
        {
            JOptionPane.showMessageDialog(null, "Can't bound to Port 1119", "Error", JOptionPane.ERROR_MESSAGE);
        }
        long time = System.currentTimeMillis();
        Vector<String> allIp = new Vector<String>();
        DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
        try
        {
            socket.setSoTimeout(150);
        }
        catch (SocketException e)
        {
            JOptionPane.showMessageDialog(null, "Can't Change Timeout", "Error", JOptionPane.ERROR_MESSAGE);
        }

        while (time + 3000 > System.currentTimeMillis())
        {
            try
            {
                socket.receive(packet);

            }
            catch (SocketTimeoutException e)
            {

                continue;
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, "Can't Get Massege or Time is over", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            String message = new String(packet.getData());
            if (message.startsWith("AP_Game"))// /////should get from finals///////////
            {
                message = message.substring("AP_Game".length()).trim();
                if (!(allIp.contains(message + " " + packet.getAddress().getHostAddress())))
                {
                    allIp.add(message + " " + packet.getAddress().getHostAddress());
                }
            }
        }
        socket.close();
        return allIp;
    }
}
