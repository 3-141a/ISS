/*
 * @author Robert Hemstedt
 */
import imageToolBox.AbstractFilter;

public class GreyFilter_RH extends AbstractFilter {

    @Override
    public void filter(double[][][] src, double[][][] dst) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double val = 0;
                for (int k = 0; k < 3; k++) {
                    val += src[k][i][j] / 3;
                }
                for (int k = 0; k < 3; k++) {
                    dst[k][i][j] = val;
                }
            }
        }
    }
}
