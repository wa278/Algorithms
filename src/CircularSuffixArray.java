/**
 * Created by wa941 on 2018/4/26.
 */
public class CircularSuffixArray {
    private char[] array;
    private int length;
    private int[] index;
    public CircularSuffixArray(String s){
        if(s == null){
            throw new IllegalArgumentException();
        }
        array = s.toCharArray();
        length = s.length();
        index = new int[length];
        for(int i =0 ;i <length;i++){
            index[i] = i;
        }
        sort(index,0, array.length-1);
    }
    private void sort(int[] a,int lo, int hi){
        if(hi <= lo){
            return;
        }
        int j = partition(a,lo,hi);
        sort(a,lo,j-1);
        sort(a,j+1,hi);
    }
    private int partition(int[] a,int lo,int hi){
        int i = lo;
        int j = hi+1;
        int v = a[lo];
        while (true){
            while (compare(a[++i],v) < 0){
                if(i == hi)
                    break;
            }
            while (compare(v,a[--j]) < 0){
                if(j == lo)
                    break;
            }
            if(i >= j)
                break;
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        int temp = a[lo];
        a[lo] = a[j];
        a[j] =temp;
        return j;
    }
    public int length(){
        return length;
    }
    public int index(int i){
        if(i <0|| i >= length){
            throw new IllegalArgumentException();
        }
        return index[i];
    }
    private int compare(int i, int j){
        for(int m = 0 ;m < length;m++){
            if(array[i] < array[j]){
                return -1;
            }
            else if(array[i] > array[j]){
                return 1;
            }
            i = (i+1)%length;
            j = (j+1)%length;
        }
        return 0;
    }
    public static void main(String[] args) {

    }
}
