import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Stack;

public class BruteCollinearPoints {
    private Point[] points;
    private int n;
    public BruteCollinearPoints(Point[] points){
        if(points == null){
            throw new IllegalArgumentException();
        }
        for (int i = 0;i< points.length;i++){
            if(points[i] == null){
                throw new IllegalArgumentException();
            }
            for (int j = i+1; j <points.length;j++){
                if (points[j] .equals(points[i])){
                    throw new IllegalArgumentException();
                }
            }
        }
        this.points = points;
        n = 0;
    }
    public int numberOfSegments(){
        return n;
    }
    public LineSegment[] segments(){
        int k = points.length;
        Stack<LineSegment> stack = new Stack<>();
        int number ;
        int min;
        int max;
        double[] array;
        for(int i = 0;i< k;i++){
            min = i;
            max = i;
            array = new double[k-1-i];
            for (int j = i+1;j<k;j++){
                array[j - i -1] = points[i].slopeTo(points[j]);
            }
            for(int s = 0;s <k-1-i;s++){
                if(points[s+1+i].compareTo(points[max]) >0 ){
                    max = s+1+i;
                }
                if (points[s+1+i].compareTo(points[min]) < 0){
                    min = s+1+i;
                }
                int q = 0;
                for(int d= s+1; d < k-1-i;d++){
                    if(array[d] == array[s]){
                        q++;
                        if(points[d+1+i].compareTo(points[max]) >0 ){
                            max = d+1+i;
                        }
                        if (points[d+1+i].compareTo(points[min]) < 0){
                            min = d+1+i;
                        }
                    }
                }
                if(q == 2){
                    n++;
                    LineSegment line = new LineSegment(points[min],points[max]);
                    stack.push(line);
                }
                min = i;
                max = i;
            }
        }
        LineSegment[] result = new LineSegment[stack.size()];
        for(int i = 0;i<result.length;i++){
            result[i] = stack.pop();
        }
        return result;
//        for(int i = 0; i< k;i++){
//            number = 1;
//            min = i;
//            max = i;
//            for(int j = i+1; j < k;j++){
//                double slope = points[i].slopeTo(points[j]);
//                if(points[j].compareTo(points[min]) <0){
//                    min = j;
//                }
//                if(points[j].compareTo(points[max])>0){
//                    max = j;
//                }
//                number++;
//                for(int m = j+1; m < k ;m++){
//                    if(points[i].slopeTo(points[m]) == slope){
//                        number++;
//                        if(points[m].compareTo(points[min]) <0){
//                            min = m;
//                        }
//                        if(points[m].compareTo(points[max])>0){
//                            max = m;
//                        }
//                    }
//                }
//                if(number >=4){
//                    LineSegment line = new LineSegment(points[min],points[max]);
//                    stack.push(line);
//                }
//                number=1;
//            }
//        }
//        LineSegment[] result = new LineSegment[stack.size()];
//        for(int i = 0;i<result.length;i++){
//            result[i] = stack.pop();
//        }
//        return result;
    }
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
