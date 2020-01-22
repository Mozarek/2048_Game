package game;

public interface IBoardChangedObserver {
    void boardChanged(Board newBoard, boolean isReversed);
}
