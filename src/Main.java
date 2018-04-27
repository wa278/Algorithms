import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by wa941 on 2018/4/15.
 */
public class Main implements Comparable<Main>{
    public int a;
    public int b;
    public Main(int a, int b){
        this.a = a;
        this.b = b;
    }
    @Override
    public int compareTo(Main o) {
        if(a*o.b - o.a*b > 0){
            return 1;
        }
        else if(a*o.b - o.a*b == 0){
            return 0;
        }
        else {
            return -1;
        }
    }
    public static void find(Main[] b,int k,int lo,int hi){
        int j = partition(b,lo,hi);
        if(j == k){
            return;
        }
        else {
            if(j > k){
                find(b,k,lo,j-1);
            }
            else {
                find(b,k,j+1,hi);
            }
        }


    }
    public static void exch(Main[] b, int a,int c){
        Main temp = b[a];
        b[a] = b[c];
        b[c] = temp;
    }
    public static int partition(Main[] a,int lo,int hi){
        int i = lo,j = hi+1;
        Main v = a[lo];
        while (true){
            while (a[++i].compareTo(v)<0){
                if(i == hi)
                    break;
            }
            while (v.compareTo(a[--j]) < 0){
                if(j == lo)
                    break;
            }
            if(i>=j)
                break;
            exch(a,i,j);
        }
        exch(a,lo,j);
        return j;

    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        String iop = input.nextLine();
        String[] number = new String[n];
        String[] aa;
        int[] result = new int[n*2];
        for(int i = 0;i < n;i++){
            number[i] = input.nextLine();
            int count = 0;
            aa = number[i].split(" ");
            int k = Integer.parseInt(aa[0]);
            int[] x = new int[aa.length-1];
            for(int j = 0;j< aa.length-1;j++){
                x[j] = Integer.parseInt(aa[j+1]);
            }
            Main[] b = new Main[x.length * (x.length-1)/2];
            for(int m = 0 ; m < x.length;m++){
                for(int mm = m+1;mm<x.length;mm++){
                    b[count] = new Main(x[m],x[mm]);
                    count++;
                }
            }
            find(b,k-1,0,b.length-1);
            result[i*2] = b[k-1].a;
            result[i*2+1] = b[k-1].b;
        }
        for(int i = 0;i<result.length;i++){
            if(i % 2 == 0){
                System.out.print(result[i] +" ");
            }
            else {
                System.out.println(result[i]);
            }
        }
    }
}
