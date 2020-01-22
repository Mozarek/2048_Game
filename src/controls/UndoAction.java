package controls;

import game.GameEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractAction {
    private GameEngine gameEngine;

    public UndoAction(GameEngine gameEngine){
        this.gameEngine = gameEngine;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        gameEngine.undoMove();
    }
}
