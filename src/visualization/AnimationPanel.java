package visualization;

import game.*;
import utils.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.TimeUnit;

public class AnimationPanel extends JPanel implements IGameStateChangedObserver, IBoardChangedObserver {
    private Config config;
    private IAnimationFinishedObserver animationFinishedObserver;
    private Board board;
    private boolean isAnimationActive = false;
    private boolean isAnimationReversed;
    private long animationClock;
    private long elapsed;
    private GameState gameState;

    private int LARGER_DIMENSION = 600;

    private int GRID_BORDER_WIDTH = 5;
    private int TILE_SIZE;

    private int animationTime; // in milliseconds

    private static final Color background = new Color(255, 255, 240);
    private static final Color emptyTile = new Color(190, 180, 170);
    private static final Color text = new Color(0, 0, 0);

    private final Font numberFont;

    AnimationPanel(Config config, Board board){
        this.animationTime = config.animationTime;
        this.config = config;
        this.board = board;

        this.TILE_SIZE = (LARGER_DIMENSION - GRID_BORDER_WIDTH) / Math.max(config.width, config.height) - GRID_BORDER_WIDTH;
        int preferredWidth = (TILE_SIZE+GRID_BORDER_WIDTH) * config.width + GRID_BORDER_WIDTH;
        int preferredHeight = (TILE_SIZE+GRID_BORDER_WIDTH) * config.height + GRID_BORDER_WIDTH;
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        this.numberFont = new Font(Font.DIALOG, Font.BOLD, TILE_SIZE/3);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(isAnimationActive) {
            elapsed = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - animationClock);
            if (elapsed > animationTime) {
                isAnimationActive = false;
                if (!isAnimationReversed) {
                    board = animationFinishedObserver.animationFinished();
                }
            }
        }

        g.clearRect(0, 0, getWidth(), getHeight());
        g.setColor(background);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int row = 0; row < config.height; row++) {
            for (int col = 0; col < config.width; col++) {
                int xPos = GRID_BORDER_WIDTH + col * (TILE_SIZE + GRID_BORDER_WIDTH);
                int yPos = GRID_BORDER_WIDTH + row * (TILE_SIZE + GRID_BORDER_WIDTH);
                g.setColor(emptyTile);
                g.fillRect(xPos, yPos, TILE_SIZE, TILE_SIZE);
            }
        }

        for (int row = 0; row < config.height; row++) {
            for (int col = 0; col < config.width; col++) {
                Tile tile = board.at(new Vector2d(col, row));
                if (tile != null) {
                    paintTile(g, tile);
                }
            }
        }
    }

    private void paintTile(Graphics g, Tile tile){
        g.setColor(getColorOfTile(tile));
        int xPos = GRID_BORDER_WIDTH + tile.position.x * (TILE_SIZE + GRID_BORDER_WIDTH);
        int yPos = GRID_BORDER_WIDTH + tile.position.y * (TILE_SIZE + GRID_BORDER_WIDTH);
        if(isAnimationActive) {
            if (!isAnimationReversed) {
                xPos += tile.move.x * (GRID_BORDER_WIDTH + TILE_SIZE) * elapsed / animationTime;
                yPos += tile.move.y * (GRID_BORDER_WIDTH + TILE_SIZE) * elapsed / animationTime;
            } else {
                xPos += tile.move.x * (GRID_BORDER_WIDTH + TILE_SIZE) * (animationTime - elapsed) / animationTime;
                yPos += tile.move.y * (GRID_BORDER_WIDTH + TILE_SIZE) * (animationTime - elapsed) / animationTime;
            }
        }
        g.fillRect(xPos, yPos, TILE_SIZE, TILE_SIZE);

        String numberString = Integer.toString(1<<tile.value);
        g.setColor(text);
        g.setFont(numberFont);
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(numberString, g);
        int dx = (TILE_SIZE - (int) r.getWidth()) / 2;
        int dy = (TILE_SIZE - (int) r.getHeight()) / 2 + fm.getAscent();
        g.drawString(numberString, xPos + dx, yPos + dy);
    }

    private Color getColorOfTile(Tile tile) {
        if (tile == null) {
            return emptyTile;
        } else {
            return new Color(255 - 4*tile.value, 200 - 16 * tile.value, 150 - 12 * tile.value);
        }
    }


    boolean isAnimationActive(){
        return isAnimationActive;
    }

    void setAnimationFinishedObserver(IAnimationFinishedObserver observer){
        this.animationFinishedObserver = observer;
    }

    @Override
    public void boardChanged(Board newBoard, boolean isReversed) {
        board = newBoard;
        animationClock = System.nanoTime();
        isAnimationActive = true;
        isAnimationReversed = isReversed;
    }

    @Override
    public void gameStateChanged(GameState state) {
        gameState = state;
    }
}
