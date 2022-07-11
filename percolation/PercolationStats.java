/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private final int gridSize;
    private double[] stats;
    private final int numTrials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Bad Input!");
        }

        Percolation perc;
        numTrials = trials;
        gridSize = n;
        int totalSites = gridSize * gridSize;
        stats = new double[trials];

        int index;
        int trial = 0;

        int currRow;
        int currCol;

        while (trial < trials) {
            perc = new Percolation(n);

            while (!perc.percolates()) {
                index = StdRandom.uniform(totalSites);

                currRow = mapIndexToRow(index);
                currCol = mapIndexToCol(index);

                if (!perc.isOpen(currRow + 1, currCol + 1)) {
                    perc.open(currRow + 1, currCol + 1);
                }
            }

            stats[trial] = (double) perc.numberOfOpenSites()/ totalSites;
            trial++;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(stats);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (numTrials == 1) {
            return Double.NaN;
        }
        else {
            return StdStats.stddev(stats);
        }
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev())/Math.sqrt(numTrials));

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev())/Math.sqrt(numTrials));
    }

    public static void main(String[] args) {

        PercolationStats percStat = new PercolationStats(Integer.parseInt(args[0]),
                                                         Integer.parseInt(args[1]));

        double mean = percStat.mean();
        double stddev = percStat.stddev();
        double conLo = percStat.confidenceLo();
        double conHi = percStat.confidenceHi();

        System.out.println("mean \t \t \t \t \t \t = " + mean);
        System.out.println("stddev \t \t \t \t \t \t = " + stddev);
        System.out.println("95% confidence interval \t = [" + conLo + ", " + conHi + "]" );
    }


    private int mapIndexToRow(int index) {
        return index / gridSize;
    }

    private int mapIndexToCol(int index) {
        return index % gridSize;
    }


}
