import java.util.Arrays;
import imageToolBox.AbstractFilter;

public class Medianfilter_PK_RH extends AbstractFilter {
	
	public void filter(double[][][] src, double[][][] dest){
		
		int x = (int) (getDoubleProperty("X")-1)/2;
		int y = (int) (getDoubleProperty("Y")-1)/2;
		
		for(int c = 0; c < 3; c++){
			for(int i = 0; i < width; i++){
				for(int j = 0; j < height; j++){
					double[] arr = new double[(int) (getDoubleProperty("X")*getDoubleProperty("Y"))];
					int a = 0;
					for(int I = Math.max(i-x, 0); I < Math.min(i + x + 1, width); I++){
						for(int J = Math.max(j-y, 0); J < Math.min(j + y + 1, height); J++){
							arr[a] = src[c][I][J];
							a++;
						}
					}
					Arrays.sort(arr, 0, a);
					if(a%2 == 0) dest[c][i][j] = arr[(int) a/2];
					else dest[c][i][j] = arr[(int) (a-1)/2];
				}
			}
		}
		
	}
	
	public boolean hasProperties()
	  {
	    return true;
	  }

	  @Override
	public void initGUI()
	  {
		addDoubleProperty("X");
		addDoubleProperty("Y");
	  }

}
