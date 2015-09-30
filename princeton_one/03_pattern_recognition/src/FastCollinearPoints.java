import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private List<LineSegment> lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if(points == null) {
            throw new NullPointerException();
        }

        for(int i = 0; i < points.length; i++) {
            if(points[i] == null) {
                throw new NullPointerException();
            }
        }

        for(int i = 0; i < points.length - 3; i++) {
            Point basePoint = points[i];
            Point[] copy = Arrays.copyOfRange(points, i + 1, points.length);
            Arrays.sort(copy, basePoint.slopeOrder());
            for(int j = 0; j < copy.length; j++) {
                
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return 0;
    }

    // the line segments
    public LineSegment[] segments() {
        return null;
    }

    private LineSegment constructSegmentFromPoints(Point... points) {
        Arrays.sort(points);
        return new LineSegment(points[0], points[points.length - 1]);
    }
}