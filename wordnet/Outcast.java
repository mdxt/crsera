
/**
 *
 * @author maitr
 */
public class Outcast {
    private final WordNet wordnet;
    public Outcast(WordNet wrdnet) {
        wordnet = wrdnet;
    }// constructor takes a WordNet object
    
   public String outcast(String[] nouns){
       int t=Integer.MAX_VALUE, k;
       String s="";
       for(String i :nouns){
           k=0;
           for(String j:nouns){
               if(!i.equals(j)){
                   k+= wordnet.distance(i, j);
               }
           }
           if(k<t){
                       s=i;
                       t=k;
                   }
       }
       return s;
   }// given an array of WordNet nouns, return an outcast
   
   public static void main(String[] args) {
       
   }
}
