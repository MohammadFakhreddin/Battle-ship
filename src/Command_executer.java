import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Command_executer
{
    Game_panel game_panel;
    ExecutorService Thread_ex;
    public Command_executer(Game_panel game_panel)
    {
        this.game_panel=game_panel;
        Thread_ex = Executors.newCachedThreadPool();
    }
    public Command_executer()
    {
        Thread_ex = Executors.newCachedThreadPool();
    }
    public void set_game_panel(Game_panel game_panel){
        this.game_panel=game_panel;
    }
    public void commandExecutor(String command) {
        System.err.println(command);
        String[] splited_command = command.split(" ");
        switch (splited_command[0]) {
            case "make":
                game_panel.selected_HQ_number = Integer.parseInt(splited_command[2]);
                if (splited_command[1].equals("human")) {
                    game_panel.makeHuman();
                } else if (splited_command[1].equals("war_ship")) {
                    game_panel.make_ship(1);
                } else if (splited_command[1].equals("fisher_ship")) {
                    game_panel.make_ship(0);
                }
                break;
            case "move":
                //game_panel.list_of_units.elementAt(Integer.parseInt(splited_command[1])).click=true;
                CartesianPoint move_point = new CartesianPoint(Integer.parseInt(splited_command[2]),Integer.parseInt(splited_command[3]));
                game_panel.set_single_destination(move_point,Integer.parseInt(splited_command[1]));
                break;
            case "work":
              try
              {// if work schedule exist
                  game_panel.list_of_units.elementAt(Integer.parseInt(splited_command[1].trim())).work_schedule.set_continue_condition(false);
              }
              catch (Exception e)
              {
              }
                  
                   Work_Schedule work_Schedule = new Work_Schedule(game_panel.list_of_HQ.elementAt(find_nation(game_panel.list_of_units.elementAt(Integer.parseInt(splited_command[1])).nation))
                           , game_panel.steady_locations.elementAt(Integer.parseInt(splited_command[2])),
                           game_panel.list_of_units.elementAt(Integer.parseInt(splited_command[1]) ));
                   Thread_ex.execute(work_Schedule);
            }
        
    }
    
    private int find_nation(int nation){// when a hq falls their number would change
        for(int i=0 ;i<game_panel.list_of_HQ.size();i++){
            if(game_panel.list_of_HQ.elementAt(i).get_nation()==nation)
                return i;
        }
        return -1;
    }
}
