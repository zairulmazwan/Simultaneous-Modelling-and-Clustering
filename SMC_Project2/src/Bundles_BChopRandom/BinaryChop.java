package Bundles_BChopRandom;
class BinaryChop
{
	private final static double EPSILON = 0.000001;
	public static double DoBinaryChop(BinaryChopFunction bcf,double minv,double maxv)
	{
		double lower = minv;
		double upper = maxv;
		double mp = 0.5*(lower+upper);
		double fx = bcf.f(mp);
		while (Math.abs(fx) > EPSILON)
		{
			if (fx > 0)
			{
				upper = mp;
			}
			else
			{
				lower = mp;
			}
			mp = 0.5*(lower+upper);
			fx = bcf.f(mp);
		}
		return(mp);
	}
}