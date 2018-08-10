package SM_HillClimbing;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HC_WriteResult {
	
	public static void writeResult (double [][] result, String modelName, long time)
	{
		//String filepath = "D:\\Java Output\\Other Output\\For Conference Paper Initial Analysis\\HC\\NB\\Random Initial Bundles\\Run 7\\HillClimbing_Result"+modelName+".txt";
		String filepath = "C:\\Java Output\\Classifiers Runtime Experiments\\HC_Result"+modelName+".txt";
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
	
	public static void writeBundles (ArrayList<ArrayList<Integer>> curCluster, String modelName)
	{
		//String filepath = "C:\\Zairul Mazwan\\Java Output\\Moorefield\\Simultaneous_Modeling_Project1\\Hill Climbing\\HC_Bundles_"+modelName+".txt";
		//String filepath = "D:\\Java Output\\Other Output\\For Conference Paper Initial Analysis\\HC\\NB\\Random Initial Bundles\\Run 7\\HC_Bundles"+modelName+".txt";
		String filepath = "D:\\Java Output\\Moorefield\\Simultaneous Modelling Project 1\\Hill Climbing\\HC_Bundles"+modelName+".txt";
		
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
