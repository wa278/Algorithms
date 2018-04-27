import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by wa941 on 2018/4/14.
 */
//public class Outcast {
//    private WordNet wordnet;
//    public Outcast(WordNet wordnet)  {
//        this.wordnet = wordnet;
//    }       // constructor takes a WordNet object
//    public String outcast(String[] nouns){
//        int[] number = new int[nouns.length];
//        int max = 0;
//        int maxnumber = number[0];
//        for(int i = 0; i< nouns.length;i++){
//            for(int j =0;j< nouns.length;j++){
//                if(i != j){
//                    number[i] += wordnet.distance(nouns[i],nouns[j]);
//                }
//            }
//        }
//        for(int i = 1;i< nouns.length;i++){
//            if(number[1] > maxnumber){
//                max = i;
//            }
//        }
//        return nouns[max];
//    }   // given an array of WordNet nouns, return an outcast
//    public static void main(String[] args){
//        WordNet wordnet = new WordNet(args[0], args[1]);
//        Outcast outcast = new Outcast(wordnet);
//        for (int t = 2; t < args.length; t++) {
//            In in = new In(args[t]);
//            String[] nouns = in.readAllStrings();
//            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
//        }
//    }  // see test client below
//}
public class Outcast {
    private WordNet wordNet;

    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    public String outcast(String[] nouns) {
        int[] distances = new int[nouns.length];
        for (int i = 0; i < nouns.length; i++) {
            String noun = nouns[i];
            for (int j = 0; j < nouns.length; j++) {
                if (i != j) {
                    distances[i] += wordNet.distance(noun, nouns[j]);
                }
            }
        }
        int max = 0;
        int id = 0;
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] > max) {
                max = distances[i];
                id = i;
            }
        }

        return nouns[id];
    }

}

