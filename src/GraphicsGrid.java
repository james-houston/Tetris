/**
 *
 * @author J
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Scanner;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class GraphicsGrid extends JFrame {
	private TetrisGrid grid;
        //ShapeMover shpMvr;
        Container pane;

	public GraphicsGrid ()
	{
		super();

		grid = new TetrisGrid(Tetris.pixels);
                //shpMvr = new ShapeMover(grid);
		pane = getContentPane();
		pane.setLayout(new BorderLayout());
		pane.add(grid,BorderLayout.CENTER);
                //grid.addKeyListener(shpMvr);
                grid.setFocusable(true);
                pane.requestFocusInWindow();

	}
	/** reference to the logical grid of cells 
	 * @return grid reference
	 */
	public TetrisGrid getGrid()
	{
		return grid;
	}
        
}
