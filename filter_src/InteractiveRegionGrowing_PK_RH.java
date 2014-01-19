/*
* Intelligente Sehsysteme - Uebungsblatt 9
* Aufgabe 1 - Interaktive Region Growing
* Autoren:
* Pia Kullik, Matr.nr. 2051889, BSc Informatik
* Robert Hemstedt, Matr.nr 2536252, Nebenfach
*/

import java.awt.Point;
import java.util.Stack;
import imageToolBox.AbstractFilter;

public class InteractiveRegionGrowing_PK_RH extends AbstractFilter {
	
	private Stack<Point> spoints = new Stack<Point>();
	
	@Override
	public void handleMouseClick(Point p) {
		System.out.println(p);
		this.spoints.push(p);
	}
	
	public void filter(double[][][] src, double[][][] dest){
		
		double t = getDoubleProperty("Tolerance");
		int regionCount = 0;
		int[][] inRegion = new int[width][height]; //i>0: in region i, -1: not in any region, 0: not yet checked
		Stack<Point> todo = new Stack<Point>();
		Stack<double[]> colours = new Stack<double[]>(); //paint each region a different colour
		
		while(spoints.isEmpty() == false){
			
			regionCount++;
			Point start = spoints.pop();
			todo.push(start);
			double target = (src[0][start.x][start.y] + src[1][start.x][start.y] + src[2][start.x][start.y])/3;
			double[] col = new double[3];
			for(int c = 0; c < 3; c++){
				col[c] = Math.random()*256;
			}
			colours.push(col);
			
			while(todo.isEmpty() == false){
				Point p = todo.pop();
				double intensity = (src[0][p.x][p.y] + src[1][p.x][p.y] + src[2][p.x][p.y])/3;
				inRegion[p.x][p.y] = -1;
				if(Math.abs(intensity - target) <= t){
					inRegion[p.x][p.y] = regionCount;
					for(int i = Math.max(p.x-1, 0); i <= Math.min(p.x+1, width-1); i++){
						for(int j = Math.max(p.y-1, 0); j <= Math.min(p.y+1, height-1); j++){
							if(inRegion[i][j] == 0) todo.push(new Point(i, j));
						}
					}
				}
			}
		}
		
		double[][] cArr = colours.toArray(new double[colours.size()][3]);
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				for(int c = 0; c < 3; c++){
					if(inRegion[i][j] > 0) dest[c][i][j] = cArr[inRegion[i][j] - 1][c];
					else dest[c][i][j] = src[c][i][j];
				}
			}
		}
	}
	
	
	public boolean hasProperties()
    {
		return true;
    }

  	public void initGUI()
	{
	    addDoubleProperty("Tolerance", 30);
    }

}
