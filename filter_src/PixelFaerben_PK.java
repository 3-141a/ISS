/*
 * 
 * Intelligente Sehsysteme - Uebungsblatt 1
 * Aufgabe 4
 * Pia Kullik
 * 27.10.2013
 * 
 */

import imageToolBox.AbstractFilter;

public class PixelFaerben_PK extends AbstractFilter{
	
	public void filter(double[][][] src, double[][][] dest){
		
		//Roter Rand ums Bild
		
		for(int i = 0; i < width; i++){
			dest[0][i][0] = 1000;
			dest[1][i][0] = 0;
			dest[2][i][0] = 0;

			dest[0][i][height-1] = 1000;
			dest[1][i][height-1] = 0;
			dest[2][i][height-1] = 0;
		}
		
		for(int i = 0; i < height; i++){
			dest[0][0][i] = 1000;
			dest[1][0][i] = 0;
			dest[2][0][i] = 0;

			dest[0][width-1][i] = 1000;
			dest[1][width-1][i] = 0;
			dest[2][width-1][i] = 0;
		}
	
		
		//Fuelle den Rest des Bildes
		
		int w = width/4;
		int h = height/4;
		
		for(int i = 1; i < width-1; i++){
			for(int j = 1; j < height-1; j++){
				
				if((i%w == 0) || (j%h == 0)){
					dest[0][i][j] = 0;
					dest[1][i][j] = 0;
					dest[2][i][j] = 1000;
				}
				else{
					dest[0][i][j] = src[0][i][j];
					dest[1][i][j] = src[1][i][j];
					dest[2][i][j] = src[2][i][j];
					
				}
				
			}
		}
		
		

		
	}
	
}