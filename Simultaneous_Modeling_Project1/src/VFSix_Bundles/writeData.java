package VFSix_Bundles;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class writeData {
	
	public static void write_Data (double [][] x)
	{
		String filepath = "C:\\Java Output\\VF 6nfb Data\\6nfb.txt";
		
		try
		{
			FileWriter fw = new FileWriter(filepath);
			BufferedWriter bw = new BufferedWriter (fw);
			
			for (int i=0; i<x.length; i++)
			{
				for (int j=0; j<x[i].length; j++)
				{
					bw.write(Double.toString(x[i][j]));
					bw.write(" ");
				}
				bw.newLine();
			}
			
			
		}
		catch (Exception e)
		{
			System.err.println("Unable to write 6NFB Data");
		}
	}

}
