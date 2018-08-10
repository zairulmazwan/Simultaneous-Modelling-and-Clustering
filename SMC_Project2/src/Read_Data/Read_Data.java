package Read_Data;
import java.io.FileReader;
import java.io.BufferedReader;


public class Read_Data {
	
	public static void main (String [] args)
	{
		double [][] data = getAllData ();
		
		printArray (data);
		System.out.println("No. of raw : "+data.length);
		System.out.println("No. of variable : "+data[0].length);
		
	}
	
	public static double [][] getAllData ()
	{
		double [][] rawdata = null;
		//String filepath = "D:\\Java Input\\SMC Framework Benchmark\\EMGPAD3.txt";
		//String filepath = "D:\\Java Input\\SMC Framework Benchmark\\synth-SV_Data.csv";
		//String filepath = "D:\\Java Input\\SMC Framework Benchmark\\Synthetic_Data-CopyXs.csv";
		String filepath = "//Users//zairulmazwan//PhD Experiments//Input//Moorefield Raw Data - Reclassified.csv";
		//String filepath = "D:\\Java Input\\SMC Framework Benchmark\\SyntheticDatav1.csv";
		//String filepath = "C:\\Zairul Mazwan\\Java Input\\SMC Framework Benchmark\\EMGPAD3SMC8x6.csv";
		
		try
		{
			FileReader fr = new FileReader (filepath);
			BufferedReader br = new BufferedReader (fr);
			
			int col=0, row=0;
			String line = null;
			
			while ((line = br.readLine()) != null)
			{
				row++;
				String [] column = line.split(",");
				col = column.length;
			}
				
			int i=0, j=0;
			rawdata = new double [row][col];
			br = new BufferedReader (new FileReader (filepath));
			
			while ((line = br.readLine()) != null)
			{
				String [] column = line.split(",");
				
				for (j=0; j<column.length; j++)
				{
					rawdata [i][j] = Double.parseDouble(column [j]) ;
				}
				
				i++;		
			}
			
		}
		catch (Exception E)
		{
			System.out.println("+++ReadArrayFile: "+E.getMessage());
		}
		return (rawdata);
	}

	
	public static void printArray (double [][] array)
	{
		for (int i=0; i<array.length; i++)
		{
			for (int j=0; j<array[i].length; j++)
			{
				System.out.print(array [i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
}
