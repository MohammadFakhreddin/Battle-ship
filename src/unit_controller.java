import java.awt.Color;
import java.awt.Graphics;
import java.util.Stack;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class unit_controller extends JComponent implements Runnable, Ship_Fight
{// debbuged recently please do not change
    ImageIcon shape;
    boolean working = false;// if worker is on a working schedule working is true
    public boolean click = false;
    boolean is_dead = false;// It is going to be used for finishing the Thread
    boolean been_hit = false;
    int full_health;
    int currnet_health;
    int mode;
    Game_panel game_panel;
    int final_sink_value = 1;
    int nation;
    int hit_point;
    int range;
    Fire_Hit_Attack_Simulator timer;
    Work_Schedule work_schedule;
    boolean incombat = false;
    Vector<Long> steady_distances;

    public unit_controller(CartesianPoint location, Game_panel game_panel, int nation, int mode)// nation shows this
                                                                                                // unit belongs to which
                                                                                                // player
    {// mode fisher_ship=0 battle_ship=1 human=2
        this.nation = nation;
        this.game_panel = game_panel;
        this.mode = mode;
        this.range = Finals.range[mode];
        steady_distances = new Vector<>();
        full_health = Finals.maximum_health[mode];
        currnet_health = full_health;
        hit_point = Finals.hit_damage[mode];
        setLocation(location.getX(), location.getY());
        setSize(Finals.unit_size[mode].width, Finals.unit_size[mode].height);

        // shape = Finals.shape[mode][1];
        shape = Finals.moving_objects[mode][1];

        // addMouseListener(this);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        if (click)
        {
            g.setColor(Color.black);
            g.drawRect(this.getSize().width - this.getSize().width / 2, 0, Finals.health_bar.width,
                    Finals.health_bar.height);
            g.setColor(Color.green);
            g.fillRect(this.getSize().width - this.getSize().width / 2, 0, Finals.health_bar.width,
                    Finals.health_bar.height);
        }

        // if(mode!=2)
        g.drawImage(shape.getImage(), 0, 0, this.getSize().width, this.getSize().width, null);

//        if (been_hit)
//        {
            g.clearRect(this.getSize().width - this.getSize().width / 2, 0, Finals.health_bar.width,
                    Finals.health_bar.height);
            if (currnet_health >= full_health / 2)
            {
                g.setColor(Color.GREEN);
            }
            else if (currnet_health < full_health / 2 && currnet_health >= full_health / 5)
            {
                g.setColor(Color.YELLOW);
            }
            else if (currnet_health < full_health / 5)
            {
                g.setColor(Color.RED);
            }

            g.fillRect(this.getSize().width - this.getSize().width / 2, 0,
                    (int) (((float) currnet_health / (float) full_health) * Finals.health_bar.width),
                    Finals.health_bar.height);
            // need to be checked for debbuge
      //  }
        // g.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (timer != null)
        {
            if (this.timer.get_inCombat())
            {// it's just a flag for animation
                g.drawImage(Finals.explosion.getImage(), this.getSize().width / 2 - Finals.unit_size[mode].width / 6,
                        this.getSize().height / 2 - Finals.unit_size[mode].height / 6,
                        Finals.unit_size[mode].width / 3, Finals.unit_size[mode].height / 3, null);
            }
        }
    }

    private CartesianPoint destination = new CartesianPoint(0, 0);
    private boolean destination_changed = false;// for new order of user to break last order
    private int value = 0;// It shows which shape is used

    public void set_new_destination(CartesianPoint new_destination)
    {
        this.destination = new_destination;
        destination_changed = true;
        try
        {// maybe there is no work_schedule
            work_schedule.set_continue_condition(false);
            working = false;
            // it is a trick to stop change location for new orders
            // destination_changed= true;
        }
        catch (Exception e)
        {
        }
    }

    // public void destination_changed(){
    //
    // }
    //
    public void set_work_schedule(Work_Schedule work_schedule)
    {
        passed = true;
        working = true;
        this.work_schedule = work_schedule;
    }

    public boolean busy = false;

    public void run()
    {
        while (!is_dead)// while not dead
        {
            // incombat = false;
            if ((click && Finals.second_click))
            {
                // System.out.println(""+this.getLocation().x+"=="+this.getLocation().y);
                // while (((Math.abs(this.getLocation().x - destination.getX()) > Finals.PIC_SIZE.width
                // || Math.abs(this.getLocation().y - destination.getY()) > Finals.PIC_SIZE.height )))//please do not
                // change
                // {
                busy = true;
                // System.out.println("i'm working");

                CartesianPoint this_location = new CartesianPoint(this.getLocation().x, this.getLocation().y);
                Back_track_way_finder back_track_way_finder = new Back_track_way_finder(this_location, destination,
                        mode);
                destination_changed = false;
                if (destination_changed || is_dead || working)
                {
                    break;
                }
                try
                {
                    Stack<CartesianPoint> list_of_movements = back_track_way_finder.way_setter();
                    for (int i = 0; i < list_of_movements.size() - 1; i++)
                    {

                        // System.out.println(i + "==" + list_of_movements.size());
                        if (destination_changed
                                || is_dead
                                || (Math.abs(this.getLocation().x - destination.getX()) < Finals.PIC_SIZE.width && Math
                                        .abs(this.getLocation().y - destination.getY()) < Finals.PIC_SIZE.height)
                                || working)
                        {
                            // System.out.println("changed");
                            break;
                        }
                        change_location(list_of_movements.elementAt(i), false);

                    }
                    change_location(list_of_movements.lastElement(), true);
                }
                catch (Exception e)
                {
                }
                // incombat = false;
            }
            busy = false;
            // }
            try
            {
                Thread.sleep(50);// needed for death animation
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            if (timer != null)
            {// for one to one combat
                if (!timer.get_inCombat())
                    scope(this.getLocation().x, this.getLocation().y);// looking for enemies
            }
            else
                scope(this.getLocation().x, this.getLocation().y);

        }// this part is after unit death
        shape = Finals.moving_objects_death[mode][final_sink_value];
        if (mode != 2)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            work_schedule.set_continue_condition(false);// new change
        }
        catch (Exception e)
        {
        }

        game_panel.unit_died(this);
        setVisible(false);
    }

    private int find_value_shape(int first_x, int first_y, int x, int y)
    {
        int result = 0;
        if (first_x > x)
        {
            result += 1;
        }
        if (first_x < x)
        {
            result += 4;
        }
        if (first_y > y)
        {
            result += 2;
        }
        if (first_y < y)
        {
            result += 8;
        }
        return result;
    }

    boolean passed = false;// it is for change location to finish the method
    private HQ_Hit_Attack_Simulator HQ_timer;

    public void change_location(CartesianPoint in_pixel_mode_position, boolean is_last_one)// last one is for more
                                                                                           // accurate selecting
    {
        passed = false;
        CartesianPoint final_position;
        if (!is_last_one)
        {
            final_position = new CartesianPoint(in_pixel_mode_position.getX() * Finals.PIC_SIZE.width,
                    in_pixel_mode_position.getY() * Finals.PIC_SIZE.height);
        }
        else
        {
            final_position = new CartesianPoint(in_pixel_mode_position.getX() - this.getWidth() / 2,
                    in_pixel_mode_position.getY() - this.getHeight() / 2);
        }
        boolean first_round = true;
        while ((!passed) && (!destination_changed) && (!is_dead))
        {
            if (!first_round)
            {
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            if (first_round)
                first_round = false;
            int x = getLocation().x;
            int y = getLocation().y;
            int first_x = x;
            int first_y = y;
            passed = true;

            try
            {
                if (final_position.getX() > x)
                {

                    x += 2;
                }
            }
            catch (Exception e)
            {

            }
            try
            {
                if (final_position.getX() < x)
                {
                    x -= 2;
                }
            }
            catch (Exception e)
            {

            }
            try
            {
                if (final_position.getY() > y)
                {
                    y += 2;
                }
            }
            catch (Exception e)
            {

            }
            try
            {
                if (final_position.getY() < y)
                {
                    y -= 2;
                }
            }
            catch (Exception e)
            {

            }
            if (first_x != x || first_y != y)
            {
                passed = false;

            }
            value = find_value_shape(first_x, first_y, x, y);

            this.setLocation(x, y);
            if (value != 0)
            {
                final_sink_value = value;
                shape = Finals.moving_objects[mode][value];
                repaint();
            }
        }
    }

    // public void died (){// for finishing this Thread
    // is_dead=true;
    // setVisible(false);
    // }

    @Override
    public void scope(int x, int y)
    {
        for (int i = 0; i < game_panel.list_of_units.size(); i++)
        {
            if (!game_panel.list_of_units.elementAt(i).equals(this) && !is_dead
                    && this.nation != game_panel.list_of_units.elementAt(i).nation)
            {
                int maxY = Math.max(y, game_panel.list_of_units.elementAt(i).getLocation().y);
                int minY = Math.min(y, game_panel.list_of_units.elementAt(i).getLocation().y);
                int maxX = Math.max(x, game_panel.list_of_units.elementAt(i).getLocation().x);
                int minX = Math.min(x, game_panel.list_of_units.elementAt(i).getLocation().x);
                if (maxY - minY < this.range && maxX - minX < this.range)
                {
                    timer = new Fire_Hit_Attack_Simulator(this, game_panel.list_of_units.elementAt(i));// need to be
                                                                                                       // debugged slow
                                                                                                       // performance
                    (new Thread(timer)).start();
                    // game_panel.list_of_units.elementAt(i).hitted(this.hit_point);
                    // try{
                    // this.hitted(game_panel.list_of_units.elementAt(i).hit_point);
                    // }catch(Exception e){
                    //
                    // }
                    break;
                }
            }

        }
        for (int i = 0; i < game_panel.list_of_HQ.size(); i++)
        {// for attacking HQ
            if (!this.is_dead && this.nation != game_panel.list_of_HQ.elementAt(i).get_nation())
            {
                int maxY = Math.max(y, game_panel.list_of_HQ.elementAt(i).location.getY());
                int minY = Math.min(y, game_panel.list_of_HQ.elementAt(i).location.getY());
                int maxX = Math.max(x, game_panel.list_of_HQ.elementAt(i).location.getX());
                int minX = Math.min(x, game_panel.list_of_HQ.elementAt(i).location.getX());
                if (maxY - minY < this.range + 100 && maxX - minX < this.range + 100)
                {
                    HQ_timer = new HQ_Hit_Attack_Simulator(this, game_panel.list_of_HQ.elementAt(i));
                    (new Thread(HQ_timer)).start();
                }
            }
        }
    }

    @Override
    public void fire(unit_controller target)
    {
        // call hitted method of enemy
        target.hitted(unit_controller.this.hit_point);

    }

    @Override
    public synchronized void hitted(int hit_point)
    {// sometimes more than one unit attacks so it need to be sync
        been_hit = true;
        incombat = true;
        currnet_health -= hit_point;

        if (currnet_health <= 0)
        {//sometimes unit takes more damage
            is_dead = true;

        }
        repaint();
    }

    // public boolean get_destination_changed()
    // {
    // return this.destination_changed;
    // }
    public void fill_health(int must_be_added_health)// each season if there is enough resources available unit would
                                                     // gains health or loose health
    {
        System.out.println("health changed");
        System.out.println(must_be_added_health);
        currnet_health = (currnet_health + must_be_added_health) ;
        if(currnet_health>Finals.maximum_health[unit_controller.this.mode])
            currnet_health=Finals.maximum_health[unit_controller.this.mode];
        if(currnet_health<=0)
            is_dead=true;
        System.out.println(currnet_health+"ch");
    }

}
