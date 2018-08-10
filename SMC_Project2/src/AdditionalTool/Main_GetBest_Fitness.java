package AdditionalTool;

public class Main_GetBest_Fitness {
	
	public static void main (String [] args)
	{
		String filename = "D:\\Java Output\\VF GSA\\VF GSA - Journal Paper Experiment Results\\All Data - Random Initial\\All Data Experiments\\qa 0.4\\GSA_RunResult_0.4.txt";
		String sep =  " ";
		
		double [][] readResult = Read_Point.readFile(filename, sep);
		double [] point = Read_Point.getPoint(readResult);
		double [][] bundles = new double [25][3];
		double [][] finalBundles = new double [25][3];
		
		for (int i=0; i<25; i++)
		{
			String filepath = "D:\\Java Output\\VF GSA\\VF GSA - Journal Paper Experiment Results\\All Data - Random Initial\\All Data Experiments\\qa 0.4\\VF_GSA_Result_0.4_"+i+".txt";
			
			double [][] searchBundles = Search_Bundles.readFileBundles(filepath, sep);
			
			int pt = (int) (point [i]);
			
			double acc = searchBundles [pt][1];
			double bund = searchBundles [pt][3];
			
			double iter = searchBundles [9999][0];
			double finAcc = searchBundles [9999][1];
			double finBund = searchBundles [9999][3];
			
			finalBundles [i][0] = iter;
			finalBundles [i][1] = finAcc;
			finalBundles [i][2] = finBund;
			
			bundles [i][0] = pt;
			bundles [i][1] = acc;
			bundles [i][2] = bund;
			
			System.out.println ("Point is : "+pt);
			System.out.println ("Accuracy is : "+acc);
			System.out.println ("Bundles is : "+bund);
			System.out.println ();
			
		}
		
		String flname = "finalBundles";
		Write_Bundles_Point.writeBundlePoint(bundles, " ");
		Write_Bundles_Point.writeBundlePoint(finalBundles, flname);
	}

}
