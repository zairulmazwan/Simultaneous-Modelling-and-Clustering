package Bundles;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Write_Data {
	
	public static void writeData (String filename, double [][] data)
	{
		String filepath = "D:\\Zairul\\Output\\Journal Paper Experiment - SMC Framework Test\\GSA\\"+filename+".txt";
		//String filepath = "C:\\Zairul Mazwan\\Java Output\\SMC Framework Benchmark\\"+filename+".txt";
		
		try
		{
			FileWriter fw = new FileWriter (filepath);
			BufferedWriter bw = new BufferedWriter (fw);
			
			for (int i=0; i<data.length; i++)
			{
				for (int j=0; j<data[i].length; j++)
				{
					double record = data [i][j];
					bw.write(Double.toString(record));
					bw.write (" ");
					
				}
				bw.newLine();
			}
			
			bw.close();
			fw.close();
			
		}
		catch (IOException e)
		{
			 System.err.println("Problem writing all data to the file");
		}
		
	}
	
	
	
	public static void writeBundles (ArrayList<ArrayList<Integer>> curCluster, String filename)
	{
		//String filepath = "C:\\Zairul Mazwan\\Java Output\\Moorefield\\Simultaneous_Modeling_Project1\\Hill Climbing\\HC_Bundles_"+modelName+".txt";
		//String filepath = "D:\\Java Output\\Other Output\\For Conference Paper Initial Analysis\\HC\\NB\\Random Initial Bundles\\Run 7\\HC_Bundles"+modelName+".txt";
		//String filepath = "C:\\Zairul Mazwan\\Java Output\\SMC Framework Benchmark\\Bundles.txt";
		String filepath = "D:\\Zairul\\Output\\Journal Paper Experiment - SMC Framework Test\\GSA\\"+filename+".txt";
		
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
