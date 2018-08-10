package Weighted_Kappa;
import java.util.ArrayList;
import java.util.Collections;

public class convertRGF {
	
	public static ArrayList<Integer> convertRGF (ArrayList<ArrayList<Integer>> bundles)
	{
		ArrayList<ArrayList<Integer>> group = new ArrayList<ArrayList<Integer>> ();
		ArrayList<Integer> rgf =  new ArrayList<Integer>();
		
		group = cloneBundle (bundles);
		
		if ((group.get(0).get(0))==0) // to consider if the set of bundles has element '0'. Thus make it becomes +1 and so other elements.
		{
			checkZeroElement (group);
		}
		
		int max = findMax (group);
		//System.out.println("Max is "+max);
		//System.out.println(group);
		
		for (int i=1; i<=max; i++)
		{
			rgf.add(-1); //add a dummy number into a rgf arraylist
		}
		
		for (int i=0; i<group.size(); i++)
		{
			for (int j=0; j<group.get(i).size(); j++)
			{
				int index = (group.get(i).get(j))-1;
				//System.out.println("index is "+index);
				rgf.set(index, (i+1));
			}
		}
		
		return rgf;
	}
	
	public static ArrayList<ArrayList<Integer>> cloneBundle (ArrayList<ArrayList<Integer>> bundles)
	{
		ArrayList<ArrayList<Integer>> newBundles = new ArrayList<ArrayList<Integer>> (bundles.size());
		
		for (int i=0; i<bundles.size(); i++)
		{
			ArrayList<Integer> inner = new ArrayList<Integer> ();
			for (int j=0; j<bundles.get(i).size(); j++)
			{
				int element = bundles.get(i).get(j);
				inner.add(element);
			}
			newBundles.add(inner);
		}
		
		return newBundles;
	}
	
	public static int findMax (ArrayList<ArrayList<Integer>> bundles)
	{
		int max = -1;
			
		for (int i=0; i<bundles.size(); i++)
		{
			int tempMax = Collections.max (bundles.get(i));
			max = Math.max(tempMax, max);
			
		}
		
		if ((bundles.get(0).get(0))==0) max++; // add 1 to the max to consider the '0' value which will be added with 1 (and so other elements).
		return max;
	}
	
	public static void checkZeroElement (ArrayList<ArrayList<Integer>> bundles)
	{
		for (int i=0; i<bundles.size(); i++)
		{
			for (int j=0; j<bundles.get(i).size(); j++)
			{
				int element = bundles.get(i).get(j);
				element++;
				
				bundles.get(i).set(j,element);
			}
		}
	}
	
	public static void main (String [] args)
	{
		String filepath =  "D:\\Java Input\\Bundles Files\\SA_Bundles_J48.txt";
		String file_name = "RRHC_Bundles0_NBayesMultinomial.txt";
		
		ArrayList<ArrayList<Integer>> bundles = ReadBundles.readBundles (filepath, file_name);
		int max = findMax (bundles);
		
		System.out.println("Max value is : "+max);
		System.out.println("BUndles is : "+bundles);
		System.out.println("Number of bundles is : "+bundles.size());
		
		sortElement.sortElement(bundles);
		sortBundles.sortBundles(bundles);
		
		System.out.println("Bundle after sort is : "+bundles);
		
		ArrayList<Integer> rgf = convertRGF (bundles);
		System.out.println (rgf);
	}

}
