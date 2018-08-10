package Weighted_Kappa;
import java.util.ArrayList;
import java.util.Collections;

public class sortBundles {
	
	public static void sortBundles (ArrayList<ArrayList<Integer>> bundles)
	{
		int size = bundles.size();
		//ArrayList<ArrayList<Integer>> sortBundles = new ArrayList<ArrayList<Integer>> (size);
	
		boolean noswap = false;
		while (noswap == false)
		{
			noswap = true;
			for (int i=0; i<size-1; i++)
			{
				if (bundles.get(i).get(0)>bundles.get(i+1).get(0))
				{
					ArrayList<Integer> temp = bundles.get(i);
					bundles.set(i,bundles.get(i+1));
					bundles.set(i+1, temp);
					noswap = false;
				}
			}
		}
		
		//System.out.println("Bundles after sorted : "+bundles);
	}
	
	public static void main (String [] args)
	{
		String filepath =  "D:\\Java Input\\Bundles Files\\HC_Bundles.txt";
		String file_name = "HC_Bundles.txt";
		
		ArrayList<ArrayList<Integer>> bundles = ReadBundles.readBundles (filepath, file_name);
		ReadBundles.printBundles(bundles);
		System.out.println ("Elements after sort is : ");
		sortElement.sortElement(bundles); 
		ReadBundles.printBundles(bundles);
		
		System.out.println ("Bundles after sort is : ");
		sortBundles (bundles);
		ReadBundles.printBundles(bundles);
	
	}

}
