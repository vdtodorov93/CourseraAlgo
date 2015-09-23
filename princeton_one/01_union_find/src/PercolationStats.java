import edu.princeton.cs.algs4.*;

import java.util.Arrays;
import java.util.Random;

public class PercolationStats {
    private double[] result;
    private int N;
    private int T;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        this.N = N;
        this.T = T;
        result = new double[T];
        for(int i = 0; i < T; i++) {
            result[i] = test();
        }
        Arrays.sort(result);
        for(double r: result) {
            System.out.print(r + " ");
        }
        System.out.println();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(result);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(result);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        //System.out.println(T / 20 + " " + T * 19 / 20);
        return StdStats.min(result, T / 20, T * 19 / 20);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return StdStats.max(result, T / 20, T * 19 / 20);
    }

    // test client (described below)
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }

    private double test() {
        Percolation p = new Percolation(N);
        int opened = 0;
        while(!p.percolates()) {
            int i = StdRandom.uniform(1, N);
            int j = StdRandom.uniform(1, N);
            if(!p.isOpen(i, j)) {
                p.open(i, j);
                opened++;
            }
        }

        return opened / ((double) N * N);
    }
}
