package Bundles;
import java.util.ArrayList;

public class Form_8Bundles {
	
	public static void main (String [] args)
	{
		int size = 8;
		
		ArrayList<ArrayList<Integer>> bundles = getBundles (size);
		System.out.print(bundles);
		
		String filename = "Synthetic8Bundles";
		Write_Data.writeBundles(bundles, filename);
		
	}
	
	
	public static ArrayList<ArrayList<Integer>> getBundles (int size)
	{
		ArrayList<ArrayList<Integer>> bundles = new ArrayList<ArrayList<Integer>> (size);
		
		for (int i=0; i<size; i++)
		{
			set8Bundles (i, bundles);
		}
		
		return bundles;
	}
	
	
	public static void set8Bundles (int i, ArrayList<ArrayList<Integer>> bundles)
	{
		
		if (i==0)
		{
			ArrayList<Integer> inner = new ArrayList<Integer> ();
			for (int j=0; j<6; j++)
			{
				inner.add(j);
			}
			bundles.add(inner);
		}
		
		if (i==1)
		{
			ArrayList<Integer> inner = new ArrayList<Integer> ();
			for (int j=6; j<12; j++)
			{
				inner.add(j);
			}
			bundles.add(inner);
		}
		
		if (i==2)
		{
			ArrayList<Integer> inner = new ArrayList<Integer> ();
			for (int j=12; j<18; j++)
			{
				inner.add(j);
			}
			bundles.add(inner);
		}
		
		if (i==3)
		{
			ArrayList<Integer> inner = new ArrayList<Integer> ();
			for (int j=18; j<24; j++)
			{
				inner.add(j);
			}
			bundles.add(inner);
		}
		
		if (i==4)
		{
			ArrayList<Integer> inner = new ArrayList<Integer> ();
			for (int j=24; j<30; j++)
			{
				inner.add(j);
			}
			bundles.add(inner);
		}
		
		if (i==5)
		{
			ArrayList<Integer> inner = new ArrayList<Integer> ();
			for (int j=30; j<36; j++)
			{
				inner.add(j);
			}
			bundles.add(inner);
		}
		
		if (i==6)
		{
			ArrayList<Integer> inner = new ArrayList<Integer> ();
			for (int j=36; j<42; j++)
			{
				inner.add(j);
			}
			bundles.add(inner);
		}
		
		if (i==7)
		{
			ArrayList<Integer> inner = new ArrayList<Integer> ();
			for (int j=42; j<48; j++)
			{
				inner.add(j);
			}
			bundles.add(inner);
		}
	
	}

}
