import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Frame;

/**
 *
 * @author Robert Hemstedt
 * @author Pia Kullik
 */
public class LaplaceFilter_RH_PK extends ConvolutionFilter_PK_RH {

    Checkbox isL4, isL8;
    @Override
    public void filter(double[][][] src, double[][][] dst) {
        boolean l8 = isL8.getState();
        double[][][] filtered = new double[3][width][height];
        applyKernel(src, filtered, this.getKernel());
        for(int c = 0; c< 3; c++) {
            for(int i = 0; i<width;i++) {
                for(int j = 0; j<height; j++) {
                    boolean edge = false;
                    // Linksverschiebung
                    if(i<width-1) //rechter Nachbar existiert
                        edge = ((filtered[c][i][j] >= 0 && filtered[c][i+1][j]<0) || (filtered[c][i][j] < 0 && filtered[c][i+1][j]>=0)); // verschiede Vorzeichen
                    
                    // Untenverschiebung
                    if(j>0 && !edge)  //oberer Nachbar exisitert
                        edge = ( (filtered[c][i][j] >= 0 && filtered[c][i][j-1]<0) || (filtered[c][i][j] < 0 && filtered[c][i][j-1]>=0));
                    
                    if(l8 && !edge) { //L8-Filter hat weitere Richtungen eventuell zu betrachten.
                        //Diagonalverschiebung links-unten
                        if(i<width-1 && j>0) //rechter oberer Nachbar existiert
                            edge = ((filtered[c][i][j] >= 0 && filtered[c][i+1][j-1]<0) || (filtered[c][i][j] < 0 && filtered[c][i+1][j-1]>=0)); // verschiede Vorzeichen
                        
                        // Diagonalverschiebung links-oben
                        if(i<width-1 && j<height-1 && !edge) //rechter unterer Nachbar exisitert
                            edge = ( (filtered[c][i][j] >= 0 && filtered[c][i+1][j+1]<0) || (filtered[c][i][j] < 0 && filtered[c][i+1][j+1]>=0));
                    }
                    
                    if (!edge) filtered[c][i][j] = 0;
                }
            }
        }
        filtered = LineareGrauwertspreizung_PK_Robert.stretch(filtered);
        for(int c = 0; c< 3; c++) {
            for(int i = 0; i<width;i++) {
                for(int j = 0; j<height; j++) {
                    dst[c][i][j] = filtered[c][i][j];
                }
            }
        }
    }

    @Override
    public void initGUI() {
        Frame pf = getPropertiesFrame();
        pf.setSize(300, 125);
        pf.setLayout(null);
        CheckboxGroup checkGroup = new CheckboxGroup();
        isL4 = new Checkbox("L4-Laplace-Filter",checkGroup, true);
        isL8 = new Checkbox("L8-Laplace-Filter",checkGroup, false);
        Checkbox[] checkboxes = {isL4, isL8};
        for (int i = 0; i < checkboxes.length; i++) {
            checkboxes[i].setLocation(0, 20 * i);
            checkboxes[i].setSize(100, 20);
            pf.add(checkboxes[i]);
        }
    }

    @Override
    public boolean hasProperties() {
        return true;
    }
    
    @Override
    public double[][] getKernel() {
        double[][] l4kernel = {{0,9,0},{9,-36,9},{0,9,0}};
        double[][] l8kernel = {{9,9,9},{9,-72,9},{9,9,9}}; 
        boolean l8 = isL8.getState();
        if(l8) {
            return l8kernel;
        } else {
            return l4kernel;
        }
    }
}
