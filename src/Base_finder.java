import java.util.Vector;

// It finds places for players HQ that must be in the most far distance from eachother
public class Base_finder
{
    Vector<CartesianPoint> HQ_points;
    int player_numbers=0;
    public Base_finder(int player_numbers){
        System.out.println("Player_number" + player_numbers);
        this.player_numbers = player_numbers;
    }
    
    public Vector<CartesianPoint> get_HQ_points (){
        return findPlaces(player_numbers, find_legall_places());
    }
    
    public Vector<CartesianPoint> findPlaces(int playerNumbers,Vector<CartesianPoint> legallPlaces) throws IllegalArgumentException
    {
        
        if(legallPlaces.size()<playerNumbers)
            throw new IllegalArgumentException("Too Much Player");
        Vector<CartesianPoint> selectedPoints=new Vector<CartesianPoint>();
        long maxOfDistance=0;
        CartesianPoint selectedPoint=null;
        for(int i=0;i<playerNumbers;i++)
        {
            maxOfDistance=0;
            selectedPoint=null;
            for(CartesianPoint cp:legallPlaces)
            {
                if(maxOfDistance<=getSumOfDistances(selectedPoints,cp))
                {
                    maxOfDistance=getSumOfDistances(selectedPoints,cp);
                    selectedPoint=cp;
                }
            }
            selectedPoints.add(selectedPoint);
        }
        return  selectedPoints;
    }
    
    public long getSumOfDistances(Vector<CartesianPoint> allPoints,CartesianPoint thisPoint)
    {
        long sumOfDistances=0;
        for(CartesianPoint cp:allPoints)
        {
            sumOfDistances+=cp.distance(thisPoint);
        }
        return  sumOfDistances;
    }
    
    private Vector<CartesianPoint> find_legall_places (){ // it finds legal places for base
        Vector<CartesianPoint> legall_points = new Vector<>();
        
       // some parts of map are invisible so it don't have to be shown
        for(int i=2 ;i<Finals.pixeles.length-2;i++){
            for(int j=2 ;j<Finals.pixeles[0].length-2;j++){
                if(Finals.pixeles[i][j]%10==1 && ((Finals.pixeles[i][j]/10)%10==1 || (Finals.pixeles[i][j]/10)%10==2)&& (Finals.pixeles[i][j]/100!=15))
                {
                    legall_points.add(new CartesianPoint(i, j));
                }
            }
        }
        
        return legall_points;
    }
}
