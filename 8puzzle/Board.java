
import edu.princeton.cs.algs4.Stack;
import java.util.Arrays;

public class Board {
    private int[][] brd;
    private int x,y,n, manhattan, hamming;
    
    public Board(int[][] blocks){
        brd = blocks;
        n = brd[0].length;
        int k=0,l=0;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(brd[i][j]!=0){
                    l+= Math.abs(((brd[i][j]-1)/n)-i) + Math.abs(((brd[i][j]-1)%n)-j);
                    if(brd[i][j] != ((i*n)+j+1)){
                    k++;
                    }
                } else{
                    x=i;
                    y=j;
                }
            }
        }
        manhattan = l;
        hamming = k;
    }// construct a board from an n-by-n array of blocks
     // (where blocks[i][j] = block in row i, column j)
    public int dimension(){
        return n;
    }// board dimension n
    
    public int hamming(){
        return hamming;
    }// number of blocks out of place
    
    public int manhattan(){
        return manhattan;
    }// sum of Manhattan distances between blocks and goal
    public boolean isGoal(){
        return hamming==0 || manhattan==0;
    }// is this board the goal board?
    public Board twin(){
        int[][] tmp = new int[n][];
            for(int i = 0; i < n; i++)
                tmp[i] = brd[i].clone();
        int a=-1, b=-1, c=-1, d=-1;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(tmp[i][j]!=0){
                    if(a<0){
                        a=i;
                        b=j;
                    } else if(c<0){
                        c=i;
                        d=j;
                    } else{
                        tmp[a][b] = tmp[a][b]+tmp[c][d];
                        tmp[c][d] = tmp[a][b]-tmp[c][d];
                        tmp[a][b] = tmp[a][b]-tmp[c][d];
                        return new Board(tmp);
                    }
                }
            }
        }        
        return null;
    }// a board that is obtained by exchanging any pair of blocks
    
    
    public boolean equals(Object other){
       if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Board that = (Board) other;
        return (Arrays.deepEquals(this.brd, that.brd));
    }// does this board equal y?
    
    public Iterable<Board> neighbors(){
        Stack<Board> st = new Stack<>();
        int[][] tmp;
        if(x+1<n){
            tmp = new int[n][];
            for(int i = 0; i < n; i++)
                tmp[i] = brd[i].clone();
            tmp[x][y] = tmp[x][y]+tmp[x+1][y];
           tmp[x+1][y] = tmp[x][y]-tmp[x+1][y];
           tmp[x][y] = tmp[x][y]-tmp[x+1][y];
           st.push(new Board(tmp));
        }
        if(x>0){
           tmp = new int[n][];
            for(int i = 0; i < n; i++)
                tmp[i] = brd[i].clone();
           
           tmp[x][y] = tmp[x][y]+tmp[x-1][y];
           tmp[x-1][y] = tmp[x][y]-tmp[x-1][y];
           tmp[x][y] = tmp[x][y]-tmp[x-1][y];
           st.push(new Board(tmp));
        }
        if(y+1<n){
           tmp = new int[n][];
            for(int i = 0; i < n; i++)
                tmp[i] = brd[i].clone();
           tmp[x][y] = tmp[x][y]+tmp[x][y+1];
           tmp[x][y+1] = tmp[x][y]-tmp[x][y+1];
           tmp[x][y] = tmp[x][y]-tmp[x][y+1];
           st.push(new Board(tmp));
        }
        if(y>0){
           tmp = new int[n][];
            for(int i = 0; i < n; i++)
                tmp[i] = brd[i].clone();
           tmp[x][y] = tmp[x][y]+tmp[x][y-1];
           tmp[x][y-1] = tmp[x][y]-tmp[x][y-1];
           tmp[x][y] = tmp[x][y]-tmp[x][y-1];
           st.push(new Board(tmp));
        }
        return st;
    }// all neighboring boards
    
    public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(n + "\n");
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            s.append(String.format("%2d ", brd[i][j]));
        }
        s.append("\n");
    }
    return s.toString();
}// string representation of this board (in the output format specified below)

    public static void main(String[] args){
    }// unit tests (not graded)
}
