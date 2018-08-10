package Weighted_Kappa;

import java.util.ArrayList;

public class Cal_WK_Bundles_Vs_6NFB {
	
	public static void main (String [] args)
	{
		double [][] WKresult = new double [25][2];
		
		for (int i=0; i<25; i++)
		{
			//Clusters
			String file_name1 = "KM_Bundles_"+(i+1);
			String filepath1 =  "C:\\Matlab Program\\Output\\Kmeans_Bundles\\Bundles Outputs\\"+file_name1+".txt";
			
			//to verify the code by using the same clusters (6NFB) - output is 1.0
			//String file_name1 = "Six_Bundles.txt";
			//String filepath1 =  "D:\\Java Input\\Bundles Files\\"+file_name1;
			
			
			//6NFB
			String file_name2 = "Six_Bundles.txt";
			String filepath2 =  "D:\\Java Input\\Bundles Files\\"+file_name2;
			
			ArrayList<ArrayList<Integer>> bundles1 = ReadBundles.readBundles (filepath1, file_name1);
			ArrayList<ArrayList<Integer>> bundles2 = ReadBundles.readBundles (filepath2, file_name2);
			
			double WK = calWeightedKappa (bundles1, bundles2);
			
			WKresult [i][0] = i;
			WKresult [i][1] = WK;
		}
		
		printMethods.printArrayDouble(WKresult);
	}
	
	public static double calWeightedKappa (ArrayList<ArrayList<Integer>> curBundle, ArrayList<ArrayList<Integer>> bundle)
	{
		double wk = 0;
		
		sortElement.sortElement(curBundle);
		sortBundles.sortBundles(curBundle);
		
		sortElement.sortElement(bundle);
		sortBundles.sortBundles(bundle);
		
		int max = convertRGF.findMax (bundle);
		int noPairs = pairCheck.countPair (max);
		
		ArrayList<Integer> rgf1 = convertRGF.convertRGF (curBundle);
		ArrayList<Integer> rgf2 = convertRGF.convertRGF (bundle);
		
		int [][] result = new int [noPairs][2];
		result = pairCheck.getPair (rgf1, rgf2, noPairs);
		
		int [][] cWK = countWK (result);
		wk = calWK (cWK);
		
		return wk;
	}
	
	public static int [][] countWK (int [][] result)
	{
		int [][] countWK = new int [2][2];
		int size = result.length;
		
		//System.out.println ("\nCount of WK is : ");
		
		for (int i=0; i<size; i++)
		{
			int c1 = result[i][0];
			int c2 = result[i][1];
			
			if (c1==1 && c2==1) // get the count for Agree Agree
			{
				countWK[0][0]++;
			}
			
			if (c1==1 && c2==0) // get the count for Agree Disagree
			{
				countWK[0][1]++;
			}
			
			if (c1==0 && c2==1) // get the count for Disagree Agree
			{
				countWK[1][0]++;
			}
			
			if (c1==0 && c2==0) // get the count for Disagree Disagree
			{
				countWK[1][1]++;
			}
		}
		return countWK;
	}
	
	public static double calWK (int [][] countWK)
	{
		double WK = 0.0;
		
		int n = countWK[0][0]+countWK[0][1]+countWK[1][0]+countWK[1][1];
		double nn = Math.pow(n,2);
				
		double Pob = countWK[0][0]+countWK[1][1];
		Pob /=n;
		//System.out.println ("Pob is : "+Pob);
		
		double row1 = countWK[0][0]+countWK[0][1];
		//System.out.println ("Row1 is : "+row1);
		
		double row2 = countWK[1][0]+countWK[1][1];
		//System.out.println ("Row2 is : "+row2);
		
		double col1 = countWK[0][0]+countWK[1][0];
		//System.out.println ("Col1 is : "+col1);
		
		double col2 = countWK[0][1]+countWK[1][1];
		//System.out.println ("Col2 is : "+col2);
		
		// Pex = (row1*col1 + row2*col2)/N^2 
		
		double Pex = ((row1*col1)+(row2*col2))/(Math.pow(n,2));
		//System.out.println ("Pex is : "+Pex);
		
		// WK = (Pob - Pex)/(1-Pex)
		
		WK = (Pob-Pex)/(1-Pex);
		//System.out.println ("Weighted Kappa is : "+WK);
		
		return WK;
		
	}
	

}
