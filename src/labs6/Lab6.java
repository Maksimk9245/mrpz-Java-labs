import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Point {
    double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

class Triangle {
    Point p1, p2, p3;
    double a, b, c;

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;

        this.a = p1.distanceTo(p2);
        this.b = p2.distanceTo(p3);
        this.c = p3.distanceTo(p1);
    }

    public boolean isValid() {
        return (a + b > c) && (a + c > b) && (b + c > a);
    }

    public void determineType() {
        if (!isValid()) {
            System.out.println("Трикутник з вершинами " + p1 + ", " + p2 + ", " + p3 + " не існує.");
            return;
        }

        double epsilon = 1e-6;
        boolean isEquilateral = Math.abs(a - b) < epsilon && Math.abs(b - c) < epsilon;
        boolean isIsosceles = Math.abs(a - b) < epsilon || Math.abs(b - c) < epsilon || Math.abs(a - c) < epsilon;

        boolean isRight = Math.abs(a * a + b * b - c * c) < epsilon ||
                Math.abs(a * a + c * c - b * b) < epsilon ||
                Math.abs(b * b + c * c - a * a) < epsilon;

        System.out.print("Трикутник " + p1 + ", " + p2 + ", " + p3 + " -> ");

        if (isEquilateral) {
            System.out.print("Рівносторонній ");
        } else if (isIsosceles) {
            System.out.print("Рівнобедрений ");
        } else {
            System.out.print("Різносторонній ");
        }

        if (isRight) {
            System.out.print("та Прямокутний");
        }

        System.out.println();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        Set<Point> thisPoints = new HashSet<>();
        thisPoints.add(p1);
        thisPoints.add(p2);
        thisPoints.add(p3);

        Set<Point> otherPoints = new HashSet<>();
        otherPoints.add(triangle.p1);
        otherPoints.add(triangle.p2);
        otherPoints.add(triangle.p3);

        return thisPoints.equals(otherPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p1, p2, p3);
    }
}

public class Lab6 {
    public static void main(String[] args) {
        Set<Triangle> triangles = new HashSet<>();

        triangles.add(new Triangle(new Point(0, 0), new Point(0, 3), new Point(4, 0)));
        triangles.add(new Triangle(new Point(0, 0), new Point(3, 0), new Point(1.5, 2.598076)));
        triangles.add(new Triangle(new Point(0, 0), new Point(2, 0), new Point(1, 3)));
        triangles.add(new Triangle(new Point(0, 0), new Point(4, 1), new Point(1, 5)));
        triangles.add(new Triangle(new Point(0, 0), new Point(0, 3), new Point(4, 0)));

        System.out.println("=== Аналіз множини трикутників ===");
        System.out.println("Кількість унікальних трикутників у множині: " + triangles.size());
        System.out.println("----------------------------------");

        for (Triangle t : triangles) {
            t.determineType();
        }
    }
}