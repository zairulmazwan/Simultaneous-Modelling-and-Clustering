package Bundles;

import java.util.ArrayList;

public class VF_Resem_Bundles {
	
	public static ArrayList<ArrayList<Integer>> definedBundles ()
	{
		ArrayList<ArrayList<Integer>> bundles = new ArrayList<ArrayList<Integer>> (8);
		
		
		//System.out.println ("Bundles size is : "+bundles.size());
		int count = 0;
		for (int i=0; i<8; i++)
		{
			int size = 0;
			
			if (i==0)
			{
				size = 4;
			}
			else if (i==1)
			{
				size = 5;
			}
			else if (i==2)
			{
				size = 6;
			}
			else if (i==3)
			{
				size = 6;
			}
			else if (i==4)
			{
				size = 5;
			}
			else if (i==5)
			{
				size = 9;
			}
			else if (i==6)
			{
				size = 10;
			}
			else
			{
				size = 4;
			}
			
			ArrayList<Integer> inner = new ArrayList<Integer> ();
			
			for (int j=0; j<size; j++)
			{
				count++;
				inner.add(count);
			}
			
			//System.out.println ("inner size is : "+inner.size());
			bundles.add(inner);
		
		}
		
		//System.out.println ("Bundles is : "+bundles);
		return bundles;
	}

}
