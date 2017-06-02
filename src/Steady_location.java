
public class Steady_location
{
    private int entity=10000;//meghdar
    private int mode;
    private CartesianPoint inMap_location ;
    private CartesianPoint real_location;
    public boolean isVisible = true;
    
    public Steady_location(CartesianPoint location , int mode){
        this.inMap_location = location;
        this.mode = mode;
        this.real_location = new CartesianPoint(inMap_location.getX()*Finals.PIC_SIZE.width
                ,inMap_location.getY()*Finals.PIC_SIZE.height);
    }
    
    public int getX(){// it would return map_location
        return inMap_location.getX();
    }
    
    public int getY(){
        return inMap_location.getY();
    }
    
    public int getMode(){
        return mode;
    }
    
    public CartesianPoint get_location(){
        return real_location;
    }
    
    public boolean pick_entity (){
        if(entity>0){
            if(mode!=5)
                entity-=100;
        return true;//It is the continue condition for work schedule
        }
        if(mode!=6)
        isVisible=false;
        return false;
    }
    public void new_season_replenish(){
        entity+=10000;
        entity = entity%20000;
        isVisible=true;
    }
}
