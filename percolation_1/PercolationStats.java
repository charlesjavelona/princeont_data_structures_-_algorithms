import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;



public class PercolationStats {
    private double[] results;
    private int t; //trials
    private int n; //grid number
    
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int n, int t) {
        if (n <= 0 || t <= 0) {
           throw new java.lang.IllegalArgumentException();
        }
        
        this.n = n;
        this.t = t;
        double gridSize = n * n;
        results = new double[t];
        Percolation percolate;
            
        for (int s = 0; s < t; ++s) {
           percolate = new Percolation(n);
           
           int i = 0;
           int j = 0;
           int openNum = 0;
           
           while(!percolate.percolates()) {
             i = StdRandom.uniform(1, n + 1);
             j = StdRandom.uniform(1, n + 1);
             
             if(percolate.isOpen(i, j)) 
                continue;
             percolate.open(i, j);
             openNum ++;
             }
             results[s] = openNum/gridSize;
           }        
        }
    
    
    //Output percolation threshold(results)
    public double mean() {
       return StdStats.mean(results);  
    } 
    
     // sample standard deviation of percolation threshold
    public double stddev() {               
       return StdStats.stddev(results);
    }
    
     // low  endpoint of 95% confidence interval
    public double confidenceLo() {               
       return mean() - 1.96 * stddev() / Math.sqrt(t);
    }
    
    // high endpoint of 95% confidence interval
    public double confidenceHi() {                
       return mean() + 1.96 * stddev() / Math.sqrt(t);
    }
    
    public static void main(String[] args) {
      int n = Integer.parseInt(args[0]);
      int t = Integer.parseInt(args[1]);
      
      PercolationStats ps = new PercolationStats(n, t);
        StdOut.printf("mean                    = %f%n", ps.mean());
        StdOut.printf("stddev                  = %f%n", ps.stddev());
        StdOut.printf("95%% confidence interval = %f, %f%n", ps.confidenceLo(), ps.confidenceHi());
    }
}