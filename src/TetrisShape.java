
import java.util.*;
/**
 *
 * @author James Houston A12083486
 */
public class TetrisShape {
    
    boolean [][] shape = new boolean[5][5];
    Random rng = new Random();
    public boolean isSquare = false;
    public Coord [] activeCoords = new Coord[4];
    int xOffset = 4, yOffset = 0;
    
    public TetrisShape(int shapeNum){
        if(shapeNum == 0){
            shapeNum = rng.nextInt(7)+1;
            System.out.println("Random is generating type " + shapeNum);
        }
            
        
        switch(shapeNum){
            case 1:
                buildO();
                break;
            case 2:
                buildI();
                break;
            case 3:
                buildS();
                break;
            case 4:
                buildZ();
                break;
            case 5:
                buildL();
                break;
            case 6:
                buildJ();
                break;
            case 7:
                buildT();
                break;              
        }
               
    }
    
    public TetrisShape(){
        this(0);
    }
    
    public void buildO(){
        shape = new boolean[2][2];
        isSquare = true;
        for(int i = 0; i<2 ; i++){
            for(int j = 0; j<2 ; j++){
               shape[i][j] = true; 
            }
        }
    }
    
    public void buildI(){
        for(int i = 0; i<5 ; i++)
            for(int j = 0 ; j<5 ; j++)
                shape[i][j] = false;
        for(int i = 0; i<4 ; i++){
            shape[i][2] = true;
        }
        yOffset = 2;
    }
    
    public void buildS(){
        shape[2][2] = true;
        shape[2][3] = true;
        shape[3][2] = true;
        shape[3][1] = true;
        yOffset = 0;
    }
    
    public void buildZ(){
        shape[2][2] = true;
        shape[2][3] = true;
        shape[1][2] = true;
        shape[1][1] = true;
        yOffset = 1;
    }
    
        public void buildL(){
        shape[2][2] = true;
        shape[2][3] = true;
        shape[1][2] = true;
        shape[0][2] = true;
        yOffset = 2;
    }
    
    public void buildJ(){
        shape[2][2] = true;
        shape[2][1] = true;
        shape[1][2] = true;
        shape[0][2] = true;
        yOffset = 2;
    }
    
    public void buildT(){
        shape[2][2] = true;
        shape[2][3] = true;
        shape[2][1] = true;
        shape[3][2] = true;
        yOffset = 0;
    }
    
    
    public void rotateCCW(){
        int i, j, ip, jp;
        boolean [][] temp = new boolean[5][5];
        
        if(isSquare)
            return;
        
        for(i = 0 ; i<5; i++){
            for(j = 0 ; j<5 ; j++){
                if(shape[i][j] == true){
                    ip = (i-2);
                    jp = -1*(j-2);
                    temp[jp+2][ip+2] = shape[i][j];
                }    
            }
        }
        
        shape = temp;
    }
    public void rotateCW(){
        int i, j, ip, jp;
        boolean [][] temp = new boolean[5][5];
        
        if(isSquare)
            return;
        
        for(i = 0 ; i<5; i++){
            for(j = 0 ; j<5 ; j++){
                if(shape[i][j] == true){
                    ip = -1*(i-2);
                    jp = (j-2);
                    temp[jp+2][ip+2] = shape[i][j];
                }    
            }
        }
        shape = temp;
    }
    
    public Coord [] getCoords(){
        int i,j,k = 0;
        if(isSquare){
            for(i = 0 ; i<2 ; i++){
                for(j = 0 ; j<2 ; j++){
                        activeCoords[k] = new Coord(i + yOffset,j + xOffset);
                        k++;
                }
            }
        }
        else{
            for(i = 0; i<5 ; i++){
                for(j = 0 ; j<5 ; j++){
                    if(shape[i][j]){
                        activeCoords[k] = new Coord(i-2+yOffset, j-2+xOffset);
                        k++;
                    }
                }
            }
        }
        return activeCoords;
    }
    
    @Override
    public String toString(){
        String stringRep = "";
        int i,j;
        for(i=0; i < shape.length ; i++){
              for(j=0; j<shape[0].length; j++){
                if(shape[i][j] == false)
                    stringRep += '.';
                else
                    stringRep += 'O';
                }
              stringRep += '\n';
        }
        
        return stringRep;
    }
    
}
