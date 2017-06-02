
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.UIManager;

public class MapEditor extends JDialog implements MouseMotionListener, Runnable, MouseListener,MouseWheelListener
{

    private static final long serialVersionUID = 1L;
    Map map;
    Mini_Map mini_map;
    
    
    
    JButton save;
    JButton load;

    JPanel show_board;
    JPanel picture_bar;
    JPanel map_tools; //preview//reset
    
    File_chooser_save_load File_save;

    ExecutorService Thread_ex;

    //show_board JLables
    JButton sand;
    JButton water_shallow;
    JButton tree;
    JButton water_deep;
    JButton grass;
    JButton fish_group ;
    JButton iron_mountain_icon;
    JButton mine_button;
    
    //map_tools JLables
    JButton preview_button;
    JButton reset_map;
    //JButton mini_map_button;
    
    int clicked = 0;

    
    boolean editor_exit=false;
    
    Font font = new Font ("Tahoma",20,20);
    
    Vector<JButton> selection_disabler = new Vector<>();
    
    Menu menu;

    public MapEditor(final Menu menu, int width , int height)
    {
        reset_map(width,height);
        this.menu=menu;
        ////////////////////////////
        map = new Map();
        map.repaint();
        ///////////////////////////
        mini_map=new Mini_Map(this.map,MapEditor.this);
        
        changeLookAndFeel();
        this.setModal(true);
        setLayout(null);
        setTitle("Map Editor");
        setResizable(false);

        setSize(Finals.MAP_EDITOR_SIZE);
        setLocation(Finals.MAP_EDITOR_LOCATION);

        picture_bar = new JPanel();
        picture_bar.setLayout(new GridLayout(1, 10));
        picture_bar.setLocation(0, 0);
        picture_bar.setSize(getSize().width-mini_map.getSize().width, Finals.BUTTON_SIZE.height);

        JScrollBar JB = new JScrollBar();
        JB.add(picture_bar);
        // add(JB);
        sand = new JButton();
        sand.setBackground(Color.white);
        // sand.setLocation(0,50);
        sand.setIcon(Finals.sand_icon[15]);
        //sand.setFocusable(false);
        sand.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                menu.dispatchEvent(new ComponentEvent(MapEditor.this,Messages.click_sound));
                 if (clicked != 1)
                {
                    Button_disabler();
                    sand.setBackground(null);
                    clicked = 1;
                }
                else
                {
                    sand.setBackground(Color.white);
                    clicked = -1;//-1 means draw nothing
                }
            }
        });
        water_shallow = new JButton();
        water_shallow.setIcon(Finals.water_shallow);
        water_shallow.setBackground(Color.white);
        water_shallow.setFocusable(false);
        water_shallow.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                menu.dispatchEvent(new ComponentEvent(MapEditor.this,Messages.click_sound));
                if (clicked != 0)
                {
                    Button_disabler();
                    water_shallow.setBackground(null);
                    clicked = 0;
                }
                else
                {
                    clicked = -1;
                    water_shallow.setBackground(Color.white);
                }
            }
        });

        tree = new JButton();
        tree.setBackground(Color.white);
        tree.setIcon(Finals.tree_icon_image);
        tree.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                menu.dispatchEvent(new ComponentEvent(MapEditor.this,Messages.click_sound));
                if (clicked != 2)
                {
                    Button_disabler();
                    tree.setBackground(null);
                    clicked = 2;
                }
                else
                {
                    tree.setBackground(Color.white);
                    clicked = -1;
                } 
            }
        });

        grass = new JButton();
        grass.setBackground(Color.white);
        grass.setIcon(Finals.grass_icon[15]);
        grass.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                menu.dispatchEvent(new ComponentEvent(MapEditor.this,Messages.click_sound));
                if (clicked != 3)
                {
                    Button_disabler();
                    grass.setBackground(null);
                    clicked = 3;
                }
                else
                {
                    grass.setBackground(Color.white);
                    clicked = -1;
            }
            }
        });
////////////////////////////////////////
        
        water_deep = new JButton();
        water_deep.setIcon(Finals.deep_water);
        water_deep.setBackground(Color.white);
        water_deep.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                menu.dispatchEvent(new ComponentEvent(MapEditor.this,Messages.click_sound));
                if (clicked != 4)
                {
                    Button_disabler();
                    water_deep.setBackground(null);
                    clicked = 4;
                }
                else
                {
                    water_deep.setBackground(Color.white);
                    clicked = -1;
                }
            }
        });
