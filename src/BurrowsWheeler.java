import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.Queue;

/**
 * Created by wa941 on 2018/4/26.
 */
public class BurrowsWheeler {
    public static void transform(){
        String s = BinaryStdIn.readString();
        CircularSuffixArray x = new CircularSuffixArray(s);
        for(int i = 0; i<x.length();i++){
            if(x.index(i) == 0){
                BinaryStdOut.write(i);
                break;
            }
        }
        for(int i = 0; i< x.length();i++){
            int m = x.index(i);
            int k = (m + x.length()-1)%x.length();
            char n = s.charAt(k);
            BinaryStdOut.write(n);
        }
        BinaryStdOut.close();
    }
    public static void inverseTransform(){
        int first = BinaryStdIn.readInt();
        Queue<Character> queue = new Queue<>();
        while (!BinaryStdIn.isEmpty()){
            char x = BinaryStdIn.readChar();
            queue.enqueue(x);
        }
        int length = queue.size();
        char[] qian = new char[length];
        int[] next = new int[length];
        for(int i = 0 ;i <length;i++ ){
            char x= queue.dequeue();
            qian[i] = x;
            next[i] = i;
        }
        char[] aux = new char[length];
        int[] nextaux = new int[length];
        sort(qian,0,length-1,next);
        BinaryStdOut.write(qian[first]);
        int index = first;
        int i = 1;
        while (i<length){
            BinaryStdOut.write(qian[next[index]]);
            index = next[index];
            i++;
        }
        BinaryStdOut.close();
    }
    private static void sort(char[] a,int lo, int hi, int[] index){
        if(hi <= lo){
            return;
        }
        int j = partition(a,lo,hi,index);
        sort(a,lo,j-1,index);
        sort(a,j+1,hi,index);
    }
    private static int partition(char[] a,int lo,int hi,int[] index){
        int i = lo;
        int j = hi+1;
        int v = a[lo];
        while (true){
            while (a[++i] < v){
                if(i == hi)
                    break;
            }
            while (v < a[--j]){
                if(j == lo)
                    break;
            }
            if(i >= j)
                break;
            char temp = a[i];
            a[i] = a[j];
            a[j] = temp;
            int x = index[i];
            index[i] = index[j];
            index[j] = x;
        }
        char temp = a[lo];
        a[lo] = a[j];
        a[j] =temp;
        int x = index[lo];
        index[lo] = index[j];
        index[j] = x;
        return j;
    }
    public static void main(String[] args) {
        if(args[0].equals("-")){
            transform();
        }
        else if(args[0].equals("+")){
            inverseTransform();
        }
    }
}
