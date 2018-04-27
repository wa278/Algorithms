import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private Percolation[] list;
    private double[] number;
    private double mean = -1;
    private double std = -1;
    public PercolationStats(int n, int trials){
        int k = 0 ;
        int m = 0;
        if(n<= 0|| trials <=0){
            throw new IllegalArgumentException();
        }

        list = new Percolation[trials];
        number = new double[trials];
        for(int i = 0;i<trials;i++){
            list[i] = new Percolation(n);

            while (!list[i].percolates()){
                 k = StdRandom.uniform(0,n)+1;
                 m = StdRandom.uniform(0,n)+1;
                list[i].open(k,m);
            }
            number[i] = list[i].numberOfOpenSites()*1.0/(n*n);
        }
    }
    public double mean(){
        if(mean == -1) {
            mean = StdStats.mean(number);
        }
        return mean;
    }
    public double stddev(){
        if(std == -1) {
            if (mean == -1) {
                mean();
            }
            std = StdStats.stddev(number);
        }
        return std;
    }
    public double confidenceLo(){
        if(mean == -1){
            mean();
        }
        if (std==-1){
            stddev();
        }
        return mean-1.96*std/Math.sqrt(number.length);
    }
    public double confidenceHi(){
        if(mean == 0){
            mean();
        }
        if(std == -1){
            stddev();
        }
        return mean+1.96*std/Math.sqrt(number.length);
    }
    public static void main(String[] args){
        PercolationStats a = new PercolationStats(100,16);
        System.out.println(a.mean());
        System.out.println(a.stddev());
        System.out.println(a.confidenceLo());
        System.out.println(a.confidenceHi());
    }
}
