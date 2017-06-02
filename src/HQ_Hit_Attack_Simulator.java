
import java.util.logging.Level;
import java.util.logging.Logger;

public class HQ_Hit_Attack_Simulator implements Runnable {

    unit_controller attacker;
    HQ_Main_Base target;
    public boolean inCombat;

    public HQ_Hit_Attack_Simulator(unit_controller attacker, HQ_Main_Base target) {
        this.attacker = attacker;
        this.target = target;
    }

    @Override
    public void run() {
        inCombat = true;
        while (!attacker.is_dead && !target.isdead && check_inRange()) {
            target.hitted(attacker.hit_point);
            inCombat = true;
            try {
                target.fire(attacker);
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Fire_Hit_Attack_Simulator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        inCombat = false;
    }

    private boolean check_inRange() {
        int maxY = Math.max(attacker.getLocation().y, target.location.getY());
        int minY = Math.min(attacker.getLocation().y, target.location.getY());
        int maxX = Math.max(attacker.getLocation().x, target.location.getX());
        int minX = Math.min(attacker.getLocation().x, target.location.getX());
        if (maxY - minY < 200 && maxX - minX < 200) {
            return true;
        }
        return false;
    }

}
