
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private Stack<Board> st;
    private int m;
    private boolean solvable;
    
    private class Node implements Comparable<Node>{
        private final Board board;
        private int moves;
        private final Node pred;
        private final int manhattan;
        private final int hamming;
        
        Node(Board a, int b, Node c){
            board = a;
            moves = b;
            pred = c;
            manhattan = a.manhattan()+b;
            hamming = a.hamming()+b;
        }
        
        @Override
        public int compareTo(Node o) {
            if(o.manhattan > this.manhattan){
                return -1;
            }
            else if(o.manhattan == this.manhattan){
                    if(o.hamming > this.hamming){
                        return -1;
                    }
                return 0;
            }
            return 1;
        }
    }
    
    public Solver(Board initial){
        if(initial==null){ throw new java.lang.IllegalArgumentException();}
        MinPQ<Node> q = new MinPQ<>(), q2 = new MinPQ<>();
        st = new Stack<>();
        m=0;
        Node nd = new Node(initial, 0, null), nd2= new Node(initial.twin(), 0, null);
        
        q.insert(nd);
        q2.insert(nd2);
        Iterable<Board> it;
        
        while(!(nd.board.isGoal() || nd2.board.isGoal())){
            nd = q.delMin();
            nd2 = q2.delMin();
            m++;
            it = nd.board.neighbors();
            for(Board i : it){
                if(nd.pred != null && i.equals(nd.pred.board)){ continue; }
                q.insert(new Node(i, m, nd));
            }
            
            it = nd2.board.neighbors();
            for(Board i : it){
                if(nd2.pred != null && i.equals(nd2.pred.board)){ continue; }
                q2.insert(new Node(i, m, nd2));
            }
        }
        st = new Stack<>();
        solvable = true;
        while(nd!=null){
            st.push(nd.board);
            nd = nd.pred;
        }
    }
 public boolean isSolvable(){
        return solvable;
    }// is the initial board solvable?
    
    public int moves(){
        return st.size()-1;
    }// min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution(){
        if(!solvable){return null;}
        return st;
    }// sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args){
        // create initial board from file
    In in = new In("src//input10.txt");
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
   
        StdOut.println(solver.isSolvable()+" Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    
    }// solve a slider puzzle (given below)
}   

