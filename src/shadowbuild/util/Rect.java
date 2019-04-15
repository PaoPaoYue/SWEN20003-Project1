package shadowbuild.util;

public class Rect {
    private double x;
    private double y;
    private double width;
    private double height;

    public Rect() {

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

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getMaxX() {
        return x + width;
    }

    public double getMaxY() {
        return y + height;
    }

    public Vector2 getPos() {
        return new Vector2(x + width/2, y + height/2);
    }

    public void setPos(Vector2 pos) {x = pos.getX() - width/2; y = pos.getY() - height/2;}

    public void move(Vector2 orientation, double distance) {
        Vector2 pos = getPos().move(orientation, distance);
        setPos(pos);
    }

    public void moveTowards(Vector2 destination, double distance) {
        Vector2 pos = getPos().moveTowards(destination, distance);
        setPos(pos);
    }

    public boolean collide(Vector2 v) {
        return v.getX() >= x && v.getX() <= getMaxX()
                && v.getY() >= y && v.getY() <= getMaxY();
    }

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
        if (obj instanceof Rect)
            return x == ((Rect) obj).x && y == ((Rect) obj).y &&
                width == ((Rect) obj).width && height == ((Rect) obj).height;
        return false;
    }
}
