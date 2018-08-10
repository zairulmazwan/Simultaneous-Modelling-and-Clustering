package Weighted_Kappa;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class WK_writer {
	
	public static void writeWK (String bundle1, String bundle2, double WK, String pathWrite)
	{
		String filepath = pathWrite;
		
		
		try
		{
			FileWriter fw = new FileWriter (filepath);
			BufferedWriter bw = new BufferedWriter (fw);
			
			bw.write("Bundle 1 is : "+bundle1);
			bw.newLine();	
			bw.write("Bundle 2 is : "+bundle2);
			bw.newLine();	
			
			bw.write("Weighted Kappa is : ");
			bw.write(Double.toString(WK));
			
			
			bw.close();
			fw.close();
		}
		catch (Exception e)
		{
			System.err.println("Problem writing Weighted Kappa result to the file");
		}
	}
	
	public static void writeWKResult (double [][] result, String pathWrite)
	{
		String filepath = pathWrite;
		
		try
		{
			FileWriter fw = new FileWriter (filepath);
			BufferedWriter bw = new BufferedWriter (fw);
			
			bw.write("Result_No");
			bw.write(" ");
			bw.write("WK");
			bw.newLine();	
			
			for (int i=0; i<result.length; i++)
			{
				double iter = result [i][0];
				double WK = result [i][1];
				
				bw.write(Double.toString(iter));
				bw.write(" ");
				bw.write(Double.toString(WK));
				bw.newLine();	
			}
			
			bw.close();
			fw.close();
		}
		catch (Exception e)
		{
			System.err.println("Problem writing Weighted Kappa result to the file");
		}
	}

}
