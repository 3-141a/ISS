
import imageToolBox.AbstractFilter;

/**
 * @author Robert Hemstedt
 * @author Pia Kullik
 */
public class Iso_Inhomo_DiffusionFilter_RH_PK extends AbstractFilter {

    @Override
    public void filter(double[][][] src, double[][][] dst) {
        double lambda = getDoubleProperty("Lambda");
        double epsilon = getDoubleProperty("Epsilon");
        double iter = getDoubleProperty("Iterations");
        double[][][] flow1 = new double[2][width][height];

        //full copy into dst
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int c = 0; c < 3; c++) {
                    dst[c][i][j] = src[c][i][j];
                }
            }
        }

        for (int a = 0; a < iter; a++) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    //calculate flow vector for pixel at position (i,j)
                    double[] pixFlow = flow(dst[0], i, j, epsilon, lambda);
                    //assign flow to flow array
                    flow1[0][i][j] = pixFlow[0];
                    flow1[1][i][j] = pixFlow[1];
                }
            }

            //all flow values are calculated, so we can calculate divergence
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    // calculate divergence for pixel at position (i,j) and
                    // set dst to its new value
                    double newVal = dst[0][i][j] - divergence(flow1, i, j);
                    for (int c = 0; c < 3; c++) {
                        dst[c][i][j] = newVal;
                    }
                }
            }
        }
    }

    @Override
    public void initGUI() {
        addDoubleProperty("Lambda", 1);
        addDoubleProperty("Epsilon", 1);
        addDoubleProperty("Iterations", 500);
    }

    @Override
    public boolean hasProperties() {
        return true;
    }

    // methods for gradients at (x,y) in arbitrary 2-dimensional matrices
    // src IS the function F
    double delFdelY(double[][] src, int x, int y) {
        int h = src[0].length;
        if (y > 0 && y < h - 1) {
            return src[x][y + 1] - src[x][y - 1];
        } else if (y == 0) {
            return src[x][y + 1] - src[x][y];
        } else {
            return src[x][y] - src[x][y - 1];
        }
    }

    double delFdelX(double[][] src, int x, int y) {
        int w = src.length;
        if (x > 0 && x < w - 1) {
            return src[x + 1][y] - src[x - 1][y];
        } else if (x == 0) {
            return src[x + 1][y] - src[x][y];
        } else {
            return src[x][y] - src[x - 1][y];
        }
    }

    double[] flow(double src[][], int x, int y, double eps,
            double lambda) {
        double l2 = lambda * lambda;
        double dIdX = delFdelX(src, x, y);
        double dIdY = delFdelY(src, x, y);
        double val = -eps * l2 / (l2 + dIdX * dIdX + dIdY * dIdY);
        // tensor matrix is val*E_2 (E_2 is identity matrix)
        // flow is therefore just scaling of (dIdX,dIdY) with val
        return new double[]{val * dIdX, val * dIdY};
    }

    double divergence(double[][][] flow, int x, int y) {
        return delFdelX(flow[0], x, y) + delFdelY(flow[1], x, y);
    }
}
