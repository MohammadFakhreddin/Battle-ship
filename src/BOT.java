import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BOT
{

    Timer timer;
    File bot_play;
    Game_panel game_panel;
    Vector<String> commands;
    int counter;
    ExecutorService Thread_ex;

    public BOT(Game_panel game_panel)
    {
        commands = new Vector<>();
        this.game_panel = game_panel;
        Thread_ex = Executors.newCachedThreadPool();
        BufferedReader br = null;
        try
        {
            timer = new Timer("timer");
            bot_play = new File("bot.txt");
            br = new BufferedReader(new FileReader(bot_play));
            String s;
            while ((s = br.readLine()) != null)
            {
                commands.add(s);
            }

            timer.scheduleAtFixedRate(new TimerTask()
            {
                @Override
                public void run()
                {
//                    if (counter < commands.size())
//                    {
                        BOT_Executor(commands.elementAt(counter));
                        counter=(counter+1)%commands.size();
//                    }
//                    else
//                    {
//                        timer.cancel();
//                    }
                }
            }, 1000, 5000);
        }
        catch (FileNotFoundException ex)
        {
            // Logger.getLogger(BOT.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            // Logger.getLogger(BOT.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try
            {
                br.close();
            }
            catch (IOException ex)
            {
                System.out.println("File not found");
                // Logger.getLogger(BOT.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void BOT_Executor(String command) {//it gives order to all bots
        System.err.println(command);
        switch (command) {
            case "make human":
                for (int i = 1; i < game_panel.list_of_HQ.size(); i++) {//i=0 is real player
                    game_panel.selected_HQ_number = i;
                    game_panel.makeHuman();
                }
                break;
            case "make war_ship":
                for (int i = 1; i < game_panel.list_of_HQ.size(); i++) {//i=0 is real player
                    game_panel.selected_HQ_number = i;
                    game_panel.make_ship(1);
                }
                break;
            case "make fisher_ship":
                for (int i = 1; i < game_panel.list_of_HQ.size(); i++) {//i=0 is real player
                    game_panel.selected_HQ_number = i;
                    game_panel.make_ship(0);
                }
                break;
            case "move":
                Random rand = new Random();
                int random_attack = rand.nextInt(game_panel.list_of_HQ.size());//attack a base by random
                CartesianPoint   attack_location = find_empty_location_sea_land(1,game_panel.list_of_HQ.elementAt(random_attack).getLocation());
                Vector<Integer>war_ships_numbers=new Vector<>();;
                for (int i = 1; i < game_panel.list_of_HQ.size(); i++) {
                    war_ships_numbers.removeAllElements();
                    for (int j = 0; j < game_panel.list_of_units.size(); j++) {
                        if (game_panel.list_of_units.elementAt(j).nation == game_panel.list_of_HQ.elementAt(i).get_nation() 
                                && game_panel.list_of_units.elementAt(j).mode==1){//moving all BOT warship
                            war_ships_numbers.add(j);
                            }
                        }
                    if(war_ships_numbers.size()>=10){// it would attack if there is enough warship available
                        for(int j=0 ;j<war_ships_numbers.size();j++)
                        game_panel.set_single_destination(attack_location, j);
                    }
                   
                    }

                break;

            case "work":
                for (int i = 1; i < game_panel.list_of_HQ.size(); i++) {
                    for (int j = 0; j < game_panel.list_of_units.size(); j++) {
                        if (game_panel.list_of_units.elementAt(j).nation == game_panel.list_of_HQ.elementAt(i).get_nation()) {
                            game_panel.list_of_units.elementAt(j).steady_distances.removeAllElements();
                            find_Steady_Distances(j);
                        }
                    }
                }

                find_Closet_Work();
        }
    }

    private void find_Steady_Distances(int index)
    {// unit index in list of unit
        for (int i = 0; i < game_panel.steady_locations.size(); i++)
        {
            if (game_panel.steady_locations.elementAt(i).isVisible
                    && check_Mode(game_panel.steady_locations.elementAt(i).getMode(),
                            game_panel.list_of_units.elementAt(index).mode))
            {
                CartesianPoint cp = new CartesianPoint(game_panel.steady_locations.elementAt(i).get_location().getX(),
                        game_panel.steady_locations.elementAt(i).get_location().getY());
                game_panel.list_of_units.elementAt(index).steady_distances.add(cp.distance(new CartesianPoint(
                        game_panel.list_of_units.elementAt(index).getLocation().x, game_panel.list_of_units.elementAt(
                                index).getLocation().y)));
            }
            else
                game_panel.list_of_units.elementAt(index).steady_distances.add(new Long(-1));
        }
    }

    private void find_Closet_Work()
    {
        for (int i = 1; i < game_panel.list_of_HQ.size(); i++)
        {
            for (int j = 0; j < game_panel.list_of_units.size(); j++)
            {
                if (game_panel.list_of_units.elementAt(j).nation == game_panel.list_of_HQ.elementAt(i).get_nation())
                {
                    find_Min(j, game_panel.list_of_units.elementAt(j).steady_distances);
                }
            }
        }
    }

    private void find_Min(int unit_index, Vector<Long> steady_dis)
    {
        long min_dis;
        int min_index;

        if (!steady_dis.isEmpty() && !game_panel.list_of_units.elementAt(unit_index).working)
        {
            min_dis = steady_dis.elementAt(0);
            min_index = 0;
            for (int i = 1; i < steady_dis.size(); i++)
            {
                if (min_dis > steady_dis.elementAt(i) && !steady_dis.elementAt(i).equals(new Long(-1)))
                {
                    min_dis = steady_dis.elementAt(i);
                    min_index = i;
                }

            }

            System.err.println(game_panel.steady_locations.elementAt(min_index).get_location().getX() + "----"
                    + game_panel.steady_locations.elementAt(min_index).get_location().getY());

            try
            {// if work schedule exist

                game_panel.list_of_units.elementAt(unit_index).work_schedule.set_continue_condition(false);
            }
            catch (Exception e)
            {
            }
            Work_Schedule work_Schedule = new Work_Schedule(
                    game_panel.list_of_HQ.elementAt(find_nation(game_panel.list_of_units.elementAt(unit_index).nation)),
                    game_panel.steady_locations.elementAt(min_index), game_panel.list_of_units.elementAt(unit_index));
            Thread_ex.execute(work_Schedule);

            // *******************
            // game_panel.steady_locations.remove(min_index);
        }
    }

    private boolean check_Mode(int steady_mode, int unit_mode)
    {
        if (unit_mode == 2)
        {
            if (steady_mode == 2 || steady_mode == 6 || steady_mode == 7)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else if (unit_mode == 0)
        {
            if (steady_mode == 5)
            {
                System.err.println("yeeeeeeeeees");
                return true;
            }
            else
            {
                return false;
            }
        }
        return false;
    }

    private CartesianPoint find_empty_location_sea_land(int mode, Point HQ_location)// when building new unit it must
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
                        // System.out.println("I'm here");
                        if (Finals.pixeles[i + search_start_location.getX()][j + search_start_location.getY()] % 10 == 0
                                || Finals.pixeles[i + search_start_location.getX()][j + search_start_location.getY()] % 10 == 4)
                            return new CartesianPoint((i + search_start_location.getX()) * Finals.PIC_SIZE.width,
                                    (j + search_start_location.getY()) * Finals.PIC_SIZE.height);
                    }
                }
            }
            count++;
        }

    }

    // private void find_Work_For_Human(int index) {//index of unit in list of units
    // long min_distance;
    // int min_distance_index;
    // CartesianPoint cp = new CartesianPoint(game_panel.list_of_units.elementAt(index).getLocation().x,
    // game_panel.list_of_units.elementAt(index).getLocation().y);
    // all_differs_human = new Vector<>();
    // for (int i = 0; i < game_panel.steady_locations.size(); i++) {
    // if (game_panel.steady_locations.elementAt(i).getMode() == 6 || game_panel.steady_locations.elementAt(i).getMode()
    // == 2 || game_panel.steady_locations.elementAt(i).getMode() == 7) {
    // all_differs_human.add(cp.distance(new
    // CartesianPoint(game_panel.steady_locations.elementAt(i).get_location().getX(),
    // game_panel.steady_locations.elementAt(i).get_location().getY())));
    // }
    //
    // if (game_panel.steady_locations.elementAt(i).getMode() == 5)
    // all_differs_human.add(cp.distance(new
    // CartesianPoint(game_panel.steady_locations.elementAt(i).get_location().getX(),
    // game_panel.steady_locations.elementAt(i).get_location().getY())));
    //
    //
    // }
    // if (!all_differs_human.isEmpty()) {
    // min_distance = all_differs_human.elementAt(0);
    // min_distance_index = 0;
    // for (int i = 1; i < all_differs_human.size(); i++) {//finding closet work
    // if (all_differs_human.elementAt(i) < min_distance) {
    // min_distance = all_differs_human.elementAt(i);
    // min_distance_index = i;
    // }
    // }
    // System.out.println(game_panel.steady_locations.elementAt(min_distance_index).get_location().getX()+"-----"+game_panel.steady_locations.elementAt(min_distance_index).get_location().getY());
    // try {// if work schedule exist
    // game_panel.list_of_units.elementAt(index).work_schedule.set_continue_condition(false);
    // } catch (Exception e) {
    // }
    // Work_Schedule work_Schedule = new
    // Work_Schedule(game_panel.list_of_HQ.elementAt(find_nation(game_panel.list_of_units.elementAt(index).nation)),
    // game_panel.steady_locations.elementAt(min_distance_index),
    // game_panel.list_of_units.elementAt(index));
    // Thread_ex.execute(work_Schedule);
    // all_differs_human.removeAllElements();
    // }
    // }
    //
    // private void find_Work_For_Fisher_ship(int index){
    //
    // CartesianPoint cp = new CartesianPoint(game_panel.list_of_units.elementAt(index).getLocation().x,
    // game_panel.list_of_units.elementAt(index).getLocation().y);
    // all_differs_fisher_ship = new Vector<>();
    // for (int i = 0; i < game_panel.steady_locations.size(); i++) {
    //
    // if (game_panel.steady_locations.elementAt(i).getMode() == 5)
    // all_differs_fisher_ship.add(cp.distance(new
    // CartesianPoint(game_panel.steady_locations.elementAt(i).get_location().getX(),
    // game_panel.steady_locations.elementAt(i).get_location().getY())));
    // }
    //
    // }
    //
    //
    // private void find_Closet_Work(boolean is_human){
    // long min_distance;
    // int min_distance_index;
    // if(is_human){
    // if (!all_differs_human.isEmpty()) {
    // min_distance = all_differs_human.elementAt(0);
    // min_distance_index = 0;
    // for (int i = 1; i < all_differs_human.size(); i++) {//finding closet work
    // if (all_differs_human.elementAt(i) < min_distance) {
    // min_distance = all_differs_human.elementAt(i);
    // min_distance_index = i;
    // }
    // }
    // for(int i=0 ; i<game_panel.list_of_units.size() ; i++){
    // try {// if work schedule exist
    // game_panel.list_of_units.elementAt(i).work_schedule.set_continue_condition(false);
    // } catch (Exception e) {
    // }
    // Work_Schedule work_Schedule = new
    // Work_Schedule(game_panel.list_of_HQ.elementAt(find_nation(game_panel.list_of_units.elementAt(i).nation)),
    // game_panel.steady_locations.elementAt(min_distance_index),
    // game_panel.list_of_units.elementAt(i));
    // Thread_ex.execute(work_Schedule);
    // }
    // all_differs_human.removeAllElements();
    // }
    // }else{
    // if (!all_differs_fisher_ship.isEmpty()) {
    // min_distance = all_differs_fisher_ship.elementAt(0);
    // min_distance_index = 0;
    // for (int i = 1; i < all_differs_fisher_ship.size(); i++) {//finding closet work
    // if (all_differs_fisher_ship.elementAt(i) < min_distance) {
    // min_distance = all_differs_fisher_ship.elementAt(i);
    // min_distance_index = i;
    // }
    // }
    // for(int i=0 ; i<game_panel.list_of_units.size() ; i++){
    // try {// if work schedule exist
    // game_panel.list_of_units.elementAt(i).work_schedule.set_continue_condition(false);
    // } catch (Exception e) {
    // }
    // Work_Schedule work_Schedule = new
    // Work_Schedule(game_panel.list_of_HQ.elementAt(find_nation(game_panel.list_of_units.elementAt(i).nation)),
    // game_panel.steady_locations.elementAt(min_distance_index),
    // game_panel.list_of_units.elementAt(i));
    // Thread_ex.execute(work_Schedule);
    // }
    // all_differs_fisher_ship.removeAllElements();
    // }
    // }
    // }
    //
    private int find_nation(int nation)
    {// when a hq falls their number would change
        for (int i = 0; i < game_panel.list_of_HQ.size(); i++)
        {
            if (game_panel.list_of_HQ.elementAt(i).get_nation() == nation)
            {
                return i;
            }
        }
        return -1;
    }
}

