//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.InetAddress;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.util.Vector;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class simple_client  implements  Runnable {
//
//    InputStream input;
//    OutputStream output;
//    Socket socket;
//    ExecutorService Thread_ex;
//    String host_name;
//    public simple_client(String host_name) {
//        this.host_name = host_name;
//        Thread_ex = Executors.newCachedThreadPool();
//        try {
//            System.out.println(InetAddress.getLocalHost().);
//            socket = new Socket(InetAddress.getBy(host_name.trim()),4000);
//            input = socket.getInputStream();
//            output = socket.getOutputStream();
//        } catch (UnknownHostException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        Thread_ex.execute(this);
//    }
//
//
////    @Override
////    public void keyPressed(KeyEvent arg0) {
////        if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
//////          System.out.println("l;askdlksd");
////            String s = tf.getText();
//////          ta.append(getName() + " : " + s + "\n");
////            try {
////                output.write((getName() + " : " + s + "\n").getBytes());
////            } catch (IOException e) {
////                // TODO Auto-generated catch block
////                e.printStackTrace();
////            }
////            tf.setText("");
////        }
////    }
//    public void send_data(String message){
//        try {
//          output.write((message).getBytes());
//      } catch (IOException e) {
//          // TODO Auto-generated catch block
//          e.printStackTrace();
//      }
//    }
//
//    @Override
//    public void run() {
//        try
//        {
//            System.out.println("i'm working");
//            byte[] plyer_num = new byte[200];
//            int player_number = input.read(plyer_num);
//            byte[] this_plyer_num = new byte[200];
//            int this_player_number = input.read(plyer_num);
//            Finals.nation=this_player_number;
//            if(!InetAddress.getLocalHost().equals(host_name)){
//                byte[] w = new byte[200];
//                int width = input.read(w);
//                byte [] h = new byte[200];
//                int height = input.read(h);
//                Finals.pixeles = new Integer[width][height];
//                for(int i=0 ;i<Finals.pixeles.length;i++){
//                    for(int j=0 ;j<Finals.pixeles[0].length;j++){
//                        byte[] b = new byte[200];
//                        Finals.pixeles[i][j]= input.read(b);
//                    }
//                }
//            }
//            Vector<CartesianPoint> HQ_points = new Vector<>();
//            HQ_points = (new Base_finder(player_number)).get_HQ_points();
//            new Game_frame(HQ_points,false);
//        }
//        catch (UnknownHostException e1)
//        {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        catch (IOException e1)
//        {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//        int i = 0;
//        while (i != -1){
//            byte [] b = new byte[200];
//            // i = input.read(b);
//            //if (i != -1)
//            Finals.terminal.commandExecutor((new String(b)).trim());
//            
//        }
//    
//    }
//}
