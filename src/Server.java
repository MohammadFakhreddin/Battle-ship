import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.Vector;



public class Server {
    final private  int playerNumber;
    private String creator;
    private boolean isAlive;
    private ServerSocket socket;
    private Vector<PlayStation> players;
    private int portNumber=Finals.port_number;
    private int broadcastRate=Finals.broadcast_rate;
    private String allMap;
    public Server(int playerNumber,File selectedMap) {
        
       
        
        allMap=getMap(selectedMap);
        System.out.println(allMap);
        isAlive=true;
        this.playerNumber=playerNumber;
        players=new Vector<PlayStation>();
        creator=Finals.player_name;
        try {
            socket=new ServerSocket(portNumber);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Can't bound to Port 1119","Error",JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(null,"Other software (probably a game from BLIZZARD) has bounded it Closs that and try again ","Hint",JOptionPane.INFORMATION_MESSAGE);
            isAlive=false;
        }

    }
    public  void Broadcast() {
        final String auth=Finals.broadcast_auth;
         DatagramSocket broadcastOut=null;
        try {
            broadcastOut=new DatagramSocket(portNumber);
        }
        catch (SocketException e) {
            JOptionPane.showMessageDialog(null, "Can't bound to BroadCast", "Error", JOptionPane.ERROR_MESSAGE);
            isAlive=false;
        }
        try {
            broadcastOut.setBroadcast(true);
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(null, "Can't Make connection Broadcastable", "Error", JOptionPane.ERROR_MESSAGE);
            isAlive=false;
        }
        final DatagramSocket finalBroadcastOut = broadcastOut;
        Runnable broadcaster=new Runnable() {
            @Override
            public void run() {
                while(isAlive&&players.size()<playerNumber) {
                    String message=auth+" "+creator+" "+(players.size()+"/"+playerNumber);
                    DatagramPacket packet=null;
                    try {
                        packet =new DatagramPacket(message.getBytes(),message.getBytes().length,InetAddress.getByName("255.255.255.255"),portNumber);
                    } catch (UnknownHostException e) {
                        JOptionPane.showMessageDialog(null, "Can't get BroadCast Address", "Error", JOptionPane.ERROR_MESSAGE);
                        isAlive=false;
                    }
                    try {
                        finalBroadcastOut.send(packet);
//                        System.out.println("broadcasted");
                    } catch (IOException e) {
                       e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Can't broadcast Sorry", "Error", JOptionPane.ERROR_MESSAGE);
                        isAlive = false;
                    }
                    try {
                        Thread.sleep(broadcastRate);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                finalBroadcastOut.close();
            }
        };
        new Thread(broadcaster).start();
    }
    public void getConnection(){
        Runnable runnable=new Runnable()
        {
            
            @Override
            public void run()
            {
        int i=0;
        while(isAlive&&players.size()<playerNumber)
        {
            Socket temp=null;
            try {
                temp=socket.accept();
                
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Can't get player", "Error", JOptionPane.ERROR_MESSAGE);
                isAlive=false;
            }
            try {
                synchronized (Object.class)
                {  
                temp.getOutputStream().write("Accepted\n".getBytes());
                temp.getOutputStream().flush();
                temp.getOutputStream().write((allMap+"\n").getBytes());
                temp.getOutputStream().flush();
                System.err.println("there");
                temp.getOutputStream().write((playerNumber+"\n").getBytes());
                temp.getOutputStream().flush();
                temp.getOutputStream().write(((players.size()-1)+"\n").getBytes());
                temp.getOutputStream().flush();
                System.err.println("there2");
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Can't set Accept to Player", "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            players.add(new PlayStation(temp,Server.this));
            sendToAll(players.get(i).getName() + " Has Connected CHAT", "Server");
            System.out.println("yeki oomad");
            if(players.size()<playerNumber)
            sendToOne(players.get(i).getName() + " We Should wait for Others to Connect CHAT", "Server",players.get(i));
            i++;//new change
        }
            }
        };
        new Thread(runnable).start();
        
        
    }
    public void startIt() {
        
        while(players.size()<playerNumber)
        {
            try
            {
                Thread.sleep(500);
            }
            catch (InterruptedException e)
            {
                JOptionPane.showMessageDialog(null, "MisBehavior some thing awake Server", "Error", JOptionPane.ERROR_MESSAGE);
                closeAll();
                return;
            }
        }
        for(PlayStation playStation:players)
        {
            playStation.start();
        }
    }
    public boolean isAlive() {
        return isAlive;
    }
    public void sendToAll(String message,String name){
        for(PlayStation ps:players)
        {
            sendToOne(message,name,ps);
        }
    }
    public void sendToOne(String message,String name,PlayStation ps)
    {
        
        try {
            ps.getSocket().getOutputStream().write((name+" : "+message+"\n").getBytes());
            ps.getSocket().getOutputStream().flush();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Can't send Message to player", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    public boolean remover(PlayStation ps)
    {
        return players.remove(ps);
    }
    public void close(PlayStation ps){
        ps.close();
        players.remove(ps);
        sendToAll(ps.getName()+" Has diconected","Server");
    }
    public void closeAll() {
        for(PlayStation ps:players)
        {
            ps.close();
        }
        players.removeAllElements();
    }
    public String getMap(File selectedMap)
    {   
       String all=new String();
        FileReader  reader=null;
        String read=null;
           try
        {
            reader =new FileReader(selectedMap);
        }
        catch (FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Can't Make Reader", "Error", JOptionPane.ERROR_MESSAGE);
        }
           BufferedReader br=new BufferedReader(reader);
        
        try
        {
            while((read=br.readLine())!=null)
            {
                all=all+read;
            }
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Can't Append map together", "Error", JOptionPane.ERROR_MESSAGE);
        }
        try
        {
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return all;
    }
//    public static void main(String[] args) {
//        Server test= new Server("Navid",1);//////////must be master name and number
//        test.Broadcast();//it has a thread 
//        test.getConnection();//it has not thread
//        test.startIt();
//
//    }
}
