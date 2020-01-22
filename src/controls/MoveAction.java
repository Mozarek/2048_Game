package controls;

import game.GameEngine;
import utils.MapDirection;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MoveAction extends AbstractAction {
    private MapDirection direction;
    private GameEngine gameEngine;
    public MoveAction(GameEngine gameEngine, MapDirection dir){
        this.direction = dir;
        this.gameEngine = gameEngine;
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        gameEngine.update(direction);
    }
}
