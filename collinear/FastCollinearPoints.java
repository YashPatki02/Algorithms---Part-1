import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private int numSegments = 0;
    private final ArrayList<LineSegment> segmentList = new ArrayList<>();

    // Finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Bad Input.");
        }

        for (Point point: points) {
            if (point == null) {
                throw new IllegalArgumentException("Null point.");
            }
        }

        if (hasDuplicates(points) == true) {
            throw new IllegalArgumentException("Duplicates.");
        }

        Point[] copy = points.clone();
        Arrays.sort(copy);

        for (int i = 0; i < copy.length - 3; i++) {
            Arrays.sort(copy);

            Arrays.sort(copy, copy[i].slopeOrder());

            for (int curr = 0, first = 1, last = 2; last < copy.length; last++) {
                while (last < copy.length && Double.compare(copy[curr].slopeTo(copy[first]),
                                      copy[curr].slopeTo(copy[last])) == 0) {
                    last++;
                }

                if (last - first >= 3 && copy[curr].compareTo(copy[first]) < 0) {
                    segmentList.add(new LineSegment(copy[curr], copy[last-1]));
                    numSegments++;
                }

                first = last;
            }
        }
    }

    private boolean hasDuplicates(Point[] points) {
        Point[] copy = points.clone();
        Arrays.sort(copy);

        for (int i = 1; i < points.length; i++) {
            if (points[i] == points[i-1]) {
                return true;
            }
        }

        return false;
    }

    // The number of line segments
    public int numberOfSegments() {
        return numSegments;
    }

    // The line segments
    public LineSegment[] segments() {
        LineSegment[] segment = new LineSegment[segmentList.size()];
        return segmentList.toArray(segment);
    }
}
