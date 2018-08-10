package Weighted_Kappa;
import java.util.ArrayList;

public class printMethods {
	
	public static void printArray (int [][] array)
	{
		
		for (int i=0; i<array.length; i++)
		{
			int  x=0, y=0;
			System.out.print("No "+i+" : ");
			for (int j=0; j<array[i].length; j++)
			{
				x = array[i][0];
				y = array[i][1];
			}
			
			System.out.print(x);
			System.out.println(" "+y);
		}
	}
	
	
	public static void printArrayDouble (double [][] array)
	{
		
		for (int i=0; i<array.length; i++)
		{
			double  x=0, y=0;
			System.out.print("No "+i+" : ");
			for (int j=0; j<array[i].length; j++)
			{
				x = array[i][0];
				y = array[i][1];
			}
			
			System.out.print(x);
			System.out.println(" "+y);
		}
	}
	
	
}
