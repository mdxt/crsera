
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 * @author maitr
 */

public class SAP {
   private final Digraph grp;
   
    // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G){
       if(G == null) throw new java.lang.IllegalArgumentException();
       grp=G;
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w){
       if(v<0 || v>=grp.V()) throw new java.lang.IllegalArgumentException();
       if(w<0 || w>=grp.V()) throw new java.lang.IllegalArgumentException();
       int res = Integer.MAX_VALUE;
       BreadthFirstDirectedPaths pV, pW;
       pV = new BreadthFirstDirectedPaths(grp, v);
       pW = new BreadthFirstDirectedPaths(grp, w);
              
       for(int i=0; i<grp.V(); i++){
           if(pV.hasPathTo(i) && pW.hasPathTo(i)){
               if(pV.distTo(i) + pW.distTo(i) < res){
                   res = pV.distTo(i) + pW.distTo(i);
               }
           }
       }
       if(res < Integer.MAX_VALUE) return res;
       return -1;
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w){
       if(v<0 || v>=grp.V()) throw new java.lang.IllegalArgumentException();
       if(w<0 || w>=grp.V()) throw new java.lang.IllegalArgumentException();
       int minD = Integer.MAX_VALUE, res=-1;
       BreadthFirstDirectedPaths pV, pW;
       pV = new BreadthFirstDirectedPaths(grp, v);
       pW = new BreadthFirstDirectedPaths(grp, w);
              
       for(int i=0; i<grp.V(); i++){
           if(pV.hasPathTo(i) && pW.hasPathTo(i)){
               if(pV.distTo(i) + pW.distTo(i) < minD){
                   res = i;
                   minD = pV.distTo(i) + pW.distTo(i);
               }
           }
       }
     return res;  
   }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w){
       if(v == null || w == null) throw new java.lang.IllegalArgumentException();
       int res = Integer.MAX_VALUE,t;
       BreadthFirstDirectedPaths pV, pW;
       pV = new BreadthFirstDirectedPaths(grp, v);
       pW = new BreadthFirstDirectedPaths(grp, w);
              
       for(int i=0; i<grp.V(); i++){
           if(pV.hasPathTo(i) && pW.hasPathTo(i)){
               if(pV.distTo(i) + pW.distTo(i) < res){
                   res = pV.distTo(i) + pW.distTo(i);
               }
           }
       }
       if(res < Integer.MAX_VALUE) return res;
       return -1;
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
       if(v == null || w == null) throw new java.lang.IllegalArgumentException();
       int minD = Integer.MAX_VALUE, res=-1;
       BreadthFirstDirectedPaths pV, pW;
       pV = new BreadthFirstDirectedPaths(grp, v);
       pW = new BreadthFirstDirectedPaths(grp, w);
              
       for(int i=0; i<grp.V(); i++){
           if(pV.hasPathTo(i) && pW.hasPathTo(i)){
               if(pV.distTo(i) + pW.distTo(i) < minD){
                   res = i;
                   minD = pV.distTo(i) + pW.distTo(i);
               }
           }
       }
     return res;  
   }

   // do unit testing of this class
   public static void main(String[] args){
       In in = new In("src//input10_1_1.txt");
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    while (!StdIn.isEmpty()) {
        int v = StdIn.readInt();
        int w = StdIn.readInt();
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor); //81679 24306
    }
   }
}
