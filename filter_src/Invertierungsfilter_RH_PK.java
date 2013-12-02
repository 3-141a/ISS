/*
* Intelligente Sehsysteme - Uebungsblatt 3
* Aufgabe 5
* Autoren:
* Pia Kullik, BSc Informatik, Matr.nr. 2051889
* Robert Hemstedt, Nebenfach, Matr.nr. 2536252
*/


import imageToolBox.AbstractFilter;
public class Invertierungsfilter_RH_PK extends AbstractFilter {

	public void filter(double[][][] src, double[][][] dest){
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				for(int c = 0; c < 3; c++){
					dest[c][i][j] = 255 - src[c][i][j];
				}
			}
			
		}
		
	}
	
}
