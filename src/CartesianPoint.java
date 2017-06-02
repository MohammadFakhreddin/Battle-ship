public class CartesianPoint {
    private int X;
    private int Y;
    public CartesianPoint(int x,int y)
    {
        X=x;
        Y=y;
    }
    public long distance(CartesianPoint otherPoint)
    {
        long xDiffer= (long) Math.pow(Math.abs(this.getX() - otherPoint.getX()), 2);
        long yDiffer= (long) Math.pow(Math.abs(this.getY() - otherPoint.getY()), 2);
        return Math.round(Math.ceil(Math.sqrt(xDiffer+yDiffer)));
    }
    public int getY() {
        return Y;
    }
    public int getX() {
        return X;
    }
    public void setX(int x)
    {
        this.X=x;
    }
    public void setY(int y)
    {
       this.Y =y; 
    }
}