// /*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
// package battleship;
//
// import java.io.BufferedReader;
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileReader;
// import java.io.IOException;
// import java.util.Timer;
// import java.util.TimerTask;
// import java.util.Vector;
// import java.util.logging.Level;
// import java.util.logging.Logger;
//
// /**
// *
// * @author arman pasha
// */
// public class BOT {
//
// Timer timer;
// File bot_play;
// Game_panel game_panel;
// Vector<String> commands;
// int counter;
//
// public BOT(Game_panel game_panel) {
// commands = new Vector<>();
// this.game_panel = game_panel;
// BufferedReader br = null;
// try {
// timer = new Timer("timer");
// bot_play = new File("bot.txt");
// br = new BufferedReader(new FileReader(bot_play));
// String s;
// while ((s = br.readLine()) != null) {
// commands.add(s);
// }
//
// timer.scheduleAtFixedRate(new TimerTask() {
// @Override
// public void run() {
// if (counter < commands.size()) {
// commandExecutor(commands.elementAt(counter));
// counter++;
// } else {
// timer.cancel();
// }
// }
// }, 1000, 5000);
// } catch (FileNotFoundException ex) {
// Logger.getLogger(BOT.class.getName()).log(Level.SEVERE, null, ex);
// } catch (IOException ex) {
// Logger.getLogger(BOT.class.getName()).log(Level.SEVERE, null, ex);
// } finally {
// try {
// br.close();
// } catch (IOException ex) {
// Logger.getLogger(BOT.class.getName()).log(Level.SEVERE, null, ex);
// }
// }
//
// }
//
// private void commandExecutor(String command) {
// String[] splited_command = command.split(" ");
// switch (splited_command[0]) {
// case "make":
// game_panel.selected_HQ_number = Integer.parseInt(splited_command[2]);
// if (splited_command[1].equals("human")) {
// game_panel.makeHuman();
// } else if (splited_command[1].equals("war_ship")) {
// game_panel.make_War_ship();
// } else if (splited_command[1].equals("fisher_ship")) {
// game_panel.make_fisher_ship();
// }
// break;
// case "move":
// game_panel.disable_selection();
// game_panel.list_of_units.elementAt(Integer.parseInt(splited_command[1])).click=true;
// game_panel.add_selected_units(game_panel.list_of_units.elementAt(Integer.parseInt(splited_command[1])));
// CartesianPoint move_point = new
// CartesianPoint(Integer.parseInt(splited_command[2]),Integer.parseInt(splited_command[3]));
// game_panel.set_destination(move_point);
//
// }
//
// }
//
// }
