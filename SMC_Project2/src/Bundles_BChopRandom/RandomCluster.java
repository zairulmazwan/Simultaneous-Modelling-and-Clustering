package Bundles_BChopRandom;
import java.util.ArrayList;

public class RandomCluster 
{
	public static void main(String[] args) 
	{
		RandPart.Init(1000);
		for(int i=0;i<1;++i)
		{
			ArrayList<Integer> rp = RandPart.GetRandomPartition(28);
			System.out.println(rp);
			
			ArrayList<ArrayList<Integer>> bundles = RGFtoBundles.RGFToGroup(rp);
			
			System.out.println("Bundels size is : "+bundles.size());
			
			System.out.println(bundles);
		}
	}
}
