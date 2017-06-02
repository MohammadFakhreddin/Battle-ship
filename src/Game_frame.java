import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.omg.CORBA.Bounds;

public class Game_frame extends JFrame implements MouseMotionListener, Runnable, MouseWheelListener
{
    // Size of Game_panel(where the game panel is displayed ) is set to fill 4/5 of the screen size
    // the selection_panel(where user selects units and building and ...) and game mini_map is set down of the map
    // before closing
    // 1-change look and feel
    // 2-exiting connection
    // 3-need and exit button
    Game_panel game_panel;
    JLabel game_panel_display_window;
    Selected_Info select_toolbar;
    ExecutorService Thread_ex;
    Dimension OLD_PIC_SIZE;
    Mini_Map mini_map;
    private boolean is_single_player=false;
    
    int mouse_x = 0;
    int mouse_y = 0;
    
    
    public Game_frame(Vector<CartesianPoint> HQ_points , boolean is_single_player)//checks playing against bots or cpu players
    {

        setLayout(null);

        Thread_ex = Executors.newCachedThreadPool();
        
        this.is_single_player = is_single_player;
        Dimension Screen_size = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(0, 0);
        setSize(Screen_size);
        setUndecorated(true);

        movement_posibilities_value_setter();

        OLD_PIC_SIZE = new Dimension(Finals.PIC_SIZE);

        game_panel_display_window = new JLabel();
        game_panel_display_window.setSize(Screen_size.width, (4 * Screen_size.height) / 5);
        game_panel_display_window.setLocation(0, 0);
        select_toolbar = new Selected_Info(new Point(0, game_panel_display_window.getHeight()), new Dimension(
                Game_frame.this.getSize().width, Game_frame.this.getSize().height
                        - game_panel_display_window.getSize().height), this);
        game_panel = new Game_panel(HQ_points, this.getLocation(), this, select_toolbar);
        select_toolbar.game_panel_setter(game_panel);// it send game_panel address for selected_info

        mini_map = new Mini_Map(game_panel, this, game_panel_display_window.getSize());
        mini_map.setSize(Finals.MINI_MAP_SIZE);
        mini_map.setLocation(this.getLocation().x + this.getSize().width - mini_map.getSize().width,
                this.getLocation().y + this.getSize().height - mini_map.getSize().height);
        Thread_ex.execute(mini_map);
        getContentPane().add(mini_map);

        game_panel_display_window.add(game_panel);
        getContentPane().add(game_panel_display_window);
        new DayAndNightSimulator(true,Game_frame.this);
        
        if(is_single_player){
        Finals.terminal = new Command_executer(game_panel); 
        }else{ 
        Finals.terminal.set_game_panel(game_panel);
        }
        
        Thread_ex.execute(this);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //BOT bot = new BOT(game_panel);
        game_panel.addMouseMotionListener(this);

        // Game_frame.this.setComponentZOrder(exit_button, 0);
        // Game_frame.this.setComponentZOrder(game_panel_display_window, 1);
        // Game_frame.this.setComponentZOrder(mini_map, 2);
        //
        setVisible(true);
    }

    private void movement_posibilities_value_setter()
    {
        Finals.move_posibilities_land_unit = new Integer[Finals.pixeles.length][Finals.pixeles[0].length];
        Finals.move_posibilities_sea_unit = new Integer[Finals.pixeles.length][Finals.pixeles[0].length];
        for (int i = 0; i < Finals.pixeles.length; i++)
        {
            for (int j = 0; j < Finals.pixeles[0].length; j++)
            {
                if (Finals.pixeles[i][j] % 10 == 0 || Finals.pixeles[i][j] % 10 == 4 || Finals.pixeles[i][j] %10 == 5)
                {
                    Finals.move_posibilities_sea_unit[i][j] = 0;
                    Finals.move_posibilities_land_unit[i][j] = 1;
                }
                else
                {
                    Finals.move_posibilities_sea_unit[i][j] = 1;
                    Finals.move_posibilities_land_unit[i][j] = 0;
                }
            }
        }

    }

    @Override
    protected void processEvent(AWTEvent arg0)
    {
        if (arg0.getID() == 201)
        {
            this.dispose();
        }
        else
            super.processEvent(arg0);
    }

    @Override
    public void mouseDragged(MouseEvent arg0)
    {

    }

    @Override
    public void mouseMoved(MouseEvent arg0)
    {
        this.mouse_x = arg0.getLocationOnScreen().x - this.getLocation().x;
        this.mouse_y = arg0.getLocationOnScreen().y - this.getLocation().y;
    }

