
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class simple_workstation extends Thread {

    simple_server s;
    Socket cleint;
    InputStream input;
    OutputStream output;
    ExecutorService Thread_ex;

    
    public simple_workstation(Socket client, simple_server s) {
        Thread_ex = Executors.newCachedThreadPool();
        this.cleint = client;
        this.s = s;
        try {
            input = client.getInputStream();
            output = client.getOutputStream();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Thread_ex.execute(this);
    }

    @Override
    public void run() {
        
        boolean flag = false;
        while (!flag) {
            byte[] b = new byte[200];
            try {
                int i = input.read(b);
                if (i == -1) {
                    flag = true;
                    continue;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if ((new String(b)).trim().endsWith("bye"))
                flag = true;
            else
                s.sendToAll((new String(b)).trim());

        }
    }
}
