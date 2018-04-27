import edu.princeton.cs.algs4.Picture;
/**
 * Created by wa941 on 2018/4/18.
 */
public class SeamCarver {
    private Picture picture;
    private double[][] energy;
    public Picture picture(){
        Picture newpic = new Picture(picture);
        return newpic;
    }
    public SeamCarver(Picture picture){
        if(picture == null){
            throw new IllegalArgumentException();
        }
        this.picture = new Picture(picture);
        energy = new double[height()][width()];
        for(int i = 0; i< energy.length;i++){
            for (int j = 0; j < energy[0].length;j++){
                energy[i][j] = energy(j,i);
            }
        }
    }
    public int width(){
        return picture.width();
    }
    public int height(){
        return picture.height();
    }
    public double energy(int x,int y){
        if(x < 0 || x>= width() || y < 0 || y >=height()){
            throw new IllegalArgumentException();
        }
        if(x == 0 || x == width()-1|| y == 0 || y == height()-1){
            return 1000;
        }
        else{
            double energy = 0.0;
            int left = picture.getRGB(x-1,y);
            int r1 = (left >> 16) & 0xFF;
            int g1 = (left >> 8) & 0xFF;
            int b1 = (left >> 0) & 0xFF;
            int right = picture.getRGB(x+1,y);
            int r2 = (right >> 16) & 0xFF;
            int g2 = (right >> 8) & 0xFF;
            int b2 = (right >> 0) & 0xFF;
            int shang = picture.getRGB(x,y-1);
            int r3 = (shang >> 16) & 0xFF;
            int g3 = (shang >> 8) & 0xFF;
            int b3 = (shang >> 0) & 0xFF;
            int xia = picture.getRGB(x,y+1);
            int r4 = (xia >> 16) & 0xFF;
            int g4 = (xia >> 8) & 0xFF;
            int b4 = (xia >> 0) & 0xFF;
            double rx2 = Math.pow(r2 - r1,2) + Math.pow(g2 - g1,2) + Math.pow(b2 - b1,2);
            double ry2 = Math.pow(r4 - r3,2) + Math.pow(g4 - g3,2) + Math.pow(b4 - b3,2);
            energy = Math.sqrt(rx2 + ry2);
            return energy;
        }
    }
    public int[] findHorizontalSeam(){
        int[] result = new int[width()];
        double[][] disTo = new double[height()][width()];
        int[][] edgeTo = new int[height()][width()];
        for(int i = 0; i< height();i++){
            for (int j = 0 ;j< width();j++){
                disTo[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        //relax 最上的虚拟节点
        for(int i = 0; i < height();i++){
            if (disTo[i][0] > energy[i][0]){
                disTo[i][0] = energy[i][0];
            }
        }
        //relax 表格中节点
        for(int j = 0; j <width()-1;j++){
            for(int i = 0;i <height();i++){
                relax(i,j,false,disTo,edgeTo);
            }
        }
        int min = 0;
        for(int i = 1; i< height();i++){
            if(disTo[i][width()-1] < disTo[min][width()-1]){
                min = i;
            }
        }
        result[width()-1] = min;
        for(int i = width()-2;i>=0;i--){
            result[i] = edgeTo[result[i+1]][i+1];
        }
        return result;
    }
    public int[] findVerticalSeam(){
        int[] result = new int[height()];
        double[][] disTo = new double[height()][width()];
        int[][] edgeTo = new int[height()][width()];
        for(int i = 0; i< height();i++){
            for (int j = 0 ;j< width();j++){
                disTo[i][j] = Double.POSITIVE_INFINITY;
            }
        }
        //relax 最上的虚拟节点
        for(int i = 0; i < width();i++){
            if (disTo[0][i] > energy[0][i]){
                disTo[0][i] = energy[0][i];
            }
        }
        //relax 表格中节点
        for(int i = 0; i <height()-1;i++){
            for(int j = 0;j <width();j++){
                relax(i,j,true,disTo,edgeTo);
            }
        }
        int min = 0;
        for(int i = 1; i< width();i++){
            if(disTo[height()-1][i] < disTo[height()-1][min]){
                min = i;
            }
        }
        result[height()-1] = min;
        for(int i = height()-2;i>=0;i--){
            result[i] = edgeTo[i+1][result[i+1]];
        }
        return result;
    }
    public void removeVerticalSeam(int[] seam){
        if(seam == null|| seam.length != height() || width()<=1){
            throw new IllegalArgumentException();
        }
        for(int i = 0; i< seam.length-1;i++){
            if(Math.abs(seam[i] - seam[i+1]) >1){
                throw new IllegalArgumentException();
            }
        }
        for(int i = 0 ;i<seam.length;i++){
            if(seam[i] < 0 || seam[i] > width()-1){
                throw new IllegalArgumentException();
            }
        }
        Picture newpic = new Picture(width()-1,height());
        double[][] newenergy = new double[height()][width()-1];
        for(int i = 0;i<height();i++){
            int k = 0;
            for(int j = 0;j<width()-1;j++){
                if(k == seam[i]){
                    k++;
                }
                newpic.setRGB(j,i,picture.getRGB(k,i));
                newenergy[i][j] = energy[i][k];
                k++;
            }
        }
        picture = newpic;
        energy = newenergy;
    }
    public void removeHorizontalSeam(int[] seam){
        if(seam == null||seam.length!= width() || height()<=1){
            throw new IllegalArgumentException();
        }
        for(int i = 0; i< seam.length-1;i++){
            if(Math.abs(seam[i] - seam[i+1]) >1){
                throw new IllegalArgumentException();
            }
        }
        for(int i = 0 ;i<seam.length;i++){
            if(seam[i] < 0 || seam[i] > height()-1){
                throw new IllegalArgumentException();
            }
        }
        Picture newpic = new Picture(width(),height()-1);
        double[][] newenergy = new double[height()-1][width()];
        for(int j = 0;j<width();j++){
            int k = 0;
            for(int i = 0;i<height()-1;i++){
                if(k == seam[j]){
                    k++;
                }
                newpic.setRGB(j,i,picture.getRGB(j,k));
                newenergy[i][j] = energy[k][j];
                k++;
            }
        }
        picture = newpic;
        energy = newenergy;

    }
    private void relax(int i, int j,boolean x,double[][] disTo,int[][] edgeTo){
        if(x){
            int low = j;
            int high = j;
            if(j>0)
                low--;
            if(j<width()-1)
                high++;
            for(int k = low;k<=high;k++){
                if(disTo[i+1][k] > disTo[i][j] + energy[i+1][k]){
                    disTo[i+1][k] = disTo[i][j] + energy[i+1][k];
                    edgeTo[i+1][k] = j;
                }
            }
        }
        else {
            int low = i;
            int high = i;
            if(i > 0){
                low--;
            }
            if(i < height()-1){
                high++;
            }
            for(int k = low;k<=high;k++){
                if(disTo[k][j+1] > disTo[i][j] + energy[k][j+1]){
                    disTo[k][j+1] = disTo[i][j] + energy[k][j+1];
                    edgeTo[k][j+1] = i;
                }
            }
        }
    }
}
