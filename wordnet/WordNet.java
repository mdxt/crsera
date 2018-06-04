
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import java.util.ArrayList;

/**
 *
 * @author maitr
 */

public class WordNet {
    // constructor takes the name of the two input files
   private Digraph dig;
   private ST<Integer, String> SynEntry; 
   private ST<String, ArrayList<Integer>> NounEntry; 
   private SAP anc;
   
    
   public WordNet(String synsets, String hypernyms){
       if(synsets==null || hypernyms==null) throw new java.lang.IllegalArgumentException();
       In syn = new In(synsets), hyp = new In(hypernyms);
       SynEntry = new ST<>();
       NounEntry = new ST<>();
       String line;
       ArrayList<Integer> temp;
       while(syn.hasNextLine()){
           line = syn.readLine();
           String[] prts = line.split(",");
           int id = Integer.parseInt(prts[0]);
           
          // System.out.println(id+" "+prts[1]);
           SynEntry.put(id, prts[1]);
           String[] nouns = prts[1].split(" ");
           
           for(String i:nouns){
                if(NounEntry.contains(i)) NounEntry.get(i).add(id);
                else{
                    temp = new ArrayList<>();
                    temp.add(id);
                    NounEntry.put(i, temp);
                }
           }
           //System.out.println(id+" "+Arrays.toString(nouns)+" "+gloss);
       }
       dig = new Digraph(SynEntry.size());
       if(!check(dig)) throw new java.lang.IllegalArgumentException();
       
       while(hyp.hasNextLine()){
           line = hyp.readLine();
           String[] prts = line.split(",");
           int t = Integer.parseInt(prts[0]);
           for(int i=1; i< prts.length; i++){
               dig.addEdge(t, Integer.parseInt(prts[i]));
           }
       }
       
       anc = new SAP(dig);
   }
   
   private boolean check(Digraph d){
       DirectedCycle dc  = new DirectedCycle(d);
       if(dc.hasCycle()) return false;
       boolean sw = false;
       for(int i=0; i<d.V(); i++){
           if(d.outdegree(i)==0) sw=true;
       }
       return sw;
   }

   // returns all WordNet nouns
   public Iterable<String> nouns(){
      return NounEntry.keys();
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word){
       if(word==null) throw new java.lang.IllegalArgumentException();
       return NounEntry.contains(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB){
       if(!(isNoun(nounB) || isNoun(nounA))) throw new java.lang.IllegalArgumentException();
       return anc.length(NounEntry.get(nounA), NounEntry.get(nounB));
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB){
       if(!(isNoun(nounB) || isNoun(nounA))) throw new java.lang.IllegalArgumentException();
       return SynEntry.get(anc.ancestor(NounEntry.get(nounA), NounEntry.get(nounB)));
   }

   // do unit testing of this class
   public static void main(String[] args){
       WordNet a = new WordNet("src//input10.txt", "src//input10_1.txt");
       System.out.println(a.distance("mileage", "white_marlin"));
   }
}