/////////////////////////////////////
        
        fish_group = new JButton();
        fish_group.setIcon(Finals.fish_group_icon);
        fish_group.setBackground(Color.white);
        fish_group.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                menu.dispatchEvent(new ComponentEvent(MapEditor.this,Messages.click_sound));
                if(clicked!=5)
                {
                    
                    Button_disabler();
                    fish_group.setBackground(null);
                    clicked=5;
                }
                else 
                {
                    fish_group.setBackground(Color.white);
                    clicked = -1; 
                }
            }
        });
        
        iron_mountain_icon = new JButton();
        iron_mountain_icon.setBackground(Color.white);
        iron_mountain_icon.setIcon(Finals.iron_mountain);
        iron_mountain_icon.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                menu.dispatchEvent(new ComponentEvent(MapEditor.this,Messages.click_sound));
                if(clicked!=6)
                {
                    
                    Button_disabler();
                    iron_mountain_icon.setBackground(null);
                    clicked=6;
                }
                else 
                {
                    iron_mountain_icon.setBackground(Color.white);
                    clicked = -1; 
                }
            }
        });
        
        mine_button  = new JButton();
        mine_button.setIcon(Finals.mine);
        mine_button.setBackground(Color.white);
        mine_button.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                menu.dispatchEvent(new ComponentEvent(MapEditor.this,Messages.click_sound));
                if(clicked!=7)
                {
                    
                    Button_disabler();
                    mine_button.setBackground(null);
                    clicked=7;
                }
                else 
                {
                    mine_button.setBackground(Color.white);
                    clicked = -1; 
                }
            }
        });
////////////////////////////////////
        picture_bar.add(grass);
        picture_bar.add(sand);
        picture_bar.add(water_shallow);
        picture_bar.add(tree);
        picture_bar.add(water_deep);
        picture_bar.add(fish_group);
        picture_bar.add(iron_mountain_icon);
        picture_bar.add(mine_button);
 /////////////////////////////////////////////
        show_board = new JPanel();
        //show_board.setBackground(Color.black);
        show_board.setLocation(Finals.BOARD_LOCATION);
        show_board.setSize(Finals.BOARD_SIZE);
        show_board.setLayout(null);
        
        show_board.add(map);
///////////////////////////
        File_save = new File_chooser_save_load();
        File_save.mini_map_getter(mini_map);
        File_save.map_getter(map);
//      File_save.setFocusable(false);
        File_save.setSize(getSize().width/4, Finals.BUTTON_SIZE.height);
        File_save.setLocation(0, Finals.BUTTON_SIZE.height);
//////////////////////////
      
        mini_map.setLocation(picture_bar.getSize().width,0);
///////////////////////////////////////////////////////
        map_tools = new JPanel();
        map_tools.setSize(mini_map.getLocation().x+File_save.getLocation().x-File_save.getSize().width,Finals.BUTTON_SIZE.height);
        map_tools.setLayout(new GridLayout(1,2));
        map_tools.setLocation(File_save.getSize().width,Finals.BUTTON_SIZE.height);
////////////////////////////////////////////////////////     
        preview_button = new JButton("Preview");
        preview_button.setFont(font);
//      preview.setFocusable(false);
        preview_button.setBackground(Color.white);
        preview_button.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                menu.dispatchEvent(new ComponentEvent(MapEditor.this,Messages.click_sound));
                Preview preview=new Preview();
            }
        });
        map_tools.add(preview_button);
   /////////////////////////////////////////////////////     
        reset_map = new JButton("Reset map");
        reset_map.setFont(font);
        reset_map.setBackground(Color.white);
       // reset_map.setFocusable(false);
        reset_map.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                
                menu.dispatchEvent(new ComponentEvent(MapEditor.this,Messages.click_sound));
                reset_map(Finals.pixeles.length,Finals.pixeles[0].length);
                mini_map.repaint();
            }
        });
        map_tools.add(reset_map);

        add(picture_bar);
        add(show_board);
        add(File_save);
        add(map_tools);
        
        this.addMouseMotionListener(this);
        this.addMouseListener(this);

        Thread_ex = Executors.newCachedThreadPool();
        Thread_ex.execute(this);
        Thread_ex.execute(mini_map);
        
        selection_disabler.add(sand);
        selection_disabler.add(grass);
        selection_disabler.add(tree);
        selection_disabler.add(water_deep);
        selection_disabler.add(water_shallow);
        selection_disabler.add(fish_group);
        selection_disabler.add(iron_mountain_icon);
        selection_disabler.add(mine_button);
        
        add(mini_map);
        
        mini_map.repaint();
        
        this.addMouseWheelListener(this);
    ///JMenue part
        JMenuBar jMenuBar=new JMenuBar();
        this.setJMenuBar(jMenuBar);
        JMenu file=new JMenu("File");
        file.setFont(font);
        file.setMnemonic('F');
        jMenuBar.add(file);
        JMenuItem open=new JMenuItem("Open");
        open.setFont(font);
        open.setMnemonic('O');
        open.addActionListener(File_save.getLoadActionListner());
        file.add(open);
        JMenuItem save=new JMenuItem("Save");
        save.setFont(font);
        save.setMnemonic('S');
        save.addActionListener(File_save.getSaveActionListner());
        file.add(save);
        JMenuItem exit=new JMenuItem("Exit");
        exit.setFont(font);
        exit.setMnemonic('E');
        exit.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                
                    MapEditor.this.dispatchEvent(new WindowEvent(MapEditor.this,WindowEvent.WINDOW_CLOSING));
                   
            }
        });
        file.add(exit);

