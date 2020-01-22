package game;

import utils.MapDirection;
import utils.Vector2d;
import visualization.Config;

import java.util.Random;

public class GameEngine implements IAnimationFinishedObserver{
    private GameState gameState;
    private Board currentBoard;
    private GameHistory gameHistory;
    private final Config config;
    private static Random randomGenerator = new Random();

    private IGameStateChangedObserver gameStateChangedObserver;
    private IBoardChangedObserver boardChangedObserver;

    public GameEngine(Config config){
        this.config = config;
        this.currentBoard = new Board(this.config.width, this.config.height);
        this.gameHistory = new GameHistory(this.config.moveHistorySize);
        this.gameState = GameState.IN_PROGRESS;

        for(int i=0;i<this.config.tilesOnStart;i++){
            generateNewTile();
        }
    }

    public Board getBoard() {
        return currentBoard;
    }

    private void generateNewTile(){
        int exponent = randomGenerator.nextInt(config.maxGeneratedValueExponent) + 1;
        Vector2d position = currentBoard.getRandomFreeSpot();
        if(position!=null) {
            currentBoard.set(position, exponent);
        }
    }

    private void checkGameState(){
        if(currentBoard.getMaxValueOnBoard() == config.targetValueExponent){
            gameState = GameState.WIN;
            gameStateChangedObserver.gameStateChanged(gameState);
        }else if(currentBoard.isFull()){
            gameState = GameState.LOSS;
            gameStateChangedObserver.gameStateChanged(gameState);
        }
    }

    private Vector2d getStartingPoint(MapDirection direction){
        switch(direction){
            case DOWN:
                return new Vector2d(0,0);
            case RIGHT:
                return new Vector2d(0, currentBoard.boundary.getHeight()-1);
            case UP:
                return new Vector2d(currentBoard.boundary.getWidth()-1, currentBoard.boundary.getHeight()-1);
            case LEFT:
                return new Vector2d(currentBoard.boundary.getWidth()-1, 0);
            default:
                throw new IllegalArgumentException("Direction enum other than declared ones");
        }
    }

    private boolean slide(MapDirection direction){
        Board newBoard = new Board(currentBoard.boundary.getWidth(), currentBoard.boundary.getHeight());

        Vector2d nextCol = direction.opposite().getUnitVector();
        Vector2d nextRow = direction.opposite().rotateLeft().getUnitVector();


        Vector2d start = getStartingPoint(direction.opposite());
        Vector2d pos = start;

        int r=0;
        int c=0;
        Vector2d lastUnused = null;
        Vector2d lastFree = pos;

        while(currentBoard.boundary.isInside(pos)){
            //process row
            if(currentBoard.at(pos) != null){
                if(lastUnused != null && currentBoard.at(pos).value == newBoard.at(lastUnused).value) {
                    //merge
                    currentBoard.at(pos).moveTo(lastUnused);
                    newBoard.set(lastUnused, currentBoard.at(pos).value+1);

                    lastFree = lastUnused.add(nextCol);
                    lastUnused = null;
                }else{
                    //just slide
                    currentBoard.at(pos).moveTo(lastFree);
                    newBoard.set(lastFree, currentBoard.at(pos).value);

                    lastUnused = lastFree;
                    lastFree = lastFree.add(nextCol);
                }
            }

            //move to next col
            pos = pos.add(nextCol);
            c++;
            //move to next row if last col
            if(!currentBoard.boundary.isInside(pos)){
                pos = start.add(nextRow.multiply(r++));
                c=0;
                lastFree = pos;
                lastUnused = null;
            }
        }

        if(currentBoard.anyTileMoved()) {
            boardChangedObserver.boardChanged(currentBoard, false);
            gameHistory.push(currentBoard);
            currentBoard = newBoard;
            return true;
        }else{
            return false;
        }
    }

    public void update(MapDirection direction){
        if(gameState == GameState.IN_PROGRESS) {
            if(slide(direction)){
                generateNewTile();
                checkGameState();
            }
        }
    }

    public void undoMove(){
        if(gameState == GameState.IN_PROGRESS && !gameHistory.isEmpty()) {
            currentBoard = gameHistory.pop();
            boardChangedObserver.boardChanged(currentBoard, true);
        }
    }


    public void setGameStateChangedObserver(IGameStateChangedObserver observer){
        this.gameStateChangedObserver = observer;
    }

    public void setBoardChangedObserver(IBoardChangedObserver observer){
        this.boardChangedObserver = observer;
    }

    @Override
    public Board animationFinished() {
        return currentBoard;
    }
}
