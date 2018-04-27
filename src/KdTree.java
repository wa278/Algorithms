import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;


public class KdTree {
    private class Node implements Comparable<Node>{
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;
        private int j = 0;
        public Node(Point2D p){
            this.p = p;
        }
        public Node(Point2D p, int j){
            this.p = p;
            this.j = j;
        }

        @Override
        public int compareTo(Node o) {
            if(o.p.x()> p.x()){
                return 1;
            }
            else if(o.p.x()< p.x()){
                return -1;
            }
            else {
                if(o.p.y()> p.y()){
                    return 1;
                }
                else if(o.p.y()> p.y()){
                    return -1;
                }
                else
                    return 0;
            }
        }
    }
    private Node root;
    private int n ;
    private double distance = 0;
    private Node nearest ;
    private int count = 0;
    private Queue<Point2D> queue ;
    public KdTree(){
        n = 0;
    }
    public boolean isEmpty(){
        return  n==0;
    }
    public int size(){
        return n;
    }
    public void insert(Point2D p){
        if(p == null){
            throw new IllegalArgumentException();
        }
        root = insert(root,p);
    }
    private Node insert(Node p, Point2D q){
        if(q == null){
            throw new IllegalArgumentException();
        }
        if(p == null){
            n++;
            int c = count;
            count = 0;
            return new Node(q,c);
        }
        else {
            if(p.j % 2 == 0){
                if(q.x() < p.p.x()){
                    count++;
                    p.lb = insert(p.lb,q);
                }
                else if(q.x() > p.p.x()){
                    count++;
                    p.rt = insert(p.rt,q);
                }
                else {
                    if(q.y() != p.p.y()){
                        count++;
                        p.rt = insert(p.rt,q);
                    }
                }
            }
            else {
                if(q.y() < p.p.y()){
                    count++;
                    p.lb = insert(p.lb,q);
                }
                else if(q.y() > p.p.y()){
                    count++;
                    p.rt = insert(p.rt,q);
                }
                else {
                    if(q.x() != p.p.x()){
                        count++;
                        p.rt = insert(p.rt,q);
                    }
                }
            }
        }
        count = 0;
        return p;
    }
    public boolean contains(Point2D p){
        if(p == null){
            throw new IllegalArgumentException();
        }
        Node x = root;
        while (true){
            if(x == null){
                return false;
            }
            else if(x.p .equals(p) ){
                return true;
            }
            else{
                if(x.j % 2 == 0){
                    if(x.p.x() <= p.x()){
                        x = x.rt;
                    }
                    else {
                        x = x.lb;
                    }
                }
                else {
                    if(x.p.y() <= p.y()){
                        x = x.rt;
                    }
                    else {
                        x = x.lb;
                    }
                }
            }
        }
    }
    public void draw(){
        draw(root);
    }
    private void draw(Node x){
        if(x != null){
            draw(x.lb);
            StdDraw.point(x.p.x(),x.p.y());
            draw(x.rt);
        }
    }
    public Iterable<Point2D> range(RectHV rect){
        if(rect == null){
            throw new IllegalArgumentException();
        }
        queue = new Queue<>();
        range(rect,root);
        return queue;
    }
    private void range(RectHV rect , Node x){
        if(x!= null){
            if(rect.contains(x.p)){
                queue.enqueue(x.p);
                range(rect,x.lb);
                range(rect,x.rt);
            }
            else if(x.j % 2 == 0){
                if(rect.xmax() < x.p.x()){
                    range(rect,x.lb);
                }
                else if(rect.xmin()>x.p.x())
                    range(rect,x.rt);
                else{
                    range(rect,x.lb);
                    range(rect,x.rt);
                }
            }
            else {
                if(rect.ymax() < x.p.y()){
                    range(rect,x.lb);
                }
                else if (rect.ymin() > x.p.y()){
                    range(rect,x.rt);
                }
                else {
                    range(rect,x.lb);
                    range(rect,x.rt);
                }
            }
        }
    }
    public Point2D nearest(Point2D p){
        if(p == null){
            throw new IllegalArgumentException();
        }
        distance = Double.POSITIVE_INFINITY;
        nearest(root,p);
        return nearest.p;
    }
    private void nearest(Node node,Point2D p){
        if(node!=null){
            if(node.p.distanceTo(p) < distance){
                distance = node.p.distanceTo(p);
                nearest = node;
            }
            if(node.j%2 == 0){
                if(p.x()<node.p.x()){
                    nearest(node.lb,p);
                    if(distance> node.p.x()-p.x()){
                        nearest(node.rt,p);
                    }
                }
                else {
                    nearest(node.rt,p);
                    if(distance > p.x()-node.p.x()){
                        nearest(node.lb,p);
                    }
                }
            }
            else {
                if(p.y()<node.p.y()){
                    nearest(node.lb,p);
                    if(distance> node.p.y()-p.y()){
                        nearest(node.rt,p);
                    }
                }
                else {
                    nearest(node.rt,p);
                    if(distance > p.y()-node.p.y()){
                        nearest(node.lb,p);
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.7,0.2));
        tree.insert(new Point2D(0.5,0.4));
        tree.insert(new Point2D(0.2,0.3));
        tree.insert(new Point2D(0.4,0.7));
        tree.insert(new Point2D(0.9,0.6));
//        tree.insert(new Point2D(0.25,1.0));
//        tree.insert(new Point2D(0.0,0.75));
//        tree.insert(new Point2D(0.5,0.75));
//        tree.insert(new Point2D(1.0,0.0));
//        tree.insert(new Point2D(0.5,0.0));
        tree.nearest(new Point2D(0.128,0.452));
        int x =0;


    }

}
