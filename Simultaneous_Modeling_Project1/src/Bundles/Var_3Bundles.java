package Bundles;
import java.util.ArrayList;

public class Var_3Bundles {
	
	public static void main (String [] args)
	{
		ArrayList<ArrayList<Integer>> bundles = VARBundles ();
		
		String filename = "VAR3Bundles";
		Write_Data.writeBundles(bundles, filename);
	}
	
	
	public static ArrayList<ArrayList<Integer>> VARBundles ()
	{
		ArrayList<ArrayList<Integer>> bundles =  new ArrayList<ArrayList<Integer>> ();
		
		int bundlesSize = 3;
		int var = 27;
		
		ArrayList<Integer> inner1 = new ArrayList<Integer> ();
		ArrayList<Integer> inner2 = new ArrayList<Integer> ();
		ArrayList<Integer> inner3 = new ArrayList<Integer> ();
		
		for (int i=0; i<27; i++)
		{
			if (i<10)
			{
				inner1.add(i);
			}
			
			if (i>9 && i<19)
			{
				inner2.add(i);
			}
			
			if (i>18)
			{
				inner3.add(i);
			}
		}
		
		bundles.add(inner1);
		bundles.add(inner2);
		bundles.add(inner3);
		
		System.out.println (bundles);
		return bundles;
	}

}
