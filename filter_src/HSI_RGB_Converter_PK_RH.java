import imageToolBox.AbstractFilter;

public class HSI_RGB_Converter_PK_RH extends AbstractFilter {
	
	public void filter(double[][][] src, double[][][] dest){
		double[][][] tmp = new double[3][width][height];
		if(getBooleanProperty("HSI to RGB")) tmp = HSItoRGB(src);
		if(getBooleanProperty("RGB to HSI")) tmp = RGBtoHSI(src);
		tmp = LineareGrauwertspreizung_PK_Robert.stretch(tmp);
		for(int c = 0; c < 3; c++){
			for(int i = 0; i < width; i++){
				for(int j = 0; j < height; j++){
					dest[c][i][j] = tmp[c][i][j];
				}
			}
		}
		double[][][] test1 = {{{0, 100}, {60, 147}}, {{2, 52}, {255, 72}}, {{2, 128}, {210, 166}}};
		double[][][] test2 = {{{90, 10}, {121, 182}}, {{22, 38}, {92, 35}}, {{0, 161}, {65, 215}}}; 
		double[][][] result = LineareGrauwertspreizung_PK_Robert.stretch(RGBtoHSI(test1));
		
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 2; j++){
				for(int c = 0; c < 3; c++){
					System.out.print(" " + result[c][i][j]);
				}
				System.out.print(" // ");
			}
			System.out.print("\n");
		}
		
		
		result = LineareGrauwertspreizung_PK_Robert.stretch(HSItoRGB(test2));
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 2; j++){
				for(int c = 0; c < 3; c++){
					System.out.print(" " + result[c][i][j]);
				}
				System.out.print(" // ");
			}
			System.out.print("\n");
		}
	}
	
	double[][][] HSItoRGB(double[][][] pic){
		
		double[][][] rgb = new double[3][pic[0].length][pic[0][0].length];
		
		for(int j = 0; j < pic[0].length; j++){
			for(int k = 0; k < pic[0][0].length; k++){
				double h = pic[0][j][k] * (Math.PI / 180);
				double thr = (2/3) * Math.PI;
				double[] xyz = getXYZ(h, pic[1][j][k], pic[2][j][k]);
				if (h >= 2*thr){
					rgb[0][j][k] = xyz[2];
					rgb[1][j][k] = xyz[0];
					rgb[2][j][k] = xyz[1];
				}
				else if (h >= thr){
					rgb[0][j][k] = xyz[0];
					rgb[1][j][k] = xyz[1];
					rgb[2][j][k] = xyz[2];
				}
				else{
					rgb[0][j][k] = xyz[1];
					rgb[1][j][k] = xyz[0];
					rgb[2][j][k] = xyz[2];
				}
				
			}
		}
		
		return rgb;
	}
	
	public double[][][] RGBtoHSI(double[][][] pic){
		double[][][] hsi = new double[3][pic[0].length][pic[0][0].length];
		
		for(int j = 0; j < pic[0].length; j++){
			for(int k = 0; k < pic[0][0].length; k++){
				
				double sum = pic[0][j][k] + pic[1][j][k] + pic[2][j][k];
				double h = getHue(pic[0][j][k], pic[1][j][k], pic[2][j][k]);
				double s = 1 - (3 * Math.min(Math.min(pic[0][j][k], pic[1][j][k]), pic[2][j][k]) / sum);
				double i = sum / 765;
				hsi[0][j][k] = h * (180/Math.PI);
				hsi[1][j][k] = 100 * s;
				hsi[2][j][k] = 255 * i;
				
			}
		}
		
		return hsi;
	}
	
	double getHue(double r, double g, double b){ 
		
		double theta = Math.acos((0.5 * ((r - g) + (r - b))) / Math.sqrt(Math.pow((r - g), 2) + ((r - b) * (g - b))));
		double h = (theta / (2 * Math.PI)) * 360; 
		if(b < g) return h;
		else return (360 - h);
	}
	
	double[] getXYZ(double h, double s, double i){
		double[] xyz = new double[3];
		xyz[0] = (i/255) * (1 - (s/100));
		xyz[1] = (i/255) * (1 + (((s/100) * Math.cos(h)) / (Math.cos(Math.PI / (3 - h)))));
		xyz[2] = (3*i/255) - (xyz[0] + xyz[1]);
		return xyz;
	}
	
	
		
	public boolean hasProperties()
	  {
	    return true;
	  }

	  @Override
	public void initGUI()
	  {
		addBooleanProperty("RGB to HSI", true);
	    addBooleanProperty("HSI to RGB", false);
	  }

}
