import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    // constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {
        StdDraw.point(x, y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that) {
        int res = this.x - that.x;
        if(res != 0) {
            return res;
        }
        return this.y - that.y;
    }

    // the slope between this point and that point
    public double slopeTo(Point that) {
        if(this.x - that.x == 0) {
            return Double.POSITIVE_INFINITY;
        } else if(this.y - that.y == 0) {
            return 0;
        }
        return  (this.y - that.y) / (double)(this.x - that.x);
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new PointComparator();
    }

    private class PointComparator implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            double diff = slopeTo(o1) - slopeTo(o2);
            if(diff < 0) return -1;
            if(diff > 0) return 1;
            return 0;
        }
    }

    public static void main(String[] args) {
        Point[] points = { new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(0, 0), new Point(3, 6), new Point(6, 9), };
        BruteCollinearPoints pts = new BruteCollinearPoints(points);
        System.out.println(pts.numberOfSegments());
    }
}