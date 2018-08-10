package AdditionalTool;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;

public class Search_Bundles {
	
	public static void main (String [] args)
	{
		String filename = "D:\\Java Output\\VF GSA\\VF GSA - Journal Paper Experiment Results\\Sampled Data - Random Initial\\qa -0.4\\VF_GSA_Result_-0.4_0.txt";
		String sep =  " ";
		
		double [][] array = readFileBundles (filename, sep);
		PrintArray (array);
		
		
		
	}
	
	public static double [][] readFileBundles (String filename, String sep)
	{
		double [][] result = null;
		
		try
		{

			FileReader reader = new FileReader (filename);
			BufferedReader input = new BufferedReader (reader);
			
			String line=null;
			int row=0, col=0;
			
			while ((line = input.readLine())!= null) // this is just to get number of columns and rows
					{
						row++;
						String[] columns = line.split(sep);
						col = columns.length;
						//System.out.println( Arrays.toString(columns));
					}
			
			//System.out.println ("No of row is (when read raw data) : "+row);
			//System.out.println ("No of col is (when read raw data) : "+col);
			//System.out.println ();
			
			
			int i=0, j=0;
			result = new double [row-1][col];
			input = new BufferedReader(new FileReader(filename));
			
			int counter = 0;
			
			while ((line = input.readLine())!=null)
			{
				counter++;
				
				if (counter>1)
				{
					String [] columns = line.split(sep); //register all values of first line found in the file into 1D array
					
					for (j=0; j<columns.length; j++)
					{
						result [i][j]=Double.parseDouble(columns[j]);
					}
					
					i++;
				}
				
				
			}
			
		
		}
		catch(Exception E)
		{
			System.out.println("+++ReadArrayFile: "+E.getMessage());
		}
		return result;
	}
	
	static public void PrintArray(double x[][])
	{
				
		for(int i=0;i<+x.length;++i)
		{
			for(int j=0;j<x[i].length;++j)
			{
				System.out.print(x[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
		
		System.out.println ("No. of col is : "+x[0].length);
		System.out.println ("No. of row is : "+x.length);
	}

}
