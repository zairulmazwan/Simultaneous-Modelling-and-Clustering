package SM_HillClimbing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class HC_Run_Write {
	
	public static void writeResult (double [][] result, String modelName, long time, String run)
	{
		String filepath = "D:\\Java Output\\Experiment 4 - Sample Data Random Clusters\\HC\\NB\\HC_Result"+modelName+"_"+run+".txt";
		//String filepath = "D:\\Java Output\\Moorefield\\Simultaneous Modelling Project 1\\Hill Climbing\\HillClimbing_Result"+modelName+".txt";
		//String filepath = "C:\\Zairul Mazwan\\Java Output\\Moorefield\\Simultaneous_Modeling_Project1\\Hill Climbing\\HillClimbing_Result.txt";
		
		try
		{
			FileWriter fw = new FileWriter(filepath);
			BufferedWriter bw = new BufferedWriter (fw);
			
			bw.write("Iteration");
			bw.write(" ");
			bw.write("Current_Accuracy"+modelName);
			bw.write(" ");
			bw.write("New_Accuracy");
			bw.write(" ");
			bw.write("Cur_Cls_Size");
			bw.write(" ");
			bw.write("New_Cls_Size");
			bw.write(" ");
			bw.write("Running_Time : "+time);
			bw.newLine ();
			
			
			for (int i=0; i<result.length; i++)
			{
				double curAccuracy = result [i][1];
				double newAccuracy = result [i][2];
				double curClsSize = result [i][3];
				double newClsSize = result [i][4];
				
				bw.write(Integer.toString(i));
				bw.write(" ");
				bw.write(Double.toString(curAccuracy));
				bw.write(" ");
				bw.write(Double.toString(newAccuracy));
				bw.write(" ");
				bw.write(Double.toString(curClsSize));
				bw.write(" ");
				bw.write(Double.toString(newClsSize));
				bw.newLine();
				
			}
			
			bw.close();
			fw.close();
		}
		catch (Exception e)
		{
			System.err.println("Problem writing Hill Climbing result to the file");
		}
	}
	
	public static void writeBundles (ArrayList<ArrayList<Integer>> curCluster, String modelName, String run)
	{
		//String filepath = "C:\\Zairul Mazwan\\Java Output\\Moorefield\\Simultaneous_Modeling_Project1\\Hill Climbing\\HC_Bundles.txt";
		String filepath = "D:\\Java Output\\Experiment 4 - Sample Data Random Clusters\\HC\\NB\\HC_Bundles"+modelName+"_"+run+".txt";
		//String filepath = "D:\\Java Output\\Moorefield\\Simultaneous Modelling Project 1\\Hill Climbing\\HC_Bundles"+modelName+".txt";
		
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
