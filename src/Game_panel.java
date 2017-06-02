import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// It displays  game
public class Game_panel extends JPanel implements MouseMotionListener, MouseListener, Runnable// It must show the game
// in the Game
{
    private ExecutorService Thread_ex;
    private Point mouse_position;
    public Vector<unit_controller> list_of_units;
    private Vector<unit_controller> selected_units;
    private Game_Environment game_enviroment;
    public Vector<HQ_Main_Base> list_of_HQ;
    public Vector<Steady_location> steady_locations;
    private Point frame_location;
    private Game_frame game_frame;
    private Selected_Info select_toolbar;
    
    int selected_HQ_number;

    public Game_panel(Vector<CartesianPoint> HQ_points, Point frame_location, Game_frame game_frame , Selected_Info select_toolbar)
    {
        setLayout(null);
        setBackground(Color.black);
        this.game_frame = game_frame;
        steady_locations = new Vector<>();// 3D steady locations
        steady_locations_vector_setter();
        list_of_HQ = new Vector<>();
        this.setLocation(0, 0);
        this.setSize(Finals.PIC_SIZE.width * Finals.pixeles.length, Finals.PIC_SIZE.height * Finals.pixeles[0].length);
        this.select_toolbar = select_toolbar;

        
        this.frame_location = frame_location;

        Thread_ex = Executors.newCachedThreadPool();

        mouse_position = new Point(0, 0);

        list_of_units = new Vector<>();
        selected_units = new Vector<>();

        game_enviroment = new Game_Environment();
        game_enviroment.setSize(this.getSize());
        game_enviroment.setLocation(0, 0);
        Thread_ex.execute(game_enviroment);

        for (int i = 0; i < HQ_points.size(); i++)
        {
            HQ_Main_Base HQ_main_base = new HQ_Main_Base(HQ_points.elementAt(i),i,Game_panel.this);//i shows HQ_nation
            add(HQ_main_base);
            list_of_HQ.add(HQ_main_base);

        }
//        for (int i = 5; i < 7; i++)
//        {
//            unit_controller unit = new unit_controller(new CartesianPoint(100 * i + 100, 100 * i + 100),
//                    Game_panel.this, 0, 1);
//            list_of_units.add(unit);
//            add(unit);
//            Thread_ex.execute(unit);
//            setComponentZOrder(unit, 0);
//            // System.out.println("adding unit");
//        }

//        unit_controller unit1 = new unit_controller(new CartesianPoint(1000, 100), Game_panel.this, 1, 1);
//        list_of_units.add(unit1);
//        add(unit1);
//        Thread_ex.execute(unit1);
//        setComponentZOrder(unit1, 0);
//
//        unit_controller unit2 = new unit_controller(new CartesianPoint(1000, 500), Game_panel.this, 1, 1);
//        list_of_units.add(unit2);
//        add(unit2);
//        Thread_ex.execute(unit2);
//        setComponentZOrder(unit2, 0);

        add(game_enviroment);
        // setComponentZOrder(game_enviroment, list_of_units.size()+list_of_HQ.size());
        game_frame.add(select_toolbar);
       // game_frame.setComponentZOrder(select_toolbar, 2);
        
        addMouseListener(this);
        addMouseMotionListener(this);
        setVisible(true);
    }

    private void steady_locations_vector_setter()
    {
        for (int j = 0; j < Finals.pixeles[0].length; j++)
            for (int i = 0; i < Finals.pixeles.length; i++)
            {
                if (Finals.pixeles[i][j]%10 == 2 || Finals.pixeles[i][j]%10 == 6 || Finals.pixeles[i][j]%10 == 7||Finals.pixeles[i][j]%10 == 5)
                {
                    steady_locations.add(new Steady_location( new CartesianPoint(i, j),Finals.pixeles[i][j]%10));
                }
            }

    }

    @Override
    protected void processEvent(AWTEvent arg0)
    {
        super.processEvent(arg0);
        if (arg0.getID() == Messages.mouse_wheel_zoom)
        {
            zoom_simulator();
        }
        else if(arg0.getID()== Messages.new_year){//fills the resources for new year
            for(int i=0 ;i<steady_locations.size();i++){
                steady_locations.elementAt(i).new_season_replenish();
            }
        }
        else if(arg0.getID()== Messages.fill_health){
            check_available_resorce();//available
        }
    }

    private void check_available_resorce()
    {
        for(int i=0 ;i<list_of_units.size();i++){
            int HQ_number = find_HQ_number(list_of_units.elementAt(i).nation);
            switch(list_of_units.elementAt(i).mode){
            case 0:
                if(list_of_HQ.elementAt(HQ_number).set_resorces(-50,2, false)){//maybe it must change
                    list_of_HQ.elementAt(HQ_number).set_resorces(-50,2, true);
                    list_of_units.elementAt(i).fill_health((int)((float)Finals.maximum_health[0]*((float)10/(float)100)));
                }
                else list_of_units.elementAt(i).fill_health(-1*((int)((float)Finals.maximum_health[0]*((float)10/(float)100))));
                break;
            case 1:
                if(list_of_HQ.elementAt(HQ_number).set_resorces(-100,7, false)){//maybe it must change
                    list_of_HQ.elementAt(HQ_number).set_resorces(-100,7, true);
                    list_of_units.elementAt(i).fill_health(((int)((float)Finals.maximum_health[1]*((float)10/(float)100))));
                }
                else list_of_units.elementAt(i).fill_health(-1*(int)((float)Finals.maximum_health[1]*((float)10/(float)100)));
                break;
            case 2:
                if(list_of_HQ.elementAt(HQ_number).set_resorces(-50,5, false)){//maybe it must change
                    list_of_HQ.elementAt(HQ_number).set_resorces(-50,5, true);
                    list_of_units.elementAt(i).fill_health((int)((float)Finals.maximum_health[2]*((float)10/(float)100)));
                }
                else list_of_units.elementAt(i).fill_health(-1*((int)((float)Finals.maximum_health[2]*((float)10/(float)100))));
                break;
            }
        }
    }

    private int find_HQ_number(int nation)
    {
        for(int i=0 ;i<list_of_HQ.size();i++){
            if(nation==list_of_HQ.elementAt(i).get_nation())
                return i;
        }
        return -1;
    }

    private void zoom_simulator()
    {
        Point game_panel_location = new Point(Game_panel.this.getLocation());
        Game_panel.this.setLocation(game_panel_location.getLocation().x
                - (Finals.pixeles.length * Finals.PIC_SIZE.width - Game_panel.this.getSize().width) / 2,
                game_panel_location.getLocation().y
                        - (Finals.pixeles[0].length * Finals.PIC_SIZE.height - Game_panel.this.getSize().height) / 2);
        Game_panel.this.setSize(Finals.pixeles.length * Finals.PIC_SIZE.width, Finals.pixeles[0].length
                * Finals.PIC_SIZE.height);
        game_enviroment.setSize(Game_panel.this.getSize());
        Dimension OLD_PIC_SIZE = game_frame.get_OLD_PIC_SIZE();
        for (int i = 0; i < list_of_HQ.size(); i++)
        {
            list_of_HQ.elementAt(i).setLocation(
                    (list_of_HQ.elementAt(i).getLocation().x / OLD_PIC_SIZE.width) * Finals.PIC_SIZE.width,
                    (list_of_HQ.elementAt(i).getLocation().y / OLD_PIC_SIZE.height) * Finals.PIC_SIZE.height);
            list_of_HQ.elementAt(i).setSize(Finals.iron_mountain_size);
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg0)
    {
        if (arg0.getButton() == MouseEvent.BUTTON1)
        {
            disable_selection();
            check_bulding_selection();// new change workers must be built at HQ
            check_one_click_selection(mouse_position);
        }
        else if (arg0.getButton() == MouseEvent.BUTTON3)
        {
            
            Finals.move_point = new Point(mouse_position);
            for (int i = 0; i < steady_locations.size(); i++)
            {
                if (contains_this_location(new Point(steady_locations.elementAt(i).getX() * Finals.PIC_SIZE.width,
                        steady_locations.elementAt(i).getY() * Finals.PIC_SIZE.height), Finals.PIC_SIZE,steady_locations.elementAt(i).getMode()))
                {// maybe must change
                    for (int j = 0; j < selected_units.size(); j++)
                    {
                        if (selected_units.elementAt(j).mode == 2 && steady_locations.elementAt(i).getMode()!=5)
                        {
                            //selected_units.elementAt(j).destination_changed();//Stops it I hope!!!
                            int unit_number = find_unit_number(j);
//                            try
//                            {// if work schedule exist
//                                selected_units.elementAt(j).work_schedule.set_continue_condition(false);
//                            }
//                            catch (Exception e)
//                            {
//                            }
                            if(!selected_units.elementAt(j).busy){
                                if(game_frame.get_is_single_player())
                                Finals.terminal.commandExecutor("work "+unit_number+" "+i);
                                else Finals.client.sendMassage("work "+unit_number+" "+i);
 //                               Work_Schedule work_Schedule = new Work_Schedule(list_of_HQ.elementAt(selected_units.elementAt(j).nation), steady_locations.elementAt(i), selected_units.elementAt(j));
 //                               Thread_ex.execute(work_Schedule);
                            }
                        }
                        else if(selected_units.elementAt(j).mode == 0 && steady_locations.elementAt(i).getMode()==5)
                        {
                            try
                            {// if work schedule exist
                                selected_units.elementAt(j).work_schedule.set_continue_condition(false);
                            }
                            catch (Exception e)
                            {
                            }
                            if(!selected_units.elementAt(j).busy){
                                int unit_number=find_unit_number(j);
                                if(game_frame.get_is_single_player())
                                    Finals.terminal.commandExecutor("work "+unit_number+" "+i);
                                    else Finals.client.sendMassage("work "+unit_number+" "+i);
//                                Work_Schedule work_Schedule = new Work_Schedule(list_of_HQ.elementAt(selected_units.elementAt(j).nation), steady_locations.elementAt(i), selected_units.elementAt(j));
//                                Thread_ex.execute(work_Schedule);
                            }
                        }
                        
                    }
                    return;
                }
            }
            set_destination();
            Finals.second_click = true;
        }
        // Finals.selected=true;

    }

    boolean new_unit_build_avilable = true;// don't let user to build so quickly

    private void check_bulding_selection()// checks if user have selected an HQ building
    {

        if (new_unit_build_avilable)
        {
            for (int i = 0; i < list_of_HQ.size(); i++)
            {
                if (contains_this_location(list_of_HQ.elementAt(i).getLocation(), list_of_HQ.elementAt(i).getSize(),0))
                {
                    selected_HQ_number = i ;
                    select_toolbar.HQSelected(list_of_HQ.elementAt(i));
                    game_frame.addSelectedToolbar(select_toolbar);//selected toolbar is added to game frame
                           break;
                }
            }
        }
    }

    private CartesianPoint find_empty_location_sea_land(int mode, Point HQ_location)// when building new unit it must
                                                                                    // find a place for that unit
    { 
        int count=0;
        while(true){//changed newly for more efficient wayfinding
        CartesianPoint search_start_location = new CartesianPoint((HQ_location.x / Finals.PIC_SIZE.width) - count,
                (HQ_location.y / Finals.PIC_SIZE.height) - count);

        for (int i = 0; i < 2*count+1; i++)
        {
            for (int j = 0; j < 2*count+1; j++)
            {
                if (mode == 2)
                {
                    try{
                    if (Finals.pixeles[i + search_start_location.getX()][j + search_start_location.getY()] % 10 != 0
                            && Finals.pixeles[i + search_start_location.getX()][j + search_start_location.getY()] % 10 != 4)
                        return new CartesianPoint((i + search_start_location.getX() ) * Finals.PIC_SIZE.width, (j
                                + search_start_location.getY() )
                                * Finals.PIC_SIZE.height);
                    }catch(Exception e){
                        
                    }
                }
                else
                {
                    //System.out.println("I'm here");
                    if (Finals.pixeles[i + search_start_location.getX()][j + search_start_location.getY()] % 10 == 0
                            || Finals.pixeles[i + search_start_location.getX()][j + search_start_location.getY()] % 10 == 4)
                        return new CartesianPoint((i + search_start_location.getX() ) * Finals.PIC_SIZE.width, (j
                                + search_start_location.getY() )
                                * Finals.PIC_SIZE.height);
                }
            }
        }
        count++;
        }

    }

    private boolean contains_this_location(Point location, Dimension size , int mode )
    {
        if(mode!=5){
        if (0 < location.x - mouse_position.x + size.width && location.x - mouse_position.x + size.width < size.width
                && 0 < location.y - mouse_position.y + size.height
                && location.y - mouse_position.y + size.height < size.height)
        {
            return true;
        }
        return false;
        }
        else {
            int maxX = Math.max(location.x, mouse_position.x);
            int minX = Math.min(location.x, mouse_position.x);
            int maxY = Math.max(location.y, mouse_position.y);
            int minY = Math.min(location.y, mouse_position.y);
            if(maxX - minX < Finals.PIC_SIZE.width && maxY - minY < Finals.PIC_SIZE.height)
            {
                return true;
            }
            return false; 
        }
    }
  
    
    private boolean contains_this_location(unit_controller unit)
    {// it is a replace for contains which in not working
        if (0 < unit.getLocation().x - mouse_position.x + unit.getSize().width
                && unit.getLocation().x - mouse_position.x + unit.getSize().width < unit.getSize().width
                && 0 < unit.getLocation().y - mouse_position.y + unit.getSize().height
                && unit.getLocation().y - mouse_position.y + unit.getSize().height < unit.getSize().height)
        {
            return true;
        }
        return false;
    }

    public void disable_selection()
    {
        for (int i = 0; i < list_of_units.size(); i++)
        {
            list_of_units.elementAt(i).click = false;
        }
        
//        for(int i=0 ; i<list_of_HQ.size() ; i++){
//            list_of_HQ.elementAt(i).click = false ;
//        }
        
        selected_units.removeAllElements();
        select_toolbar.disableSelection();
    }

    @Override
    public void mouseEntered(MouseEvent arg0)
    {

    }

    @Override
    public void mouseExited(MouseEvent arg0)
    {

    }

    @Override
    public void mousePressed(MouseEvent arg0)
    {

    }

    @Override
    public void mouseReleased(MouseEvent arg0)
    {
        dragged = false;

        check_selection();

        repaint();
    }

    boolean dragged = false;

    @Override
    public void mouseDragged(MouseEvent arg0)
    {
        if (SwingUtilities.isLeftMouseButton(arg0))
        {
            if (!dragged)
            {
                Finals.first_selected_point.x = arg0.getLocationOnScreen().x - this.getLocation().x
                        - this.frame_location.x;
                Finals.first_selected_point.y = arg0.getLocationOnScreen().y - this.getLocation().y
                        - this.frame_location.y;
            }
            dragged = true;
            disable_selection();
            Finals.final_selected_point.x = arg0.getLocationOnScreen().x - this.getLocation().x - this.frame_location.x;
            Finals.final_selected_point.y = arg0.getLocationOnScreen().y - this.getLocation().y - this.frame_location.y;

            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent arg0)
    {
        // System.out.println("working");
        mouse_position.x = arg0.getLocationOnScreen().x - this.getLocation().x - this.frame_location.x;
        mouse_position.y = arg0.getLocationOnScreen().y - this.getLocation().y - this.frame_location.y;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

//        for (int human_AT = 0; human_AT < list_of_humans.size(); human_AT++)
//        {
//            g.drawImage(list_of_humans.elementAt(human_AT).shape.getImage(), list_of_humans.elementAt(human_AT)
//                    .getLocation().x, list_of_humans.elementAt(human_AT).getLocation().y,
//                    list_of_humans.elementAt(human_AT).getSize().width,
//                    list_of_humans.elementAt(human_AT).getSize().height, null);
//        }
        // this part makes player feels it is a 3d environment
        for (int steady_locations_AT = 0; steady_locations_AT < steady_locations.size(); steady_locations_AT++)
        {
            if(steady_locations.elementAt(steady_locations_AT).isVisible)
            switch (steady_locations.elementAt(steady_locations_AT).getMode())
            {
            case 2:
                g.drawImage(Finals.pre_tree_icon[Finals.pixeles_mode[steady_locations.elementAt(steady_locations_AT)
                        .getX()][steady_locations.elementAt(steady_locations_AT).getY()]].getImage(),
                        (steady_locations.elementAt(steady_locations_AT).getX()) * Finals.PIC_SIZE.width
                                - Finals.PIC_SIZE.width / 2, (steady_locations.elementAt(steady_locations_AT).getY())
                                * Finals.PIC_SIZE.height - Finals.PIC_SIZE.height, Finals.tree_size.width,
                        Finals.tree_size.height, null);
                break;
            case 7:
                g.drawImage(Finals.mine.getImage(), (steady_locations.elementAt(steady_locations_AT).getX())
                        * Finals.PIC_SIZE.width - Finals.PIC_SIZE.width / 2,
                        (steady_locations.elementAt(steady_locations_AT).getY()) * Finals.PIC_SIZE.height
                                - Finals.PIC_SIZE.height, Finals.tree_size.width, Finals.tree_size.height, null);
                break;
            case 6:
                g.drawImage(Finals.iron_mountain.getImage(), (steady_locations.elementAt(steady_locations_AT).getX())
                        * Finals.PIC_SIZE.width - 3 * Finals.PIC_SIZE.width / 4,
                        (steady_locations.elementAt(steady_locations_AT).getY()) * Finals.PIC_SIZE.height - 3
                                * Finals.PIC_SIZE.height / 2, Finals.iron_mountain_size.width,
                        Finals.iron_mountain_size.height, null);
                break;
            }
        }
        for (int i = 0; i < list_of_HQ.size(); i++)
        {// HQ size is mountain_size
            g.drawImage(Finals.Base_HQ_image.getImage(), list_of_HQ.elementAt(i).getLocation().x,
                    list_of_HQ.elementAt(i).getLocation().y, list_of_HQ.elementAt(i).getSize().width, list_of_HQ
                            .elementAt(i).getSize().height, null);
        }

        if (dragged)
        {
            g.setColor(Color.yellow);

            int rec_width = Finals.final_selected_point.x - Finals.first_selected_point.x;
            int rec_height = Finals.final_selected_point.y - Finals.first_selected_point.y;

            int start_x = Finals.first_selected_point.x;
            int start_y = Finals.first_selected_point.y;

            if (rec_height < 0)
            {
                rec_height *= -1;
                start_y = Finals.final_selected_point.y;
            }
            if (rec_width < 0)
            {
                rec_width *= -1;
                start_x = Finals.final_selected_point.x;
            }
            g.drawRect(start_x, start_y, rec_width, rec_height);
        }

    }

    private void check_one_click_selection(Point selected_point)
    {
        Finals.second_click = false;
        for (int i = 0; i < list_of_units.size(); i++)
        {
            if (contains_this_location(list_of_units.elementAt(i)))
            {
                list_of_units.elementAt(i).click = true;
                selected_units.add(list_of_units.elementAt(i));
                switch (list_of_units.elementAt(i).mode)
                {
                case 0:
                    select_toolbar.fisherShipSelected(1);
                    break;
                case 1:
                    select_toolbar.warShipSelected(1);
                    break;
                case 2:
                    select_toolbar.humanSelected(1);
                    break;

                }

                game_frame.addSelectedToolbar(select_toolbar);
            }
        }
    }

    private void check_selection()
    {

        swap_points();
        Finals.second_click = false;
        for (int i = 0; i < list_of_units.size(); i++)
        {

            int x = list_of_units.elementAt(i).getLocation().x + list_of_units.elementAt(i).getSize().width / 2;
            int y = list_of_units.elementAt(i).getLocation().y + list_of_units.elementAt(i).getSize().height;

            if (x < Finals.final_selected_point.x && Finals.first_selected_point.x < x
                    && y < Finals.final_selected_point.y && Finals.first_selected_point.y < y)
            {
                // System.out.println("click");
                list_of_units.elementAt(i).click = true;
                selected_units.add(list_of_units.elementAt(i));
                check_unit_mode_selection();// checks the modes of selected units
            }
        }
        Finals.final_selected_point = new Point(0, 0);
        Finals.first_selected_point = new Point(0, 0);

    }

    private void check_unit_mode_selection()
    {
        //this part won't let ship and humans to be selected together
        boolean humans_remove = false; //indicates human must be deleted or ships
        if(selected_units.size()>=1){
        if(selected_units.elementAt(0).mode!=2){
            humans_remove=true;
        }
        if(humans_remove)
        for(int i=selected_units.size()-1 ;i>=0 ;i--){//it must be inverse to avoid changing the unchecked units number
            if(selected_units.elementAt(i).mode==2){
                selected_units.elementAt(i).click=false;
                selected_units.remove(i);
            }
        }
        }
        // this part gets the necessary information to selected_info to display
        boolean allWar_ship = true, allFisher_ship = true, allHuman = true;
        for (int i = 0; i < selected_units.size(); i++)
        {
            if (selected_units.elementAt(i).mode != 0)
            {
                allFisher_ship = false;
            }
            if (selected_units.elementAt(i).mode != 1)
            {
                allWar_ship = false;
            }
            if (selected_units.elementAt(i).mode != 2)
            {
                allHuman = false;
            }
        }
        if (!selected_units.isEmpty())
        {
            if (allFisher_ship)
            {
                select_toolbar.fisherShipSelected(selected_units.size());
            }
            else if (allWar_ship)
            {
                select_toolbar.warShipSelected(selected_units.size());
            }
            else if (allHuman)
            {
                select_toolbar.humanSelected(selected_units.size());
            }
            else if (!allFisher_ship && !allWar_ship)
            {
                select_toolbar.fisher_And_War_Ship((Vector) selected_units.clone());
            }
        }
        else
        {
            select_toolbar.disableSelection();
        }
        game_frame.addSelectedToolbar(select_toolbar);

    }

    private void swap_points()
    {
        // System.out.println("Swapped");
        if (Finals.final_selected_point.x < Finals.first_selected_point.x)
        {
            int temp = Finals.final_selected_point.x;
            Finals.final_selected_point.x = Finals.first_selected_point.x;
            Finals.first_selected_point.x = temp;
        }
        if (Finals.final_selected_point.y < Finals.first_selected_point.y)
        {
            int temp = Finals.final_selected_point.y;
            Finals.final_selected_point.y = Finals.first_selected_point.y;
            Finals.first_selected_point.y = temp;
        }
    }

    public void set_destination()
    {// destination
        int num = find_the_start_point(selected_units.size());
        Finals.move_point.x -= num * Finals.unit_size[0].width;
        Finals.move_point.y -= num * Finals.unit_size[0].height;
        int check_point = num * 2 + 1;
        for (int i = 0; i < check_point; i++)
        {
            for (int j = 0; j < check_point; j++)
            {
                if (i * check_point + j < selected_units.size())
                {
                    int unit_number = find_unit_number(i * check_point + j);
                    if(game_frame.get_is_single_player()){
                    Finals.terminal.commandExecutor("move "+unit_number+" "+(Finals.move_point.x + 
                            i * check_point * Finals.unit_size[0].width / 2)+" "+
                            (Finals.move_point.y + j * check_point * Finals.unit_size[0].height / 2));
                    }
                    else {
                        Finals.terminal.commandExecutor("move "+unit_number+" "+(Finals.move_point.x + 
                                i * check_point * Finals.unit_size[0].width / 2)+" "+
                                (Finals.move_point.y + j * check_point * Finals.unit_size[0].height / 2));
                    }
//                    selected_units.elementAt(i * check_point + j).set_new_destination(
//                            new CartesianPoint(Finals.move_point.x + i * check_point * Finals.unit_size[0].width / 2,
//                                    Finals.move_point.y + j * check_point * Finals.unit_size[0].height / 2));
                }
            }
        }
    }
    
    private int find_unit_number(int selected_number){
        for(int i=0 ;i<list_of_units.size();i++){
            if(selected_units.elementAt(selected_number)==list_of_units.elementAt(i)){
                return i;
            }
        }
        return -1;
    }
    
    public void set_single_destination(CartesianPoint move_point_bot ,int unit_number)
    {// destination
        list_of_units.elementAt(unit_number).click=true;
//        int num = find_the_start_point(selected_units.size());
//        move_point_bot.setX(  -1*num * Finals.unit_size[0].width+move_point_bot.getX());
//        move_point_bot.setY( -1* num * Finals.unit_size[0].height+move_point_bot.getY());
//        int check_point = num * 2 + 1;
//        for (int i = 0; i < check_point; i++)
//        {
//            for (int j = 0; j < check_point; j++)
//            {
//                if (i * check_point + j < selected_units.size())
//                {
                    list_of_units.elementAt(unit_number).set_new_destination(
                            new CartesianPoint(move_point_bot.getX(),
                                    move_point_bot.getY() ));
//                }
//            }
//        }
        Finals.second_click = true;
    } 

    private int find_the_start_point(int size)
    {
        int counter = 0;
        for (int i = 1;; i += 2)
        {
            if (i * i >= size)
            {
                return counter;
            }
            counter++;
        }
    }

    public void unit_died(unit_controller unit)
    {

        list_of_units.remove(unit);
        selected_units.remove(unit);
        check_unit_mode_selection();
        repaint();
    }

    public void run()
    {
        while (true)
        {
            repaint();
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }
    
    public void makeHuman(){//it is called by new human button in the game_info
        if(list_of_HQ.elementAt(selected_HQ_number).set_resorces(-1000, 2,false)
                && list_of_HQ.elementAt(selected_HQ_number).set_resorces(-2000, 5,false)){//Needs 2000 food, 1000 wood
            list_of_HQ.elementAt(selected_HQ_number).set_resorces(-1000, 2,true);
            list_of_HQ.elementAt(selected_HQ_number).set_resorces(-1000, 7,true);
         Thread bulding_new_unit = new Thread(new Runnable()
        {
            public void run()
            {
                new_unit_build_avilable = false;
                try
                {
                    Thread.sleep(5000);
                }
                catch (Exception e)
                {
                }
                new_unit_build_avilable = true;
            }
        });
        bulding_new_unit.start();
        CartesianPoint human_start_locations = find_empty_location_sea_land(2, list_of_HQ.elementAt(selected_HQ_number)
                .getLocation());
        unit_controller human = new unit_controller(human_start_locations, Game_panel.this, selected_HQ_number, 2);
        Thread_ex.execute(human);
        list_of_units.add(human);
        add(human);
        add(game_enviroment);
        setComponentZOrder(human, 0);
        }
    }
    
    public void make_ship(int mode){// mode differs in making fisher ship or warship
        //battle ship Needs 3000 iron, 500 wood,1000 food
        //fisher ship Needs 500 iron, 500 wood
        boolean building_is_available = false;//checks if there is not enough resorce avialable
        
        switch(mode){
        case 0:
            if(list_of_HQ.elementAt(selected_HQ_number).set_resorces(-500,7,false)&&
                    list_of_HQ.elementAt(selected_HQ_number).set_resorces(-500,2,false)){
            building_is_available=true;
            list_of_HQ.elementAt(selected_HQ_number).set_resorces(-500,7,true);
            list_of_HQ.elementAt(selected_HQ_number).set_resorces(-500,2,true);
            }
                break;
        case 1:
            if(list_of_HQ.elementAt(selected_HQ_number).set_resorces(-3000, 7,false)
                    && list_of_HQ.elementAt(selected_HQ_number).set_resorces(-500, 2,false)
                    &&list_of_HQ.elementAt(selected_HQ_number).set_resorces(-1000, 5,false)){
                building_is_available=true;
                list_of_HQ.elementAt(selected_HQ_number).set_resorces(-3000, 7,true);
                list_of_HQ.elementAt(selected_HQ_number).set_resorces(-500, 2,true);
                list_of_HQ.elementAt(selected_HQ_number).set_resorces(-1000, 5,true);
            }
                break;
        
        }
        if(building_is_available){
        Thread bulding_new_unit = new Thread(new Runnable()
        {
            public void run()
            {
                new_unit_build_avilable = false;
                try
                {
                    Thread.sleep(10000);
                }
                catch (Exception e)
                {
                }
                new_unit_build_avilable = true;
            }
        });
        bulding_new_unit.start();
        CartesianPoint ship_start_locations = find_empty_location_sea_land(1, list_of_HQ.elementAt(selected_HQ_number)
                .getLocation());
        unit_controller ship = new unit_controller(ship_start_locations, Game_panel.this, selected_HQ_number, mode);
        Thread_ex.execute(ship);
        list_of_units.add(ship);
        add(ship);
        add(game_enviroment);
        setComponentZOrder(ship, 0);
        }
    }
    
    public void HQ_Destroyed(HQ_Main_Base hq){
        Vector<unit_controller> v = new Vector<>();//instead of using list_of_units directly i used this
        for(int i=0 ; i<list_of_units.size() ; i++){
             if(list_of_units.elementAt(i).nation == hq.get_nation()){
                 v.add(list_of_units.elementAt(i));
                 repaint();
             }
        }
        
        for(int i=0 ; i<v.size() ; i++){//killing every same HQ units
            this.unit_died(v.elementAt(i));
            v.elementAt(i).setVisible(false);
        }
        
       list_of_HQ.remove(hq);
       
       if(hq.player_name.equals(Finals.player_name.trim())){
           JOptionPane.showMessageDialog(this, "You Have Lost!");
           game_frame.dispose();
       }
       else if(list_of_HQ.size()==1 && list_of_HQ.elementAt(0).player_name.equals(Finals.player_name)){
           JOptionPane.showMessageDialog(this,"You Have Victory!");
           game_frame.dispose();
       }
    }
    
//    public Vector getList(){
//        return list_of_units;
//    }
    
//    public void add_selected_units(unit_controller unit){
//        selected_units.add(unit);
//    }

}
