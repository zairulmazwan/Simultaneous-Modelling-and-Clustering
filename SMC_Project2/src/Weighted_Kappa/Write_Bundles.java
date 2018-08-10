package Weighted_Kappa;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class Write_Bundles {
	
	public static void writeBundles (ArrayList<ArrayList<Integer>> curCluster, String path, String filename)
	{
		
		String filepath = path+filename;
		//String filepath = "D:\\Java Output\\VF GSA\\Bundles Results\\GSA_Bundles_"+modelName+"_"+qa+"_"+k+".txt";
		
		try
		{
			FileWriter fw = new FileWriter (filepath);
			BufferedWriter bw = new BufferedWriter (fw);
			
			int bundleSize = curCluster.size();
			
			for (int i=0; i<bundleSize; i++)
			{
				for (int j=0; j<curCluster.get(i).size(); j++)
				{
					int element = curCluster.get(i).get(j);
					bw.write(Integer.toString(element));
					bw.write(" ");
				}
				bw.newLine();
			}
					
			bw.close();
			fw.close();
		}
		catch (Exception e)
		{
			 System.err.println("Problem writing bundles to the file");
		}
		
	}
	

}