//        this.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                int flag=-2;
//                        boolean temp=false;
//                while(!temp)
//                {
//                    if (!MapEditor.this.File_save.getIsSaved()) {
//                        flag = JOptionPane.showOptionDialog(null, "You're Map Is not saved\nDo You Want to Save It?", "Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
//                        if (flag == JOptionPane.YES_OPTION) {
//                            temp = MapEditor.this.File_save.makeSave();
//                        }
//                        else
//                            temp=true;
//                    }
//                }
//                if (flag != JOptionPane.CANCEL_OPTION) {
//                    flag = JOptionPane.showOptionDialog(null, "Are sure to Quit?", "Quit?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
//                    if (flag == JOptionPane.YES_OPTION) {
//                        MapEditor.this.dispose();
//                    }
//
//                }
//            }
//        });
        
        JMenu music_on_off = new JMenu("Music");
        music_on_off.setFont(font);
        jMenuBar.add(music_on_off);
        JMenuItem on_off = new JMenuItem("ON/OFF");
        music_on_off.add(on_off);
        on_off.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                menu.dispatchEvent(new ComponentEvent(MapEditor.this,Messages.music_off_on));
            }
        });
        
        
        JMenu more=new JMenu("More");
        more.setFont(font);
        more.setMnemonic('M');
        jMenuBar.add(more);
        JMenuItem aboutUs=new JMenuItem("About Us");
        aboutUs.setFont(font);
        aboutUs.setMnemonic('A');
        more.add(aboutUs);
        aboutUs.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                JOptionPane.showMessageDialog(MapEditor.this,new String("<HTML><body>This Program is our Final project for Advance Programming course<br><br>our Team Members are :<br><br>Arman Pashamokhtari<br><br>Mohammad Fakhreddin<br><br>Navid Farahmand</body></HTML>"),"About Us",JOptionPane.INFORMATION_MESSAGE);
                        
            }
        });
        


       // this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
        
    }

  
    boolean user_clicked ;
    @Override
    public void mouseDragged(MouseEvent arg0)
    {   
       
        mouse_x = arg0.getLocationOnScreen().x - this.getLocation().x - show_board.getLocation().x;
        mouse_y = arg0.getLocationOnScreen().y - this.getLocation().y - show_board.getLocation().y - 50;

    }

    int mouse_x = 0;
    int mouse_y = 0;

    @Override
    public void mouseMoved(MouseEvent arg0)
    {
        mouse_x = arg0.getLocationOnScreen().x - this.getLocation().x - show_board.getLocation().x;
        mouse_y = arg0.getLocationOnScreen().y - this.getLocation().y - show_board.getLocation().y-75;

       //  System.out.println(mouse_x+"=="+mouse_y);
        if (mouse_x < 0 || mouse_y < 0 || mouse_x > show_board.getSize().width || mouse_y > show_board.getSize().height)
        {
            Finals.in_board_panel = false;
        }
        else
        {
            Finals.in_board_panel = true;
        }

    }

    public void run()
    {
        int x = 0;// it's just a temp for setting location for map
        int y = 0;
        while (!editor_exit)
        {
            while(user_clicked&&Finals.in_board_panel){
                    int selected_x = mouse_x - map.getLocation().x;
                    int selected_y = mouse_y - map.getLocation().y;
                    
                    try{
                        if(clicked!=-1)
                        switch(clicked){
                        case 2://tree
                            if(Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height]%10==3
                            && map.mapUpdater(selected_x/Finals.PIC_SIZE.width, selected_y/ Finals.PIC_SIZE.height,3)==15 &&
                            same_finder(7 , selected_x/Finals.PIC_SIZE.width, selected_y/ Finals.PIC_SIZE.height)==false){
                                Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height] = clicked;
                                Finals.pixeles_mode[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height]=random(4);  
                            }
                            break;
                        case 1://sand
                            if(Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height]/10==0){
                              //System.out.println("---"+Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height]);
                              if(Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height]%10 != 4){
                                  Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height] = clicked+10;
                              }
                              else if(Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height]%10 == 4) 
                              {
                                  Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height] = clicked+20;
                              }
                              //System.out.println(Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height]);
                              }
                            break;
                        case 3://grass
                            if( map.mapUpdater(selected_x/Finals.PIC_SIZE.width, selected_y/ Finals.PIC_SIZE.height,1)==15){
                                Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height] = clicked; 
                            }
                            break;
                        case 5://small_fish
                            if(map.mapUpdater(selected_x/Finals.PIC_SIZE.width, selected_y/ Finals.PIC_SIZE.height,5)==15){// checks if there is enough space for fishes
                                switch (Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height] )
                                {
                                case 0:
                                    Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height] = clicked+0*10;
                                    break;
                                case 4:
                                    Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height] = clicked+4*10;
                                default:
                                    break;
                                }
                            }
                            break;
                        case 6://iron_mountain
                            if( map.mapUpdater(selected_x/Finals.PIC_SIZE.width, selected_y/ Finals.PIC_SIZE.height,1)==15){
                            Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height] = clicked;
                            }
                            break;
                        case 7:
                            if(Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height]%10==3 && 
                            map.mapUpdater(selected_x/Finals.PIC_SIZE.width, selected_y/ Finals.PIC_SIZE.height,3)==15 && 
                            same_finder(clicked ,selected_x/Finals.PIC_SIZE.width, selected_y/ Finals.PIC_SIZE.height)==false &&  
                            same_finder(2 , selected_x/Finals.PIC_SIZE.width, selected_y/ Finals.PIC_SIZE.height)==false){
                                Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height] = clicked;
                            }
                          break;
                        default://4= deep water//0= shallow water
                                Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height] = clicked;
                            break;
                        }
                    }catch(Exception e){
                        
                    }
                    map.repaint();
               }
            
            x = map.getLocation().x;
            y = map.getLocation().y;

            if (mouse_x > show_board.getSize().width - 50 && 
                    x+map.getSize().width+8>=Finals.BOARD_SIZE.width-100)
            {// changes the location
                x -= 8;
            }
            if (mouse_y > show_board.getSize().height - 50  &&
                    y+map.getSize().height+8>=Finals.BOARD_SIZE.height-100)
            {
                y -= 8;
            }
            if (mouse_y < 100 && y<100)
            {
                y = y + 8;
            }
            if (mouse_x < 100 && x<100)
            {
                x = x + 8;
            }
            if (Finals.in_board_panel)
                map.setLocation(x, y);
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            map.repaint();
            show_board.setBackground(Color.black);
            show_board.repaint();
        }
    }

  
    @Override
    protected void processEvent(AWTEvent arg0)
    {
        if(arg0.getID()==201){
            Finals.PIC_SIZE = new Dimension(50,50);
            Finals.tree_size  = new Dimension(100,100);
            Finals.iron_mountain_size = new Dimension(150,150);
        }
        super.processEvent(arg0);
    }

    private boolean same_finder(int mode, int x, int y)
    {
        try{
        
            if(x>0)
            if (Finals.pixeles[x-1][y ]%10 ==mode ) {
                return true;
            }
            if(x<Finals.pixeles.length-1)
            if (Finals.pixeles[x+1][y]%10 ==mode)  {
                return true;
            }
            if(y<Finals.pixeles.length-1)
            if (Finals.pixeles[x][y+1] %10==mode ) {
                return true;
            }
            if(y>0)
            if (Finals.pixeles[x][y-1]%10 == mode) {
                return true;
            }  
            
        }catch(Exception e){
            
        }
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent arg0)// creates images on selected locations
    {
//
//        int selected_x = mouse_x - map.getLocation().x;
//        int selected_y = mouse_y - map.getLocation().y;
//        System.out.println(selected_x + "==" + selected_y);
//        if (Finals.in_board_panel)
//        {
//            Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height] = clicked;
//            System.out.println(selected_x / Finals.PIC_SIZE.width + "==" + selected_y / Finals.PIC_SIZE.height + "=="
//                    + +Finals.pixeles[selected_x / Finals.PIC_SIZE.width][selected_y / Finals.PIC_SIZE.height]);
//            System.out.println(clicked);
//            repaint();
//            map.repaint();
//        }
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
        user_clicked=true;
        
    }

    @Override
    public void mouseReleased(MouseEvent arg0)
    {
        user_clicked=false;

    }
    private void changeLookAndFeel()
    {
        try
        {
            UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //need to complete not working
    @Override
    public void mouseWheelMoved(MouseWheelEvent arg0)
    {
       // System.out.println("zoom");
        
        int rotate = arg0.getWheelRotation();
        Point map_location = new Point(map.getLocation());// It would changes the location of map for usual zoom
        Point current_location = new Point(show_board.getSize().width/2 - map_location.x , show_board.getSize().height/2 - map_location.y) ;
        Dimension OLD_PIC_SIZE = new Dimension(Finals.PIC_SIZE.width , Finals.PIC_SIZE.height );
        
        if(rotate<0&& Finals.PIC_SIZE.width<=75){//zoom in
            Finals.PIC_SIZE.width=Finals.PIC_SIZE.width+Finals.zoom_point.width;
            Finals.PIC_SIZE.height=Finals.PIC_SIZE.height+Finals.zoom_point.height;
        
            Finals.tree_size.width=Finals.tree_size.width+2*Finals.zoom_point.width;
            Finals.tree_size.height=Finals.tree_size.height+2*Finals.zoom_point.height;
            
            Finals.iron_mountain_size.width=Finals.iron_mountain_size.width+3*Finals.zoom_point.width;
            Finals.iron_mountain_size.height=Finals.iron_mountain_size.height+3*Finals.zoom_point.height;


           // map.setLocation(map_location.x+10*Finals.zoom_point.width,map_location.y+10*Finals.zoom_point.height);
            
            show_board.repaint();
        }
        else if(Finals.PIC_SIZE.width>=25){// zoom out
            Finals.PIC_SIZE.width=Finals.PIC_SIZE.width-Finals.zoom_point.width;
            Finals.PIC_SIZE.height=Finals.PIC_SIZE.height-Finals.zoom_point.height;
            
            Finals.tree_size.width=Finals.tree_size.width-2*Finals.zoom_point.width;
            Finals.tree_size.height=Finals.tree_size.height-2*Finals.zoom_point.height;
            
            Finals.iron_mountain_size.width=Finals.iron_mountain_size.width-3*Finals.zoom_point.width;
            Finals.iron_mountain_size.height=Finals.iron_mountain_size.height-3*Finals.zoom_point.height;
            
            //map.setLocation(map_location.x-10*Finals.zoom_point.width,map_location.y-10*Finals.zoom_point.height);
            
            show_board.repaint();
        }
        
        Finals.MAP_SIZE=new  Dimension(Finals.pixeles.length*Finals.PIC_SIZE.width,Finals.pixeles[0].length*Finals.PIC_SIZE.height);
        map.setLocation(map_location.x-(Finals.MAP_SIZE.width-map.getSize().width)/2 ,map_location.y-(Finals.MAP_SIZE.height-map.getSize().height)/2);
        map.setSize(Finals.MAP_SIZE);
    
    }

    private int random (int board){
        Random rand= new Random();
        return rand.nextInt(board);
    }
    
    private void Button_disabler(){
        for(int i =0; i<selection_disabler.size();i++){
            selection_disabler.elementAt(i).setBackground(Color.white);
        }
    }
    
    public void reset_map (int width,int height ){

        Finals.pixeles = new Integer[width][height];
        Finals.pixeles_mode=new Integer[width][height];
        for(int i=0 ;i<Finals.pixeles.length;i++){
            for(int j=0 ;j<Finals.pixeles[i].length;j++){
                Finals.pixeles[i][j]=4;
                Finals.pixeles_mode[i][j]=0;
            }
        }
        Finals.MAP_SIZE = new Dimension(Finals.PIC_SIZE.width*Finals.pixeles.length,Finals.PIC_SIZE.height*Finals.pixeles[0].length);

    }
    ///for save and load and reseting the map
}
