import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final double EPS = 0.000001;
    private List<LineSegment> lineSegments;

    private int totalCount;
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if(points == null) {
            throw new NullPointerException();
        }

        for(int i = 0; i < points.length; i++) {
            if(points[i] == null) {
                throw new NullPointerException();
            }
        }

        totalCount = 0;
        lineSegments = new ArrayList<>();

        for(int i = 0; i < points.length - 3; i++) {
            for(int j = i + 1; j < points.length - 2; j++) {
                for(int k = j + 1; k < points.length - 1; k++) {
                    for(int p = k + 1; p < points.length; p++) {
                        if(points[i].equals(points[j])) {
                            throw new IllegalArgumentException();
                        }

                        double firstSlope = points[i].slopeTo(points[j]);
                        double secondSlope = points[j].slopeTo(points[k]);
                        double thirdSlope = points[k].slopeTo(points[p]);
                        if(compareDoubles(firstSlope, secondSlope) && compareDoubles(secondSlope, thirdSlope)) {
                            lineSegments.add(constructSegmentFromPoints(points[i], points[j], points[k], points[p]));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[lineSegments.size()];
        lineSegments.toArray(result);
        return result;
    }

    private boolean compareDoubles(double a, double b) {
        return Math.abs(a - b) < EPS;
    }

    private LineSegment constructSegmentFromPoints(Point... points) {
        Arrays.sort(points);
        return new LineSegment(points[0], points[points.length - 1]);
    }
}