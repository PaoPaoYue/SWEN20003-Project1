package shadowbuild.util;

import org.lwjgl.Sys;

public class Vector2 {

    public static final Vector2 IDENTITY = new Vector2(0, 0);
    public static final Vector2 UP = new Vector2(0, -1);
    public static final Vector2 DOWN = new Vector2(0, 1);
    public static final Vector2 RIGHT = new Vector2(1, 0);
    public static final Vector2 LEFT = new Vector2(-1, 0);

    private double x;
    private double y;

    public Vector2() {
        x = 0;
        y = 0;
    }

    public Vector2(Vector2 origin) {
        x = origin.x;
        y = origin.y;
    }

    public Vector2(double x, double y) {
        setX(x);
        setY(y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vector2 subtract(Vector2 v) {
        return new Vector2(this.x - v.x, this.y - v.y);
    }

    public Vector2 add(Vector2 v) {
        return new Vector2(this.x + v.x, this.y + v.y);
    }

    public Vector2 multiply(double a) {
        return new Vector2(a*x, a*y);
    }

    public Double distance(Vector2 v) {
        Vector2 subV = subtract(v);
        return Math.sqrt(subV.getX() * subV.getX() + subV.getY() * subV.getY());
    }

    public Vector2 orientation(Vector2 v) {
        Vector2 subV = v.subtract(this);
        Double dist = Math.sqrt(subV.getX() * subV.getX() + subV.getY() * subV.getY());
        subV.setX(subV.getX()/dist);
        subV.setY(subV.getY()/dist);
        return subV;
    }

    public void move(Vector2 orientation, double distance) {
        Vector2 res = this.add(orientation.multiply(distance));
        x = res.x;
        y = res.y;
    }

    public void moveTowards(Vector2 destination, double distance) {
        if(distance(destination) > distance) {
            move(orientation(destination), distance);
        } else {
            x = destination.x;
            y = destination.y;
        }
    }

    public boolean collide(Vector2 v) {
        return x == v.x && y == v.y;
    }

    public  boolean collide(Rect r) {
        return x >= r.getX() && x <= r.getMaxX()
                && y >= r.getY() && y <= r.getMaxY();
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Vector2)
            return x == ((Vector2) obj).x && y == ((Vector2) obj).y;
        return false;
    }
}
