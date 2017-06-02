import java.io.*;
import java.net.Socket;

public class PlayStation {
    Socket player;
    String name;
    Server server;
    boolean flag;
    public PlayStation(Socket player,Server server) {
        flag=true;
        this.server=server;
        this.player=player;
        try {
            name=(new BufferedReader(new InputStreamReader(this.player.getInputStream()))).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start() {
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                while (!player.isClosed()&&flag) {
                    String message=null;
                    try {
                        message = new BufferedReader(new InputStreamReader(player.getInputStream())).readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(message.equals("Qu!T"))
                    {
                        server.close(PlayStation.this);
                    }
                    else
                    server.sendToAll(message,name);
                }
            }
        };
        new Thread(runnable).start();
    }
    public Socket getSocket() {
        return player;
    }
    public String getName(){return  name;}
    public void close() {
        flag=false;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            player.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
