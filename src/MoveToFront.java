import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

/**
 * Created by wa941 on 2018/4/25.
 */
public class MoveToFront {
    public static void encode(){
        class Node{
            char x;
            Node last;
            Node next;
        }
        Node root = new Node();
        root.x = (char)(0);
        root.last = null;
        Node first = root;
        for(int i = 1 ;i<256;i++){
            Node c = new Node();
            c.x = (char)(i);
            first.next = c;
            c.last = first;
            first = c;
        }
        while (!BinaryStdIn.isEmpty()){
            char c = BinaryStdIn.readChar();
            Node index = root;
            for(int i = 0; i< 256;i++){
                if(index.x == c){
                    if(i == 0){
                        BinaryStdOut.write(i,8);
                        break;
                    }
                    else if( i == 255){
                        BinaryStdOut.write(i,8);
                        index.last.next = null;
                        index.next = root;
                        index.last = null;
                        root.last = index;
                        root = index;
                        break;
                    }
                    else {
                        BinaryStdOut.write(i,8);
                        index.last.next = index.next;
                        index.next.last = index.last;;
                        index.next = root;
                        index.last = null;
                        root.last = index;
                        root = index;
                        break;
                    }
                }
                index = index.next;
            }
        }
        BinaryStdOut.close();
    }
    public static void decode(){
        char[] array = new char[256];
        for(int i = 0 ;i<256;i++){
            array[i] = (char)(i);
        }
        while (!BinaryStdIn.isEmpty()){
            char c = BinaryStdIn.readChar();
            char x = array[(int)(c)];
            BinaryStdOut.write(x);
            for(int j = (int)(c);j>=1;j--){
                array[j] = array[j-1];
            }
            array[0] = x;
        }
        BinaryStdOut.close();
    }
    public static void main(String[] args){
        if(args[0].equals("-") ){
            encode();
        }
        else if(args[0].equals("+")){
            decode();
        }
    }
}
