
import imageToolBox.AbstractFilter;
import java.awt.*;

/**
 *
 * @author Robert Hemstedt
 */
public class SelectColorFilter_RH extends AbstractFilter {

    Checkbox isRed;
    Checkbox isGreen;
    Checkbox isBlue;

    int boolToInt(boolean input) {
        return (input) ? 1 : 0;
    }

    @Override
    public void filter(double[][][] src, double[][][] dst) {
        boolean[] useColor = {isRed.getState(), isGreen.getState(), isBlue.getState()};
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < 3; k++) {
                    dst[k][i][j] = boolToInt(useColor[k]) * src[k][i][j];
                }
            }
        }
    }

    @Override
    public void initGUI() {
        Frame pf = getPropertiesFrame();
        pf.setSize(300, 125);
        pf.setLayout(null);
        isRed = new Checkbox("red", true);
        isGreen = new Checkbox("green", true);
        isBlue = new Checkbox("blue", true);
        Checkbox[] checkboxes = {isRed, isGreen, isBlue};
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
}
