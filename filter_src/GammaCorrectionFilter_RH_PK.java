
import imageToolBox.AbstractFilter;

/**
 *
 * @author Robert Hemstedt
 * @author Pia Kullik
 */
public class GammaCorrectionFilter_RH_PK extends AbstractFilter{

    @Override
    public void filter(double[][][] src, double[][][] dst) {
        double gamma = getDoubleProperty("gamma");
        for(int i = 0; i<width; i++) {
            for(int j = 0; j< height; j++) {
                for(int c = 0; c< 3; c++) {
                    dst[c][i][j] = 255 * Math.pow(src[c][i][j]/255,gamma);
                }
            }
        }
    }
 
    @Override
    public void initGUI() {
        addDoubleProperty("gamma",1.0);
    }

    @Override
    public boolean hasProperties() {
        return true;
    }
}
