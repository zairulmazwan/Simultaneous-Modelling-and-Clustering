package VFSix_Bundles;
import java.util.ArrayList;

import VFpreparation.ReadFile;
import VFpreparation.form_ClusteredData;
import VFpreparation.getSource;
import VFpreparation.form_ClusteredData;

public class Main_SixBundles {
	
	public static void main (String [] args)
	{
		int cluster = 6;
				
		ArrayList<ArrayList<Integer>> VFCluster = getClusters.getCluster(cluster);
		getClusters.printCluster(VFCluster);
		
		String filesource = getSource.getData();
		String sep = ",";
		
		double [][] rawData = ReadFile.ReadFileArray(filesource, sep); //raw data
		
		//print(rawData);
		
		double [][] BundledData = null;
		
		BundledData = form_ClusteredData.getData(VFCluster, rawData); //prepare a VF data according to the bundles
		print(BundledData);
		
		writeData.write_Data(BundledData);
	}
	
	public static void print (double [][] x)
	{
		for (int i=0; i<x.length; i++)
		{
			for (int j=0; j<x[i].length; j++)
			{
				System.out.print(x[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}

}
