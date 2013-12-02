/*
 * Intelligente Sehsysteme - Uebungsblatt 4
 * Aufgabe 5 - Mittelwertfilter
 * Autoren:
 * Pia Kullik, Matr.nr. 2051889, BSc Informatik
 * Robert Hemstedt, Matr.nr 2536252, Nebenfach
 */

public class Mittelwertfilter_PK_RH extends ConvolutionFilter_PK_RH {

	public void filter(double[][][] src, double[][][] dest){
		double[][][] result = applyKernel(src, getKernel());
		for(int c = 0; c < 3; c++){
			for(int i = 0; i < width; i++){
				for(int j = 0; j < height; j++){
					dest[c][i][j] = result[c][i][j];
				}
			}
		}
	}
	
	
	@Override
	public double[][] getKernel() {
		int x = (int)getDoubleProperty("x");
		int y = (int)getDoubleProperty("y");
		double[][] kernel = new double[x][y];
		for(int i = 0; i < x; i++){
			for(int j = 0; j < y; j++){
				kernel[i][j] = 1;
			}
		}
		return kernel;
	}
	
	public boolean hasProperties()
	  {
	    return true;
	  }

	 
	public void initGUI()
	  {
	    addDoubleProperty("x", 5);
	    addDoubleProperty("y", 5);
	  }

}
