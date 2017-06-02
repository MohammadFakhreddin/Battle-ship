import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;


public class Mini_Map extends JPanel implements Runnable , MouseMotionListener , MouseListener
{
    Map map;
    MapEditor ME;
    
    Game_panel game_panel;
    Game_frame game_frame;
    
    Dimension board_size;
    boolean is_inGame;
    
    public Mini_Map(Map map,MapEditor ME)
    {
        setLayout(null);
        
        is_inGame=false;
        
        this.setFocusable(true);
        
        addMouseListener(this);
        addMouseMotionListener(this);
       
        this.ME=ME;
        this.map=map;
       
        reset_mini_map();

        setVisible(true);
    }
    
    public Mini_Map(Game_panel game_panel , Game_frame game_frame, Dimension board_size){
        
        setLayout(null);
        
        is_inGame=true;
        
        this.setFocusable(true);
        
        addMouseListener(this);
        addMouseMotionListener(this);
        
        this.game_panel = game_panel;
        this.game_frame = game_frame;
        this.board_size = new Dimension(game_frame.select_toolbar.getSize().height,game_frame.select_toolbar.getSize().height);
        
        reset_mini_map();
        
        setVisible(true);
    }
    
    public void paint(Graphics g){
        super.paint(g);
        
        Vector<Dimension> locations = new Vector<>();
        Vector<Integer> mode = new Vector<>();
        
        for (int j = 0; j < Finals.pixeles[0].length; j++)
        {
            for (int i = 0; i < Finals.pixeles.length; i++)
            {
                switch (Finals.pixeles[i][j]%10)
                {
                case 0:
                      g.drawImage(Finals.water_shallow.getImage(), i * Finals.MINI_MAP_PIC_SIZE.width, j *Finals.MINI_MAP_PIC_SIZE.height, Finals.MINI_MAP_PIC_SIZE.width, Finals.MINI_MAP_PIC_SIZE.height, null);
                    break;
                case 1:
                    if(Finals.pixeles[i][j]/10 - (Finals.pixeles[i][j]/100)*10 ==1)
                        g.drawImage(Finals.water_shallow.getImage(), i * Finals.MINI_MAP_PIC_SIZE.width, j *Finals.MINI_MAP_PIC_SIZE.height, Finals.MINI_MAP_PIC_SIZE.width, Finals.MINI_MAP_PIC_SIZE.height, null);
                    else if(Finals.pixeles[i][j]/10 - (Finals.pixeles[i][j]/100)*10==2)
                        g.drawImage(Finals.deep_water.getImage(),  i * Finals.MINI_MAP_PIC_SIZE.width, j *Finals.MINI_MAP_PIC_SIZE.height, Finals.MINI_MAP_PIC_SIZE.width, Finals.MINI_MAP_PIC_SIZE.height, null);
                    
                        g.drawImage(Finals.sand_icon[Finals.pixeles[i][j]/100].getImage(), i * Finals.MINI_MAP_PIC_SIZE.width, j *Finals.MINI_MAP_PIC_SIZE.height, Finals.MINI_MAP_PIC_SIZE.width, Finals.MINI_MAP_PIC_SIZE.height, null);
                    break;
                case 2:
                    locations.add(new Dimension(i,j));
                    mode.add(2);
                    g.drawImage(Finals.grass_icon[15].getImage(), i * Finals.MINI_MAP_PIC_SIZE.width, j *Finals.MINI_MAP_PIC_SIZE.height, Finals.MINI_MAP_PIC_SIZE.width, Finals.MINI_MAP_PIC_SIZE.height,null);
                    break;
                case 3:
                    g.drawImage(Finals.sand_icon[15].getImage(), i * Finals.MINI_MAP_PIC_SIZE.width, j *Finals.MINI_MAP_PIC_SIZE.height, Finals.MINI_MAP_PIC_SIZE.width, Finals.MINI_MAP_PIC_SIZE.height,null);
                    g.drawImage(Finals.grass_icon[Finals.pixeles[i][j]/10].getImage(), i * Finals.MINI_MAP_PIC_SIZE.width, j *Finals.MINI_MAP_PIC_SIZE.height, Finals.MINI_MAP_PIC_SIZE.width, Finals.MINI_MAP_PIC_SIZE.height,null);
                    break;
                case 4:
                    g.drawImage(Finals.deep_water.getImage(), i * Finals.MINI_MAP_PIC_SIZE.width, j *Finals.MINI_MAP_PIC_SIZE.height, Finals.MINI_MAP_PIC_SIZE.width, Finals.MINI_MAP_PIC_SIZE.height,null);
                    break;
                case 5:
                    if(Finals.pixeles[i][j]/10==0)
                        g.drawImage(Finals.water_shallow.getImage(), i * Finals.MINI_MAP_PIC_SIZE.width, j *Finals.MINI_MAP_PIC_SIZE.height, Finals.MINI_MAP_PIC_SIZE.width, Finals.MINI_MAP_PIC_SIZE.height, null);
                    if(Finals.pixeles[i][j]/10==4)
                        g.drawImage(Finals.deep_water.getImage(), i * Finals.MINI_MAP_PIC_SIZE.width, j *Finals.MINI_MAP_PIC_SIZE.height, Finals.MINI_MAP_PIC_SIZE.width, Finals.MINI_MAP_PIC_SIZE.height,null);
                    g.drawImage(Finals.fish_group.getImage(),i*Finals.MINI_MAP_PIC_SIZE.width-Finals.tree_size.width/4
                    ,(j)*Finals.MINI_MAP_PIC_SIZE.height-Finals.MINI_MAP_TREE_SIZE.height/2, Finals.MINI_MAP_TREE_SIZE.width, Finals.MINI_MAP_TREE_SIZE.height,null);
                    break;
              case 6:
                    locations.add(new Dimension(i,j));
                    mode.add(6);
                  //g.drawImage(Finals.sand_icon[15].getImage(),i*Finals.MINI_MAP_PIC_SIZE.width,j*Finals.MINI_MAP_PIC_SIZE.height, Finals.MINI_MAP_PIC_SIZE.width, Finals.MINI_MAP_PIC_SIZE.height,null);
                break;
                case 7:
                    g.drawImage(Finals.grass_icon[15].getImage(),i*Finals.MINI_MAP_PIC_SIZE.width,j*Finals.MINI_MAP_PIC_SIZE.height, Finals.MINI_MAP_PIC_SIZE.width, Finals.MINI_MAP_PIC_SIZE.height,null);
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
                g.drawImage(Finals.tree_icon[Finals.pixeles_mode[locations.elementAt(i).width][locations.elementAt(i).height]].getImage(),(locations.elementAt(i).width)*Finals.MINI_MAP_PIC_SIZE.width-Finals.MINI_MAP_TREE_SIZE.width/4
                        ,(locations.elementAt(i).height)*Finals.MINI_MAP_PIC_SIZE.height-Finals.MINI_MAP_TREE_SIZE.height/2, Finals.MINI_MAP_TREE_SIZE.width, Finals.MINI_MAP_TREE_SIZE.height,null);
            break;
            case 7:
                g.drawImage(Finals.mine.getImage(),(locations.elementAt(i).width)*Finals.MINI_MAP_PIC_SIZE.width-Finals.MINI_MAP_TREE_SIZE.width/4
                        ,(locations.elementAt(i).height)*Finals.MINI_MAP_PIC_SIZE.height-Finals.MINI_MAP_TREE_SIZE.height/2, Finals.MINI_MAP_TREE_SIZE.width, Finals.MINI_MAP_TREE_SIZE.height,null);
            break;
            case 6:
                g.drawImage(Finals.iron_mountain.getImage(),(locations.elementAt(i).width)*Finals.MINI_MAP_PIC_SIZE.width-Finals.MINI_MAP_IRON_MOUNTAIN_SIZE.width/4
                      ,(locations.elementAt(i).height)*Finals.MINI_MAP_PIC_SIZE.height-Finals.MINI_MAP_IRON_MOUNTAIN_SIZE.height/2, Finals.MINI_MAP_IRON_MOUNTAIN_SIZE.width, Finals.MINI_MAP_IRON_MOUNTAIN_SIZE.height,null);
          
            break;
            }
        }
        
        if(is_inGame){
            for(int i=0 ;i<game_panel.list_of_units.size();i++){
                switch(game_panel.list_of_units.elementAt(i).nation){//gives color according to nation
                case 0:
                    g.setColor(Color.red);
                    break;
                case 1:
                    g.setColor(Color.blue);
                    break;
                case 2:
                    g.setColor(Color.green);
                    break;
                case 3:
                    g.setColor(Color.yellow);
                    break;
                }
                g.fillArc((game_panel.list_of_units.elementAt(i).getLocation().x/Finals.PIC_SIZE.width)*Finals.MINI_MAP_PIC_SIZE.width,(game_panel.list_of_units.elementAt(i).getLocation().y/Finals.PIC_SIZE.height)*Finals.MINI_MAP_PIC_SIZE.height,
                        Finals.MINI_MAP_PIC_SIZE.width,Finals.MINI_MAP_PIC_SIZE.height,0, 360);
            }
        }
        
        g.setColor(Color.yellow);
//        System.out.println((-map.getLocation().x/Finals.PIC_SIZE.width)*Finals.MINI_MAP_PIC_SIZE.width+""+-(map.getLocation().y/Finals.PIC_SIZE.height)*Finals.MINI_MAP_PIC_SIZE.height);
        if(is_inGame){
            g.drawRect((-game_panel.getLocation().x/Finals.PIC_SIZE.width)*Finals.MINI_MAP_PIC_SIZE.width,-(game_panel.getLocation().y/Finals.PIC_SIZE.height)*Finals.MINI_MAP_PIC_SIZE.height,(game_frame.game_panel_display_window.getSize().width/Finals.PIC_SIZE.width)*Finals.MINI_MAP_PIC_SIZE.width,(game_frame.game_panel_display_window.getSize().height/Finals.PIC_SIZE.height)*Finals.MINI_MAP_PIC_SIZE.height);
        }
        else{
            g.drawRect((-map.getLocation().x/Finals.PIC_SIZE.width)*Finals.MINI_MAP_PIC_SIZE.width,-(map.getLocation().y/Finals.PIC_SIZE.height)*Finals.MINI_MAP_PIC_SIZE.height,(Finals.BOARD_SIZE.width/Finals.PIC_SIZE.width)*Finals.MINI_MAP_PIC_SIZE.width,(Finals.BOARD_SIZE.height/Finals.PIC_SIZE.height)*Finals.MINI_MAP_PIC_SIZE.height);
        }
    }
    
    @Override
    protected void processEvent(AWTEvent arg0)
    {
        if(arg0.getID()==Messages.reset_mini_map){
            System.out.println("mini_map reseted");
            reset_mini_map();
        }
        super.processEvent(arg0);
    }
    
    public void run (){
        while(true){
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            repaint();
        }
    }

//    private void changeLookAndFeel()
//    {
//        try
//        {
//            UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//    }

    int mouse_x=0;
    int mouse_y=0;
    
    @Override
    public void mouseClicked(MouseEvent arg0)
    {
        System.out.println("clicked");
        if(is_inGame){
          //  Point last_location =(Point) game_panel.getLocation().clone();// if where player clicks is outside of game border it would reset the position
            game_panel.setLocation(-(mouse_x/Finals.MINI_MAP_PIC_SIZE.width)*Finals.PIC_SIZE.width+game_frame.game_panel_display_window.getSize().width/2,
                    -(mouse_y/Finals.MINI_MAP_PIC_SIZE.height)*Finals.PIC_SIZE.height+game_frame.game_panel_display_window.getSize().height/2);
//            if(game_panel.getLocation().x>0 || game_panel.getLocation().y>0 || 
//                    game_panel.getLocation().x+game_panel.getSize().width<game_frame.getSize().width||
//                    game_panel.getLocation().y+game_panel.getSize().height<game_frame.getSize().height)
//            {
//                game_panel.setLocation(last_location);
//            }
        }
        else{ 
            map.setLocation(-(mouse_x/Finals.MINI_MAP_PIC_SIZE.width)*Finals.PIC_SIZE.width+Finals.BOARD_SIZE.width/2,-(mouse_y/Finals.MINI_MAP_PIC_SIZE.height)*Finals.PIC_SIZE.height+Finals.BOARD_SIZE.height/2);
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent arg0)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent arg0)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent arg0)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(MouseEvent arg0)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent arg0)
    {
        if(is_inGame){
            mouse_x=arg0.getLocationOnScreen().x-game_frame.getLocation().x-this.getLocation().x;
            mouse_y=arg0.getLocationOnScreen().y-game_frame.getLocation().y-this.getLocation().y;
        }
        else{
            mouse_x=arg0.getLocationOnScreen().x-ME.getLocation().x-this.getLocation().x;
            mouse_y=arg0.getLocationOnScreen().y-ME.getLocation().y-this.getLocation().y-30;
            //System.out.println("mini_map "+mouse_x+"=="+mouse_y);
        }
    }
    
    private void reset_mini_map(){
        
        Finals.MINI_MAP_PIC_SIZE.width=Finals.MINI_MAP_SIZE.width/Finals.pixeles.length;
        Finals.MINI_MAP_PIC_SIZE.height=Finals.MINI_MAP_SIZE.height/Finals.pixeles[0].length;
        
        Finals.MINI_MAP_TREE_SIZE.width=2*Finals.MINI_MAP_PIC_SIZE.width;
        Finals.MINI_MAP_TREE_SIZE.height=2*Finals.MINI_MAP_PIC_SIZE.height;
        
        Finals.MINI_MAP_IRON_MOUNTAIN_SIZE.width=3*Finals.MINI_MAP_PIC_SIZE.width/2;
        Finals.MINI_MAP_IRON_MOUNTAIN_SIZE.height=3*Finals.MINI_MAP_PIC_SIZE.height/2;
        
       //System.out.println(Finals.pixeles.length+"=="+Finals.pixeles[0].length);
        setSize(Finals.MINI_MAP_SIZE);
    }
   
    
}
