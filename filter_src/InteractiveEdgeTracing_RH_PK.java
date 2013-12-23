
import imageToolBox.AbstractFilter;
import java.awt.Point;
import java.util.LinkedList;

/**
 *
 * @author Robert Hemstedt
 * @author Pia Kullik
 */
public class InteractiveEdgeTracing_RH_PK extends AbstractFilter{
    
    Point startPoint;
    boolean startProvided = false;
    
    @Override
    public void filter(double[][][] src, double[][][] dst) {
        if(!startProvided) {
             System.out.println("No start point has been provided. Doing nothing");
             return;
         }
        double[][][] gradients = new double[width][height][3]; // Index 0 = absolute value, Index 1 = angle, Index 2 = direction bin
        double[][][] searchMasks = {
            {{0,0,0,0,1},{0,0,0,1,1},{0,0,0,1,1},{0,0,0,1,1},{0,0,0,0,1}}, /*   0 == 0 */
            {{0,0,0,0,0},{0,0,0,0,0},{0,0,0,1,1},{0,0,1,1,1},{0,0,1,1,1}}, /*  45 == 1*/
            {{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,1,1,1,0},{1,1,1,1,1}}, /*  90 == 2 */
            {{0,0,0,0,0},{0,0,0,0,0},{1,1,0,0,0},{1,1,1,0,0},{1,1,1,0,0}}, /* 135 == 3*/
            {{1,0,0,0,0},{1,1,0,0,0},{1,1,0,0,0},{1,1,0,0,0},{1,0,0,0,0}}, /* 180 == 4*/
            {{1,1,1,0,0},{1,1,1,0,0},{1,1,0,0,0},{0,0,0,0,0},{0,0,0,0,0}}, /* 225 == 5*/
            {{1,1,1,1,1},{0,1,1,1,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0}}, /* 270 == 6*/
            {{0,0,1,1,1},{0,0,1,1,1},{0,0,0,1,1},{0,0,0,0,0},{0,0,0,0,0}}  /* 315 == 7*/
        };
        
        GreyFilter_RH greyfilter = new GreyFilter_RH();
        greyfilter.setDimensions(width, height);
        double[][][] tmp = new double[3][width][height];
        greyfilter.filter(src, tmp);
        
        // get sobel values:
        double maxSobelVal = 0;
        for(int i = 0; i< width; i++) {
            for (int j = 0; j < height; j++) {
                double[] sobelAnswer = getSobelValues(tmp, i, j);
                for(int c = 0; c<3; c++) {
                    gradients[i][j][c] = sobelAnswer[c];
                    dst[c][i][j] = src[c][i][j];
                }
                if(sobelAnswer[0] > maxSobelVal) maxSobelVal = sobelAnswer[0];
            }
        }
        
        // find start point with highest sobel answer
        Point newStart = new Point(startPoint);
        double startVal=0;
        for(int i=-1; i<2; i++) {
            for(int j = -1; j<2; j++) {
                int xMod = modPix(startPoint.x +i, width);
                int yMod = modPix(startPoint.y+j, height);
                if (gradients[xMod][yMod][0] > startVal) {
                    startVal = gradients[xMod][yMod][0];
                    newStart.x = xMod;
                    newStart.y = yMod;
                }
            }
        }
        
        // edge linking
        boolean[][] marked = new boolean[width][height];
        double edgeThreshold = 0.1; // threshold for edges relative to maximum
        LinkedList<Point> queue = new LinkedList<>();
        LinkedList<Point> edge = new LinkedList<>(); // edge to be found
        queue.add(newStart);
        edge.add(newStart);
        while(!queue.isEmpty()) { //work with queue
            Point curPoint = queue.removeFirst();
            if(!marked[curPoint.x][curPoint.y]) {
                marked[curPoint.x][curPoint.y] = true;
                double[] grad = gradients[curPoint.x][curPoint.y];//getSobelValues(tmp, curPoint.x, curPoint.y);//
                // get search Mask for orthogonal direction of gradient
                double[][] searchMask = searchMasks[((int)grad[2]+2)%8];//rotate by 90 degrees
                for(int i = -2; i<3; i++) {
                    for(int j=-2; j<3; j++) {
                        if(searchMask[2+j][2+i] == 0) continue; // y and x in matrices are swapped
                        int xmod = modPix(curPoint.x+i, width);
                        int ymod = modPix(curPoint.y+j, height);
                        if(gradients[xmod][ymod][0]/maxSobelVal < edgeThreshold) continue;
                        // is edge
                        double valDiff = Math.abs(grad[0] - gradients[xmod][ymod][0]); // similar value
                        double angleDiff = Math.abs(grad[1] - gradients[xmod][ymod][1]); // between 0 and 2PI
                        if (angleDiff > Math.PI) {
                            angleDiff -= Math.PI;
                        }
                        if (valDiff < 0.15*maxSobelVal && angleDiff < Math.PI / 8) { //similar
                            queue.addLast(new Point(xmod, ymod));
                            edge.add(new Point(xmod, ymod));
                        }
                    }
                }
            }
        }
        for(int i=0; i<edge.size(); i++) {
            for(int c = 0; c<3; c++) {
                dst[c][edge.get(i).x][edge.get(i).y] = (c==0)?255:0; //mark Edge red
            }
        }
    }

    @Override
    public void handleMouseClick(Point p) {
         startPoint = p;
         startProvided = true;
    }
    
    //calculate sobel filter values
    double[] getSobelValues(double[][][] src, int x, int y) {
        double[][] xMask = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        double[][] yMask = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};
        double xGrad = 0;
        double yGrad = 0;
        double val;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int modx = modPix(x + i, width);
                int mody = modPix(y + j, height);
                xGrad += xMask[1 - j][1 - i] * src[0][modx][mody]; //reversed x and y in filter masks
                yGrad -= yMask[1 - j][1 - i] * src[0][modx][mody]; // and y has reverted direction
            }
        }
        val = Math.sqrt(xGrad*xGrad+yGrad*yGrad);
        double angle = Math.atan2(yGrad, xGrad);
        if(angle < 0) angle += 2*Math.PI; //extend values to full circle
        double origAngle = angle;
        angle += Math.PI/8; // rotate angles for better bin calculation
        if(angle >= 2*Math.PI) angle -= 2*Math.PI;
        return new double[]{val,origAngle,Math.floor(angle/(Math.PI/4))};
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