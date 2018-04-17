
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JPanel;

/**
 *
 * @author James Houston A12083486
 */
public class TetrisGrid extends JPanel {
    boolean [][] grid;
    public static final int HEIGHT = 20;
    public static final int WIDTH = 10;
    public static final int PAD = 2;
    public static int pixels = 20;
    /**
     * Constructs a grid of HEIGHT by WIDTH
     */
    public TetrisGrid(int pix){
        int i,j;
        pixels = pix;
        grid = new boolean[HEIGHT][WIDTH];
        for(i = 0; i<20 ; i++)
            for(j = 0; j<10 ; j++)
                grid[i][j] = false;
    }
    
    /**
     * returns reference to grid array
     * @return 
     */
    public boolean [][] getArray(){
        return grid;
    }
    
    @Override
    public String toString(){
        String theGrid = "";
        for(int i = 0; i<HEIGHT ; i++){
            for(int j = 0; j<WIDTH ; j++){
                if(grid[i][j] == true)
                    theGrid += 'O';
                else
                    theGrid += '.';
                }
            theGrid += '\n';
        }
        return theGrid;
    }
    
/**
 * Attempts to add a shape to the grid at the shape's internal coordinates
 * @param shape shape to be added
 * @return true if successful 
 */
    
    public boolean addShape(TetrisShape shape){
        Coord [] shapeCoord;
        shapeCoord = shape.getCoords();
        int r,c,i;
        
        for(i = 0 ; i<4 ; i++){
            r = shapeCoord[i].r;
            c = shapeCoord[i].c;
            if(r < 0)
                continue;
            if(grid[r][c] == true)
                return false;
            else grid[r][c] = true;
        }
        repaint();
        return true;
    }
   
/**
 * Removes a shape 
 * @param shape shape to be removed
 * @return true if successfully removed
 */
    public boolean removeShape(TetrisShape shape){
        Coord [] shapeCoord;
        shapeCoord = shape.getCoords();
        int r,c,i;
        
        for(i = 0 ; i<4 ; i++){
            r = shapeCoord[i].r;
            c = shapeCoord[i].c;
            if(r < 0)
                continue;
            if(grid[r][c] == false)
                return false;
            else grid[r][c] = false;
        }
        repaint();
        return true;
    }
    
/**
 * Checks whether a shape will intersect with other blocks when placed
 * @param shape shape to check
 * @return true if intersects
 */
    
    public boolean doesIntersect (TetrisShape shape){
        Coord [] shapeCoord;
            shapeCoord = shape.getCoords();
            int r,c,i;

            for(i = 0 ; i<4 ; i++){
                r = shapeCoord[i].r;
                c = shapeCoord[i].c;
            if(r < 0) continue;
            if(grid[r][c] == true){
                    return true;
                }
            }
            return false;
    }
  
    /**
     * Checks if a shape is out of bounds on the sides or bottom
     * @param shape shape to check
     * @return true if out of bounds
     */
    public boolean isOutOfBounds(TetrisShape shape){
        Coord [] shapeCoord;
            shapeCoord = shape.getCoords();
            int r,c,i;

            for(i = 0 ; i<4 ; i++){
                r = shapeCoord[i].r;
                c = shapeCoord[i].c;
                
                if( c<0 || c>WIDTH-1 || r > HEIGHT-1)
                    return true;                    
            }
            return false;        
    }
  
    
    /**
     * Checks for and removes completed rows on the board
     * @return returns the number of completed rows found
     */
    public int checkRowsComplete(){
        int i,j,rowsComplete=0;
        for(i=0 ; i<HEIGHT ; i++){
            int numFilled = 0;
            for(j=0 ; j<WIDTH ; j++){
                if(grid[i][j] == true)
                    numFilled++;
            }
            if(numFilled == WIDTH){
                removeRow(i);
                rowsComplete++;
            }
        }
        return rowsComplete;
    }
    
    /**
     * Helper method for removing completed rows
     * @param row row to be removed
     */
    private void removeRow(int row){
        int i,j;
        for(i=row ; i>0 ; i--){
            for(j = 0; j<WIDTH ; j++){
                if(i == 0)
                    grid[i][j] = false;
                else grid[i][j] = grid[i-1][j];
            }
        }
    }
    
    @Override
    protected synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
                int i,j;
		for (i=0; i<HEIGHT ; i++) {
                    for(j=0 ; j<WIDTH ; j++){
                        if(grid[i][j] == true){
			int cellX = j * pixels;
			int cellY = i * pixels;
			g.setColor(Color.BLUE);
			g.fillRect(cellX, cellY, pixels, pixels);
                        }
                    }
		}

		g.setColor(Color.BLACK);
		g.drawRect(0, 0,  WIDTH*pixels, HEIGHT*pixels);

		for (i = 0; i < WIDTH*pixels; i += pixels) {
			g.drawLine(i, 0, i, HEIGHT*pixels);
		}

		for (i = 0; i < HEIGHT*pixels; i += pixels) {
			g.drawLine(0, i, WIDTH*pixels, i);
		}
	}
    
    	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(WIDTH*pixels + PAD, HEIGHT*pixels + PAD); 
	}
    
}
