
import edu.princeton.cs.algs4.StdRandom;


public class Percolation {
    private boolean[] matrix;
    private int[] list;
    private int[] size;
    private int lenth = 0;
    public Percolation(int n){
        if(n <=0){
            throw new IllegalArgumentException();
        }
        lenth = n;
        matrix = new boolean[n*n+2];
        list = new int[n*n+2];
        size = new int[n*n+2];
        for(int i = 0;i<n*n;i++){
            matrix[i] = false;
            list[i] = i;
            size[i] = 1;
        }
        matrix[n*n] = false;
        matrix[n*n+1] = false;
        list[n*n] = n*n;
        list[n*n+1] = n*n+1;
        size[n*n] = 0;
        size[n*n+1] = 0;

    }
    public void open(int row,int col){
        if(!legal(row,col) ){
            throw new IllegalArgumentException("Wrong index");
        }
        if(!isOpen(row,col)){
            int x = findx(row,col);
            matrix[x] =true;
            if(legal(row-1,col)){
                if(isOpen( row-1,col)){
                    union(findx(row-1,col),x);
                }
            }
            if(legal(row+1,col)){
                if(isOpen(row+1,col)){
                    union(findx(row+1,col),x);
                }
            }
            if(legal(row,col-1)){
                if(isOpen(row,col-1)){
                    union(findx(row,col-1),x);
                }
            }
            if((legal(row,col+1))){
                if(isOpen(row,col+1)){
                    union(x,findx(row,col+1));
                }
            }
        }
    }
    public boolean isOpen(int row,int col){
        if(!legal(row,col) ){
            throw new IllegalArgumentException("Wrong index");
        }
        int n = findx(row,col);
        return matrix[n] == true?true:false;
    }
    private void union(int one,int two){
        int i = root(one);
        int j = root(two);
        if(i == j)
            return;
        else if(size[i] >= size[j]){
            list[j] = i;
            size[i] += size[j];
        }
        else{
            list[i] = j;
            size[j]+=size[i];
        }
    }
    private int root(int n){
        while (list[n] != n){
            list[n] = list[list[n]] ;
            n = list[n];
        }
        return n;
    }
    public int numberOfOpenSites(){
        int count= 0;
        for(int i = 0;i<matrix.length;i++){
            if(matrix[i] == true)
                count+= 1;
        }
        return count;
    }
    public boolean percolates(){
        list[lenth * lenth] = lenth*lenth;
        list[lenth * lenth+1] = lenth * lenth+1;
        for(int k = 0;k<lenth;k++){
            if(isOpen(1,k+1))
                union(k,list.length-1);
        }
        for (int k = 0;k<lenth;k++){
            if(isOpen(lenth,k+1))
                union(findx(lenth,k+1),list.length-2);
        }
        boolean a = isUnion(list.length-1,list.length-2);
        list[lenth * lenth] = lenth*lenth;
        list[lenth * lenth+1] = lenth * lenth+1;
        return a;


    }
    private boolean isUnion(int one,int two){
        int i = root(one);
        int j = root(two);
        return i==j?true:false;
    }
    public boolean isFull(int row, int col){
        if(!legal(row,col) ){
            throw new IllegalArgumentException("Wrong index");
        }
        int x = findx(row,col);
        if(isOpen(row,col)){
            for(int k = 0;k<lenth;k++){
                if(isOpen(1,k+1))
                    union(findx(1,k+1),list.length-1);
            }
            boolean a = isUnion(list.length-1,x);
            list[list.length-1] = list.length-1;
            return a;
        }
        return false;

    }
    private int findx(int row,int col){
        return (row-1)*lenth + col -1;
    }
    private boolean legal(int row,int col){
        if(row>= 1&&row<=lenth && col >=1 && col<=lenth){
            return true;
        }
        else {
            return false;
        }
    }
    public static void main(String[] args){

        Percolation a = new Percolation(3);

        int count =0;
        while (!a.percolates()){
            a.open(1,3);
            a.open(2,3);
            a.open(3,3);
            a.open(3,1);
            boolean b = a.isFull(3,1);
            System.out.println(b);

        }
        System.out.println(a.numberOfOpenSites()/(500.0*500.0));
    }
}
