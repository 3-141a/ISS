import imageToolBox.AbstractFilter;
import java.awt.Point;
import java.util.LinkedList;

/**
 *
 * @author Robert Hemstedt
 * @author Pia Kullik
 */
public class HoughTrafoVoting_RH_PK extends AbstractFilter{

    @Override
    public void filter(double[][][] src, double[][][] dst) {
        
         double[][] grads = new double[width][height];
         double[][] houghSpace = new double[181][129];
         double maxGrad = 0;
         double rMin = -64;
         double rMax = 64;
         for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double sobelAnswer = getSobelValues(src, i, j);
                if(sobelAnswer > maxGrad) maxGrad = sobelAnswer;
                grads[i][j] = sobelAnswer;
            }
        }
        LinkedList<Point> contourPxls = new LinkedList<>();
        
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(grads[i][j] > 0.1*maxGrad) contourPxls.add(new Point(i,j));
            }
        }
        while(!contourPxls.isEmpty()) {
            Point curPoint = contourPxls.pop();
            for(int theta = 0; theta<181; theta++) {
                double th = theta;
                //th *= 180/181;
                double rho = curPoint.x * Math.cos(th*Math.PI/181) 
                                + curPoint.y*Math.sin(th*Math.PI/181);
                if(rMin <= rho && rho <= rMax) houghSpace[theta][Math.round((float)(rho-rMin))]++;
            }
        }
        for (int i = 0; i < width; i++) 
            for (int j = 0; j < height; j++) 
                for(int c = 0; c<3;c++)
                    dst[c][i][j] = houghSpace[i][j];
       LineareGrauwertspreizung_PK_Robert stretchFilter = new LineareGrauwertspreizung_PK_Robert();
       stretchFilter.setDimensions(width, height);
       stretchFilter.filter(dst, dst);        
    }
    
    double getSobelValues(double[][][] src, int x, int y) {
        double[][] xMask = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        double[][] yMask = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};
        double xGrad = 0;
        double yGrad = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int modx = modPix(x + i, width);
                int mody = modPix(y + j, height);
                xGrad += xMask[1 - j][1 - i] * src[0][modx][mody]; //reversed x and y in filter masks
                yGrad -= yMask[1 - j][1 - i] * src[0][modx][mody]; // and y has reverted direction
            }
        }
        return Math.sqrt(xGrad*xGrad+yGrad*yGrad);
    }
    
    int modPix(int x, int interval) { //handle image borders
        if(x>=0 && x<interval) 
            return x;
        else if (x<0)
            return 0;
        else
            return interval-1;
    }
}
