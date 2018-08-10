package AdditionalTool;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;

public class Write_Bundles_Point {
	
	public static void writeBundlePoint (double [][] results, String filename)
	{
		String path = "D:\\Java Output\\VF GSA\\VF GSA - Journal Paper Experiment Results\\All Data - Random Initial\\All Data Experiments\\qa 0.4\\BundlePoint_"+filename+".txt";
		
		try
		{
			FileWriter fw = new FileWriter (path);
			BufferedWriter bw = new BufferedWriter (fw);
			
			bw.write("Iter");
			bw.write(" ");
			bw.write("Accuracy");
			bw.write(" ");
			bw.write("Bundles");
			bw.newLine();
			
			for (int i=0; i<results.length; i++)
			{
				double point = results [i][0];
				double acc = results [i][1];
				double bund = results [i][2];
				
				bw.write(Double.toString(point));
				bw.write(" ");
				bw.write(Double.toString(acc));
				bw.write(" ");
				bw.write(Double.toString(bund));
				bw.newLine();
			}
			
			bw.close();
			fw.close();
			
		}
		catch (Exception e)
		{
			 System.err.println("Problem writing Bundles Point to the file");
		}
	}

}
