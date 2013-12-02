import imageToolBox.AbstractFilter;

/*
 *Intelligente Sehsysteme - �bungsblatt 2
 *Aufgabe 3
 *Autoren: Pia Kullik, Robert ??
 *
 */

public class LineareGrauwertspreizung_PK_Robert extends AbstractFilter{
	
	public void filter(double[][][] src, double[][][] dest){
		
		double[] min = {getMin(0, src), getMin(1, src), getMin(2, src)};
		double[] max = {getMax(0, src), getMax(1, src), getMax(2, src)};
		for(int i = 0; i < width; i++)
	    {
	      for(int j = 0; j < height; j++)
	      {
	    	dest[0][i][j] = (src[0][i][j] - min[0]) * (max[0] / (max[0] - min[0]));
	    	dest[1][i][j] = (src[1][i][j] - min[1]) * (max[1] / (max[1] - min[1]));
	    	dest[2][i][j] = (src[2][i][j] - min[2]) * (max[2] / (max[2] - min[2]));
	      }
	    }
	}
	
	public static double[][][] stretch(double[][][] pic){
		
		double[][][] result = new double[3][pic[0].length][pic[0][0].length];
		double[] min = {getMin(0, pic), getMin(1, pic), getMin(2, pic)};
		double[] max = {getMax(0, pic), getMax(1, pic), getMax(2, pic)};
		for(int i = 0; i < pic[0].length; i++)
	    {
	      for(int j = 0; j < pic[0][0].length; j++)
	      {
	    	result[0][i][j] = (pic[0][i][j] - min[0]) * (255 / (max[0] - min[0]));
	    	result[1][i][j] = (pic[1][i][j] - min[1]) * (255 / (max[1] - min[1]));
	    	result[2][i][j] = (pic[2][i][j] - min[2]) * (255 / (max[2] - min[2]));
	      }
	    }
		return result;
	}
	
	static double getMin(int channel, double[][][] pic){
		double min = pic[channel][0][0];
		for(int i = 0; i < pic[channel].length; i++)
	    {
	      for(int j = 0; j < pic[channel][0].length; j++)
	      {
	    	  if(pic[channel][i][j] < min) min = pic[channel][i][j];
	      }
	    }
		
		return min;
	}
	
	static double getMax(int channel, double[][][] pic){
		double max = pic[channel][0][0];
		for(int i = 0; i < pic[channel].length; i++)
	    {
	      for(int j = 0; j < pic[channel][0].length; j++)
		  {
	    	  if(pic[channel][i][j] > max) max = pic[channel][i][j];
	      }
	    }
		return max;
	}

}
