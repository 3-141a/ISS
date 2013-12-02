/*
 * Intelligente Sehsysteme - Uebungsblatt 4
 * Aufgabe 4 - Konvolutionsfilter
 * Autoren:
 * Pia Kullik, Matr.nr. 2051889, BSc Informatik
 * Robert Hemstedt, Matr.nr 2536252, Nebenfach
 */
 
import imageToolBox.AbstractFilter;

public abstract class ConvolutionFilter_PK_RH extends AbstractFilter {
	
	public double[][][] applyKernel(double[][][] src, double[][] kernel){
		
		int dx = (int) Math.floor(kernel.length/2); // Grenzdistanz-1. Annahme: Es werden nur ungerade natuerliche Zahlen als Abmessungen der Maske eingegeben.
		int dy = (int) Math.floor(kernel[0].length/2);
		double conv; //Faltungswert
		double[][][] dest = new double[3][width][height];
		
		for(int c = 0; c < 3; c++){
			for(int i = 0; i < width; i++){
				for(int j = 0; j < height; j++){
					//Berechne die Faltung fuer das  Pixel (i, j)
					conv = 0;
					for(int x = i - dx; x <= i + dx; x++){
						for(int y = j - dy; y <= j + dy; y++){
							if(x >=0 && x < width && y >=0 && y < height){
								conv += kernel[x - i + dx][y - j + dy] * src[c][x][y];
							}
						}
					}
					dest[c][i][j] = conv;
				}
			}
		}
		return dest;
	}
	
	public void filter(double[][][] src, double[][][] dest){
		double[][][] tmp = applyKernel(src, getKernel());
		for(int c = 0; c < 3; c++){
			for(int i = 0; i < width; i++){
				for(int j = 0; j < height; j++){
					dest[c][i][j] = tmp[c][i][j];
				}
			}
		}
	}

	public abstract double[][] getKernel();
	/*
	 * Definiere die Faltungsmaske als 2D-Array, in dem die Gewichte
	 * codiert sind. Dabei ist jeder Eintrag das gewuenschte Gewicht fuer das jeweilige Pixel 
	 * multipliziert mit der Groesse der Maske. ZB fuer den (3x3)-Mittelwertsfilter:
	 * Gewichte:   (1/9 1/9 1/9  ==> Maske:	(1 1 1
	 *  		 	1/9 1/9 1/9				 1 1 1
	 *  			1/9 1/9 1/9)			 1 1 1)
	 */
	
	
	
}
