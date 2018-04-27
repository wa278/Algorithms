import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

/**
 * Created by wa941 on 2018/4/14.
 */
public class SAP {
    private final Digraph G;
    private boolean[] marked;
    private int[] distance;
    private int length;
    private int[] distancehou;
    private int ancestor;
    private int[] edgeto;
    private int[] edgetohou;
    private boolean[] markedhou;
    private Queue<Integer> queuehou;
    private int min;
    private Queue<Integer> queue;
    public SAP(Digraph G){
        if(G == null){
            throw new IllegalArgumentException();
        }
        this.G = new Digraph(G);
    }
    private class Node implements Comparable<Node> {
        private int length;
        private int id;

        Node(int length, int id) {
            this.length = length;
            this.id = id;
        }

        @Override
        public int compareTo(Node o) {
            if (length > o.length) {
                return 1;
            } else if (length == o.length) {
                return 0;
            } else {
                return -1;
            }
        }
    }
    public int length(int v,int w){
//        if(v< 0 || v >= G.V() || w<0 || w >= G.V()){
//            throw new IllegalArgumentException();
//        }
//        ancestor = -1;
//        min = -1;
//        marked = new boolean[G.V()];
//        distance = new int[G.V()];
//        queue = new Queue<>();
//        edgeto = new int[G.V()];
//        bfs(v);
//        markedhou = new boolean[G.V()];
//        distancehou = new int[G.V()];
//        queuehou = new Queue<>();
//        edgetohou = new int[G.V()];
//        markedhou[w] = true;
//        distancehou[w] = 0;
//        if(marked[w]){
//            if(min == -1){
//                min = distance[w] + distancehou[w];
//                ancestor = w;
//            }
//            else if(distance[w] + distancehou[w] < min){
//                min = distance[w] + distancehou[w];
//                ancestor = w;
//            }
//        }
//        queuehou.enqueue(w);
//        while (!queuehou.isEmpty()){
//            int x = queuehou.dequeue();
//            for(int e : G.adj(x)){
//                if(!markedhou[e]){
//                    queuehou.enqueue(e);
//                    edgetohou[e] = x;
//                    markedhou[x] = true;
//                    distancehou[e] = distancehou[x] + 1;
//                    if(marked[e]){
//                        if(min == -1){
//                            min = distance[e] + distancehou[e];
//                            ancestor = e;
//                        }
//                        else if(distance[e] + distancehou[e] < min){
//                            min = distance[e] + distancehou[e];
//                            ancestor = e;
//                        }
//                    }
//                }
//            }
//        }
//        if(ancestor != -1){
//            return min;
//        }
//        else {
//            return -1;
//        }
        if (v < 0 || v >= G.V() || w < 0 || w >= G.V())
            throw new IllegalArgumentException();
        MinPQ<Node> possibleLength = new MinPQ();
        boolean[] marked = new boolean[this.G.V()];
        marked[v] = true;
        int[] pathV = new int[G.V()];
        int[] pathW = new int[G.V()];
        for (int i = 0; i < G.V(); i++) {
            pathV[i] = -1;
            pathW[i] = -1;
        }
        pathV[v] = 0;
        pathW[w] = 0;

        // bfs on v
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(v);
        while (!queue.isEmpty()) {
            int vertex = queue.dequeue();
            for (int nextVertex : G.adj(vertex)) {
                if (!marked[nextVertex]) {
                    marked[nextVertex] = true;
                    queue.enqueue(nextVertex);
                    if (pathV[nextVertex] == -1 || pathV[nextVertex] > pathV[vertex] + 1) {
                        pathV[nextVertex] = pathV[vertex] + 1;
                    }

                }
            }
        }

        // bfs on w
        marked = new boolean[this.G.V()];
        marked[w] = true;
        queue.enqueue(w);
        while (!queue.isEmpty()) {
            int vertex = queue.dequeue();
            if (pathV[vertex] > -1) {
                Node node = new Node(pathV[vertex] + pathW[vertex], vertex);
                possibleLength.insert(node);
            }
            for (int nextVertex : G.adj(vertex)) {
                if (!marked[nextVertex]) {
                    marked[nextVertex] = true;
                    queue.enqueue(nextVertex);
                    if (pathW[nextVertex] == -1 || pathW[nextVertex] > pathW[vertex] + 1) {
                        pathW[nextVertex] = pathW[vertex] + 1;
                    }

                }
            }
        }
        if (possibleLength.size() > 0) {
            Node node = possibleLength.delMin();
            this.length = node.length;
            this.ancestor = node.id;
        } else {
            this.length = -1;
            this.ancestor = -1;
        }
        return this.length;
    }
    private void bfs(int v){
        if(v< 0 || v >= G.V()){
            throw new IllegalArgumentException();
        }
        marked[v] = true;
        distance[v] = 0;
        queue.enqueue(v);
        while (!queue.isEmpty()){
            int x = queue.dequeue();
            for(int e : G.adj(x)){
                if(!marked[e]){
                    queue.enqueue(e);
                    edgeto[e] = x;
                    marked[e] = true;
                    distance[e] = distance[x] + 1;
                }
            }
        }
    }
    public int ancestor(int v,int w){
        length(v,w);
        return ancestor;
    }
    public int length(Iterable<Integer> v,Iterable<Integer> w){
        if(v== null || w == null){
            throw new IllegalArgumentException();
        }
        ancestor = -1;
        min = -1;
        marked = new boolean[G.V()];
        distance = new int[G.V()];
        queue = new Queue<>();
        edgeto = new int[G.V()];
        bfs(v);
        markedhou = new boolean[G.V()];
        distancehou = new int[G.V()];
        queuehou = new Queue<>();
        edgetohou = new int[G.V()];
        for(int e : w){
            if(e< 0 || e >= G.V()){
                throw new IllegalArgumentException();
            }
            markedhou[e] = true;
            distancehou[e] = 0;
            if(marked[e]){
                if(min == -1){
                    min = distance[e] + distancehou[e];
                    ancestor = e;
                }
                else if(distance[e] + distancehou[e] < min){
                    min = distance[e] + distancehou[e];
                    ancestor = e;
                }
            }
            queue.enqueue(e);
        }
        while (!queuehou.isEmpty()){
            int x = queuehou.dequeue();
            for(int e : G.adj(x)){
                if(!markedhou[e]){
                    queuehou.enqueue(e);
                    edgetohou[e] = x;
                    markedhou[e] = true;
                    distancehou[e] = distancehou[x] + 1;
                    if(marked[e]){
                        if(min == -1){
                            min = distance[e] + distancehou[e];
                            ancestor = e;
                        }
                        else if(distance[e] + distancehou[e] < min){
                            min = distance[e] + distancehou[e];
                            ancestor = e;
                        }
                    }
                }
                else {
                    if(distancehou[e] > distancehou[x] + 1){
                        distancehou[e] = distancehou[x] +1;
                        if(marked[e]){
                            if(min == -1){
                                min = distance[e] + distancehou[e];
                                ancestor = e;
                            }
                            else if(distance[e] + distancehou[e] < min){
                                min = distance[e] + distancehou[e];
                                ancestor = e;
                            }
                        }
                    }
                }
            }
        }
        if(ancestor != -1){
            return min;
        }
        else {
            return -1;
        }

    }
    private void bfs(Iterable<Integer> v){
        if(v == null){
            throw new IllegalArgumentException();
        }
        for(int e : v){
            if(e < 0 || e >= G.V() ){
                throw new IllegalArgumentException();
            }
            marked[e] = true;
            distance[e] = 0;
            queue.enqueue(e);
        }
        while (!queue.isEmpty()){
            int x = queue.dequeue();
            for(int e : G.adj(x)){
                if(!marked[e]){
                    queue.enqueue(e);
                    edgeto[e] = x;
                    marked[e] = true;
                    distance[e] = distance[x] + 1;
                }
                else {
                    if(distance[e] > distance[x] + 1){
                        distance[e] = distance[x]+1;
                        edgeto[e] = x;
                    }
                }
            }
        }
    }
    public int ancestor(Iterable<Integer> v , Iterable<Integer> w){
        length(v,w);
        return ancestor;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
