/*
 * Intelligente Sehsysteme - Uebungsblatt 4
 * Aufgabe 2
 * Autoren:
 * Pia Kullik, Matr.nr. 2051889, BSc Informatik
 * Robert Hemstedt, Matr.nr 2536252, Nebenfach
 */


import imageToolBox.AbstractFilter;

public class Gammakorrektur_PK_RH extends AbstractFilter {
	
	public void filter(double[][][] src, double[][][] dest){
		
		double[] max = getMax(src);
		double[] min = getMin(src);
		double gamma = getDoubleProperty("Gamma");
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				for(int c = 0; c < 3; c++){
					dest[c][i][j] = (max[c] * Math.pow(((src[c][i][j] - min[c])/(max[c] - min[c])), gamma) ) + min[c];
				}
			}
		}
		
	}
	
	public double[] getMax(double[][][] pic){
		double[] max = {0, 0, 0};
		
		for(int c = 0; c < 3; c++){
			for(int i = 0; i < pic.length; i++){
				for(int j = 0; j < pic[0].length; j++){
					if(pic[c][i][j] > max[c]) max[c] = pic[c][i][j];
				}
			}	
		}
		
		return max;
	}
	
	public double[] getMin(double[][][] pic){
		double[] min = {255, 255, 255};
		
		for(int c = 0; c < 3; c++){
			for(int i = 0; i < pic.length; i++){
				for(int j = 0; j < pic[0].length; j++){
					if(pic[c][i][j] < min[c]) min[c] = pic[c][i][j];
				}
			}	
		}
		
		return min;
	}
	
	public boolean hasProperties()
	  {
	    return true;
	  }

	  @Override
	  public void initGUI()
	  {
	    addDoubleProperty("Gamma", 30);
	  }

}
