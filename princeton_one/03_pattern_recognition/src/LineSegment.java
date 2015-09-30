import edu.princeton.cs.algs4.StdDraw;

public class LineSegment {

    private Point p1;
    private Point p2;
    // constructs the line segment between points p and q
    public LineSegment(Point p, Point q) {
        if (p == null || q == null) {
            throw new NullPointerException("argument is null");
        }
        p1 = p;
        p2 = q;
    }

    // draws this line segment
    public void draw() {
        p1.drawTo(p2);
    }

    // string representation
    public String toString() {
        return p1 + " -> " + p2;
    }

    public int hashCode() {
        throw new UnsupportedOperationException();
    }
}