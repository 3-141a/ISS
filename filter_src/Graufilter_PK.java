/*
 * 
 * Intelligente Sehsysteme - Uebungsblatt 1
 * Aufgabe 2
 * Pia Kullik
 * 27.10.2013
 * 
 */

import imageToolBox.AbstractFilter;

public class Graufilter_PK extends AbstractFilter
{
  public void filter(double[][][] src, double[][][] dest)
  {
    for(int i = 0; i < width; i++)
    {
      for(int j = 0; j < height; j++)
      {
    	  for(int k = 0; k < 3; k++){
    		  dest[k][i][j] = (src[0][i][j] + src[1][i][j] + src[2][i][j])/3;
    	  }
      }
    }
  }
}