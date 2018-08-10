package Bundles;

import java.util.ArrayList;

public class VAR_5Bundles {
	
	public static void main (String [] args)
	{
		ArrayList<ArrayList<Integer>> bundles = VARBundles ();
		
		String filename = "VAR5Bundles";
		Write_Data.writeBundles(bundles,filename);
		
		for (int i=0; i<bundles.size(); i++)
		{
			System.out.println ("Size "+i +" is "+bundles.get(i).size());
		}
		
		
	}
	
	public static ArrayList<ArrayList<Integer>> VARBundles ()
	{
		ArrayList<ArrayList<Integer>> bundles =  new ArrayList<ArrayList<Integer>> ();
		
		ArrayList<Integer> inner1 = new ArrayList<Integer> ();
		ArrayList<Integer> inner2 = new ArrayList<Integer> ();
		ArrayList<Integer> inner3 = new ArrayList<Integer> ();
		ArrayList<Integer> inner4 = new ArrayList<Integer> ();
		ArrayList<Integer> inner5 = new ArrayList<Integer> ();
		
		for (int i=0; i<40; i++)
		{
			if (i<10) //(10) 0-9
			{
				inner1.add(i);
			}
			else if  (i>9 && i<19) // (9) 10-18
			{
				inner2.add(i); 
			}
			
			else if (i>18 && i<27) //(8) 19-26
			{
				inner3.add(i);
			}
			
			else if (i>26 && i<34) //(7) 27-33
			{
				inner4.add(i);
			}
			else
			{
				inner5.add(i);
			}
		}
		
		bundles.add(inner1);
		bundles.add(inner2);
		bundles.add(inner3);
		bundles.add(inner4);
		bundles.add(inner5);
		
		System.out.println (bundles);
		return bundles;
	}


}
