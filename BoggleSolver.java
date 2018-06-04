
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 * @author maitr
 */
public class BoggleSolver {
    
    private class TrieSET3{       //mod. of main TrieSET with only necessary stuff
        private final int R=26;  //one branch for each possible letter
        
        private class TNode{
            boolean notBlank;
            TNode[] next = new TNode[R];
        }
        
        private TNode root;
        
        private boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        TNode x = get(root, key, 0);
        if (x == null) return false;
        return x.notBlank;
        }
    
        private boolean containsPrefix(String key){
        if (key == null) throw new IllegalArgumentException("argument to containsPrefix() is null");
        TNode x = get(root, key, 0);
        return x!=null;
        }
        
        private TNode get(TNode x, String key, int d){
            if (x == null) return null;
            if (d == key.length()) return x;
            char c = key.charAt(d);
            return get(x.next[c-'A'], key, d+1);
        }
        
        private void add(String key) {
        if (key == null) throw new IllegalArgumentException("argument to add() is null");
        root = add(root, key, 0);
        }

        private TNode add(TNode x, String key, int d) {
        if (x == null) x = new TNode();
        if (d == key.length()) {
           x.notBlank = true;
        }
        else {
            char c = key.charAt(d);
            x.next[c-'A'] = add(x.next[c-'A'], key, d+1);
        }
        return x;
        }
        
        /*
         * Thanks to vinsonlee and his repo for the c-'A' trick
         */      
    }
    
private TrieSET3 ts;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary){
        ts = new TrieSET3();
        for(String i : dictionary) ts.add(i);
    }

    
    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){
        SET<String> wrds = new SET<>();
        int rows = board.rows(), cols = board.cols();
        
        for(int i=0; i<rows; i++){
            for(int j=0; j<cols; j++){
                dfs(new boolean[rows][cols], i, j, "", board, wrds);
            }
        }
    
        return wrds;
    }

    private void dfs(boolean[][] vst, int px, int py, String cur, BoggleBoard board, SET<String> wrds){      
    if(!vst[px][py]){
    
        char l = board.getLetter(px, py);
        String next; 
        if(l=='Q'){ next = cur + "QU"; }
        else { next = cur + l; }
        
        if(ts.containsPrefix(next)){
        
             if(next.length()>2){
                 if(ts.contains(next)) wrds.add(next); 
             } 
            vst[px][py] = true;
        
            boolean x1 = (px==0), x2 = (px==board.rows()-1),y1 = (py==0), y2 = (py==board.cols()-1);
            if(!y1){    dfs(vst, px, py-1, next, board, wrds); //left
                if(!x1) dfs(vst, px-1, py-1, next, board, wrds);//up
                if(!x2) dfs(vst, px+1, py-1, next, board, wrds);//down
            }
            if(!y2){    dfs(vst, px, py+1, next, board, wrds); //right
                if(!x1) dfs(vst, px-1, py+1, next, board, wrds); //up
                if(!x2) dfs(vst, px+1, py+1, next, board, wrds); //down
            }
                if(!x1) dfs(vst, px-1, py, next, board, wrds);//up
                if(!x2) dfs(vst, px+1, py, next, board, wrds); //down 
        
            vst[px][py] = false;
            }
        }
    }
    
    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){
        int l = word.length();
        if(l<3) return 0;
        if(l<=4) return 1;
        if(l<=6) return l-3;
        if(l==7) return 5;
        return 11;
    }

    public static void main(String[] args) {
    In in = new In("src//input10_1_1.txt");
    String[] dictionary = in.readAllStrings();
    BoggleSolver solver = new BoggleSolver(dictionary);
    BoggleBoard board = new BoggleBoard("src//input10_1.txt");
    int score = 0;
    for (String word : solver.getAllValidWords(board)) {
        StdOut.println(word);
        score += solver.scoreOf(word);
    }
    StdOut.println("Score = " + score);
}
}

       
    

