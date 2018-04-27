import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        RandomizedQueue<String> k = new RandomizedQueue<>();
        while (!StdIn.isEmpty()){
            k.enqueue(StdIn.readString());
        }
        for(int i = 0;i<m;i++){
            System.out.println(k.dequeue());
        }
    }

}
