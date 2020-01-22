package game;

import java.util.Deque;
import java.util.LinkedList;

class GameHistory {
    private Deque<Board> previousBoards = new LinkedList<>();
    private int maxLength;

    GameHistory(int maxLength){
        this.maxLength = maxLength;
    }

    boolean isEmpty(){
        return previousBoards.isEmpty();
    }

    void push(Board board){
        previousBoards.addFirst(board);
        while(previousBoards.size() > maxLength){
            previousBoards.removeLast();
        }
    }

    Board pop(){
        return previousBoards.pop();
    }
}
