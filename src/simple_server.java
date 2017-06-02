import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class simple_server
{
    InputStream input;
    OutputStream output;
    ServerSocket ss;
    Vector<simple_workstation> v;
    int player_number;
    ExecutorService Thread_ex;

    public simple_server(int player_number) {
        v = new Vector<>();
        this.player_number=player_number;
        try {
            // int num = 0;
            // for (int i = 0; i<5001; i++) {
            // try{
            ss = new ServerSocket(4000);// port number, MaxClients
            // num = i;
            // }
            // catch (BindException e){
            // continue;
            // }
            // }
            // System.out.println(num);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    private void execute() {
        while (true) {
            try {
                for(int i=0 ;i<player_number;i++){
                Socket client = ss.accept();
                simple_workstation w = new simple_workstation(client, this);
                w.output.write(player_number);
                w.output.write(i);
                w.output.write(Finals.pixeles.length);
                w.output.write(Finals.pixeles[0].length);
                for(int k=0 ;k<Finals.pixeles.length;k++){
                    for(int j=0 ;j<Finals.pixeles[0].length;j++){
                        w.output.write(Finals.pixeles[k][j]);
                    }
                }
                v.addElement(w);

                w.start();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public synchronized void sendToAll(String msg) {
        
        for (simple_workstation w : v) {
            try {
                w.output.write(msg.getBytes());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
