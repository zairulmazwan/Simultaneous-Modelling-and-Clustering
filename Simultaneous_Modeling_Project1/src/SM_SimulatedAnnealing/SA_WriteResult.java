package SM_SimulatedAnnealing;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;


public class SA_WriteResult {
	
	public static void writeResult (double [][] result, String modelName, double time)
	{
		String filepath = "D:\\Java Output\\VF GSA\\SA\\SA_Result_"+modelName+".txt";
		//String filepath = "D:\\Java Output\\Moorefield\\Simultaneous Modelling Project 1\\Simulated Annealing\\SA_Result_"+modelName+".txt";
		//String filepath = "C:\\Zairul Mazwan\\Java Output\\Moorefield\\Simultaneous_Modeling_Project1\\Simulated Annealing\\SA_Result.txt";
		
		try
		{
			FileWriter fw = new FileWriter (filepath);
			BufferedWriter bw = new BufferedWriter (fw);
			
			bw.write("Iteration");
			bw.write(" ");
			bw.write("Current_Accuracy_"+modelName);
			bw.write(" ");
			bw.write("New_Accuracy");
			bw.write(" ");
			bw.write("Temperature");
			bw.write(" ");
			bw.write("Class_Size");
			bw.write(" ");
			bw.write("Running_time : ");
			bw.write(Double.toString(time));
			bw.newLine();
			
			for (int i=0; i<result.length; i++)
			{
				double curAccuracy = result [i][1];
				double newAccuracy = result [i][2];
				double temp = result [i][3];
				double clsSize = result [i][4];
				
				bw.write(Integer.toString(i));
				bw.write(" ");
				bw.write(Double.toString(curAccuracy));
				bw.write(" ");
				bw.write(Double.toString(newAccuracy));
				bw.write(" ");
				bw.write(Double.toString(temp));
				bw.write(" ");
				bw.write(Double.toString(clsSize));
				bw.newLine();
				
			}
			
			bw.close();
			fw.close();
		}
		catch (Exception e)
		{
			   System.err.println("Problem writing Simulated Annealing result to the file");
		}
		
	}
	
	public static void writeBundles (ArrayList<ArrayList<Integer>> curCluster, String modelName)
	{
		String filepath = "D:\\Java Output\\VF GSA\\SA\\SA_Bundles_"+modelName+".txt";
		//String filepath = "D:\\Java Output\\Moorefield\\Simultaneous Modelling Project 1\\Simulated Annealing\\SA_Bundles_"+modelName+".txt";
		//String filepath = "C:\\Zairul Mazwan\\Java Output\\Moorefield\\Simultaneous_Modeling_Project1\\Simulated Annealing\\SA_Bundles.txt";
		
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
