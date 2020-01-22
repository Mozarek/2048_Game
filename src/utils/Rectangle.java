package utils;

@SuppressWarnings("ALL")
public class Rectangle {
    public final Vector2d lowerLeft;
    public final Vector2d upperRight;

    public Rectangle(Vector2d lowerLeft , Vector2d upperRight){
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    public boolean isInside(Vector2d position){
        return position.follows(lowerLeft) && position.precedes(upperRight);
    }

    public int getWidth(){
        return upperRight.x-lowerLeft.x;
    }

    public int getHeight(){
        return upperRight.y-lowerLeft.y;
    }
}

