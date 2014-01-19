/*
* Intelligente Sehsysteme - Uebungsblatt 10
* Aufgabe 1 - Formmerkmale
* Autoren:
* Pia Kullik, Matr.nr. 2051889, BSc Informatik
* Robert Hemstedt, Matr.nr 2536252, Nebenfach
*/

import java.awt.Point;
import java.io.*;
import java.util.Stack;

import imageToolBox.AbstractFilter;

public class Formmerkmale_PK_RH extends AbstractFilter {
	
	private Point start;
	
	@Override
	public void handleMouseClick(Point p) {
		System.out.println(p);
		this.start = p;
	}
	
	public void filter(double[][][] src, double[][][] dest){
		
		int[][] checked = new int[width][height];
		double[] properties = new double[6];
		Point centre = new Point(0,0);
		Stack<Point> todo = new Stack<Point>();
		todo.push(start);
		
		//compute area, contour length and centre of gravity
		while(todo.isEmpty() == false){
			
			Point p = todo.pop();
			checked[p.x][p.y] = 1;
			if(src[0][p.x][p.y] == 0){
				properties[0]++; //area
				centre.x += p.x; //centre of gravity
				centre.y += p.y;
				int local = 0;
				for(int i = Math.max(p.x-1, 0); i <= Math.min(p.x+1, width-1); i++){
					for(int j = Math.max(p.y-1, 0); j <= Math.min(p.y+1, height-1); j++){
						if(src[0][i][j] > 0) local++; //contour
						if(checked[i][j] == 0) todo.push(new Point(i, j));
					}
				}
				if (local > 0) properties[1]++; //contour
			}
		}
		centre.x /= properties[0]; //centre of gravity
		centre.y /= properties[0];
		properties[2] = properties[0] / (Math.pow(properties[1], 2));
		
		//compute moments of inertia
		todo.push(start);
		while(todo.isEmpty() == false){
			
			Point p = todo.pop();
			checked[p.x][p.y] = 1;
			if(src[0][p.x][p.y] == 0){
				properties[3] += Math.pow((p.x - centre.x), 2);
				properties[4] += Math.pow((p.y - centre.y), 2);
				properties[5] += (p.x - centre.x) * (p.y - centre.y);
				for(int i = Math.max(p.x-1, 0); i <= Math.min(p.x+1, width-1); i++){
					for(int j = Math.max(p.y-1, 0); j <= Math.min(p.y+1, height-1); j++){
						if(checked[i][j] == 0) todo.push(new Point(i, j));
					}
				}
			}
		}
		
		//write properties to file
		try{
			  FileWriter fstream = new FileWriter("Sliding_Caliper.feat");
			  BufferedWriter out = new BufferedWriter(fstream);
			  out.write("Sliding_Caliper.ppm \n");
			  for(int i = 0; i < properties.length; i++){
				  out.write(properties[i] + " \n");
			  }
			  out.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
		
	}
}
