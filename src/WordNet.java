import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

/**
 * Created by wa941 on 2018/4/14.
 */
public class WordNet {
    private SeparateChainingHashST<String,Integer> hash;
    private SeparateChainingHashST<Integer,String> list;
    private Digraph graph;
    private SAP sap;

    public WordNet(String synsets, String hypernyms){
        list = new SeparateChainingHashST<>();
        hash = new SeparateChainingHashST<>();
        In in = new In(synsets);
        while (in.hasNextLine()){
            String a = in.readLine();
            String[] b = a.split(",");
            String[] c = b[1].split(" ");
            for(String e : c){
                hash.put(e,Integer.parseInt(b[0]));
            }
            list.put(Integer.parseInt(b[0]),b[1]);
        }
        in.close();
        In input = new In(hypernyms);
        graph = new Digraph(list.size());
        while (input.hasNextLine()){
            String[] a = input.readLine().split(",");
            for(int i = 1;i<a.length;i++){
                graph.addEdge(Integer.parseInt(a[0]),Integer.parseInt(a[i]));
            }
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return hash.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if(word == null){
            throw new IllegalArgumentException();
        }
        return hash.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if( nounA == null || nounB == null ){
            throw new IllegalArgumentException();
        }
        if(!isNoun(nounA)|| !isNoun(nounB)){
            throw new IllegalArgumentException();
        }
        if(sap == null){
            sap = new SAP(graph);
        }
        return sap.length(hash.get(nounA),hash.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if( nounA == null || nounB == null ){
            throw new IllegalArgumentException();
        }
        if(!isNoun(nounA)|| !isNoun(nounB)){
            throw new IllegalArgumentException();
        }
        if(sap == null){
            sap = new SAP(graph);
        }
        return list.get(sap.ancestor(hash.get(nounA),hash.get(nounB)));
    }

    // do unit testing of this class
    public static void main(String[] args){

    }
}
