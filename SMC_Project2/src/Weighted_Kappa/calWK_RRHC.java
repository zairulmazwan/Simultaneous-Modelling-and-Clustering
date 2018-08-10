package Weighted_Kappa;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class calWK_RRHC {
	
	public static void main (String [] args)
	{
		for (int i=1; i<11; i++)
		{
			for (int j=1; j<11; j++)
			{
				//RRHC_Bundles_1_J48_run1
				System.out.println ("Weighted Kappa  : "+i);
				String filename1 = "RRHC_Bundles_"+j+"_J48_run"+i;
				//String filename1 = "RRHC_Bundles_TreeJ48_run1";
				String filepathReader =  "D:\\Java Output\\RRHC_SameSampledData\\Run "+i+"\\"+filename1+".txt";
				String filepathWriter =  "D:\\Java Output\\RRHC_SameSampledData\\Run "+i+"\\WK\\WK_"+filename1+".txt";
			
				
				String filepath2 =  "D:\\Java Input\\Bundles Files\\SixBundles_1.txt";
				String filename2 = "SixBundles_1";
				
				ArrayList<ArrayList<Integer>> bundles1 = ReadBundles.readBundles (filepathReader, filename1);
				ArrayList<ArrayList<Integer>> bundles2 = ReadBundles.readBundles (filepath2, filename2);
				
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
				writeWK(filename1, filename2, WK, filepathWriter);
			}
			
			
		}
	}
	
	public static int [][] countWK (int [][] result)
	{
		int [][] countWK = new int [2][2];
		int size = result.length;
		
		System.out.println ("\nCount of WK is : ");
		
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
		System.out.println ("Weighted Kappa is : "+WK);
		System.out.println ("-----------------------------------------");
		return WK;
		
	}
	
	public static void writeWK (String bundle1, String bundle2, double WK, String filePath)
	{
		String filepath = filePath;
		
		
		try
		{
			FileWriter fw = new FileWriter (filepath);
			BufferedWriter bw = new BufferedWriter (fw);
			
			bw.write("Bundle 1 is : "+bundle1);
			bw.newLine();	
			bw.write("Bundle 2 is : "+bundle2);
			bw.newLine();	
			
			bw.write("Weighted Kappa is : ");
			bw.write(Double.toString(WK));
			
			
			bw.close();
			fw.close();
		}
		catch (Exception e)
		{
			System.err.println("Problem writing Weighted Kappa result to the file");
		}
	}

}
