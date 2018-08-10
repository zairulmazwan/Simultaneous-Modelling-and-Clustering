package Bundles;
//import Read_Data.Read_Data;
import VFpreparation.ReadFile;
import VFpreparation.getSource;

import java.util.ArrayList;

public class BundledData {
	
	public static void main (String [] args)
	{
		int size = 8;
		String filesource = getSource.getData();
		String sep = ",";
		double [][] rawdata = ReadFile.ReadFileArray(filesource, sep);
		ArrayList<ArrayList<Integer>> bundles = Form_8Bundles.getBundles (size);
		//ArrayList<ArrayList<Integer>> bundles =  new ArrayList<ArrayList<Integer>> ();
		//bundles = Bundles_Creation.initialCluster(bundles, rawdata[0].length);
		System.out.println(bundles);
	
			
		//Read_Data.printArray(data);
		
		System.out.println("No. of rawdata row : "+rawdata.length);
		System.out.println("No. of rawdata col : "+rawdata[0].length);
		
		
		double [][] sumData = bundlesData (rawdata, bundles);
		
		String filename1 = "sumData";
		Write_Data.writeData(filename1, sumData);
		System.out.println("No. of summed row : "+sumData.length);
		System.out.println("No. of summed col : "+sumData[0].length);
		
		double [][] avData = averagedData (bundles, sumData);
		
		System.out.println("No. of av row : "+avData.length);
		System.out.println("No. of av col : "+avData[0].length);
		
		String filename2 = "avgData";
		Write_Data.writeData(filename2, avData);
		
		String bundlesName = "Bundles";
		Write_Data.writeBundles(bundles,bundlesName);
		
	}
	
	public static double [][] bundlesData(double [][] rawData, ArrayList<ArrayList<Integer>> bundles)
	{
		int size = (bundles.size())+1;
		
		//System.out.println("Size is : "+size);
		
		double [][] data = new double [rawData.length][size];
		
		for (int row=0; row<rawData.length; row++) // to read all row of the data
		{
			for (int col=0; col<rawData[0].length; col++) //to read all column of the data
			{
				double record = round(rawData [row][col], 5); // for every column record
				sumData (record, row, col, bundles, data); // pass the each record to this method for summation
				
				//System.out.println ("Record is : "+record);
				
				if (col == rawData[0].length-1)
				{
					//System.out.println ("Record is : "+rawData[row][rawData[0].length-1]);
					data [row][size-1] = rawData[row][rawData[0].length-1];
				}
			}
			
		}
		
		return data;
	}
	
	public static void sumData (double record, int row, int col, ArrayList<ArrayList<Integer>> bundles, double [][] data)
	{
		for (int i=0; i<bundles.size(); i++) //to read all clusters
		{
			
			boolean bundlePoint = bundles.get(i).contains((col)); // if the record column is available in a particular cluster return true
			//System.out.println ("Record is : "+record);
			
				if (bundlePoint == true)
				{
					//System.out.println(col+1);	
					data [row][i] += record; //add the value of the record to a particular cluster i
					break;
				}
		}
	}
	
	public static double [][] averagedData (ArrayList<ArrayList<Integer>> bundles, double [][] data)
	{
		for (int row=0; row<data.length; row++)
		{
			for (int i=0; i<bundles.size(); i++)
			{
				int size = bundles.get(i).size();
				//System.out.println("Size is : "+size);
				
				double avData = round((data [row][i]/size), 5);
				
				data [row][i] = avData;
			}
		}
			
	
		return data;
	}
	
	
	public static double round(double value, int places) 
    {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        //System.out.println ("Factor is : "+factor);
        value = value * factor;
        //System.out.println ("Value is : "+value);
        long tmp = Math.round(value);
       //System.out.println ("tmp is : "+tmp);
        return (double) tmp / factor;
    }

}
