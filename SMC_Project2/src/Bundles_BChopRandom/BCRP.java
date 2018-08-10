package Bundles_BChopRandom;
public class BCRP implements BinaryChopFunction 
{
	private double n = 0.0;
	
	public BCRP(double nn)
	{
		n = nn;
	}
	public double f(double R) 
	{
		return(R*Math.exp(R)-n);
	}
}
