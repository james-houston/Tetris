
/**
 *
 * @author James Houston A12083486
 */
public class tester {
    
    public static void main(String [] args){
        Coord [] coordTest;
        int i = 0;
        TetrisShape tester;
        TetrisGrid aGrid = new TetrisGrid();
        
        for(i = 0 ; i<8 ; i++){
            int k = 0;
            System.out.println("Shape type: " + i);
            tester = new TetrisShape(i);
            System.out.println(tester.toString());            
            
            System.out.println("Rotating CW");
            tester.rotateCW();
            System.out.println(tester.toString() + "\n");
            
            coordTest = tester.getCoords();            
            System.out.println("Printing logical shape coords");
            while(k < coordTest.length){
                System.out.println("("+coordTest[k].r +","+coordTest[k].c+")");
                k++;
            }
            
        }
/*      
        tester = new TetrisShape();
        tester.rotateCW();
        System.out.println("Rotating CW");
        System.out.println(tester.toString() + "\n");
        
        System.out.println("Printing blank grid");
        System.out.println(aGrid.toString() + "\n");        
        
        coordTest = tester.getCoords();
        System.out.println("Printing logical shape coords");
        while(i < coordTest.length){
            System.out.println("("+coordTest[i].r +","+coordTest[i].c+")");
            i++;
        }
*/
        tester = new TetrisShape();
        System.out.println("\n");
        aGrid.addShape(tester);
        System.out.println("adding shape to grid");
        System.out.println(aGrid.toString() + "\n");
        aGrid.removeShape(tester);
        System.out.println("removing shape from grid");
        System.out.println(aGrid.toString() + "\n");    
        
        
}
    
}
