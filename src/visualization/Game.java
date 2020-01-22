package visualization;

import controls.MoveAction;
import controls.UndoAction;
import game.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javafx.animation.Animation;
import org.json.simple.parser.ParseException;
import utils.MapDirection;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game extends JPanel {
    private int frameTime;
    private ScheduledExecutorService executor;

    public Game() throws IOException, ParseException {
        Config config = new Config("src/config.json");
        this.frameTime = 1000/config.fps;

        GameEngine gameEngine = new GameEngine(config);
        AnimationPanel animationPanel = new AnimationPanel(config, gameEngine.getBoard());

        animationPanel.setAnimationFinishedObserver(gameEngine);
        gameEngine.setBoardChangedObserver(animationPanel);
        gameEngine.setGameStateChangedObserver(animationPanel);

        add(animationPanel);

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), MapDirection.UP);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), MapDirection.RIGHT);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), MapDirection.DOWN);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), MapDirection.LEFT);
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0), "undo");

        for(MapDirection dir : MapDirection.values()){
            getActionMap().put(dir, new MoveAction(gameEngine, dir));
        }
        getActionMap().put("undo", new UndoAction(gameEngine));



        Runnable animateNextFrame = () -> {
            if(animationPanel.isAnimationActive()) {
                repaint();
            }
        };

        repaint();
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(animateNextFrame, 0, frameTime, TimeUnit.MILLISECONDS);

    }
}
