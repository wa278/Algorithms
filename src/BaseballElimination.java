import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
public class BaseballElimination {
    private LinearProbingHashST<String,Integer> index;
    private String[] gameteam;
    private Queue<String> queue = new Queue<>();
    private int inteam;
    private Queue<String> myqueue;
    private int number;
    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] array;
    private void valide(String x){
        if(!index.contains(x)){
            throw new IllegalArgumentException();
        }
    }
    public BaseballElimination(String filename){
        if(filename == null){
            throw new IllegalArgumentException();
        }
        index = new LinearProbingHashST<>();
        In in = new In(filename);
        number = in.readInt();
        array = new int[number][number];
        gameteam = new String[number];
        w = new int[number];
        l = new int[number];
        r = new int[number];
        for(int i = 0; i< number;i++){
            String team = in.readString();
            index.put(team,i);
            gameteam[i] = team;
            queue.enqueue(team);
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();
            for(int j = 0;j < number;j++){
                array[i][j] = in.readInt();
            }
        }
    }
    public Iterable<String> teams(){
        return queue;
    }
    public int numberOfTeams(){
        return number;
    }
    public int wins(String team){
        valide(team);
        return w[index.get(team)];
    }
    public int losses(String team){
        valide(team);
        return l[index.get(team)];
    }
    public int remaining(String team){
        valide(team);
        return r[index.get(team)];
    }
    public int against(String team1, String team2){
        valide(team1);
        valide(team2);
        int a = index.get(team1);
        int b = index.get(team2);
        return array[a][b];
    }
    public boolean isEliminated(String team){
        valide(team);
        myqueue = new Queue<>();
        boolean result = false;
        inteam = index.get(team);
        for(int i = 0; i< number;i++){
            if(w[inteam] + r[inteam] < w[i]){
                result = true;
                myqueue.enqueue(gameteam[i]);
            }
        }
        if(result){
            return true;
        }
        else {
            myqueue = null;
        }
        FlowNetwork game = new FlowNetwork(1+1+(number-1)*(number-2)/2 + number-1);
        int[] cap = new int[(number-1)* ( number-2) /2];
        int count =0;
        int sum = 0;
        for(int i = 0;i< number;i++){
            if(i != inteam) {
                for(int j = i+1; j<number;j++){
                    if(j!=inteam){
                        cap[count] = array[i][j];
                        sum+=cap[count];
                        count++;
                    }
                }
            }
        }
        for(int i =0 ; i< (number-1)*(number-2)/2 ;i++){
            FlowEdge x = new FlowEdge(game.V()-2,i,cap[i]);
            game.addEdge(x);
        }
        int[] cap1 = new int[number-1];
        count = 0;
        for(int i = 0; i< number;i++){
            if(i!=inteam){
                cap1[count] = w[inteam] + r[inteam] - w[i];
                count++;
            }
        }
        for(int i = 0; i< number-1;i++){
            FlowEdge x = new FlowEdge((number-1)*(number-2)/2+i,game.V()-1,cap1[i]);
            game.addEdge(x);
        }
        int loop = number-2;
        count = 0;
        while (loop>0){
            for(int i = 0; i< loop;i++){
                FlowEdge y = new FlowEdge(count+i,(number-1)*(number-2)/2+number-2-loop,Double.POSITIVE_INFINITY);
                FlowEdge z = new FlowEdge(count+i,(number-1)*(number-2)/2+number-2-loop+1+i,Double.POSITIVE_INFINITY);
                game.addEdge(y);
                game.addEdge(z);
            }
            count += loop;
            loop--;
        }
        FordFulkerson al = new FordFulkerson(game,game.V()-2,game.V()-1);
        if(sum!=al.value()){
            myqueue = new Queue<>();
            int xxx =0;
            String[] gameteam1 = new String[number-1];
            for(int i = 0; i<number-1;i++){
                if(xxx == inteam){
                    xxx++;
                }
                gameteam1[i] = gameteam[xxx];
                xxx++;
            }
            for(int i = 0; i < number-1;i++){
                if(al.inCut((number-1)*(number-2)/2 +i)){
                    myqueue.enqueue(gameteam1[i]);
                }
            }
        }
        else {
            myqueue = null;
        }
        return sum!=al.value();
    }
    public Iterable<String> certificateOfElimination(String team){
        valide(team);
        int x = index.get(team);
        if(x == inteam){
            return myqueue;
        }
        else {
            isEliminated(team);
            return myqueue;
        }
    }
    public static void main(String[] args) {

    }
}
