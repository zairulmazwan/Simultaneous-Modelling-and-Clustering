package Weighted_Kappa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

public class Convert_Cluster_MatlabOutput {
	
	public static void main (String [] args)
	{
		String path = "C:\\Matlab Program\\Output\\MVNormal\\Clusters Outputs\\";
		
		String path1 = "C:\\Matlab Program\\Output\\MVNormal\\Clusters Outputs\\Clusters Outputs\\";
		
		
		for (int i=1; i<26; i++)
		{
			System.out.println("Bundles no "+i);
			
			String filename = "MNV_Cluster_"+i+".csv";
			ArrayList<Integer> x = readFile(path,filename);
			System.out.println(x);
			convertIndex(x);
			System.out.println(x);
			//System.out.println(x.size());
			
			ArrayList<ArrayList<Integer>> bundles = convert2Bundles(x);
			System.out.println(bundles);
			
			String filename1 = "KM_MNV_Bundles_"+i+".txt";
			Write_Bundles.writeBundles(bundles, path1, filename1);
		}
		
		
		
	}
	
	public static ArrayList<Integer> readFile (String path, String filename)
	{
		//String a = "D:\\Java Input\\Bundles Files\\BundlesA.txt";
		//String b = "D:\\Java Input\\Bundles Files\\BundlesB.txt";
		String filepath = path+filename;
		String sep = ",";
			
		ArrayList<Integer> vfA = new ArrayList<Integer> ();
		
		int counter = 0; //to count no. of bundles in the file
		
		try
		{
			FileReader fr =  new FileReader (filepath);
			BufferedReader br = new BufferedReader (fr);
			
			String line = null;
			
			while ((line = br.readLine()) !=null)
			{
				counter++;
			}
			
			System.out.println("Number of bundles in "+filename+" is : "+counter);
			
			vfA = new ArrayList<Integer> (counter);
			
			fr = new FileReader (filepath);
			br = new BufferedReader (fr);
			
			while ((line = br.readLine())!= null)
			{
				String [] readInput = line.split(sep);
				
				
				for (int i=0; i<readInput.length; i++)
				{
					int element = Integer.parseInt(readInput[i]);
					vfA.add(element);
				}
			
			}
			
			//System.out.println ("Bundles are : "+vfA);
			
			br.close();
			fr.close();
		}
		catch(Exception e)
		{
			 System.err.println("Problem read the bundles file");
		}
		
		return vfA;
	}
	
	public static void convertIndex(ArrayList<Integer> x) //change the element in cluster to start from 0
	{
		for (int i=0; i<x.size(); i++)
		{
			int value = x.get(i);
			x.set(i, (value-1));
		}
	}
	
	public static ArrayList<ArrayList<Integer>> convert2Bundles (ArrayList<Integer> x)
	{
		int numOfCluster = (Collections.max(x)+1);
		System.out.println("Number of clusters is : "+numOfCluster);
		ArrayList<ArrayList<Integer>> bundles =  new ArrayList<ArrayList<Integer>> (numOfCluster);
		
		
		
		
		for (int i=0; i<numOfCluster; i++) // add the inner clusters
		{
			ArrayList<Integer> inner = new ArrayList<Integer>();
			bundles.add(inner);
		}
		
		System.out.println("Size is : "+bundles.size());
		
		for (int i=0; i<x.size(); i++)
		{
			int cluster = x.get(i);
			//System.out.println("cluster is : "+cluster);
			
			bundles.get(cluster).add(i);
		}
		
		
		
		return bundles;
	}

}
