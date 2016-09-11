import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    //Intialize private global variables
    private boolean [][] grid; // grid[i][j] == 0 for blocked, 1 for open element. 
    private WeightedQuickUnionUF union; // for percolates()
    private WeightedQuickUnionUF unionBack; // for percolates()
    private int s;
    private int n;
    
    /*Constructor 
     *  create n-by-n grid, with all sites blocked
     * 
     * @param n - input size of grid
     * 
     */
    public Percolation(int n) { 
        //Check to see input n is greater than 0
        if(n <= 0) {
          throw new IllegalArgumentException("n must be > 0");      
        } 
       
        //Create grid with all elements blocked
        grid = new boolean[n][n];
  
        //Nested for loop to fill the arrays
        //Prefix the increments
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
               grid[i][j] = false;
            }
        }
        
        this.n = n;
        int gridSize = n * n;
        s = gridSize + 2;
        union = new WeightedQuickUnionUF(s); //Create two virtual nodes, top and bottom;
        unionBack = new WeightedQuickUnionUF(gridSize + 1); // Top virtual node;
    
        for(int i = 0; i <= n; ++i) {
          union.union(0, i);
          union.union(s - 1, gridSize + 1 - i);
          unionBack.union(0, i);
          
        }      
    }
    
     // open site (row i, column j) if it is not open already
    public void open(int i, int j) {        
        isValid(i, j);
        
        grid[i - 1][j - 1] = true;
        int id = toIndex(i, j);
        
        if(i > 1 && isOpen(i - 1, j)) {
           union.union(toIndex(i - 1, j), id);
           unionBack.union(toIndex(i - 1, j), id);
        } 
        
        if(i < n && isOpen(i + 1, j)) {
            union.union(toIndex(i + 1, j), id);
            unionBack.union(toIndex(i + 1, j), id);
        }
        
        if(j > 1 && isOpen(i, j - 1)) {
            union.union(toIndex(i, j -1), id);
            unionBack.union(toIndex(i, j - 1), id);
        }
        
        if(j < n && isOpen(i, j + 1)) {
            union.union(toIndex(i, j + 1), id);
            unionBack.union(toIndex(i, j + 1), id);
        }
    }
        
    public boolean isOpen(int i, int j) {    // is site (row i, column j) open?
        isValid(i, j);
        return grid[i -1][j -1];
    }
    
    public boolean isFull(int i, int j) {    // is site (row i, column j) full?
         isValid(i, j);
         return isOpen(i, j) && unionBack.connected(toIndex(i, j), 0);
    }
    
    public boolean percolates() {         // does the system percolate?
        if(n == 1) 
            return isOpen(1, 1);
        return union.connected(0, s - 1);
    }
        
    private int toIndex(int i, int j) {
       return (i - 1) * n + j;
    
    }
    
    public void isValid(int i, int j) {
        if(i < 1 || i > n || j < 1 || j > n) {
           throw new java.lang.IndexOutOfBoundsException();
        }
    }
    
    public static void main(String[] args) {}  // test client (optional)
}