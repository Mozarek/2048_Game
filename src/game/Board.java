package game;

import utils.MapDirection;
import utils.Rectangle;
import utils.Vector2d;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    private Tile [][] tiles;
    public final Rectangle boundary;
    private static Random randomGenerator = new Random();

    Board(int width, int height){
        Vector2d lowerLeft = new Vector2d(0,0);
        Vector2d delta = new Vector2d(width, height);
        boundary = new Rectangle(lowerLeft, lowerLeft.add(delta));
        tiles = new Tile[height][width];
    }

    boolean isFull(){
        boolean isFull = true;
        for(int r=0;r<boundary.getHeight();r++){
            for(int c=0;c<boundary.getWidth();c++){
                isFull &= tiles[r][c] != null;
                if(r+1<boundary.getHeight()){
                    isFull &= !(tiles[r][c] != null && tiles[r+1][c] != null && tiles[r][c].value == tiles[r+1][c].value);
                }
                if(c+1<boundary.getWidth()){
                    isFull &= !(tiles[r][c] != null && tiles[r][c+1] != null && tiles[r][c].value == tiles[r][c+1].value);
                }
            }
        }
        return isFull;
    }

    public Tile at(Vector2d position){
        if(!boundary.isInside(position)){
            throw new IllegalArgumentException("Position out of bounds of board");
        }
        return tiles[position.y][position.x];
    }

    void set(Vector2d position, int value){
        if(!boundary.isInside(position)){
            throw new IllegalArgumentException("Position out of bounds of board");
        }
        tiles[position.y][position.x] = new Tile(position, value);
    }

    int getMaxValueOnBoard(){
        int maxValue = 0;
        for(int r=0;r<boundary.getHeight();r++){
            for(int c=0;c<boundary.getWidth();c++){
                if(tiles[r][c] != null) {
                    maxValue = Math.max(maxValue, tiles[r][c].value);
                }
            }
        }
        return maxValue;
    }

    boolean anyTileMoved(){
        for(int r=0;r<boundary.getHeight();r++){
            for(int c=0;c<boundary.getWidth();c++){
                if(tiles[r][c]!= null && tiles[r][c].moved()){
                    return true;
                }
            }
        }
        return false;
    }

    Vector2d getRandomFreeSpot(){
        ArrayList<Vector2d> emptySpots = new ArrayList<>();
        for(int r=0;r<boundary.getHeight();r++){
            for(int c=0;c<boundary.getWidth();c++){
                if(tiles[r][c] == null) {
                    emptySpots.add(new Vector2d(c, r));
                }
            }
        }

        if(emptySpots.size() == 0){
            return null;
        }

        int index = randomGenerator.nextInt(emptySpots.size());
        return emptySpots.get(index);
    }
}
