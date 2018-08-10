package Weighted_Kappa;
import java.util.ArrayList;
import java.util.Collections;

public class sortElement {
	
	public static void sortElement (ArrayList<ArrayList<Integer>> bundles)
	{
		int size = bundles.size();
		//ArrayList<ArrayList<Integer>> sortBundles = new ArrayList<ArrayList<Integer>> (size);
		
		for (int i=0; i<size; i++)
		{
			Collections.sort (bundles.get(i));
		}
		
		//System.out.println(bundles);
	
	}
	
	public static void main (String [] args)
	{
		String filepath =  "D:\\Java Input\\Bundles Files\\HC_Bundles.txt";
		String file_name = "HC_Bundles.txt";
		
		ArrayList<ArrayList<Integer>> bundles = ReadBundles.readBundles (filepath, file_name);
		ReadBundles.printBundles(bundles);
		System.out.println ("Elements after sorted is : ");
		sortElement (bundles);
		ReadBundles.printBundles(bundles);
	}

}
