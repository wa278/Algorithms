//import edu.princeton.cs.algs4.In;
//import edu.princeton.cs.algs4.StdDraw;
//import edu.princeton.cs.algs4.StdOut;
//import java.util.Stack;
//
//public class FastCollinearPoints {
//    private Point[] points;
//    private int n;
//    public FastCollinearPoints(Point[] points){
//        if(points == null){
//            throw new IllegalArgumentException();
//        }
//        for (int i = 0;i< points.length;i++){
//            if(points[i] == null){
//                throw new IllegalArgumentException();
//            }
//            for (int j = i+1; j <points.length;j++){
//                if (points[j] .equals(points[i])){
//                    throw new IllegalArgumentException();
//                }
//            }
//        }
//        this.points = points;
//        n = 0;
//    }
//    public int numberOfSegments(){
//        return n;
//    }
//    public LineSegment[] segments(){
//        Stack<LineSegment> stack = new Stack<>();
//        double[] list ;
//        int [] array;
//        int k = points.length;
//        for(int i = 0 ;i< k-3;i++){
//            list = new double[k-1-i];
//            array = new int[k-1-i];
//            for (int s = 0;s <k-1-i;s++){
//                list[s] = points[i].slopeTo(points[s+1+i]);
//                array[s] = i+1+s;
//            }
//            for(int s = 1;s<k-1-i;s++){
//                for(int p = s;p>0;p--){
//                    if((list[p] < list[p-1] )||(list[p] == list[p-1] && points[array[p]].compareTo(points[array[p-1]])<0)){
//                        double x =list[p];
//                        list[p] = list[p-1];
//                        list[p -1] = x;
//                        int y = array[p];
//                        array[p] = array[p-1];
//                        array[p-1] = y;
//                    }
//
//                }
//            }
//            double r = list[0];
//            int op =1;
//            int min = 0;
//            int max = 0;
//            int start = 0;
//            for(int q = 1;q<list.length;q++){
//                if(list[q] == r){
//                    op++;
//                }
//                else{
//                    if(op >=3){
//                        if(points[i].compareTo(points[array[q-1]]) >0 ){
//                            max = i;
//                        }
//                        else {
//                            max = array[q-1];
//                        }
//                        if (points[i].compareTo(points[array[start]]) < 0){
//                            min = i;
//                        }
//                        else {
//                            min = array[start];
//                        }
//                        LineSegment line = new LineSegment(points[min],points[max]);
//                        stack.push(line);
//                        start = q;
//                        r = list[q];
//                        op =1;
//                    }
//                }
//            }
//        }
//        LineSegment[] result = new LineSegment[stack.size()];
//        for(int i = 0 ;i < result.length;i++){
//            result[i] = stack.pop();
//        }
//        return result;
//    }
//    public static void main(String[] args) {
//        // read the n points from a file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        Point[] points = new Point[n];
//        for (int i = 0; i < n; i++) {
//            int x = in.readInt();
//            int y = in.readInt();
//            points[i] = new Point(x, y);
//        }
//
//        // draw the points
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setXscale(0, 32768);
//        StdDraw.setYscale(0, 32768);
//        for (Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();
//
//        // print and draw the line segments
//        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
//        StdDraw.show();
//    }
//}
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private int lineNumber;
    private Node last;

    public FastCollinearPoints(Point[] points) // finds all line segments containing 4 or more points
    {
        if (points == null) {
            throw new NullPointerException();
        }

        lineNumber = 0;

        int num = points.length;
        Point[] clone = new Point[num];

        for (int i = 0; i < num; i++) {
            if (points[i] == null) {
                throw new NullPointerException();
            }

            for (int j = i + 1; j < num; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
            clone[i] = points[i];
        }
        Arrays.sort(clone);

        if (num < 4) {
            return;
        }

        for (int i = 0; i < num - 1; i++) {
            int tempPointsNum = 0;
            Point[] tempPoints = new Point[num - 1];

            for (int j = 0; j < num; j++) {
                if (i != j) tempPoints[tempPointsNum++] = clone[j];
            }

            Arrays.sort(tempPoints, clone[i].slopeOrder());

            int count = 0;
            Point min = null;
            Point max = null;

            for (int j = 0; j < (tempPointsNum - 1); j++) {
                if (clone[i].slopeTo(tempPoints[j]) == clone[i].slopeTo(
                        tempPoints[j + 1])) {
                    if (min == null) {
                        if (clone[i].compareTo(tempPoints[j]) > 0) {
                            max = clone[i];
                            min = tempPoints[j];
                        } else {
                            max = tempPoints[j];
                            min = clone[i];
                        }
                    }

                    if (min.compareTo(tempPoints[j + 1]) > 0) {
                        min = tempPoints[j + 1];
                    }

                    if (max.compareTo(tempPoints[j + 1]) < 0) {
                        max = tempPoints[j + 1];
                    }

                    count++;

                    if (j == (tempPointsNum - 2)) {
                        if (count >= 2 && clone[i].compareTo(min) == 0) {
                            addLine(min, max);
                        }

                        count = 0;
                        min = null;
                        max = null;
                    }
                } else {
                    if (count >= 2 && clone[i].compareTo(min) == 0) {
                        addLine(min, max);
                    }

                    count = 0;
                    min = null;
                    max = null;
                }
            }
        }
    }

    private void addLine(Point a, Point b) {
        if (last != null) {
            Node newNode = new Node();
            newNode.prev = last;
            newNode.value = new LineSegment(a, b);
            last = newNode;
        } else {
            last = new Node();
            last.value = new LineSegment(a, b);
        }
        lineNumber++;
    }

    public int numberOfSegments() // the number of line segments
    {
        return lineNumber;
    }

    public LineSegment[] segments() // the line segments
    {
        LineSegment[] lines = new LineSegment[lineNumber];
        Node current = last;

        for (int i = 0; i < lineNumber; i++) {
            lines[i] = current.value;
            current = current.prev;
        }

        return lines;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

        StdDraw.show();
    }

    private class Node {
        private LineSegment value;
        private Node prev;
    }
}