    public void run()
    {
        while (true)
        {

            int x = game_panel.getLocation().x;
            int y = game_panel.getLocation().y;

            if (mouse_x > game_panel_display_window.getLocation().x
                    && mouse_x < game_panel_display_window.getLocation().x + game_panel_display_window.getSize().width
                    && mouse_y > game_panel_display_window.getLocation().y
                    && mouse_y < game_panel_display_window.getLocation().y + game_panel_display_window.getSize().height)
            {
                if (mouse_x < game_panel_display_window.getLocation().x + game_panel_display_window.getSize().width
                        && mouse_x > game_panel_display_window.getLocation().x
                                + game_panel_display_window.getSize().width - 100
                        && x + game_panel.getSize().width > game_panel_display_window.getSize().width)
                {
                    x = x - 1;
                }
                else if (mouse_x > game_panel_display_window.getLocation().x
                        && mouse_x < game_panel_display_window.getLocation().x + 100 && x < 0)
                {
                    x = x + 1;
                }
                if (mouse_y < game_panel_display_window.getSize().height - 100
                        && mouse_y > game_panel_display_window.getSize().height - 200
                        && y + game_panel.getSize().height > game_panel_display_window.getSize().height)
                {
                    y = y - 1;
                }
                else if (mouse_y > game_panel_display_window.getLocation().y
                        && mouse_y < game_panel_display_window.getLocation().y + 100 && y < 0)
                {
                    y = y + 1;
                }
                game_panel.setLocation(x, y);
            }
            try
            {
                Thread.sleep(1);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent arg0)
    {
        // System.out.println("zoom");

        int rotate = arg0.getWheelRotation();
        // Point map_location = new Point(map.getLocation());// It would changes the location of map for usual zoom
        // Point current_location = new Point(show_board.getSize().width/2 - map_location.x ,
        // show_board.getSize().height/2 - map_location.y) ;
        this.OLD_PIC_SIZE = new Dimension(Finals.PIC_SIZE.width, Finals.PIC_SIZE.height);
        //
        // if(game_panel_is_inside_window()){

        if (rotate < 0 && Finals.PIC_SIZE.width <= 60 && game_panel_is_inside_window())
        {// zoom in
            Finals.PIC_SIZE.width = Finals.PIC_SIZE.width + Finals.zoom_point.width;
            Finals.PIC_SIZE.height = Finals.PIC_SIZE.height + Finals.zoom_point.height;

            Finals.tree_size.width = Finals.tree_size.width + 2 * Finals.zoom_point.width;
            Finals.tree_size.height = Finals.tree_size.height + 2 * Finals.zoom_point.height;

            Finals.iron_mountain_size.width = Finals.iron_mountain_size.width + 3 * Finals.zoom_point.width;
            Finals.iron_mountain_size.height = Finals.iron_mountain_size.height + 3 * Finals.zoom_point.height;

            // map.setLocation(map_location.x+10*Finals.zoom_point.width,map_location.y+10*Finals.zoom_point.height);

            game_panel.repaint();
        }

        // }
        else if (Finals.PIC_SIZE.width >= 45 && rotate > 0)
        {// zoom out
            Finals.PIC_SIZE.width = Finals.PIC_SIZE.width - Finals.zoom_point.width;
            Finals.PIC_SIZE.height = Finals.PIC_SIZE.height - Finals.zoom_point.height;

            Finals.tree_size.width = Finals.tree_size.width - 2 * Finals.zoom_point.width;
            Finals.tree_size.height = Finals.tree_size.height - 2 * Finals.zoom_point.height;

            Finals.iron_mountain_size.width = Finals.iron_mountain_size.width - 3 * Finals.zoom_point.width;
            Finals.iron_mountain_size.height = Finals.iron_mountain_size.height - 3 * Finals.zoom_point.height;

            // map.setLocation(map_location.x-10*Finals.zoom_point.width,map_location.y-10*Finals.zoom_point.height);

            game_panel.repaint();
        }

        game_panel.dispatchEvent(new ComponentEvent(this, Messages.mouse_wheel_zoom));
    }

    private boolean game_panel_is_inside_window()// It checks for availability of zoom
    {
        if (game_panel.getLocation().x > 0)
            return false;
        if (game_panel.getLocation().x + game_panel.getSize().width < game_panel_display_window.getSize().width)
            return false;
        if (game_panel.getLocation().y > 0)
            return false;
        if (game_panel.getLocation().y + game_panel.getSize().height < game_panel_display_window.getSize().height)
            return false;

        return true;
    }

    public Dimension get_OLD_PIC_SIZE()
    {
        return this.OLD_PIC_SIZE;
    }

    public void addSelectedToolbar(JPanel component)
    {
        getContentPane().add(component);
        repaint();
    }

    public void reset_resorces()
    {
        game_panel.dispatchEvent(new ComponentEvent(Game_frame.this, Messages.new_year));

    }
    
    public boolean get_is_single_player(){
        return is_single_player;
    }

    public void fill_health()
    {
        game_panel.dispatchEvent(new ComponentEvent(Game_frame.this, Messages.fill_health));
    }
    
}
