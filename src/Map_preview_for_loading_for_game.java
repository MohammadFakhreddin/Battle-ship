import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class Map_preview_for_loading_for_game extends JPanel implements Runnable// it must to be dynamic for using many_times
{
   
    int height=0;
    int width=0;
    Dimension panel_size ;
    ExecutorService Thread_ex;
    Map_preview_for_loading_for_game(Dimension panel_size){
        
        this.panel_size=panel_size;
//        height=panel_size.height / Finals.pixeles[0].length;
//        width=panel_size.width / Finals.pixeles.length;
        reset_pre_map();
        repaint();
    }
    public Map_preview_for_loading_for_game(Dimension panel_size, boolean b)
    {
        this.panel_size=panel_size;
        Thread_ex =Executors.newCachedThreadPool();
        Thread_ex.execute(Map_preview_for_loading_for_game.this);
    }
    @Override
    public void paint (Graphics g){
        super.paint(g);
        
        try{
        height=panel_size.height / Finals.pixeles[0].length;
        width=panel_size.width / Finals.pixeles.length;
        Vector<Dimension> locations = new Vector<>();
        Vector<Integer> mode = new Vector<>();  
      //  System.out.println(Finals.pixeles.length);

            for (int j = 0; j < Finals.pixeles[0].length; j++) {
                for (int i = 0; i < Finals.pixeles.length; i++) {
                switch (Finals.pixeles[i][j] % 10) {
                    case 0:
                        g.drawImage(Finals.water_shallow.getImage(), i * width, j * height, width, height, null);
                        break;
                    case 1:
                       
                        if (Finals.pixeles[i][j] / 10 - (Finals.pixeles[i][j] / 100) * 10 == 1)
                            g.drawImage(Finals.pre_water_shallow_gif.getImage(), i * width, j * height, width, height, null);
                        else if (Finals.pixeles[i][j] / 10 - (Finals.pixeles[i][j] / 100) * 10 == 2)
                            g.drawImage(Finals.deep_water.getImage(), i * width, j * height, width, height, null);

                        g.drawImage(Finals.sand_icon[Finals.pixeles[i][j] / 100].getImage(), i * width, j * height, width+1, height+1, null);
                        
                        break;
                    case 2:
                        
                        locations.add(new Dimension(i, j));
                        mode.add(2);
                        g.drawImage(Finals.grass_icon[15].getImage(), i * width, j * height, width, height, null);
                        break;
                    case 3:
                        
                        g.drawImage(Finals.sand_icon[15].getImage(), i * width, j * height, width, height, null);
                        g.drawImage(Finals.grass_icon[Finals.pixeles[i][j] / 10].getImage(), i * width, j * height, width, height, null);
                        
                        break;
                    case 4:
                        g.drawImage(Finals.deep_water.getImage(), i * width, j * height,width, height, null);
                        break;
                    case 5:
                        if(Finals.pixeles[i][j]/10==0)
                            g.drawImage(Finals.water_shallow.getImage(), i * width, j * height, width, height, null);
                        if(Finals.pixeles[i][j]/10==4)
                            g.drawImage(Finals.deep_water.getImage(), i * width, j * height,width, height, null);
                        g.drawImage(Finals.fish_group.getImage(),i*width-width/2
                        ,(j)*height-height, 2*width, 2*height,null);
                        break;
                    case 6:
                        locations.add(new Dimension(i,j));
                        mode.add(6);
//                        //g.drawImage(Finals.sand_icon[15].getImage(),i*width,j*height, width,height,null);
                        break;
                    case 7:
                        
                        g.drawImage(Finals.grass_icon[15].getImage(),i*width,j*height, width, height,null);
                        locations.add(new Dimension(i,j));
                        mode.add(7);
                        
                        break;
                    default:
                        break;
                }
            }
    }
 
          for(int i=0 ;i<locations.size();i++){
              switch(mode.elementAt(i)){
              case 2:
              g.drawImage(Finals.tree_icon[Finals.pixeles_mode[locations.elementAt(i).width][locations.elementAt(i).height]].getImage(),(locations.elementAt(i).width)*width-width/2
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
      //    System.out.println("working");
        }catch(Exception e){}
        
        }
    public void reset_pre_map ( ){

        Finals.reAddress();
        
//        //make the tree icons
        for(int i=0 ;i<4;i++){
        String string = String.format("tree%d.png",i+1);
        Finals.tree_icon[i] = new ImageIcon(string);
        }
        
        for(int i=0 ;i<Finals.sand_icon.length;i++){
            String string = String.format("sand%d.png",i);
            Finals.sand_icon[i]= new ImageIcon(string);
        }
        
        for(int i=0 ;i<Finals.grass_icon.length;i++){
            String string = String.format("grass%d.png",i);
            Finals.grass_icon[i]= new ImageIcon(string);
        }
        
//        //return pixels;
    }
    public void run(){
        while(true){
            this.repaint();
            //System.err.println("working");
            try{
            Thread.sleep(1000);
            }catch(Exception e){    
            }
       }
    }
    
    }

