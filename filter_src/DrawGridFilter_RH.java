
import imageToolBox.AbstractFilter;

/**
 *
 * @author Robert Hemstedt
 */
public class DrawGridFilter_RH extends AbstractFilter {

    @Override
    public void filter(double[][][] src, double[][][] dst) {
        //copying source
        for(int i = 0; i< width;i++) {
            for(int j = 0; j< height; j++){
                for(int k = 0; k<3; k++){
                    dst[k][i][j] = src[k][i][j];
                }                    
            }
        }
        
        //drawing grid        
        for(int i = 0; i<width;i++) {
            for(int j = 1;j<4;j++) {
                dst[2][i][(j*height)/4] = 255;
                for( int k = 0; k<2 ; k++) dst[k][i][(j*height)/4] = 0;
            }
        }
        for(int i = 0; i<height;i++) {
            for(int j = 1;j<4;j++) {
                dst[2][(j*width)/4][i] = 255;
                for( int k = 0; k<2 ; k++) dst[k][(j*width)/4][i] = 0;
            }
        }

        //making frame
        for(int i = 0; i< width;i ++){
            dst[0][i][0] = 255;
            dst[0][i][height-1] = 255;
            for( int k = 1; k<3 ; k++) {
                dst[k][i][0] = 0;
                dst[k][i][height-1] = 0;
            }
        }
        
        for(int i = 0; i< height;i ++){
            dst[0][0][i] = 255;
            dst[0][width-1][i] = 255;
            for( int k = 1; k<3 ; k++) {
                dst[k][0][i] = 0;
                dst[k][width-1][i] = 0;
            }
        }
    }
}
