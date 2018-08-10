package Modelling;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Write_accFold {
	
	public static void write_accFold (int run, int fold, double [][] accFold)
	{
		String filepath = "D:\\Java Output\\SMC Framework Benchmark\\accFold"+run+".txt";
		
		try
		{
			FileWriter fw = new FileWriter (filepath);
			BufferedWriter bw = new BufferedWriter (fw);
			
			bw.write("Iter");
			bw.write(" ");
			
			for (int i=0; i<fold; i++)
			{
				bw.write("fold_"+(i+1));
				bw.write(" ");
			}
			bw.newLine();
			
			for (int i=0; i<accFold.length; i++)
			{
				bw.write(Double.toString(i));
				bw.write(" ");
				for (int j=0; j<accFold[i].length; j++)
				{
					bw.write(Double.toString(accFold[i][j]));
					bw.write(" ");
				}
				bw.newLine();
			}
			
			
			bw.close();
			fw.close();
		}
		catch (Exception e)
		{
			 System.err.println("Problem writing accuracy fold to the file");
		}
		
	}
	
	
	
	

}
