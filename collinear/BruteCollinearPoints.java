import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private int numSegments = 0;
    private final ArrayList<LineSegment> segmentList = new ArrayList<>();

    // Finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Bad input.");
        }
        else {
            for (Point point: points) {
                if (point == null) {
                    throw new IllegalArgumentException("Null point.");
                }
            }

            if (hasDuplicates(points) == true) {
                throw new IllegalArgumentException("Duplicates.");
            }

            for (int i = 0; i < points.length - 3; i++) {
                for (int j = i+1; j < points.length - 2; j++) {
                    for (int k = j+1; k < points.length - 1; k++) {
                        for (int m = k+1; m < points.length; m++) {

                            double jSlope = points[i].slopeTo(points[j]);
                            double kSlope = points[i].slopeTo(points[k]);
                            double lSlope = points[i].slopeTo(points[m]);

                            if (jSlope == kSlope && jSlope == lSlope) {

                                Point[] linePoint = new Point[4];
                                linePoint[0] = points[i];
                                linePoint[1] = points[j];
                                linePoint[2] = points[k];
                                linePoint[3] = points[m];

                                Arrays.sort(linePoint);

                                segmentList.add(new LineSegment(linePoint[0], linePoint[3]));
                                numSegments++;
                            }
                        }
                    }
                }
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
