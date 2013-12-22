import imageToolBox.AbstractFilter;

/**
 *
 * @author Robert Hemstedt
 * @author Pia Kullik
 */

public class Kontrastaufloesung_PK_RH extends AbstractFilter {

    @Override
    public void filter(double[][][] src, double[][][] dst) {
        double steps = getDoubleProperty("steps");
        double normalizedSteps = (steps>1)?(1/(steps-1)):1;
        for(int i = 0; i< width; i++) {
            for(int j = 0; j< height; j++) {
                dst[0][i][j] = (int) (255*(normalizedSteps*((int)(src[0][i][j] * steps/256))));
                dst[1][i][j] = (int) (255*(normalizedSteps*((int)(src[1][i][j] * steps/256))));
                dst[2][i][j] = (int) (255*(normalizedSteps*((int)(src[2][i][j] * steps/256))));
            }
        }
    }
/*
    kleine Erklaerung in Bildern:
    0     85    170   255
    |-----|---*-|-----| wird geschickt auf:
    
    0     1     2     3 
    |-----*-----|-----| und dies auf:
    
    0     85    170   255
    |-----*-----|-----|
              
    */
    
    

    @Override
    public boolean hasProperties() {
        return true;
    }

    @Override
    public void initGUI() {
        addDoubleProperty("steps", 256.0);
    }
    
    
    
    
    
    
}
