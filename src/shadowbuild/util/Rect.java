package shadowbuild.util;

/**
 * Standard class to represent a rectangle
 * With the top-left corner position recorded as x,y
 */
public class Rect {
    private double x;
    private double y;
    private double width;
    private double height;

    public Rect() {
    }

    public Rect(Rect rect) {
        if(rect != null){
            x = rect.x;
            y = rect.y;
            width = rect.width;
            height = rect.height;
        }
    }

    public Rect(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getMaxX() {
        return x + width;
    }

    public void setMaxX(double maxX) {
        this.x = maxX - width;
    }

    public double getMaxY() {
        return y + height;
    }

    public void setMaxY(double maxY) {
        this.y = maxY - height;
    }

    /** Get the center position */
    public Vector2 getPos() {
        return new Vector2(x + width/2, y + height/2);
    }

    /** Set the center position by changing top-left x and y */
    public void setPos(Vector2 pos) {x = pos.getX() - width/2; y = pos.getY() - height/2;}

    /**
     *  Move the rect based on its center in certain direction
     *  Will change the rect
     */
    public void move(Vector2 orientation, double distance) {
        Vector2 pos = getPos().move(orientation, distance);
        setPos(pos);
    }

    /**
     *  Move the rect based on its center to certain destination
     *  Will change the rect
     */
    public void moveTowards(Vector2 destination, double distance) {
        Vector2 pos = getPos().moveTowards(destination, distance);
        setPos(pos);
    }

    /** Detect collide with a position */
    public boolean collide(Vector2 v) {
        return v.getX() >= x && v.getX() <= getMaxX()
                && v.getY() >= y && v.getY() <= getMaxY();
    }

    /** Detect collide with another rect */
    public boolean collide(Rect r) {
        Vector2 subV = getPos().subtract(r.getPos());
        double a = (width + r.width)/2;
        double b = (height + r.height)/2;
        return (width + r.width)/2 >= Math.abs(subV.getX()) &&
                (height + r.height)/2 >= Math.abs(subV.getY());
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + getMaxX() + ", " + getMaxY() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if (obj.getClass() == Rect.class)
            return x == ((Rect) obj).x && y == ((Rect) obj).y &&
                width == ((Rect) obj).width && height == ((Rect) obj).height;
        return false;
    }
}
