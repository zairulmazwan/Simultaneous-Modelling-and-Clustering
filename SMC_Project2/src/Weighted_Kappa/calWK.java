package Weighted_Kappa;

/*
 This program is to calculate WK to see agreement between the proposed bundles by the algorithms and the six fibre bundles
 */

import java.util.ArrayList;

public class calWK {
	
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
	
	
	public static void main (String [] args)
	{
		double [][] WKresult = new double [25][2];
		
/*		for (int i=0; i<5; i++)
		{
			//SA_Bundles_MNB_run1
			String file_name1 = "Bundles_"+i;
			String filepath1 =  "D:\\Java Output\\SMC Framework Benchmark\\SA\\"+file_name1+".txt";
			
			String file_name2 = "MV3Bundles.txt";
			String filepath2 =  "D:\\Java Input\\Bundles Files\\"+file_name2;
			
			
			
			ArrayList<ArrayList<Integer>> bundles1 = ReadBundles.readBundles (filepath1, file_name1);
			ArrayList<ArrayList<Integer>> bundles2 = ReadBundles.readBundles (filepath2, file_name2);
			
			sortElement.sortElement(bundles1);
			sortBundles.sortBundles(bundles1);
			
			sortElement.sortElement(bundles2);
			sortBundles.sortBundles(bundles2);
			
			int max = convertRGF.findMax (bundles2);
			int noPairs = pairCheck.countPair (max);
			
			ArrayList<Integer> rgf1 = convertRGF.convertRGF (bundles1);
			ArrayList<Integer> rgf2 = convertRGF.convertRGF (bundles2);
			
			System.out.println ("RGF for bundles 1 : "+rgf1);
			System.out.println ("RGF for bundles 2 : "+rgf2);
			
			int [][] result = new int [noPairs][2];
			result = pairCheck.getPair (rgf1, rgf2, noPairs);
			
			int [][] cWK = countWK (result);
			
			printMethods.printArray(cWK);
			
			double WK = calWK (cWK);
			
			WKresult [i][0] = i;
			WKresult [i][1] = WK;
			
			String pathWrite = "D:\\Java Output\\SMC Framework Benchmark\\SA\\WK\\"+"WK_"+file_name1+".txt";
			WK_writer.writeWK(file_name1, file_name2, WK, pathWrite);
			
		}
*/		
		for (int i=0; i<10; i++)
		{
			//SA_Bundles_MNB_run1
		String file_name1 = "HC_BundlesNBU_run_"+i;
		String filepath1 =  "D:\\Java Output\\SMC Framework Benchmark\\HC\\"+file_name1+".txt";
		
		String file_name2 = "MV3Bundles.txt";
		String filepath2 =  "D:\\Java Input\\Bundles Files\\"+file_name2;
		
		ArrayList<ArrayList<Integer>> bundles1 = ReadBundles.readBundles (filepath1, file_name1);
		ArrayList<ArrayList<Integer>> bundles2 = ReadBundles.readBundles (filepath2, file_name2);
		
		double WK = calWeightedKappa (bundles1, bundles2);
		
		WKresult [i][0] = i;
		WKresult [i][1] = WK;
		
		String pathWrite = "D:\\Java Output\\SMC Framework Benchmark\\HC\\WK\\"+"WK_"+file_name1+".txt";
		WK_writer.writeWK(file_name1, file_name2, WK, pathWrite);
		
		}
		
		String pathWrite2 = "D:\\Java Output\\SMC Framework Benchmark\\HC\\WK\\WK_Result.txt";
		WK_writer.writeWKResult(WKresult, pathWrite2);
	}

}
