import edu.princeton.cs.algs4.TST;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by wa941 on 2018/4/24.
 */
public class BoggleSolver {
    private TST<Integer> tst;
    public BoggleSolver(String [] dictionary){
        tst = new TST<>();
        for(String e : dictionary){
            tst.put(e,1);
        }
    }
    public Iterable<String> getAllValidWords(BoggleBoard board){
        LinearProbingHashST<String,Integer> result = new LinearProbingHashST<>();
        for(int i = 0; i < board.rows();i++){
            for(int j = 0; j< board.cols();j++){
                String s = "";
                if(board.getLetter(i,j) == 'Q'){
                    s = "QU";
                }
                else {
                    s = ""+ board.getLetter(i,j);
                }
                boolean[][] marked = new boolean[board.rows()][board.cols()];
                marked[i][j]= true;
                dfs(board,i,j,s,result,marked);
            }
        }
        return result.keys();
    }
    private void dfs(BoggleBoard board, int i, int j, String s, LinearProbingHashST<String,Integer> result, boolean[][] marked){
        if(i-1 >=0&&j-1>=0&&!marked[i-1][j-1]){
            String a =new String(s);
            if(board.getLetter(i-1,j-1) == 'Q'){
                s = s+"QU";
            }
            else {
                s = s+board.getLetter(i-1,j-1);
            }
            if(tst.keysWithPrefix(s).iterator().hasNext()){
                if(tst.contains(s)){
                    if(s.length()>=3){
                        result.put(s,1);
                    }
                }
                marked[i-1][j-1] = true;
                dfs(board,i-1,j-1,s,result,marked);
                marked[i-1][j-1] = false;
                s = a;
            }
            s = a;
        }
        if(i-1 >=0&&!marked[i-1][j]){
            String a = new String(s);
            if(board.getLetter(i-1,j) == 'Q'){
                s = s+"QU";
            }
            else {
                s = s+board.getLetter(i-1,j);
            }

            if(tst.keysWithPrefix(s).iterator().hasNext()){
                if(tst.contains(s)){
                    if(s.length()>=3){
                        result.put(s,1);
                    }
                }
                marked[i-1][j] = true;
                dfs(board,i-1,j,s,result,marked);
                marked[i-1][j] = false;
                s = a;
            }
            s = a;
        }
        if(i-1 >=0&&j+1<=board.cols()-1&&!marked[i-1][j+1]){
            String a = new String(s);
            if(board.getLetter(i-1,j+1) == 'Q'){
                s = s+"QU";
            }
            else {
                s = s+board.getLetter(i-1,j+1);
            }

            if(tst.keysWithPrefix(s).iterator().hasNext()){
                if(tst.contains(s)){
                    if(s.length()>=3){
                        result.put(s,1);
                    }
                }
                marked[i-1][j+1] = true;
                dfs(board,i-1,j+1,s,result,marked);
                marked[i-1][j+1] = false;
                s = a;
            }
            s = a;
        }
        if(j-1>=0&&!marked[i][j-1]){
            String a = new String(s);
            if(board.getLetter(i,j-1) == 'Q'){
                s = s+"QU";
            }
            else {
                s = s+board.getLetter(i,j-1);
            }

            if(tst.keysWithPrefix(s).iterator().hasNext()){
                if(tst.contains(s)){
                    if(s.length()>=3){
                        result.put(s,1);
                    }
                }
                marked[i][j-1] = true;
                dfs(board,i,j-1,s,result,marked);
                marked[i][j-1] = false;
                s = a;
            }
            s = a;
        }
        if(j+1<=board.cols()-1&&!marked[i][j+1]){
            String a = new String(s);
            if(board.getLetter(i,j+1) == 'Q'){
                s = s+"QU";
            }
            else {
                s = s+board.getLetter(i,j+1);
            }

            if(tst.keysWithPrefix(s).iterator().hasNext()){
                if(tst.contains(s)){
                    if(s.length()>=3){
                        result.put(s,1);
                    }
                }
                marked[i][j+1] = true;
                dfs(board,i,j+1,s,result,marked);
                marked[i][j+1] = false;
                s = a;
            }
            s = a;
        }
        if(i+1<=board.rows()-1&&j-1>=0&&!marked[i+1][j-1]){
            String a = new String(s);
            if(board.getLetter(i+1,j-1) == 'Q'){
                s = s+"QU";
            }
            else {
                s = s+board.getLetter(i+1,j-1);
            }

            if(tst.keysWithPrefix(s).iterator().hasNext()){
                if(tst.contains(s)){
                    if(s.length()>=3){
                        result.put(s,1);
                    }
                }
                marked[i+1][j-1] = true;
                dfs(board,i+1,j-1,s,result,marked);
                marked[i+1][j-1] = false;
                s = a;
            }
            s = a;
        }
        if(i+1<=board.rows()-1&&!marked[i+1][j]){
            String a = new String(s);
            if(board.getLetter(i+1,j) == 'Q'){
                s = s+"QU";
            }
            else {
                s = s+board.getLetter(i+1,j);
            }

            if(tst.keysWithPrefix(s).iterator().hasNext()){
                if(tst.contains(s)){
                    if(s.length()>=3){
                        result.put(s,1);
                    }
                }
                marked[i+1][j] = true;
                dfs(board,i+1,j,s,result,marked);
                marked[i+1][j] = false;;
                s = a;
            }
            s = a;
        }
        if(i+1<=board.rows()-1&&j+1<=board.cols()-1&&!marked[i+1][j+1]){
            String a = new String(s);
            if(board.getLetter(i+1,j+1) == 'Q'){
                s = s+"QU";
            }
            else {
                s = s+board.getLetter(i+1,j+1);
            }

            if(tst.keysWithPrefix(s).iterator().hasNext()){
                if(tst.contains(s)){
                    if(s.length()>=3){
                        result.put(s,1);
                    }
                }
                marked[i+1][j+1] = true;
                dfs(board,i+1,j+1,s,result,marked);
                marked[i+1][j+1] = false;
                s =a ;
            }
            s = a;
        }
    }
    public int scoreOf(String word){
        switch (word.length()){
            case 0:return 0;
            case 1:return 0;
            case 2:return 0;
            case 3:return 1;
            case 4:return 1;
            case 5:return 2;
            case 6:return 3;
            case 7:return 5;
        }
        return 11;
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}

