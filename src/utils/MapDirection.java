package utils;

public enum MapDirection {
    UP(new Vector2d(0,-1)),
    RIGHT(new Vector2d(1,0)),
    DOWN(new Vector2d(0,1)),
    LEFT(new Vector2d(-1,0));

    private final Vector2d unitVector;

    MapDirection(Vector2d unitVector){
        this.unitVector = unitVector;
    }

    public Vector2d getUnitVector() {
        return unitVector;
    }

    public MapDirection rotateRight() {
        int len = MapDirection.values().length;
        return MapDirection.values()[(this.ordinal()+1)%len];
    }

    public MapDirection rotateLeft(){
        int len = MapDirection.values().length;
        return MapDirection.values()[(this.ordinal()+len-1)%len];
    }

    public MapDirection opposite(){
        int len = MapDirection.values().length;
        return MapDirection.values()[(this.ordinal()+2)%len];
    }
}
