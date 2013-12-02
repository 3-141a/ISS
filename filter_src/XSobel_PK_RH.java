/*
 * Intelligente Sehsysteme - Uebungsblatt 5
 * Aufgabe 2 - Sobeloperator
 * Autoren:
 * Pia Kullik, Matr.nr. 2051889, BSc Informatik
 * Robert Hemstedt, Matr.nr 2536252, Nebenfach
 */
 
public class XSobel_PK_RH extends ConvolutionFilter_PK_RH {
	
	@Override
	public double[][] getKernel() {
		double[][] kernel = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
		return kernel;
	}

}
