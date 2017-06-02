import java.awt.Point;
import java.util.Stack;

public class Work_Schedule implements Runnable
{// Debugged recently needs no more attention
    HQ_Main_Base HQ;
    Steady_location work_place;
    unit_controller unit;
    Stack<CartesianPoint> work_way_base_to_work, work_way_work_to_base;
    CartesianPoint second_location;
    private boolean continue_condition = true;

    // I want to set a route for this unit
    // to follow that route until user diselects the order
    public Work_Schedule(HQ_Main_Base HQ, Steady_location resorce, unit_controller unit)
    {
        this.unit = unit;
        this.HQ = HQ;
        this.work_place = resorce;

        second_location = find_empty_location_sea_land(unit.mode, HQ.getLocation());

    }

    @Override
    public void run()
    {
        unit.set_work_schedule(this);
        Back_track_way_finder to_work_from_here = new Back_track_way_finder(new CartesianPoint(unit.getLocation().x,
                unit.getLocation().y), work_place.get_location(), unit.mode);
        Stack<CartesianPoint> to_work_from_here_answer = to_work_from_here.way_setter();
        for (int i = 0; i < to_work_from_here_answer.size() - 1; i++)
        {
            this.unit.change_location(to_work_from_here_answer.elementAt(i), false);
        }

        Back_track_way_finder way_finder_base_to_work = new Back_track_way_finder(second_location,
                work_place.get_location(), unit.mode);
        this.work_way_base_to_work = way_finder_base_to_work.way_setter();
        Back_track_way_finder way_finder_work_to_base = new Back_track_way_finder(work_place.get_location(),
                second_location, unit.mode);
        this.work_way_work_to_base = way_finder_work_to_base.way_setter();

        if (work_place.pick_entity())
        {
            while (continue_condition)
            {
                synchronized (new Object())
                {// to ensure that all orders are done together

                    for (int i = 0; i < work_way_work_to_base.size() - 1 && continue_condition; i++)
                    {

                       // System.out.println("work to base");
                        this.unit.change_location(work_way_work_to_base.elementAt(i), false);
                    }
                }

                if (continue_condition)
                    this.unit.change_location(work_way_work_to_base.elementAt(work_way_work_to_base.size() - 1), true);

                if (!unit.is_dead)
                    HQ.set_resorces(100, work_place.getMode(), true);// it sends the resource to main_base
                synchronized (new Object())
                {
                    for (int i = 0; i < work_way_base_to_work.size() - 1 && continue_condition; i++)
                    {

                       // System.out.println("changng location");
                        this.unit.change_location(work_way_base_to_work.elementAt(i), false);
                    }
                }

                if (continue_condition)
                    this.unit.change_location(work_way_base_to_work.elementAt(work_way_base_to_work.size() - 1), true);

                if (!unit.is_dead)// If unit is dead the other orders are cancelled but this one not(I don't know why)
                                  // so we have to avoid it
                    if (!work_place.pick_entity())
                    {// it takes resources and makes sure

                        continue_condition = false;
                    }
            }
        }

    }

    public void set_continue_condition(boolean continue_condition)
    {
        this.continue_condition = continue_condition;
    }

    private CartesianPoint find_empty_location_sea_land(int mode, Point HQ_location)// some units can't reach HQ but
                                                                                    // they can come near it
    // find a place for that unit
    {
        int count = 0;
        while (true)
        {// changed newly for more efficient wayfinding
            CartesianPoint search_start_location = new CartesianPoint((HQ_location.x / Finals.PIC_SIZE.width) - count,
                    (HQ_location.y / Finals.PIC_SIZE.height) - count);

            for (int i = 0; i < 2 * count + 1; i++)
            {
                for (int j = 0; j < 2 * count + 1; j++)
                {
                    if (mode == 2)
                    {
                        try
                        {
                            if (Finals.pixeles[i + search_start_location.getX()][j + search_start_location.getY()] % 10 != 0
                                    && Finals.pixeles[i + search_start_location.getX()][j
                                            + search_start_location.getY()] % 10 != 4)
                                return new CartesianPoint((i + search_start_location.getX()) * Finals.PIC_SIZE.width,
                                        (j + search_start_location.getY()) * Finals.PIC_SIZE.height);
                        }
                        catch (Exception e)
                        {
                        }
                    }
                    else
                    {
                        try
                        {
                            if (Finals.pixeles[i + search_start_location.getX()][j + search_start_location.getY()] % 10 == 0
                                    || Finals.pixeles[i + search_start_location.getX()][j
                                            + search_start_location.getY()] % 10 == 4)
                            {
                                return new CartesianPoint((i + search_start_location.getX()) * Finals.PIC_SIZE.width,
                                        (j + search_start_location.getY()) * Finals.PIC_SIZE.height);
                            }
                        }
                        catch (Exception e)
                        {
                        }
                    }
                }
            }
            count++;
        }

    }
}
