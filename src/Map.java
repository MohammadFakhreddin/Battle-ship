import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JPanel;

public class Map extends JPanel implements KeyListener
{

    
    private static final long serialVersionUID = 1L;
    int mouse_x = 0;
    int mouse_y = 0;
    
    public Map()
    {
        this.addKeyListener(this);
        this.requestFocus(true);
        setLocation(0, 0);
        
        resize_map();
        this.requestFocusInWindow();
    }

    private void resize_map()
    {
        Finals.MAP_SIZE = new Dimension(Finals.pixeles.length*Finals.PIC_SIZE.width,Finals.pixeles[0].length*Finals.PIC_SIZE.height);
        setSize(Finals.MAP_SIZE);
    }

    public void paint(Graphics g)
    {
        Vector<Dimension> locations = new Vector<>();
        Vector<Integer> mode = new Vector<>();
        
        this.requestFocusInWindow();
        for (int j = 0; j < Finals.pixeles[0].length; j++)
        {
            for (int i = 0; i < Finals.pixeles.length; i++)
            {
                switch (Finals.pixeles[i][j]%10)
                {
                case 0:
                      g.drawImage(Finals.water_shallow.getImage(), i * Finals.PIC_SIZE.width, j *Finals.PIC_SIZE.height, Finals.PIC_SIZE.width, Finals.PIC_SIZE.height, null);
                    break;
                case 1:
                    //System.out.println(Finals.pixeles[i][j]/10 - (Finals.pixeles[i][j]/100)*10);
                    if(Finals.pixeles[i][j]/10 - (Finals.pixeles[i][j]/100)*10 ==1)
                    g.drawImage(Finals.water_shallow.getImage(), i * Finals.PIC_SIZE.width, j *Finals.PIC_SIZE.height, Finals.PIC_SIZE.width, Finals.PIC_SIZE.height, null);
                    else if(Finals.pixeles[i][j]/10 - (Finals.pixeles[i][j]/100)*10==2)
                        g.drawImage(Finals.deep_water.getImage(), i * Finals.PIC_SIZE.width, j *Finals.PIC_SIZE.height, Finals.PIC_SIZE.width, Finals.PIC_SIZE.height, null);
                    
                    int value_1= mapUpdater(i, j, 1);
                    Finals.pixeles[i][j]=100*value_1+Finals.pixeles[i][j]%100;//it saves completely for in game map // steady map
                    g.drawImage(Finals.sand_icon[value_1].getImage(), i * Finals.PIC_SIZE.width, j *Finals.PIC_SIZE.height, Finals.PIC_SIZE.width+1, Finals.PIC_SIZE.height+1, null);
                    break;
                case 2:
                    locations.add(new Dimension(i,j));
                    mode.add(2);
                  
                    g.drawImage(Finals.grass_icon[15].getImage(),i*Finals.PIC_SIZE.width,j*Finals.PIC_SIZE.height, Finals.PIC_SIZE.width, Finals.PIC_SIZE.height,null);
                    break;
                case 3:
                    g.drawImage(Finals.sand_icon[15].getImage(),i*Finals.PIC_SIZE.width,j*Finals.PIC_SIZE.height, Finals.PIC_SIZE.width, Finals.PIC_SIZE.height,null);
                    int value_3= mapUpdater(i, j, 3);
                    Finals.pixeles[i][j]=10*value_3+Finals.pixeles[i][j]%10;
                    g.drawImage(Finals.grass_icon[value_3].getImage(),i*Finals.PIC_SIZE.width,j*Finals.PIC_SIZE.height, Finals.PIC_SIZE.width, Finals.PIC_SIZE.height,null);
                    break;
                case 4:
                    g.drawImage(Finals.deep_water.getImage(),i*Finals.PIC_SIZE.width,j*Finals.PIC_SIZE.height, Finals.PIC_SIZE.width, Finals.PIC_SIZE.height,null);
                    break;
                case 5:
                    if(Finals.pixeles[i][j]/10==0)
                        g.drawImage(Finals.water_shallow.getImage(), i * Finals.PIC_SIZE.width, j *Finals.PIC_SIZE.height, Finals.PIC_SIZE.width, Finals.PIC_SIZE.height, null);
                    if(Finals.pixeles[i][j]/10==4)
                        g.drawImage(Finals.deep_water.getImage(),i*Finals.PIC_SIZE.width,j*Finals.PIC_SIZE.height, Finals.PIC_SIZE.width, Finals.PIC_SIZE.height,null);
                    g.drawImage(Finals.fish_group.getImage(),i*Finals.PIC_SIZE.width-Finals.tree_size.width/4
                    ,(j)*Finals.PIC_SIZE.height-Finals.tree_size.height/2, Finals.tree_size.width,Finals.tree_size.height,null);
                    break;
                case 6:
                    locations.add(new Dimension(i,j));
                    mode.add(6);
                   // g.drawImage(Finals.sand_icon[15].getImage(),i*Finals.PIC_SIZE.width,j*Finals.PIC_SIZE.height, Finals.PIC_SIZE.width, Finals.PIC_SIZE.height,null);
                    break;
                case 7:
                    locations.add(new Dimension(i,j));
                    mode.add(7);
                    
                    g.drawImage(Finals.grass_icon[15].getImage(),i*Finals.PIC_SIZE.width,j*Finals.PIC_SIZE.height, Finals.PIC_SIZE.width, Finals.PIC_SIZE.height,null);
                    
                    break;
                default:
                    break;
                }

            }
        }
        
        
        for(int i=0 ;i<locations.size();i++){
            
            switch(mode.elementAt(i)){
            case 2:
            g.drawImage(Finals.tree_icon[Finals.pixeles_mode[locations.elementAt(i).width][locations.elementAt(i).height]].getImage(),(locations.elementAt(i).width)*Finals.PIC_SIZE.width-Finals.tree_size.width/4
                    ,(locations.elementAt(i).height)*Finals.PIC_SIZE.height-Finals.tree_size.height/2, Finals.tree_size.width, Finals.tree_size.height,null);
                break;
            case 7:
            g.drawImage(Finals.mine.getImage(),(locations.elementAt(i).width)*Finals.PIC_SIZE.width-Finals.tree_size.width/4
                        ,(locations.elementAt(i).height)*Finals.PIC_SIZE.height-Finals.tree_size.height/2, Finals.tree_size.width, Finals.tree_size.height,null);
                break;
            case 6:
            g.drawImage(Finals.iron_mountain.getImage(),(locations.elementAt(i).width)*Finals.PIC_SIZE.width-Finals.iron_mountain_size.width/4
                        ,(locations.elementAt(i).height)*Finals.PIC_SIZE.height-Finals.iron_mountain_size.height/2, Finals.iron_mountain_size.width, Finals.iron_mountain_size.height,null);
          
            default:
                break;
            }
        }
      
            
        this.setFocusable(true);
        this.getFocusListeners();
        
    }
    
