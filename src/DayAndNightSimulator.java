
import java.awt.event.ComponentEvent;
import java.util.Timer;
import java.util.TimerTask;

public class DayAndNightSimulator  {
    private int daysLeft=0;
    private int monthsLeft=0;
    private Timer timer;
   public DayAndNightSimulator(boolean isGame)
   {
       long time =(isGame?Finals.DAY_LENGTH_IN_MILLISECOND_inGame:Finals.DAY_LENGTH_IN_MILLISECOND_preview);
       timer=new Timer("DayAndNightSimulator",false);
       timer.scheduleAtFixedRate(new TimerTask() {
           @Override
           public void run() {
               Finals.changeDayOrNight();// moved to preview
               if(Finals.isDay)
                   daysLeft++;
               if(daysLeft>30) {
                   monthsLeft++;
                   daysLeft=0;
               }
               if(monthsLeft>3)
               {
                   monthsLeft=0;
                   Finals.changeSeason();
                   
               }
               }
       },0,time);

   }
   public DayAndNightSimulator(boolean isGame , final Game_frame game_frame)
   {
       long time =(isGame?Finals.DAY_LENGTH_IN_MILLISECOND_inGame:Finals.DAY_LENGTH_IN_MILLISECOND_preview);
       timer=new Timer("DayAndNightSimulator",false);
       timer.scheduleAtFixedRate(new TimerTask() {
           @Override
           public void run() {
               Finals.changeDayOrNight();// moved to preview
               if(Finals.isDay)
                   daysLeft++;
               if(daysLeft>30) {
                   game_frame.fill_health();
                   monthsLeft++;
                   daysLeft=0;
               }
               if(monthsLeft>3)
               {
                   monthsLeft=0;
                   Finals.changeSeason();
                   game_frame.reset_resorces();
               }
               }
       },0,time);

   }
    public void cancel()
    {
        timer.cancel();
    }
}
