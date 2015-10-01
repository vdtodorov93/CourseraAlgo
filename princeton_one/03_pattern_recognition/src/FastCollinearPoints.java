import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final double EPS = 0.000001;

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
        lineSegments = new ArrayList<>();

        for(int i = 0; i < points.length - 3; i++) {
            Point basePoint = points[i];
            Point[] copy = Arrays.copyOfRange(points, i + 1, points.length);
            Arrays.sort(copy, basePoint.slopeOrder());
            for(int j = 0; j < copy.length - 1; j++) {
                int rememberj = j;
                while(j < copy.length - 1 && compareDoubles(basePoint.slopeTo(points[j]), basePoint.slopeTo(points[j + 1]))) {
                    j++;
                }
                if(j - rememberj > 1) {
                    lineSegments.add(constructSegmentFromPoints(basePoint, Arrays.copyOfRange(copy, rememberj, j + 1) ));
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

    private LineSegment constructSegmentFromPoints(Point basePoint, Point[] points) {
        List<Point> allPoints = new ArrayList<Point>(Arrays.asList(points));
        allPoints.add(basePoint);
        points = new Point[allPoints.size()];
        allPoints.toArray(points);
        Arrays.sort(points);
        return new LineSegment(points[0], points[points.length - 1]);
    }

    private boolean compareDoubles(double a, double b) {
        return Math.abs(a - b) < EPS;
    }
}