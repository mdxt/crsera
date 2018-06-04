
import edu.princeton.cs.algs4.Picture;
import java.util.Arrays;

/**
 *
 * @author maitr
 */


public class SeamCarver { 
//    r = (rgb >> 16) & 0xFF;
//          g = (rgb >>  8) & 0xFF;
//          b = (rgb >>  0) & 0xFF;
    
    private Picture p;
    private double[][] pixels;
    
    private int[] sqdiffx(int x, int y){
        int[] r = new int[3];
        int cola = p.getRGB(x-1, y), colb = p.getRGB(x+1, y);
        r[0] = ((colb >> 16) & 0xFF) - ((cola >> 16) & 0xFF);
        r[1] = ((colb >> 8) & 0xFF) - ((cola >> 8) & 0xFF);
        r[2] = ((colb >> 0) & 0xFF) - ((cola >> 0) & 0xFF);
        r[0]*=r[0];
        r[1]*=r[1];
        r[2]*=r[2];
        return r;
    }
    
    private int[] sqdiffy(int x, int y){
        int[] r = new int[3];
        int cola = p.getRGB(x, y-1), colb = p.getRGB(x, y+1);
        r[0] = ((colb >> 16) & 0xFF) - ((cola >> 16) & 0xFF);
        r[1] = ((colb >> 8) & 0xFF) - ((cola >> 8) & 0xFF);
        r[2] = ((colb >> 0) & 0xFF) - ((cola >> 0) & 0xFF);
        r[0]*=r[0];
        r[1]*=r[1];
        r[2]*=r[2];
        return r;
    }
    
       public SeamCarver(Picture pic){
           p=pic;
           pixels = new double[p.width()][p.height()];
           int[][] col = new int[p.width()*p.height()][3];
           int[] dx, dy;
           for(int i=0; i<p.width(); i++){
               for(int j=0; j<p.height(); j++){
                   if(i==0 || i == p.width()-1 || j==0 || j == p.height()-1){
                       pixels[i][j] = 1000.0;
                   } else {
                      dx = sqdiffx(i, j);
                      dy = sqdiffy(i, j);
                      pixels[i][j] = Math.sqrt(dx[0]+dx[1]+dx[2]+dy[0]+dy[2]+dy[1]);
                   }
               }
           }
           
       } // create a seam carver object based on the given picture
       
   public Picture picture(){
   return p; 
   } // current picture
   
   public     int width(){
       return p.width();
   } // width of current picture
   
   public     int height(){
       return p.height();
   }// height of current picture
   
   public  double energy(int x, int y){
       return pixels[x][y]; 
   } // energy of pixel at column x and row y
    
   public   int[] findHorizontalSeam(){
       int[] res = new int[p.width()];
       double[][] distTo = new double[p.width()][p.height()];
       int[][] parent = new int[p.width()][p.height()];
       int endloc=0;
       double temptotal=Double.MAX_VALUE;
       
       for(int i=0; i<p.width();i++) Arrays.fill(distTo[i], Double.MAX_VALUE);
       
       for(int i=0; i<p.width(); i++){
           for(int j=0; j<p.height(); j++){
               if(i==0){ 
                   distTo[i][j] = 0.0;
                   parent[i][j] = j;
               }
               if(i<p.width()-1){
               for(int k=j-1; k<=j+1; k++){
                 if(k>=0 && k<p.height() && distTo[i+1][k] > distTo[i][j] + pixels[i+1][k]){
                   distTo[i+1][k] = distTo[i][j] + pixels[i+1][k];
                   parent[i+1][k] = j;
                 }
               } } else{
                   if(distTo[i][j] < temptotal){
                       temptotal = distTo[i][j];
                       endloc = j; 
                   }
               }
           }
       }
       res[p.width()-1] = endloc;
       for(int i = p.width()-1; i>0; i--){
           res[i-1] = parent[i][res[i]];
       }
       return res;
   }// sequence of indices for horizontal seam
   
   public   int[] findVerticalSeam(){
       Picture temp = p;
       double[][] ptemp = pixels;
       p = new Picture(p.height(), p.width());
       pixels = new double[temp.height()][temp.width()];
       for(int i=0; i<temp.width(); i++){
           for(int j=0; j<temp.height(); j++){
           p.set(j, i, temp.get(i, j));
           pixels[j][i] = ptemp[i][j];
       }
       }
       int[] res = findHorizontalSeam();
       p = temp;
       pixels = ptemp;
       return res;
   } // sequence of indices for vertical seam
   
   public    void removeHorizontalSeam(int[] seam){
       double[][] tempix = new double[p.width()][p.height()-1];
       Picture temp = new Picture(p.width(), p.height()-1);
       for(int i=0; i<p.width(); i++){
           for(int j=0; j<p.height()-1; j++){
               if(j<seam[i]){
                   tempix[i][j] = pixels[i][j];
                   temp.set(i, j, p.get(i, j));
               }
               else{
                   tempix[i][j] = pixels[i][j+1];
                   temp.set(i, j, p.get(i, j+1));
               }
           }
       }
       p = temp;
       pixels = tempix;
   }// remove horizontal seam from current picture
   
   public    void removeVerticalSeam(int[] seam){
       double[][] tempix = new double[p.width()-1][p.height()];
       Picture temp = new Picture(p.width()-1, p.height());
       for(int i=0; i<p.height(); i++){
           for(int j=0; j<p.width()-1; j++){
               if(j<seam[i]){
                   tempix[j][i] = pixels[j][i];
                   temp.set(j, i, p.get(j, i));
               }
               else{
                   tempix[j][i] = pixels[j+1][i];
                   temp.set(j, i, p.get(j+1, i));
               }
           }
       }
       p = temp;
       pixels = tempix;
   }// remove vertical seam from current picture
}
