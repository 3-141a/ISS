/*
 * Intelligente Sehsysteme - Uebungsblatt 5
 * Aufgabe 2 - Sobeloperator
 * Autoren:
 * Pia Kullik, Matr.nr. 2051889, BSc Informatik
 * Robert Hemstedt, Matr.nr 2536252, Nebenfach
 */

public class SobelFilter_PK_RH extends ConvolutionFilter_PK_RH {
	
	public void filter(double[][][] src, double[][][] dest){
		XSobel_PK_RH xsobel = new XSobel_PK_RH();
		YSobel_PK_RH ysobel = new YSobel_PK_RH();
		
		//Wende die Sobelfilter in x- und y- Richtung an
		double[][][] xgrad = applyKernel(src, xsobel.getKernel());
		double[][][] ygrad = applyKernel(src, ysobel.getKernel());
		double[][][] tmp = new double[3][width][height];
		
		//Berechne die Gradientenbetraege
		for(int c = 0; c < 3; c++){
			for(int i = 0; i < width; i++){
				for(int j = 0; j < height; j++){
					tmp[c][i][j] = Math.sqrt((Math.pow(xgrad[c][i][j], 2)) + (Math.pow(ygrad[c][i][j], 2)));
					//tmp[c][i][j] = xgrad[c][i][j];
					//tmp[c][i][j] = ygrad[c][i][j];
				}
			}
		}
		//Fuehre die finale lineare Streckung durch und gebe das Resultat aus
		tmp = LineareGrauwertspreizung_PK_Robert.stretch(tmp);
		for(int c = 0; c < 3; c++){
			for(int i = 0; i < width; i++){
				for(int j = 0; j < height; j++){
					dest[c][i][j] = tmp[c][i][j];
				}
			}
		}
	}

	@Override
	public double[][] getKernel() {
		// TODO Auto-generated method stub
		return null;
	}

}
