
import java.util.Stack;
//cherchenco martini
// it gets final destination and unit position and it returns a stack of the way that unit must pass to get to destination
// it got the same algorithm like sand and ship shape 
// 1 and 2 and 4 and 8
// it would send the CartesianPoints that ship must go to there before final location
// It was so hard and so interesting
// This algorithm is ours and it is not like other algorithm you may see in Internet
public class Back_track_way_finder
{// warning filled pixels must be changed
    CartesianPoint current_location;
    CartesianPoint final_destination;
    CartesianPoint final_destination_complete;
    Stack<CartesianPoint> result;
    Integer[][] filled_pixeles;
    int unit_mode;
    

    public Back_track_way_finder(CartesianPoint current_location, CartesianPoint final_destination_complete , int unit_mode)//sea =1 land =2
    {
        result = new Stack<>();
        // list_of_states = new Vector<>();
        this.current_location = new CartesianPoint(current_location.getX() / Finals.PIC_SIZE.width, current_location.getY()
                / Finals.PIC_SIZE.height);
        this.final_destination_complete = final_destination_complete;
        this.final_destination = new CartesianPoint(final_destination_complete.getX() / Finals.PIC_SIZE.width, final_destination_complete.getY()
                / Finals.PIC_SIZE.height);
        
        switch (unit_mode)// it checks waht kind of points this unit need
        {
        case 0:
        case 1:
        this.unit_mode=1;
            break;
        
        case 2:
        this.unit_mode=2;
        }
        movement_posibilities_value_setter();
        
    }

    public Stack<CartesianPoint> way_setter()
    {
        Stack<CartesianPoint> temp = new Stack<>();
        try
        {

            if (filled_pixeles[final_destination.getX()][final_destination.getY()] != 1)
            {
                Thread timer = new Thread(new Runnable()//if there is no way possible this thread helps the unit controller to avoid stopped working
                {
                    
                    @Override
                    public void run()
                    {
                     try
                    {
                        Thread.sleep(50);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    } 
                     end=true;
                    }
                });
                timer.start();
                synchronized (new Object())
                {
                    recursive_way_finder(temp, this.current_location, -1, new CartesianPoint(-100, -100));
                

                // some CartesianPoints are terrible
                // this last part is for a better route if possible
                return final_check_for_better_route();
                }
            }
        }
        catch (Exception e)
        {
        }
        Stack<CartesianPoint> Eror = new Stack<>();
        return Eror;

    }

    private Stack<CartesianPoint> final_check_for_better_route()
    {
        Stack<CartesianPoint> sumerized_result = new Stack<>();
        if(result.size()>=1){
        sumerized_result.push(result.elementAt(0));
        int last_added_element_num = 0;
        while (last_added_element_num != result.size() - 1)
        {
            for (int j = result.size() - 1; j > last_added_element_num; j--)
            {
                if (are_reachable_manually(sumerized_result.elementAt(sumerized_result.size() - 1), result.elementAt(j)))
                {
                    sumerized_result.push(result.elementAt(j));
                    last_added_element_num = j;
                    break;
                    
                }
            }
        }
       // sumerized_result.pop();
        sumerized_result.add(final_destination_complete);//for final destination that user has selected
        }
        return sumerized_result;
    }

    private boolean are_reachable_manually(CartesianPoint start_location, CartesianPoint finish_location)
    {
        boolean passed = false;
        int x = start_location.getX();
        int y = start_location.getY();
        while (!passed)
        {
            if (filled_pixeles[x][y] == 1)
                return false;

            int first_x = x;
            int first_y = y;
            passed = true;

            try
            {
                if (finish_location.getX() > x)
                {

                    x += 1;
                }
            }
            catch (Exception e)
            {

            }
            try
            {
                if (finish_location.getX() < x)
                {
                    x -= 1;
                }
            }
            catch (Exception e)
            {

            }
            try
            {
                if (finish_location.getY() > y)
                {
                    y += 1;
                }
            }
            catch (Exception e)
            {

            }
            try
            {
                if (finish_location.getY() < y)
                {
                    y -= 1;
                }
            }
            catch (Exception e)
            {

            }
            if (first_x != x || first_y != y)
            {
                passed = false;
            }
        }
        return true;
    }

    

    boolean end = false;

