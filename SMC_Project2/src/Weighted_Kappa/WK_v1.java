package Weighted_Kappa;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

/*
 * This program is to calculate WK among bundles proposed by the optimisation search
 * Bundles from run1 is to be measured with Bundles from run2, run3, run4...
 */

public class WK_v1 {
	
	public static void main (String [] args)
	{
		int count=0;
		double [][] wks = new double [300][2];
		
		for (int i=0; i<25-1; i++)
		{
			for (int j=i+1; j<25; j++)
			{
				count++;
								
				System.out.println ("Count "+count);
				System.out.println (i+" "+j);
				runWK (i, j, count, wks);
			}
		}
		
		printMethods.printArrayDouble(wks);
		writeWKs (wks); // to write WKs result to a txt file for statistics
		
		
	}
	
	public static void runWK (int i, int j, int count, double [][] wks)
	{
		
		//RRHC_Bundles_NB_run1
		System.out.println ("Weighted Kappa  : "+i);
		String filename1 = "GSA_BestBundles_MNB_0.4_"+i;
		String filename2 = "GSA_BestBundles_MNB_0.4_"+j;
		
		System.out.println ("Bundles 1 is : "+filename1);
		System.out.println ("Bundles 2 is : "+filename2);
		
		String filepathReader1 =  "D:\\Java Output\\VF GSA\\VF GSA - Journal Paper Experiment Results\\All Data - Random Initial\\All Data Experiments\\qa 0.4\\Bundles\\"+filename1+".txt";
		String filepathReader2 =  "D:\\Java Output\\VF GSA\\VF GSA - Journal Paper Experiment Results\\All Data - Random Initial\\All Data Experiments\\qa 0.4\\Bundles\\"+filename2+".txt";
		
		String filepathWriter =  "D:\\Java Output\\VF GSA\\VF GSA - Journal Paper Experiment Results\\All Data - Random Initial\\All Data Experiments\\qa 0.4\\Bundles\\Among Bundles\\WK_"+i+"vs"+j+".txt";
	
		
		
		ArrayList<ArrayList<Integer>> bundles1 = ReadBundles.readBundles (filepathReader1, filename1);
		ArrayList<ArrayList<Integer>> bundles2 = ReadBundles.readBundles (filepathReader2, filename2);
		
		int size1 = bundles1.size();
		int size2 = bundles2.size();
		
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
		wks [count-1][0] = count; //to register WK to an array for WK statistic values
		wks [count-1][1] = WK; //to register WK to an array for WK statistic values
		
		System.out.println ("Wks : "+wks[count-1][1]);
		
		writeWK(filename1, filename2, WK, filepathWriter, size1, size2);
		
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
	
	public static void writeWK (String bundle1, String bundle2, double WK, String filePath, int size1, int size2)
	{
		String filepath = filePath;
		
		
		try
		{
			FileWriter fw = new FileWriter (filepath);
			BufferedWriter bw = new BufferedWriter (fw);
			
			bw.write("Bundle 1 is : "+bundle1);
			bw.write("\t Bundle size is : "+size1);
			bw.newLine();	
			bw.write("Bundle 2 is : "+bundle2);
			bw.write("\t Bundle size is : "+size2);
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
	
	public static void writeWKs (double [][] wk)
	{
		String filepath = "D:\\Java Output\\VF GSA\\VF GSA - Journal Paper Experiment Results\\All Data - Random Initial\\All Data Experiments\\qa 0.4\\Bundles\\Among Bundles\\WKstatistics.txt";;
		int count=0;
		
		System.out.println ("wk length is : "+wk.length);
		
		try
		{
			FileWriter fw = new FileWriter (filepath);
			BufferedWriter bw = new BufferedWriter (fw);
			
			for (int k=0; k<24; k++)
			{
				for (int j=k+1; j<25; j++)
				{
					
					double WK = wk [count][1];
					bw.write(Integer.toString(count));
					bw.write(" ");
					bw.write("WK_"+k+"vs"+j);
					bw.write(" ");
					bw.write(Double.toString(WK));
					bw.newLine();
					count++;
				}
				
			}
			
			bw.close();
			fw.close();
			
		}
		catch (Exception e)
		{
			System.err.println("Problem writing WKs results to the file");
		}
	}

}
