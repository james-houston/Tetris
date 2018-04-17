
/**
 *
 * @author James Houston
 *  PID: A12083486
 */
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.TimeUnit;

public class ShapeMover implements Runnable, KeyListener {

    private final int MILLISWAIT = 50;
    int speedStep;
    boolean stop = false;
    TetrisGrid theGrid;
    TetrisShape activeShape;
    public boolean moveCall = false, manualSpeed = false, gameGoing = true;
    int move, dropCount;

    /**
     * Constructor
     *
     * @param grid reference to logical grid
     */
    public ShapeMover(TetrisGrid grid) {
        theGrid = grid;
    }

    /**
     * Implement Runnable
     */
    public void run() {
        activeShape = new TetrisShape();
        while (!stop) {
            for (int i = 0; i < 20 - speedStep; i++) {
                theGrid.addShape(activeShape);
                try {
                    TimeUnit.MILLISECONDS.sleep(MILLISWAIT);
                } catch (InterruptedException e) {
                };
                theGrid.removeShape(activeShape);

                if (moveCall) {
                    if(executeMove())
                        i = 0;
                    moveCall = false;
                }
                
            }

            if (!moveDown()) {
                theGrid.addShape(activeShape);
                checkCompleted();
                activeShape = new TetrisShape();
                if (theGrid.doesIntersect(activeShape)) {
                    stopRun();
                    gameGoing = false;
                }

            } else {
                Tetris.currScore += 10;
            }
            if(!manualSpeed){
               speedStep = Tetris.currScore/2000;
            }
           
            if(speedStep > 19){
                speedStep = 19;
            }
        }
    }

    /* how to tell the mover to stop what it is doing
     */
    public void stopRun() {
        stop = true;
    }

    public boolean executeMove() {
        switch (move) {
            case 72:
                return moveLeft();
            case 74:
                activeShape.rotateCCW();
                if (isNotLegal()) {
                    activeShape.rotateCW();
                    return false;
                }
                return true;
            case 75:
                activeShape.rotateCW();
                if (isNotLegal()) {
                    activeShape.rotateCCW();
                    return false;
                }
                return true;
            case 76:
                return moveRight();
            case 32:
                return drop();
            case 10:
                stopRun();
                return true;
        }
        return false;
    }

    public boolean moveDown() {
        activeShape.yOffset += 1;
        if (theGrid.isOutOfBounds(activeShape) || theGrid.doesIntersect(activeShape)) {
            moveUp();
            return false;
        }
        return true;
    }

    public void moveUp() {
        activeShape.yOffset -= 1;
    }

    public boolean moveLeft() {
        activeShape.xOffset -= 1;
        if (isNotLegal()) {
            moveRight();
            return false;
        }
        return true;
    }

    public boolean moveRight() {
        activeShape.xOffset += 1;
        if (isNotLegal()) {
            moveLeft();
            return false;
        }
        return true;
    }

    public boolean drop() {
        if (moveDown()) {
            dropCount++;
            drop();
        }
        Tetris.currScore += (dropCount * 10);
        dropCount = 0;
        return true;
    }

    public boolean isNotLegal() {
        if (theGrid.isOutOfBounds(activeShape) || theGrid.doesIntersect(activeShape)) {
            return true;
        }
        return false;
    }
    
    public void checkCompleted(){
            switch (theGrid.checkRowsComplete()) {
                case 1:
                    Tetris.currScore += 100;
                    break;
                case 2:
                    Tetris.currScore += 400;
                    break;
                case 3:
                    Tetris.currScore += 800;
                    break;
                case 4:
                    Tetris.currScore += 1600;
                    break;
                case 0:
                    break;
            }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("key pressed");
        move = e.getKeyCode();
        moveCall = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
