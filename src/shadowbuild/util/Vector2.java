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
        if(origin != null){
            x = origin.x;
            y = origin.y;
        }
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
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
        return Math.sqrt(subV.x * subV.x + subV.y * subV.y);
    }

    public Vector2 orientation(Vector2 v) {
        Vector2 subV = v.subtract(this);
        Double dist = Math.sqrt(subV.getX() * subV.getX() + subV.getY() * subV.getY());
        subV.x = subV.x/dist;
        subV.y = subV.y/dist;
        return subV;
    }

    public Vector2 move(Vector2 orientation, double distance) {
        return this.add(orientation.multiply(distance));
    }

    public Vector2 moveTowards(Vector2 destination, double distance) {
        if(distance(destination) > distance) {
            return move(orientation(destination), distance);
        } else {
            return new Vector2(destination);
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
