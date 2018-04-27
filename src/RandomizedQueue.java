import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private int n ;
    private Item[] list ;
    public RandomizedQueue(){
        list = (Item[]) new Object[1];
        n = 0;
    }

    public Iterator<Item> iterator() {
        return new ListIterator() ;
    }
    public boolean isEmpty(){
        return (n==0);
    }
    public int size(){
        return n;
    }
    private class ListIterator implements Iterator<Item>{
        int count = 0;
        int[] rand = new int[n];
        public ListIterator(){
            super();
            int x = 0;
            for(int i = 0;i< n ;i++){
                rand[i] = i;
            }
            StdRandom.shuffle(rand);
        }
        public boolean hasNext(){
            return (count <n);
        }

        @Override
        public Item next() {
            if(count < n){
                return list[rand[count++]];
            }
            else {
                throw new NoSuchElementException();
            }
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }
    public void enqueue(Item item){
        if(item == null){
            throw new IllegalArgumentException();
        }
        if(n == list.length){
            Item[] newlist =(Item[])new Object[2*n];
            for(int i = 0;i<n;i++){
                newlist[i] = list[i];
            }
            list = newlist;
        }
        list[n++] = item;
    }
    public Item sample(){
        if(isEmpty()){
            throw  new NoSuchElementException();
        }
        int k = StdRandom.uniform(n);
        return list[k];
    }
    public Item dequeue(){
        if(isEmpty()){
            throw  new NoSuchElementException();
        }
        int k = StdRandom.uniform(n);
        Item x = list[k];
        list[k] = list[n-1];
        list[--n] = null;

        if(n>0&&n <= list.length/4){
            Item[] newlist =(Item[])new Object[list.length /2];
            for(int i = 0;i< n;i++){
                newlist[i] = list[i];
            }
            list = newlist;
        }
        return x;
    }
    public static void main(String[] args){
        RandomizedQueue<Integer> list =new RandomizedQueue<>();
        for(int i = 0;i<2;i++){
            list.enqueue(i);
        }
        for(int i = 0;i<100;i++){
            Iterator x = list.iterator();
            System.out.println(x.next()+" " + x.next());
        }

    }

}
