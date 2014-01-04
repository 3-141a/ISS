import imageToolBox.AbstractFilter;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Robert Hemstedt
 * @author Pia Kullik
 */

public class WSTFilter_RH_PK extends AbstractFilter{

    public class Gradient {
        
        public int x;
        public int y;
        public double val;
        
        public Gradient(int x, int y, double val) {
            this.x =x;
            this.y = y;
            this.val = val;                  
        }
    };
    
    public class GradientComparator implements java.util.Comparator<Gradient> {
        int state; // -1 : isolated, 0:  wasserscheide, 1: existing label
        public int compare(Gradient o1, Gradient o2) {
            return Double.compare(o1.val, o2.val);            
        }
    }
    
    @Override
    public void filter(double[][][] src, double[][][] dst) {
        LinkedList<Gradient> sortGrads = new LinkedList<>();
        double[][] grads = new double[width][height];
        int[][] labeled = new int[width][height];

        GreyFilter_RH greyfilter = new GreyFilter_RH();
        greyfilter.setDimensions(width, height);
        double[][][] tmp = new double[3][width][height];
        greyfilter.filter(src, tmp);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double sobelAnswer = getSobelValues(tmp, i, j);
                for (int c = 0; c < 3; c++) {
                    dst[c][i][j] = src[c][i][j];
                }
                grads[i][j] = sobelAnswer;
                sortGrads.add(new Gradient(i, j, sobelAnswer));
                labeled[i][j] = Integer.MAX_VALUE;
            }
        }
        
        int highestLabel = 0; // 0: ws; >0: segment
        Collections.sort(sortGrads, new GradientComparator());
        while(!sortGrads.isEmpty()) {
            Gradient active = sortGrads.pop();
            int firstLabel = Integer.MAX_VALUE;
            boolean ws = false;
            for(int i = -1; i<2 ;i++) {
                for(int j =-1 ; j<2; j++) {
                    int modx = modPix(active.x+i, width);
                    int mody = modPix(active.y+j,height);
                    if(modx == active.x && mody == active.y) continue;
                    if(labeled[modx][mody] != Integer.MAX_VALUE) {
                        if(firstLabel == Integer.MAX_VALUE && labeled[modx][mody]!=0) 
                            firstLabel = labeled[modx][mody];
                        else if (firstLabel != labeled[modx][mody] && labeled[modx][mody] != 0) {
                            ws = true;
                            break;
                        }
                    }
                }
                if(ws) break;
            }
            if(ws) 
                labeled[active.x][active.y] = 0;
            else if(firstLabel == Integer.MAX_VALUE) {
                labeled[active.x][active.y] = highestLabel +1;
                highestLabel++;
            } else {
                labeled[active.x][active.y] = firstLabel;
            }
        }
        for(int i=0; i<width; i++)
            for(int j=0;j<height; j++)
                for(int c = 0; c<3; c++)
                    tmp[c][i][j] = labeled[i][j];
        
        tmp = LineareGrauwertspreizung_PK_Robert.stretch(tmp);
        for(int i=0; i<width; i++)
            for(int j=0;j<height; j++)
                for(int c = 0; c<3; c++)
                    dst[c][i][j] = tmp[c][i][j];
        
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
