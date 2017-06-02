import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Game_Environment extends JComponent implements Runnable//////it is the environment of game which must not be drawn more than once
{
    boolean first_time_draw = true;
   // Vector<CartesianPoint> locations;
    Vector<Integer> mode;
    
    public Game_Environment()
    {
       // locations = new Vector<>();
        mode = new Vector<>();  
        repaint();
    }
    @Override
    public void paint(Graphics g)
    {
        boolean season_changed = Finals.season_changed;
        //System.out.println("drawing");
//        if(first_time_draw){
//                for(int j=0 ;j<Finals.pixeles[0].length;j++)
//                    for(int i=0 ;i<Finals.pixeles.length;i++){
//                    if(Finals.pixeles[i][j]==2||Finals.pixeles[i][j]==6||Finals.pixeles[i][j]==7){
//                        locations.add(new CartesianPoint(i,j));
//                        mode.add(Finals.pixeles[i][j]);
//                    }
//            }
//            
//        } 
        
            for (int j = 0; j < Finals.pixeles[0].length; j++) {
                for (int i = 0; i < Finals.pixeles.length; i++) {
                switch (Finals.pixeles[i][j] % 10) {
                    case 0:
                        g.drawImage(Finals.pre_water_shallow_gif.getImage(), i * Finals.PIC_SIZE.width, j * Finals.PIC_SIZE.height, Finals.PIC_SIZE.width, Finals.PIC_SIZE.height, null);
                        break;
                    case 1:
                        //if(season_changed||first_time_draw){
                        if (Finals.pixeles[i][j] / 10 - (Finals.pixeles[i][j] / 100) * 10 == 1)
                            g.drawImage(Finals.pre_water_shallow_gif.getImage(), i * Finals.PIC_SIZE.width, j * Finals.PIC_SIZE.height, Finals.PIC_SIZE.width,  Finals.PIC_SIZE.height, null);
                        else if (Finals.pixeles[i][j] / 10 - (Finals.pixeles[i][j] / 100) * 10 == 2)
                            g.drawImage(Finals.pre_deep_water_gif.getImage(), i *  Finals.PIC_SIZE.width, j *  Finals.PIC_SIZE.height,  Finals.PIC_SIZE.width,  Finals.PIC_SIZE.height, null);
                        g.drawImage(Finals.pre_sand_icon[Finals.pixeles[i][j] / 100].getImage(), i *  Finals.PIC_SIZE.width, j *  Finals.PIC_SIZE.height,  Finals.PIC_SIZE.width+1,  Finals.PIC_SIZE.height+1, null);
                        // g.setColor(Color.red);
                        // g.fillRect( i *  Finals.PIC_SIZE.width, j *  Finals.PIC_SIZE.height,  Finals.PIC_SIZE.width+1,  Finals.PIC_SIZE.height+1);
                        // g.drawImage(Finals.pre_sand_icon[15].getImage(), i *  Finals.PIC_SIZE.width, j *  Finals.PIC_SIZE.height,  Finals.PIC_SIZE.width+1,  Finals.PIC_SIZE.height+1, null);
                      //  }
                        break;
                    case 2:
//                        if(season_changed||first_time_draw){
//                        locations.add(new Dimension(i, j));
//                        mode.add(2);
                        g.drawImage(Finals.pre_grass_icon[15].getImage(), i * Finals.PIC_SIZE.width, j * Finals.PIC_SIZE.height, Finals.PIC_SIZE.width, Finals.PIC_SIZE.height, null);
                      //  }
                        break;
                    case 3:
                     //   if(season_changed||first_time_draw){
                        g.drawImage(Finals.pre_sand_icon[15].getImage(), i * Finals.PIC_SIZE.width, j * Finals.PIC_SIZE.height, Finals.PIC_SIZE.width, Finals.PIC_SIZE.height, null);
                        g.drawImage(Finals.pre_grass_icon[Finals.pixeles[i][j] / 10].getImage(), i * Finals.PIC_SIZE.width, j * Finals.PIC_SIZE.height, Finals.PIC_SIZE.width, Finals.PIC_SIZE.height, null);
                   //     }
                        break;
                    case 4:
                        g.drawImage(Finals.pre_deep_water_gif.getImage(), i * Finals.PIC_SIZE.width, j * Finals.PIC_SIZE.height,Finals.PIC_SIZE.width, Finals.PIC_SIZE.height, null);
                        break;
                    case 5:
                        if(Finals.pixeles[i][j]/10==0)
                            g.drawImage(Finals.pre_water_shallow_gif.getImage(), i * Finals.PIC_SIZE.width, j * Finals.PIC_SIZE.height, Finals.PIC_SIZE.width, Finals.PIC_SIZE.height, null);
                        if(Finals.pixeles[i][j]/10==4)
                            g.drawImage(Finals.pre_deep_water_gif.getImage(), i * Finals.PIC_SIZE.width, j * Finals.PIC_SIZE.height,Finals.PIC_SIZE.width, Finals.PIC_SIZE.height, null);
                        g.drawImage(Finals.fish_group.getImage(),i*Finals.PIC_SIZE.width-Finals.PIC_SIZE.width/2
                        ,(j)*Finals.PIC_SIZE.height-Finals.PIC_SIZE.height, 2*Finals.PIC_SIZE.width, 2*Finals.PIC_SIZE.height,null);
                        break;
                    case 6:
//                        locations.add(new Dimension(i,j));
//                        mode.add(6);
//                        //g.drawImage(Finals.sand_icon[15].getImage(),i*width,j*height, width,height,null);
                        break;
                    case 7:
                      //  if(season_changed||first_time_draw){
                        g.drawImage(Finals.pre_grass_icon[15].getImage(),i*Finals.PIC_SIZE.width,j*Finals.PIC_SIZE.height, Finals.PIC_SIZE.width, Finals.PIC_SIZE.height,null);
//                      locations.add(new Dimension(i,j));
//                      mode.add(7);
//                       }
                        break;
                    default:
                        break;
                }
            }
     }
        

         // if(Finals.season_changed||first_time_draw){
//          for(int i=0 ;i<locations.size();i++){
//              switch(mode.elementAt(i)){
//              case 2:
//                  g.drawImage(Finals.pre_tree_icon[Finals.pixeles_mode[locations.elementAt(i).getX()][locations.elementAt(i).getY()]].getImage(),(locations.elementAt(i).getX())*Finals.PIC_SIZE.width-Finals.PIC_SIZE.width/2
//                          ,(locations.elementAt(i).getY())*Finals.PIC_SIZE.height-Finals.PIC_SIZE.height, Finals.tree_size.width,Finals.tree_size.height,null);
//              break;
//              case 7:
//                  g.drawImage(Finals.mine.getImage(),(locations.elementAt(i).getX())*Finals.PIC_SIZE.width-Finals.PIC_SIZE.width/2
//                          ,(locations.elementAt(i).getY())*Finals.PIC_SIZE.height-Finals.PIC_SIZE.height, Finals.tree_size.width,Finals.tree_size.height,null);
//              break;
//              case 6:
//                  g.drawImage(Finals.iron_mountain.getImage(),(locations.elementAt(i).getX())*Finals.PIC_SIZE.width-3*Finals.PIC_SIZE.width/4
//                          ,(locations.elementAt(i).getY())*Finals.PIC_SIZE.height-3*Finals.PIC_SIZE.height/2,Finals.iron_mountain_size.width,Finals.iron_mountain_size.height,null);
//              break;
//              }   
//          }

       // }

        if(season_changed)
        Finals.season_changed=false;
        if(first_time_draw)
        first_time_draw=false;
    }
    public void run(){
        while(true){
            try
            {
                Thread.sleep(50);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            Game_Environment.this.repaint();
        }
    }
  
}