    public int mapUpdater(int x, int y , int mode) {
        int value = 0;
        try{// instead of using a lot of if else statements we used try_catch it's easier (not sure is it good or not!)
        if(mode==1){
        try{
        if (Finals.pixeles[x-1][y ]%10 != 0&&Finals.pixeles[x-1][y ]%10 != 4) {
            value += 1;
        }
        }catch(Exception e){
            
        }
        try{
        if (Finals.pixeles[x+1][y] %10!= 0 && Finals.pixeles[x+1][y]%10 != 4 )  {
            value += 4;
        }
        }catch(Exception e){
            
        }
        try{
        if (Finals.pixeles[x][y+1]%10 != 0 &&Finals.pixeles[x][y+1]%10 != 4  ) {
            value += 8;
        }
        }catch(Exception e){
            
        }
        try{
        if (Finals.pixeles[x][y-1]%10 != 0 && Finals.pixeles[x][y-1] %10!= 4 ) {
            value += 2;
        }
        }catch(Exception e){
            
        }
        }
        else if(mode==5){
            try{
                if (Finals.pixeles[x-1][y ]%10 ==4 ||Finals.pixeles[x-1][y ]%10 == 0 ) {
                    value += 1;
                }
                }catch(Exception e){
                    
                }
                try{
                if (Finals.pixeles[x+1][y]%10 ==4 || Finals.pixeles[x+1][y]%10 == 0)  {
                    value += 4;
                }
                }catch(Exception e){
                    
                }
                try{
                if (Finals.pixeles[x][y+1] %10==4 || Finals.pixeles[x][y+1]%10 == 0 ) {
                    value += 8;
                }
                }catch(Exception e){
                    
                }
                try{
                if (Finals.pixeles[x][y-1]%10 ==4 || Finals.pixeles[x][y-1]%10 ==0) {
                    value += 2;
                }  
                }catch(Exception e){
                    
                }
        }
        else  {
                try{
                if (Finals.pixeles[x-1][y ]%10 ==mode ||Finals.pixeles[x-1][y ]%10 == 2 ||Finals.pixeles[x-1][y ]%10 == 7) {
                    value += 1;
                }
                }catch(Exception e){
                    
                }
                try{
                if (Finals.pixeles[x+1][y]%10 ==mode || Finals.pixeles[x+1][y]%10 == 2 ||Finals.pixeles[x+1][y ]%10 == 7)  {
                    value += 4;
                }
                }catch(Exception e){
                    
                }
                try{
                if (Finals.pixeles[x][y+1] %10==mode || Finals.pixeles[x][y+1]%10 == 2 ||Finals.pixeles[x][y+1 ]%10 == 7) {
                    value += 8;
                }
                }catch(Exception e){
                    
                }
                try{
                if (Finals.pixeles[x][y-1]%10 == mode || Finals.pixeles[x][y-1]%10 == 2 ||Finals.pixeles[x][y-1]%10 ==7) {
                    value += 2;
                }  
                }catch(Exception e){
                    
                }
        }
        
        }catch(Exception e){
        }
        return value;
    }
    

    @Override
    public void keyPressed(KeyEvent arg0)
    {
        //System.out.println("hi");
        int x= this.getLocation().x;
        int y=this.getLocation().y;
        if(arg0.getKeyCode()==KeyEvent.VK_UP&&y<=5){
            y+=5;
        }
        else if (arg0.getKeyCode()==KeyEvent.VK_DOWN&&
                y+this.getSize().height+5>=Finals.BOARD_SIZE.height){
            y-=5;
        }
        else if (arg0.getKeyCode()==KeyEvent.VK_RIGHT&&
                x+this.getSize().width+5>=Finals.BOARD_SIZE.width*9/10){
            x-=5; 
        }
        else if(arg0.getKeyCode()==KeyEvent.VK_LEFT&&
               x<Finals.BOARD_SIZE.width/10 ){
            x+=5;
        }
        this.setLocation(x,y);

    }

    @Override
    public void keyReleased(KeyEvent arg0)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyTyped(KeyEvent arg0)
    {
        // TODO Auto-generated method stub
        
    }


    @Override
    protected void processEvent(AWTEvent arg0)
    {
        if(arg0.getID()==Messages.reset_map){
            System.out.println("map resized");
            resize_map();
        }
        super.processEvent(arg0);
    }
    
    
}
