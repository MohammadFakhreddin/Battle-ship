
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Preview extends JDialog implements Runnable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private boolean EndOfThread=false;
    private DayAndNightSimulator dans;
    private boolean first_time_draw=true;

    int width=0;
    int height=0;
    
    public Preview()
    {
        
       
        setUndecorated(true);
        super.setTitle("Preview");
        
        //edited for better screen result
        
        
        height=Toolkit.getDefaultToolkit().getScreenSize().height / Finals.pixeles[0].length;
        width=Toolkit.getDefaultToolkit().getScreenSize().width / Finals.pixeles.length;
        
        super.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width -Finals.pixeles.length*width) / 2,(Toolkit.getDefaultToolkit().getScreenSize().height -Finals.pixeles[0].length*height) / 2);
        super.setSize(Finals.pixeles.length*width, Finals.pixeles[0].length*height);
        
        setResizable(false);
        
//        top_layout =new Top_Layout(width,height);
//        top_layout.setLocation(0,0);
//        top_layout.setSize(Toolkit.getDefaultToolkit().getScreenSize().height, Toolkit.getDefaultToolkit().getScreenSize().height);
//        
//        down_layout =new  Background_Layout(width,height);
//        down_layout.setLocation(0,0);
//        down_layout.setSize(Toolkit.getDefaultToolkit().getScreenSize().height, Toolkit.getDefaultToolkit().getScreenSize().height);
//   
//        
//        add(top_layout);
//        add(down_layout);
 
        
        ExecutorService thread = Executors.newCachedThreadPool();
        thread.execute(this);
        
        
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dans.cancel();
                Finals.first_Address();
                EndOfThread=true;
                Finals.season_changed=true;
                dispose();
            }
        });
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                dans.cancel();
                EndOfThread=true;
                Finals.first_Address();
                Finals.season_changed=true;
                dispose();            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                dans.cancel();
                EndOfThread=true;
                Finals.first_Address();
                Finals.season_changed=true;
                dispose();            
                }

            @Override
            public void mousePressed(MouseEvent e) {
                dans.cancel();
                Finals.first_Address();
                EndOfThread=true;
                Finals.season_changed=true;
                dispose();            
                }

            @Override
            public void mouseClicked(MouseEvent e) {
                dans.cancel();
                Finals.first_Address();
                EndOfThread=true;
                Finals.season_changed=true;
                dispose();           
                }
            
        });
        
        
        
        
        super.setModal(true);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        super.setVisible(true);
    }
    Vector<Dimension> locations = new Vector<>();
    Vector<Integer> mode = new Vector<>();  
    public void paint(Graphics g)
    {
        boolean season_changed = Finals.season_changed;
        if(first_time_draw){
            for(int i=0 ;i<Finals.pixeles.length;i++){
                for(int j=0 ;j<Finals.pixeles[0].length;j++)
                    if(Finals.pixeles[i][j]==2||Finals.pixeles[i][j]==6||Finals.pixeles[i][j]==7){
                        locations.add(new Dimension(i,j));
                        mode.add(Finals.pixeles[i][j]);
                    }
            }
        } 
            for (int j = 0; j < Finals.pixeles[0].length; j++) {
                for (int i = 0; i < Finals.pixeles.length; i++) {
                switch (Finals.pixeles[i][j] % 10) {
                    case 0:
                        g.drawImage(Finals.pre_water_shallow_gif.getImage(), i * width, j * height, width, height, null);
                        break;
                    case 1:
                        if(season_changed||first_time_draw){
                        if (Finals.pixeles[i][j] / 10 - (Finals.pixeles[i][j] / 100) * 10 == 1)
                            g.drawImage(Finals.pre_water_shallow_gif.getImage(), i * width, j * height, width, height, null);
                        else if (Finals.pixeles[i][j] / 10 - (Finals.pixeles[i][j] / 100) * 10 == 2)
                            g.drawImage(Finals.pre_deep_water_gif.getImage(), i * width, j * height, width, height, null);

                        g.drawImage(Finals.pre_sand_icon[Finals.pixeles[i][j] / 100].getImage(), i * width, j * height, width+1, height+1, null);
                        }
                        break;
                    case 2:
                        if(season_changed||first_time_draw){
//                        locations.add(new Dimension(i, j));
//                        mode.add(2);
                        g.drawImage(Finals.pre_grass_icon[15].getImage(), i * width, j * height, width, height, null);
                        }
                        break;
                    case 3:
                        if(season_changed||first_time_draw){
                        g.drawImage(Finals.pre_sand_icon[15].getImage(), i * width, j * height, width, height, null);
                        g.drawImage(Finals.pre_grass_icon[Finals.pixeles[i][j] / 10].getImage(), i * width, j * height, width, height, null);
                        }
                        break;
                    case 4:
                        g.drawImage(Finals.pre_deep_water_gif.getImage(), i * width, j * height,width, height, null);
                        break;
                    case 5:
                        if(Finals.pixeles[i][j]/10==0)
                            g.drawImage(Finals.pre_water_shallow_gif.getImage(), i * width, j * height, width, height, null);
                        if(Finals.pixeles[i][j]/10==4)
                            g.drawImage(Finals.pre_deep_water_gif.getImage(), i * width, j * height,width, height, null);
                        g.drawImage(Finals.fish_group.getImage(),i*width-width/2
                        ,(j)*height-height, 2*width, 2*height,null);
                        break;
                    case 6:
                        locations.add(new Dimension(i,j));
                        mode.add(6);
//                        //g.drawImage(Finals.sand_icon[15].getImage(),i*width,j*height, width,height,null);
                        break;
                    case 7:
                        if(season_changed||first_time_draw){
                        g.drawImage(Finals.pre_grass_icon[15].getImage(),i*width,j*height, width, height,null);
                       // locations.add(new Dimension(i,j));
                       // mode.add(7);
                        }
                        break;
                    default:
                        break;
                }
            }
    }
        

          if(Finals.season_changed||first_time_draw){
          for(int i=0 ;i<locations.size();i++){
              switch(mode.elementAt(i)){
              case 2:
              g.drawImage(Finals.pre_tree_icon[Finals.pixeles_mode[locations.elementAt(i).width][locations.elementAt(i).height]].getImage(),(locations.elementAt(i).width)*width-width/2
                          ,(locations.elementAt(i).height)*height-height, 2*width, 2*height,null);
              break;
              case 7:
                  g.drawImage(Finals.mine.getImage(),(locations.elementAt(i).width)*width-width/2
                          ,(locations.elementAt(i).height)*height-height, 2*width, 2*height,null);
              break;
              case 6:
                  g.drawImage(Finals.iron_mountain.getImage(),(locations.elementAt(i).width)*width-3*width/4
                        ,(locations.elementAt(i).height)*height-3*height/2, 3*width,3*height,null);
              break;
              }   
          }
        
        }
        if(season_changed)
        Finals.season_changed=false;
        if(first_time_draw)
        first_time_draw=false;
    }

    public void run() {
        dans=new DayAndNightSimulator(false);// it's not actual game
        while (!EndOfThread) {
            repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    protected void processEvent(AWTEvent e) {
//
//        super.processEvent(e);
//    }
//    
  
    }
