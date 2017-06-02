
public interface Ship_Fight {
    void scope(int x , int y);  //for finding enemy    ;   x,y are ship location
     void fire(unit_controller target);
     void hitted(int hit_point);
}

