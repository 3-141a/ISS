/*
 * Intelligente Sehsysteme - Uebungsblatt 6
 * Aufgabe 
 * Autoren:
 * Pia Kullik, Matr.nr. 2051889, BSc Informatik
 * Robert Hemstedt, Matr.nr 2536252, Nebenfach
 */



public class Differenz_PK_RH{
	
	public String getID()
	  {
	    return new String("<id>ImageToolBox</id>");
	  }

	  public String getNSource()
	  {
	    return new String("<nsource>2</nsource>");
	  }

	  public void filter(double[][][] src1, double[][][] src2, double[][][] dest){
			//Annahme: src1, src2 haben die gleichen Abmessungen.
			double[][][] tmp = new double[3][src1[0].length][src1[0][0].length];
			for(int c = 0; c < 3; c++){
				for(int i = 0; i < src1[0].length; i++){
					for(int j = 0; j < src1[0][0].length; j++){
						tmp[c][i][j] = src1[c][i][j] - src2[c][i][j];
					}
				}
			}
			tmp = LineareGrauwertspreizung_PK_Robert.stretch(tmp);
			for(int c = 0; c < 3; c++){
				for(int i = 0; i < src1[0].length; i++){
					for(int j = 0; j < src1[0][0].length; j++){
						dest[c][i][j] = tmp[c][i][j];
					}
				}
			}
		}
	    
	  }


