package game;

import utils.Vector2d;

public class Tile {
    public Vector2d position;
    public int value;
    private static final Vector2d notMoved = new Vector2d(0,0);

    public Vector2d move = notMoved;

    Tile(Vector2d position, int value){
        this.position = position;
        this.value = value;
    }

    void moveTo(Vector2d newPosition){
        this.move = newPosition.subtract(position);
    }

    boolean moved(){
        return !move.equals(notMoved);
    }
}
