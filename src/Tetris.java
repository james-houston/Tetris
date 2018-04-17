
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author J
 */
public class Tetris extends JFrame {

    private static int width = 200;
    private static int height = 400;
    public static int pixels = 20;
    public TetrisShape activeShape;
    public static ShapeMover shapeMover;
    public static GraphicsGrid window;
    private static JFrame tetrisWindow;
    private static JPanel scoreBoard, GUIControls;
    private static JLabel currScoreLabel, hiScoreLabel, gameOverLabel;
    private static JButton newGame, reset;
    private static JSlider speedSlider;
    private static ActionListener actlst;
    private static ChangeListener chglst;
    public static int currScore, hiScore;
    private static boolean newGameCall = false, resetCall = false;
    private static TetrisGrid grid;
    public static Thread t;

    private static void usage() {
        System.out.format("Usage: Tetris [block size]\n");
        System.exit(-1);
    }

    public static void main(String[] args) {

        if (args.length > 0 && args.length != 1) {
            usage();
        }
        try {

            if (args.length == 1) {
                pixels = Integer.parseInt(args[0]);
            }
            if (pixels <= 0 || pixels > height
                    || pixels > width) {
                usage();
            }
        } catch (NumberFormatException e) {
            usage();
        }

        tetrisWindow = new JFrame("Tetris");
        tetrisWindow.setLayout(new BorderLayout());

        
        constructWindow();
        shapeMover = new ShapeMover(window.getGrid());
        window.getGrid().addKeyListener(shapeMover);
        tetrisWindow.add(window.pane, BorderLayout.CENTER);
        scoreBoard();
        tetrisWindow.add(scoreBoard, BorderLayout.PAGE_START);
        initBottomGUI();
        tetrisWindow.add(GUIControls, BorderLayout.PAGE_END);

        actlst = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionResponse(e);
            }
        };

        chglst = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                stateChangeResponse(e);
            }
        };

        tetrisWindow.addKeyListener(shapeMover);
        speedSlider.addChangeListener(chglst);
        newGame.addActionListener(actlst);
        reset.addActionListener(actlst);

        tetrisWindow.pack();
        tetrisWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tetrisWindow.setVisible(true);

        grid = window.getGrid();
        t = new Thread(shapeMover);
        t.start();

        while (true) {
            currScoreLabel.setText("Score:  " + currScore);
            if (currScore > hiScore) {
                hiScore = currScore;
                hiScoreLabel.setText("High Score:  " + currScore);
            }
            
            if(!shapeMover.gameGoing){
                tetrisWindow.remove(scoreBoard);
                scoreBoard();
                tetrisWindow.add(scoreBoard, BorderLayout.PAGE_START);
                tetrisWindow.pack();
            }
             if(resetCall){
                resetCallResponse();
            }
            if (newGameCall) {
                newGameResponse();
            }

        }
        /*
        window.dispatchEvent(new WindowEvent(window,
                WindowEvent.WINDOW_CLOSING));
        window.dispose();
         */
    }

    public static void constructWindow() {
        window = new GraphicsGrid();

    }

    public static void scoreBoard() {
        scoreBoard = new JPanel(new GridLayout(2, 2));

        currScoreLabel = new JLabel();
        hiScoreLabel = new JLabel();
        gameOverLabel = new JLabel();

        currScoreLabel.setText("Score:  " + currScore);
        hiScoreLabel.setText("High Score:  " + hiScore);
        gameOverLabel.setText("GAME OVER!");

        scoreBoard.add(currScoreLabel); 
        if(!shapeMover.gameGoing){
            scoreBoard.add(gameOverLabel);
        }
        scoreBoard.add(hiScoreLabel);

    }

    public static void initBottomGUI() {
        GUIControls = new JPanel();
        newGame = new JButton("New Game");
        reset = new JButton("Reset!");
        speedSlider = new JSlider(0, 19, 0);
        
        newGame.setFocusable(false);
        reset.setFocusable(false);
        
        GUIControls.add(newGame);
        GUIControls.add(reset);
        GUIControls.add(speedSlider);
        
        GUIControls.setFocusable(false);

    }

    public static void newGameResponse() {
        newGameCall = false; 
 
        shapeMover.stopRun();

        tetrisWindow.remove(window.pane);
        window = new GraphicsGrid();
        shapeMover = new ShapeMover(window.getGrid());
        grid = window.getGrid();
        tetrisWindow.addKeyListener(shapeMover);
        tetrisWindow.add(window.pane, BorderLayout.CENTER);
        
 
        currScore = 0;
        
        tetrisWindow.remove(scoreBoard);
        scoreBoard();
        tetrisWindow.add(scoreBoard, BorderLayout.PAGE_START);
        tetrisWindow.pack();

        t = new Thread(shapeMover);
        t.start();

    }
    
    public static void resetCallResponse(){
        
        resetCall = false;
        speedSlider.setValue(0);
        shapeMover.manualSpeed = false;
        hiScore = 0;
        newGameResponse();
    }

    public static void actionResponse(ActionEvent e) {
        if (e.getSource() == newGame) {
            System.out.println("New Game");
            newGameCall = true;
        }
        if (e.getSource() == reset) {
            resetCall = true;
            System.out.println("Reset!");
        }
    }
    
    public static void stateChangeResponse(ChangeEvent e){
        shapeMover.speedStep = speedSlider.getValue();
        shapeMover.manualSpeed = true;
        System.out.println(speedSlider.getValue());
    }


}
