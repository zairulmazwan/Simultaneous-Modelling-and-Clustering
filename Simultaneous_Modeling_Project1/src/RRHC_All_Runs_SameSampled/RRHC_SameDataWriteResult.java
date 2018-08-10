package RRHC_All_Runs_SameSampled;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import VFpreparation.getSource;

public class RRHC_SameDataWriteResult {
	
	public static void writeResult (double [][] result, String modelName, long time, int restart, String run, int running)
	{
		//String filepath = "D:\\Java Output\\Other Output\\For Conference Paper Initial Analysis\\RRHC\\NB\\Random Initial Bundle\\Run 5\\RRHC_Result_"+restart+"_"+modelName+".txt";
		
		String filepath = "D:\\Java Output\\RRHC_SameSampledData\\run "+running+"\\RRHC_Result_"+restart+"_"+modelName+"_"+run+".txt";
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
	
	public static void writeBundles (ArrayList<ArrayList<Integer>> curCluster, int restart, String modelName, String run, int running)
	{
		//String filepath = "C:\\Zairul Mazwan\\Java Output\\Moorefield\\Simultaneous_Modeling_Project1\\Hill Climbing\\HC_Bundles.txt";
		//String filepath = "D:\\Java Output\\Other Output\\For Conference Paper Initial Analysis\\RRHC\\NB\\Random Initial Bundle\\Run 5\\RRHC_Bundles_"+restart+"_"+modelName+".txt";
		String filepath = "D:\\Java Output\\RRHC_SameSampledData\\run "+running+"\\RRHC_Bundles_"+restart+"_"+modelName+"_"+run+".txt";
		
		
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
	
	public static void writetime (double time, String modelName, String run, int running)
	{
				//String filepath = "C:\\Zairul Mazwan\\Java Output\\Moorefield\\Simultaneous_Modeling_Project1\\Hill Climbing\\HC_Bundles.txt";
				
				String filepath = "D:\\Java Output\\RRHC_SameSampledData\\run "+running+"\\RRHC_runningtime_"+modelName+"_"+run+".txt";
				//String filepath = "D:\\Java Output\\Other Output\\For Conference Paper Initial Analysis\\RRHC\\NB\\Random Initial Bundle\\Run 5\\RRHC_runningtime_"+modelName+".txt";
				
				try
				{
					FileWriter fw = new FileWriter (filepath);
					BufferedWriter bw = new BufferedWriter (fw);
					
					bw.write("Running time in second is : ");
					bw.write(Double.toString(time));
					bw.newLine();
					
					double time2 = time/60;
					
					bw.write("Running time in minutes is : ");
					bw.write(Double.toString(time2));
					bw.newLine();
					
					double time3 = time2/60;
					
					bw.write("Running time in hours is : ");
					bw.write(Double.toString(time3));
					
					bw.close();
					fw.close();
					
				}
				catch (Exception e)
				{
					System.err.println("Problem writing time to the file");
				}
	}
	
	public static void write52VF (double [][] x, int i) 
	{
		String fn = getSource.write52VF(i);
		
		try
		{
		FileWriter writehandle = new FileWriter(fn);
		BufferedWriter bw = new BufferedWriter (writehandle);
		
		
		bw.write("Patient_ID");
		bw.write(" "); 
		
		for (int k=1; k<55; k++)
		{
			
		if (k==26 || k==35)
			{
				k++;
			}
		bw.write("VF_"+k); 
		bw.write(" ");
		}
		bw.write("AGIS(n+1)"); 
		bw.write(" "); 
		bw.write("AuditTrail_RandomPost"); 
		bw.write(" "); 
		bw.write("AuditTrail_NoRecord"); 
		bw.write(" "); 
		bw.newLine(); 
		
		for (int j=0; j<x.length; j++)
		{
			for (int k=0; k<x[0].length; k++)
			{
				String r = Double.toString(x[j][k]); 
				bw.write(r); 
				bw.write(" ");
			}
			bw.newLine(); 
		}
		
		bw.close();
		writehandle.close();
		}
		catch (IOException e) 
		{
	        System.err.println("Problem writing to the file");
		}
	}


}
