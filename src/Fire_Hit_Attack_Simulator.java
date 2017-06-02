import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Fire_Hit_Attack_Simulator implements Runnable{
    unit_controller attacker , target ;
    boolean inCombat=true;
    public Fire_Hit_Attack_Simulator(unit_controller attacker , unit_controller target) {
        this.target = target;
        this.attacker = attacker;
    }
    @Override
    public void run() {
        inCombat=true;
        while((!attacker.is_dead)&& (check_inRange())&&(!target.is_dead)){
        attacker.fire(target);
        inCombat=true;
        try {
            target.fire(attacker);
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Fire_Hit_Attack_Simulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        inCombat=false;
    }
    
    public boolean get_inCombat(){
        return inCombat;
    }
    
    public boolean check_inRange(){
        int y=attacker.getLocation().y;
        int x=attacker.getLocation().x;
        int maxY = Math.max(attacker.getLocation().y,target.getLocation().y);
        int minY = Math.min(attacker.getLocation().y,target.getLocation().y);
        int maxX = Math.max(attacker.getLocation().x,target.getLocation().x);
        int minX = Math.min(attacker.getLocation().x, target.getLocation().x);
        if(maxY - minY < 200 && maxX - minX <200){
            return true;
        }
        return false;
    }

}
