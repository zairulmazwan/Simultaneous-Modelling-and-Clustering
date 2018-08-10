package Weighted_Kappa;
import java.util.ArrayList;

public class pairCheck {
	
	public static int [][] getPair (ArrayList<Integer> rgf1 , ArrayList<Integer> rgf2, int noPairs) // checks each element of the rgf and register agree and disagree
	{
		int size = rgf1.size();
		int counter=0;
		int agree=0;
		int regCountAgree [][] = new int [noPairs][2];
		
		/*     		c1 c2
		 * 1-2 		1  0
		 * 1-3  	0  1
		 * 1-4 		1  1
		 * ...
		 * 10-11	1  1
		 */
		
		for (int i=0; i<size-1; i++)
		{
			for (int j=i+1; j<size; j++)
			{
					counter++;
					//System.out.println ("No : "+counter);
					//System.out.println (+i +" "+j);
					//getClusterPair(rgf, i, j);
					int x1 = rgf1.get(i);
					int y1 = rgf1.get(j);
					
					int x2 = rgf2.get(i);
					int y2 = rgf2.get(j);
					
					int cluster1 = 0;
					int cluster2 = 0;
					
					if (x1==y1) //check element in rgf 1
					{
						agree++;
						//System.out.println ("No which agree in cluster 1 is : "+counter);
						//System.out.println (+i +" "+j);
						//System.out.println (+x1 +" "+y1);
						cluster1 = 1;
					}
					
					
					if (x2==y2) //check element in rgf 2
					{
						agree++;
						//System.out.println ("No which agree in cluster 2 is : "+counter);
						//System.out.println (+i +" "+j);
						//System.out.println (+x2 +" "+y2);
						cluster2 = 1;
					}
					
					regCountAgree = regAgree (regCountAgree, cluster1, cluster2, counter);
			}
		}
		
		//printMethods.printArray (regCountAgree);
		
		return regCountAgree;
	}
	
	public static int [][] regAgree (int [][] AgreeCount, int c1, int c2, int counter)
	{
		//System.out.println ("counter is : "+counter);
		AgreeCount [counter-1][0] = c1;
		AgreeCount [counter-1][1] = c2;
		return AgreeCount;
	}
	
	
	
	public static int countPair (int max)
	{
		max = max-1;
		int count=0;
		
		for (int i=0; i<=max; i++)
		{
			count +=i; 
		}
		
		return count;
		
	}
	
	
	
	public static void main (String [] args)
	{
		String filepath1 =  "D:\\Java Input\\Bundles Files\\b1.txt";
		String file_name1 = "b1.txt";
		
		String filepath2 =  "D:\\Java Input\\Bundles Files\\b2.txt";
		String file_name2 = "b2.txt";
		
		ArrayList<ArrayList<Integer>> bundles1 = ReadBundles.readBundles (filepath1, file_name1);
		int max = convertRGF.findMax (bundles1);
		
		System.out.println("Max value in bundle 1 is : "+max);
		System.out.println("Bundles 1 is : "+bundles1);
		System.out.println("Number of bundles in bundles 1 is : "+bundles1.size()+"\n");
		
		sortElement.sortElement(bundles1);
		sortBundles.sortBundles(bundles1);
		
		ArrayList<ArrayList<Integer>> bundles2 = ReadBundles.readBundles (filepath2, file_name2);
		int max2 = convertRGF.findMax (bundles2);
		
		System.out.println("Max value is : "+max2);
		System.out.println("BUndles is : "+bundles2);
		System.out.println("Number of bundles is : "+bundles2.size());
		
		sortElement.sortElement(bundles2);
		sortBundles.sortBundles(bundles2);
		
		int noPairs = countPair (max);
		System.out.println("Number of pairs is " +noPairs);
		
		//System.out.println("Bundle after sort is : "+bundles);
		
		ArrayList<Integer> rgf1 = convertRGF.convertRGF (bundles1);
		ArrayList<Integer> rgf2 = convertRGF.convertRGF (bundles2);
		System.out.println ("Bundle in RGF 1 is : "+rgf1);
		System.out.println ("Bundle in RGF 2 is : "+rgf2);

		
		getPair (rgf1, rgf2, noPairs);
//		printArray (bundles1);
//		printArray (bundles2);
		
	}

}
