/*
 * 
 * Intelligente Sehsysteme - Uebungsblatt 1
 * Aufgabe 3
 * Pia Kullik
 * 27.10.2013
 * 
 */

import imageToolBox.AbstractFilter;

public class Farbfilter_PK extends AbstractFilter{
	
	boolean r = true, g = true, b = true; //Stelle hier ein, welche Kanaele angezeigt werden sollen
	
	public void filter(double[][][] src, double[][][] dest){
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				
				if(r) dest[0][i][j] = src[0][i][j];
				else dest[0][i][j] = 0;
				
				if(g) dest[1][i][j] = src[1][i][j];
				else dest[1][i][j] = 0;
				
				if(b) dest[2][i][j] = src[2][i][j];
				else dest[2][i][j] = 0;
			}
		}
	}

}
