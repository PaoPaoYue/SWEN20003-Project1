package shadowbuild.util;

/**
 * Standard class to represent a 2D vector or position
 * All method should not modify the vector itself
 */
public class Vector2 {
    /** Standard unit vectors in common use */
    public static final Vector2 IDENTITY = new Vector2(0, 0);
    public static final Vector2 UP = new Vector2(0, -1);
    public static final Vector2 DOWN = new Vector2(0, 1);
    public static final Vector2 RIGHT = new Vector2(1, 0);
    public static final Vector2 LEFT = new Vector2(-1, 0);

    private double x;
    private double y;

    public Vector2() {
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

    /** Subtract a vector from this vector */
    public Vector2 subtract(Vector2 v) {
        return new Vector2(this.x - v.x, this.y - v.y);
    }

    /** Add a vector to this vector */
    public Vector2 add(Vector2 v) {
        return new Vector2(this.x + v.x, this.y + v.y);
    }

    /** Multiply this vector by a constant */
    public Vector2 multiply(double a) {
        return new Vector2(a*x, a*y);
    }

    /** Calculate the distance from this position to another */
    public double distance(Vector2 v) {
        Vector2 subV = subtract(v);
        return Math.sqrt(subV.x * subV.x + subV.y * subV.y);
    }

    /**
     * Calculate orientation from this position pointing to another
     * Return a standard unit vector
     */
    public Vector2 orientation(Vector2 v) {
        Vector2 subV = v.subtract(this);
        Double dist = Math.sqrt(subV.getX() * subV.getX() + subV.getY() * subV.getY());
        subV.x = subV.x/dist;
        subV.y = subV.y/dist;
        return subV;
    }

    /**
     *  Calculate the next position by moving the position a certain distance in that direction
     *  orientation must be a standard unit vector
     */
    public Vector2 move(Vector2 orientation, double distance) {
        return this.add(orientation.multiply(distance));
    }

    /**
     * Calculate the next position for each step to the destination
     * Clamp to prevent it go beyond the destination
     */
    public Vector2 moveTowards(Vector2 destination, double distance) {
        if(distance(destination) > distance) {
            return move(orientation(destination), distance);
        } else {
            return new Vector2(destination);
        }
    }

    /** Detect collide with another position */
    public boolean collide(Vector2 v) {
        return x == v.x && y == v.y;
    }

    /** Detect collide with a rectangle */
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
        if(obj == null) return false;
        if(obj == this) return true;
        if(obj.getClass() == Vector2.class)
            return x == ((Vector2) obj).x && y == ((Vector2) obj).y;
        return false;
    }
}
