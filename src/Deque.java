import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int number;
    public Deque(){
        Node first ;
        Node last ;
        number = 0;
    }
    private class Node{
        Item content;
        Node last;
        Node next;
    }
    public boolean isEmpty(){
        return (first == null);
    }
    public int size(){
        return number;
    }
    public void addFirst(Item item){
        if(item == null){
            throw new IllegalArgumentException();
        }
        if(isEmpty()){
            Node index =new Node();
            index.content = item;
            index.next = null;
            index.last = null;
            first = index;
            last = index;
            index = null;

        }
        else {
            Node oldFirst = first;
            first = new Node();
            first.content = item;
            first.next = oldFirst;
            first.last = null;
            oldFirst.last = first;
            oldFirst = null;
        }
        number++;
    }
    public void addLast(Item item){
        if(item == null){
            throw new IllegalArgumentException();
        }
        if(isEmpty()){
            Node index =new Node();
            index.last = null;
            index.content = item;
            index.next = null;
            first = index;
            last = index;
            index = null;
        }
        else {
            Node oldLast = last;
            last = new Node();
            last.last = oldLast;
            last.content = item;
            last.next = null;
            oldLast.next = last;
            oldLast = null;
        }
        number++;
    }
    public Item removeFirst(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        else {
            Node index = first;
            if(first.next == null){
                first = null;
                last = null;
            }
            else {
                first = first.next;
                first.last = null;
                index.next = null;
            }
            number--;
            return index.content;
        }
    }
    public Item removeLast(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        else {
            Node index = last;
            if(first.next == null){
                first = null;
                last = null;
            }
            else {
                last = last.last;
                last.next = null;
                index.last=null;
            }
            number--;
            return index.content;
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item>{
        private Node current = first;
        public boolean hasNext(){
            return current!=null;
        }

        @Override
        public Item next() {
            if(current == null){
                throw new NoSuchElementException();
            }
            Item item = current.content;
            current = current.next;
            return item;
        }
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }
    public static void main(String[] args){
        Deque<Integer> list =new Deque<>();
        for (int i = 0 ;i< 10;i++){
            list.addFirst(i);
        }
        for(int i = 0;i<10;i++){
            list.removeLast();
        }
    }
}
