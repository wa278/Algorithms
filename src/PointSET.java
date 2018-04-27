import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> mySet;
    public PointSET(){
        mySet = new SET();
    }
    public boolean isEmpty(){
        return  mySet.isEmpty();
    }
    public int size(){
        return mySet.size();
    }
    public void insert(Point2D p){
        if( p == null){
            throw new IllegalArgumentException();
        }
        mySet.add(p);
    }
    public boolean contains(Point2D p){
        if( p == null){
            throw new IllegalArgumentException();
        }
        return mySet.contains(p);
    }
    public void draw(){
        for(Point2D p : mySet){
            StdDraw.point(p.x(),p.y());
        }
    }
    public Iterable<Point2D> range(RectHV rect){
        if( rect == null){
            throw new IllegalArgumentException();
        }
        Queue<Point2D> queue = new Queue<>();
        for(Point2D p : mySet){
            if(rect.contains(p)){
                queue.enqueue(p);
            }
        }
        return queue;
    }
    public Point2D nearest (Point2D p){
        if( p == null){
            throw new IllegalArgumentException();
        }
        if(mySet.isEmpty()){
            return null;
        }
        else {
            Point2D min = new Point2D(0,0);
            double mindis = Double.POSITIVE_INFINITY;
            for(Point2D x : mySet){
                if(x.distanceTo(p) < mindis){
                    mindis = x.distanceTo(p);
                    min = x;
                }
            }
            return min;
        }
    }

    public static void main(String[] args) {

    }
}
