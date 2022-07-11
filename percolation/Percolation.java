/* *****************************************************************************
 *  Name:              Yash Pakti
 *  Last modified:     6/23/2022
 *
 * Compiling:
 * Executing:
 **************************************************************************** */


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[] openGrid;

    private int numOpenSites = 0;
    private final int gridSize;
    private final int totalSites;
    private final WeightedQuickUnionUF ufPerc;
    private final WeightedQuickUnionUF ufFull;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        gridSize = n;
        totalSites = gridSize*gridSize;
        openGrid = new boolean[totalSites];
        ufPerc = new WeightedQuickUnionUF(totalSites);
        ufFull = new WeightedQuickUnionUF(totalSites);

        for (int i = 0; i < totalSites; i++) {
                openGrid[i] = false;
        }

        // Connect top row in both UFs
        for (int idx = 1; idx < gridSize; idx++) {
            ufPerc.union(0, idx);
            ufFull.union(0, idx);
        }

        // Connect bottom row in ufPerc UF
        for (int idx = totalSites - gridSize + 1; idx < totalSites; idx++) {
            ufPerc.union(totalSites-gridSize, idx);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);

        int actualRow = row - 1;
        int actualCol = col - 1;

        int siteIndex = actualRow*gridSize + actualCol;
        int index;

        if (!isOpen(row, col)) {
            openGrid[actualRow * gridSize + actualCol] = true;
            numOpenSites++;
        }

        // Top Neighbor
        if (validSite(actualRow-1, actualCol)) {
            if (isOpen(row - 1, col)) {
                index = (actualRow-1)*gridSize + actualCol;
                ufPerc.union(siteIndex, index);
                ufFull.union(siteIndex, index);
            }
        }

        // Bottom Neighbor
        if (validSite(actualRow+1, actualCol)) {
            if (isOpen(row + 1, col)) {
                index = (actualRow+1)*gridSize + actualCol;
                ufPerc.union(siteIndex, index);
                ufFull.union(siteIndex, index);
            }
        }

        // Right Neighbor
        if (validSite(actualRow, actualCol+1)) {
            if (isOpen(row, col + 1)) {
                index = actualRow*gridSize + (actualCol+1);
                ufPerc.union(siteIndex, index);
                ufFull.union(siteIndex, index);
            }
        }

        // Left Neighbor
        if (validSite(actualRow, actualCol-1)) {
            if (isOpen(row, col - 1)) {
                index = actualRow*gridSize + (actualCol-1);
                ufPerc.union(siteIndex, index);
                ufFull.union(siteIndex, index);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int actualRow = row - 1;
        int actualCol = col - 1;

        return openGrid[actualRow*gridSize + actualCol];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);

        int actualRow = row - 1;
        int actualCol = col - 1;

        return isOpen(row, col) &&
                (ufFull.find(0) == ufFull.find(actualRow*gridSize + actualCol));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return ufPerc.find(0) == ufPerc.find(totalSites-1);
    }


    public static void main (String[] args) {}

    private void validate (int row, int col) {
        if (row <= 0 || row > gridSize) {
            throw new IllegalArgumentException("Row Index: " + row
                                                       + " out of bounds.");
        }
        else if (col <= 0 || col > gridSize) {
            throw new IllegalArgumentException("Col Index: " + col
                                                       + " out of bounds.");
        }
    }

    private boolean validSite (int row, int col) {
        if (row < 0 || col < 0 || row >= gridSize || col >= gridSize) {
            return false;
        }
        return true;
    }
}