    public void recursive_way_finder(Stack<CartesianPoint> way, CartesianPoint current_position, int last_change, CartesianPoint last_location)
    {
        // for avoiding stackOverFlow and endless recursive we have to storage the the old state for ending the
        // recursive
        try
        {
            if (!end)
            {
                if (!(current_position.getX() <= final_destination.getX() + 1 && current_position.getX() >= final_destination.getX() - 1
                        && current_position.getY() <= final_destination.getY() + 1 && current_position.getY() >= final_destination.getY() - 1))
                {
                    if (check_avoiding_square_locations(current_position, last_location))
                    {

                        filled_pixeles[current_position.getX()][current_position.getY()] = 2;
                        int order_setter = 0;
                        if (final_destination.getX() > current_position.getX() + 1)
                            order_setter += 4;
                        else if (final_destination.getX() < current_position.getX() - 1)
                            order_setter += 1;
                        if (final_destination.getY() < current_position.getY() - 1)
                            order_setter += 2;
                        else if (final_destination.getY() > current_position.getY() + 1)
                            order_setter += 8;
                        switch (order_setter)
                        {
                        case 1:
                            change_location(way, 1, current_position, last_change);
                            change_location(way, 3, current_position, last_change);
                            change_location(way, 9, current_position, last_change);
                            change_location(way, 2, current_position, last_change);
                            change_location(way, 8, current_position, last_change);
                            change_location(way, 6, current_position, last_change);
                            change_location(way, 12, current_position, last_change);
                            change_location(way, 4, current_position, last_change);

                            break;
                        case 2:
                            change_location(way, 2, current_position, last_change);
                            change_location(way, 3, current_position, last_change);
                            change_location(way, 6, current_position, last_change);
                            change_location(way, 1, current_position, last_change);
                            change_location(way, 4, current_position, last_change);
                            change_location(way, 9, current_position, last_change);
                            change_location(way, 12, current_position, last_change);
                            change_location(way, 8, current_position, last_change);

                            break;
                        case 3:
                            change_location(way, 3, current_position, last_change);
                            change_location(way, 1, current_position, last_change);
                            change_location(way, 2, current_position, last_change);
                            change_location(way, 9, current_position, last_change);
                            change_location(way, 6, current_position, last_change);
                            change_location(way, 4, current_position, last_change);
                            change_location(way, 8, current_position, last_change);
                            change_location(way, 12, current_position, last_change);

                            break;
                        case 4:
                            change_location(way, 4, current_position, last_change);
                            change_location(way, 6, current_position, last_change);
                            change_location(way, 12, current_position, last_change);
                            change_location(way, 2, current_position, last_change);
                            change_location(way, 8, current_position, last_change);
                            change_location(way, 3, current_position, last_change);
                            change_location(way, 9, current_position, last_change);
                            change_location(way, 1, current_position, last_change);

                            break;
                        case 6:
                            change_location(way, 6, current_position, last_change);
                            change_location(way, 2, current_position, last_change);
                            change_location(way, 4, current_position, last_change);
                            change_location(way, 3, current_position, last_change);
                            change_location(way, 12, current_position, last_change);
                            change_location(way, 1, current_position, last_change);
                            change_location(way, 8, current_position, last_change);
                            change_location(way, 9, current_position, last_change);

                            break;
                        case 8:
                            change_location(way, 8, current_position, last_change);
                            change_location(way, 12, current_position, last_change);
                            change_location(way, 9, current_position, last_change);
                            change_location(way, 1, current_position, last_change);
                            change_location(way, 4, current_position, last_change);
                            change_location(way, 3, current_position, last_change);
                            change_location(way, 6, current_position, last_change);
                            change_location(way, 2, current_position, last_change);

                            break;
                        case 9:
                            change_location(way, 9, current_position, last_change);
                            change_location(way, 1, current_position, last_change);
                            change_location(way, 8, current_position, last_change);
                            change_location(way, 12, current_position, last_change);
                            change_location(way, 3, current_position, last_change);
                            change_location(way, 2, current_position, last_change);
                            change_location(way, 4, current_position, last_change);
                            change_location(way, 6, current_position, last_change);

                            break;
                        case 12:
                            change_location(way, 12, current_position, last_change);
                            change_location(way, 8, current_position, last_change);
                            change_location(way, 4, current_position, last_change);
                            change_location(way, 6, current_position, last_change);
                            change_location(way, 9, current_position, last_change);
                            change_location(way, 2, current_position, last_change);
                            change_location(way, 1, current_position, last_change);
                            change_location(way, 3, current_position, last_change);

                            break;
                        default:
                            break;
                        }

                        filled_pixeles[current_position.getX()][current_position.getY()] = 0;

                        return;
                    }
                    else
                    {
                        filled_pixeles[current_position.getX()][current_position.getY()] = 1;
                    }
                }
                else
                {
                    //System.out.println("adding");
                    result = (Stack<CartesianPoint>) way.clone();// without clone in won't work properly
                    end = true;
                    return;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void change_location(Stack<CartesianPoint> way, int mode, CartesianPoint current_position, int last_change)
    {

        switch (mode)
        {
        case 1:
            if (last_change != 4 && last_change != 6 && last_change != 12)
            {
                try
                {
                    if (filled_pixeles[current_position.getX() - 1][current_position.getY()] == 0)
                    {
                        way.push(new CartesianPoint(current_position.getX() - 1, current_position.getY()));
                        recursive_way_finder(way, new CartesianPoint(current_position.getX() - 1, current_position.getY()), 1,
                                current_position);
                        way.pop();
                    }

                }
                catch (Exception e)
                {
                }
            }
            break;
        case 2:
            if (last_change != 8 && last_change != 9 && last_change != 12)
            {
                try
                {
                    if (filled_pixeles[current_position.getX()][current_position.getY() - 1] == 0)
                    {
                        way.push(new CartesianPoint(current_position.getX(), current_position.getY() - 1));
                        recursive_way_finder(way, new CartesianPoint(current_position.getX(), current_position.getY() - 1), 2,
                                current_position);
                        way.pop();
                    }
                }
                catch (Exception e)
                {
                }
            }

            break;
        case 3:
            if (last_change != 12 && last_change != 4 && last_change != 8)
            {
                try
                {
                    if (filled_pixeles[current_position.getX() - 1][current_position.getY() - 1] == 0)
                    {
                        way.push(new CartesianPoint(current_position.getX() - 1, current_position.getY() - 1));
                        recursive_way_finder(way, new CartesianPoint(current_position.getX() - 1, current_position.getY() - 1), 3,
                                current_position);
                        way.pop();
                    }
                }
                catch (Exception e)
                {
                }
            }
            break;
        case 4:
            if (last_change != 1 && last_change != 3 && last_change != 9)
            {
                try
                {
                    if (filled_pixeles[current_position.getX() + 1][current_position.getY()] == 0)
                    {
                        way.push(new CartesianPoint(current_position.getX() + 1, current_position.getY()));
                        recursive_way_finder(way, new CartesianPoint(current_position.getX() + 1, current_position.getY()), 4,
                                current_position);
                        way.pop();
                    }
                }
                catch (Exception e)
                {
                }
            }
            break;
        case 6:
            if (last_change != 9 && last_change != 1 && last_change != 8)
            {
                try
                {
                    if (filled_pixeles[current_position.getX() + 1][current_position.getY() - 1] == 0)
                    {
                        way.push(new CartesianPoint(current_position.getX() + 1, current_position.getY() - 1));
                        recursive_way_finder(way, new CartesianPoint(current_position.getX() + 1, current_position.getY() - 1), 6,
                                current_position);
                        way.pop();
                    }
                }
                catch (Exception e)
                {
                }
                ;
            }
            break;
        case 8:
            if (last_change != 3 && last_change != 2 && last_change != 6)
            {
                try
                {
                    if (filled_pixeles[current_position.getX()][current_position.getY() + 1] == 0)
                    {
                        way.push(new CartesianPoint(current_position.getX(), current_position.getY() + 1));
                        recursive_way_finder(way, new CartesianPoint(current_position.getX(), current_position.getY() + 1), 8,
                                current_position);
                        way.pop();
                    }
                }
                catch (Exception e)
                {
                }
            }
            break;
        case 9:
            if (last_change != 4 && last_change != 6 && last_change != 2)
            {
                try
                {
                    if (filled_pixeles[current_position.getX() - 1][current_position.getY() + 1] == 0)
                    {
                        way.push(new CartesianPoint(current_position.getX() - 1, current_position.getY() + 1));
                        recursive_way_finder(way, new CartesianPoint(current_position.getX() - 1, current_position.getY() + 1), 9,
                                current_position);
                        way.pop();
                    }
                }
                catch (Exception e)
                {
                }
            }
            break;
        case 12:
            if (last_change != 1 && last_change != 2 && last_change != 3)
            {
                try
                {
                    if (filled_pixeles[current_position.getX() + 1][current_position.getY() + 1] == 0)
                    {
                        way.push(new CartesianPoint(current_position.getX() + 1, current_position.getY() + 1));
                        recursive_way_finder(way, new CartesianPoint(current_position.getX() + 1, current_position.getY() + 1), 12,
                                current_position);
                        way.pop();
                    }
                }
                catch (Exception e)
                {
                }
            }
            break;
        }

    }

    private boolean check_avoiding_square_locations(CartesianPoint current_position, CartesianPoint last_position)
    {
        //System.out.println("blocking");
        int x = current_position.getX() - 1;
        int y = current_position.getY() - 1;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                try
                {
                    if (filled_pixeles[x + i][y + j] == 2 && (x + i != last_position.getX() || y + j != last_position.getY())
                            && (i % 2 != 0 || j % 2 != 0))
                        return false;
                }
                catch (Exception e)
                {
                }
            }
        }
        return true;

    }
    
    private void movement_posibilities_value_setter()
    {
        filled_pixeles = new Integer[Finals.pixeles.length][Finals.pixeles[0].length];
//        switch(unit_mode){
//        case 1:
//            filled_pixeles = Finals.move_posibilities_sea_unit.clone();
//            break;
//        case 2:
//            filled_pixeles = Finals.move_posibilities_land_unit.clone();
//            break;
//        }
        for (int i = 0; i < Finals.pixeles.length; i++)
        {
            for (int j = 0; j < Finals.pixeles[0].length; j++)
            {
                if (Finals.pixeles[i][j] % 10 == 0 || Finals.pixeles[i][j] % 10 == 4 ||Finals.pixeles[i][j]%10==5)
                {
                  switch(unit_mode){
                  case 1:
                      filled_pixeles[i][j] = 0;
                      break;
                  case 2:
                      filled_pixeles[i][j] = 1;
                      break;
                  }
                }
                else
                {
                    switch(unit_mode){
                    case 1:
                        filled_pixeles[i][j] = 1;
                        break;
                    case 2:
                        filled_pixeles[i][j] = 0;
                        break;
                }
                }
                
        }
        
    }

  }
}
