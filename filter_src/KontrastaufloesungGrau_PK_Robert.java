import imageToolBox.AbstractFilter;

public class KontrastaufloesungGrau_PK_Robert extends AbstractFilter {
	
	public void filter(double[][][] src, double[][][] dest)
	  {
	    double steps = getDoubleProperty("Steps");
	    int stepsize = (int) (256/steps);

	    for (int i = 0; i < width; i++)
	    {
	      for (int j = 0; j < height; j++)
	      {
	        dest[0][i][j] = (double) stepsize * ((int) src[0][i][j] / stepsize);
	        dest[1][i][j] = (double) stepsize * ((int) src[1][i][j] / stepsize);
	        dest[2][i][j] = (double) stepsize * ((int) src[2][i][j] / stepsize);
	      }

	    }
	  }

	  @Override
	  public boolean hasProperties()
	  {
	    return true;
	  }

	  @Override
	  public void initGUI()
	  {
	    addDoubleProperty("Steps", 256);
	  }

}
