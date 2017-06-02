import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;


public class HQ_Main_Base extends JComponent implements Ship_Fight//after change in the season this resorce must be filled again please consider
{
    private int wood=10000;//wood=2
    private int iron=10000;//iron=7
    private int food=10000;//food=5
   // private int gold=2000;//gold=6
    private int nation=-1;
  //  private int[] resources ;// wood =2 , iron =6 , food =5
    CartesianPoint location;// location of HQ
    HQ_Hit_Attack_Simulator timer;
    boolean click = false;
    boolean been_hit = false ;
    boolean isdead = false;
    int current_health,full_health = Finals.maximum_health[3];
    Game_panel game_panel;
    String player_name;
    
    public HQ_Main_Base (CartesianPoint CP , int nation,Game_panel game_panel) {
        //resorces = new int [10];
        this.nation = nation;
        current_health = full_health ; 
        this.game_panel = game_panel;
        
        setLocation(CP.getX()*Finals.PIC_SIZE.width-Finals.iron_mountain_size.width/4,CP.getY()*Finals.PIC_SIZE.height-Finals.iron_mountain_size.height/2);
        setSize(Finals.iron_mountain_size);
        location = new CartesianPoint(this.getLocation().x,this.getLocation().y);
        setVisible(true);
//        Thread t = new Thread(new Runnable() // I thought it's useless so I removed it
//        {
//            
//            @Override
//            public void run()
//            {
//                try
//                {
//                    Thread.sleep(100);
//                }
//                catch (InterruptedException e)
//                {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                repaint();
//            }
//        });
//        t.start();
    }
    
    public boolean set_resorces(int pick_put_value , int mode ,boolean spend){//it both checks for availability and spending by the last boolean
        System.out.println("recived");
        switch(mode){
        case 2:
            if(wood+pick_put_value>=0 ){
                if(spend)
                wood+=pick_put_value;
            }
            else 
                return false;
            break;
        case 5:
            if(food+pick_put_value>=0){
                if(spend)
                food+=pick_put_value;
            }
            else 
                return false;
            break;
        case 6:
//            if(gold+pick_put_value>=0){
//                if(spend)
//                gold+=pick_put_value;
//            }
//            else 
//                return false;
//            break;
        case 7:
            if(iron+pick_put_value>=0){
                if(spend)
                iron+=pick_put_value;
            }
            else 
                return false;
            break;
        default:
            break;
        }
        return true;
    }
    
    public int get_wood(){
        return wood;
    }
    
    public int get_iron(){
        return iron;
    }
    
    public int get_food(){
        return food;
    }
//    
//    public int get_gold(){
//        return gold;
//    }
//    
    @Override
    public void scope(int x, int y) {
   }

    @Override
    public void fire(unit_controller target) {
        target.hitted(Finals.hit_damage[3]);
    }

    @Override
    public synchronized void hitted(int hit_point) {
        current_health -= hit_point ;
        been_hit = true;
        if(current_health <= 0 ){
            isdead = true;
            game_panel.HQ_Destroyed(this);
            this.setVisible(false);
        }
        repaint();
    }
    
    public CartesianPoint get_location(){
        return location;
    }
    
    @Override
    public void paint(Graphics g) {
         super.paint(g);
        if (click) {
            g.setColor(Color.black);
            g.drawRect(this.getSize().width - this.getSize().width / 2, 0, Finals.health_bar.width,
                    Finals.health_bar.height);
            g.setColor(Color.green);
            g.fillRect(this.getSize().width - this.getSize().width / 2, 0, Finals.health_bar.width,
                    Finals.health_bar.height);
        }

        if (been_hit) {
            g.clearRect(this.getSize().width - this.getSize().width / 2, 0, Finals.health_bar.width, Finals.health_bar.height);
            if (current_health >= full_health / 2) {
                g.setColor(Color.GREEN);
            } else if (current_health < full_health / 2 && current_health >= full_health / 5) {
                g.setColor(Color.YELLOW);
            } else if (current_health < full_health / 5) {
                g.setColor(Color.RED);
            }

            g.fillRect(this.getSize().width - this.getSize().width / 2, 0, (int) (((float) current_health / (float) full_health) * Finals.health_bar.width), Finals.health_bar.height);
            // need to be checked for debbuge
        }
        //g.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (timer != null) {
            if (this.timer.inCombat) {//it's just a flag for animation
                g.drawImage(Finals.explosion.getImage(), this.getSize().width / 2 - this.getSize().width / 6, this.getSize().height / 2 - this.getSize().height / 6, this.getSize().width / 3, this.getSize().height / 3, null);
            }
        } 
    }
    
    public int get_nation(){
        return nation;
    }
    
}
