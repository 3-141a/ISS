/*
 * Intelligente Sehsysteme - Uebungsblatt 7
 * Aufgabe 1 - Interaktie Binarisierung
 * Autoren:
 * Pia Kullik, Matr.nr. 2051889, BSc Informatik
 * Robert Hemstedt, Matr.nr 2536252, Nebenfach
 */

import java.util.Arrays;
import imageToolBox.AbstractFilter;

public class Binarisierung_PK_RH extends AbstractFilter {

	public void filter(double[][][] src, double dest[][][]){
		
		double[][][] tmp = new double[3][width][height];
		
		//Read desired thresholds
		double[] thr = getThresholds();
		Arrays.sort(thr);
		
		//Calculate intensity of each pixel and set greyscale value in dest accordingly
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				double intensity = (src[0][i][j] + src[1][i][j] + src[2][i][j]) / 3;
				for(int k = 0; k < thr.length-1; k++){
					if(intensity >= thr[k] && intensity <= thr[k+1]){
						for(int c = 0; c < 3; c++){
							tmp[c][i][j] = (thr[k] + thr[k])/2;
						}
					}
				}
			}
		}
		tmp = LineareGrauwertspreizung_PK_Robert.stretch(tmp);
		for(int c = 0; c < 3; c++){
			for(int i = 0; i < width; i++){
				for(int j = 0; j < height; j++){
					dest[c][i][j] = tmp[c][i][j];
				}
			}
		}
		
		
		
		
	}
	
	public double[] getThresholds(){
		double[] thr = new double[10];
		int c = 0;
		for(int i = 1; i <= 9; i++){
			String prop = "Threshold " + Integer.toString(i);
			if(getDoubleProperty(prop) != 0){ thr[c] = getDoubleProperty(prop); c++;}
		}
		thr[c] = 255;
		return Arrays.copyOfRange(thr, 0, c+1);
	}
	
	public boolean hasProperties()
	  {
	    return true;
	  }

	  @Override
	 public void initGUI()
	  {
	    addDoubleProperty("Threshold 1", 0);
	    addDoubleProperty("Threshold 2", 0);
	    addDoubleProperty("Threshold 3", 0);
	    addDoubleProperty("Threshold 4", 0);
	    addDoubleProperty("Threshold 5", 0);
	    addDoubleProperty("Threshold 6", 0);
	    addDoubleProperty("Threshold 7", 0);
	    addDoubleProperty("Threshold 8", 0);
	    addDoubleProperty("Threshold 9", 0);
	  }
	
}
