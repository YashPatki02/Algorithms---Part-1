/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {

        int currCount = 0;
        String currChampion = "";
        String currentString;

        while (!StdIn.isEmpty()) {
            currentString = StdIn.readString();
            currCount += 1;

            if (StdRandom.bernoulli(1/(double) currCount)) {
                currChampion = currentString;
            }
        }

        StdOut.print(currChampion);
    }
}

