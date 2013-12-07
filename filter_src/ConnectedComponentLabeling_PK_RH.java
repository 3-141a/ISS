/*
 * Intelligente Sehsysteme - Uebungsblatt 7
 * Aufgabe 2 - Connected Component Labeling
 * Autoren:
 * Pia Kullik, Matr.nr. 2051889, BSc Informatik
 * Robert Hemstedt, Matr.nr 2536252, Nebenfach
 */

import imageToolBox.AbstractFilter;

public class ConnectedComponentLabeling_PK_RH extends AbstractFilter {
	
	public void filter(double[][][] src, double[][][] dest){
		
		int[][] tmp = new int[width][height]; //store labels here
		int label = 0;
		int[] xLabels; //equivalence table
		double[][] colours; //colour table
		
		//label the pixels
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				if(src[0][i][j] == 0){
					if (getLabel(tmp, i, j) < Integer.MAX_VALUE) tmp[i][j] = getLabel(tmp, i, j);
					else {label++; tmp[i][j] = label;}
				}
			}
		}
		
		//resize tables
		xLabels = new int[label];
		colours = new double[label][3];
		
		//generate colours for each label
		for(int i = 0; i < label; i++){
			xLabels[i] = i+1;
			colours[i] = getColour();
		}
		
		//create equivalence table
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				if(tmp[i][j] > 0){
					int localmin = tmp[i][j];
					for(int k = Math.max(0, i-1); k <= Math.min(i+1, width-1) ; k++){
						for(int l = Math.max(0, j-1); l <= Math.min(j+1, height-1); l++){
							if(tmp[k][l]>0 && xLabels[tmp[k][l]-1]<localmin) localmin = xLabels[tmp[k][l]-1];
						}
					}
					xLabels[tmp[i][j] - 1] = localmin;
				}
			}
		}
		
		//create output
		for(int c = 0; c < 3; c++){
			for(int i = 0; i < width; i++){
				for(int j = 0; j < height; j++){
					if(tmp[i][j] == 0) dest[c][i][j] = 255;
					else dest[c][i][j] = colours[xLabels[tmp[i][j]-1]-1][c];
				}
			}
		}	
	}
	
	public int getLabel(int[][] pic, int i, int j){
		
		int min = Integer.MAX_VALUE;
		
		for(int k = Math.max(0, i-1); k <= Math.min(i+1, pic.length-1) ; k++){
			for(int l = Math.max(0, j-1); l <= Math.min(j+1, pic[0].length-1); l++){
				if(pic[k][l] != 0){
					if(pic[k][l] != min){
						if(pic[k][l] < min) min = pic[k][l];
					}
				}
			}
		}
		return min;
	}
	
	public double[] getColour(){
		double[] col = new double[3];
		for(int i = 0; i < 3; i++){
			col[i] = Math.floor(Math.random() * 255);
		}
		return col;
	}
	
}
