package Bundles_BChopRandom;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Random;
import Jama.Matrix;

public class RandPart
{
	private static final double NITER = 25;
	private static Matrix ls2a;
	private static ArrayList<Double> lba;
	private static int nvars;
	private static Random rand = new Random();
	//private static boolean initcalled = false;
	/*private static double Rf(double x,double n)
	{
		double rf = x*Math.exp(x) - n;
		return(rf);
	}

	private static double Rfdash(double x)
	{
		double rfdash = (x+1)*Math.exp(x);
		return(rfdash);
	}*/
	public static int factorial(int n)
	{
		if (n < 2) return(1);
		return(n * factorial(n-1));
	}
	public static double factorial(double n)
	{
		return(factorial((int)n));
	}
	public static double Bell(int n)
	{
		double res = 0.0,k;
		for(k=0;k<=n;++k)
		{
			res +=  StirlingSecond(n,k);
		}
		return(res);
	}
	public static double NCR(double n,double r)
	{
		double res = factorial(n);
		res /= factorial(r);
		res /= factorial(n-r);
		return(res);
	}
	public static double StirlingSecond(double n,double k)
	{
		double s2 = 0.0;
		for(int i=0;i<=(int)k;++i)
		{
			
			s2 += Math.pow(-1.0,(double)(i))*NCR(k,(double)(i))*Math.pow((double)(k-i),n);
		}
		s2 /= factorial(k);
		return(s2);
	}
	public static double ComputeR(double n)
	{
		//double x = (0.5*log(n)+0.5*n)/2.0;
		//for(unsigned long i=0;i<NITER;++i)
		//{ 
		//	x = x - Rf(x,n)/Rfdash(x);
		//}
		BCRP bcrp = new BCRP(n);
		double minval = 0.5*Math.log(n);
		double maxval = Math.sqrt(n);
		double x = BinaryChop.DoBinaryChop(bcrp,minval,maxval);
		return(x);
	}
	private static double x0f(double x,double n,double k)
	{
		double x0f = 1 - Math.exp(-x) - x*(k/n);
		return(x0f);
	}
	private static double x0fdash(double x,double n,double k)
	{
		double x0fdash = Math.exp(-x) - (k/n);
		return(x0fdash);
	}
	public static double Computex0(double n,double k)
	{
		double x = n/k;
		for(int i=0;i<NITER;++i)
		{ 
			x = x - x0f(x,n,k)/x0fdash(x,n,k);
		}
		return(x);
	}
	public static double logfact(double n)
	{
	    double s = 0;
	    for(double i=1;i<=n;++i)
		{
	        s = s + Math.log(i);
		}
		return(s);
	}
	public static double LogS2Approx(double n,double k)
	{
		double s = 0.0;
	    if (n == k || k == 1 || k == 0 || n == 0)
		{
	        return(s);
		}
		double x0 = Computex0(n,k),m;
		if (x0 > 500)
		{
			m = x0;
		}
		else
		{
			m = Math.log(Math.exp(x0)-1);
		}
		//OUTPUT << n << " " << k << endl;
		//OUTPUT << x0 << endl;
		//OUTPUT << log(exp(x0)-1) << endl;
		//exit(1);
	    double t0 = (n-k)/k;
	    double thix0 = -n*Math.log(x0)+k*m;
	    double A = thix0 - k*t0 + (n-k)*Math.log(t0);
	    double ft0 = Math.sqrt(t0/((1+t0)*(x0-t0)));
		//OUTPUT << n << " " << k << " " << x0 << " " << t0 << " " << ft0 << endl;
	    s = A + (n-k)*Math.log(k) + Math.log(ft0) + logfact(n) - logfact(n-k) - logfact(k);
		//OUTPUT << A << " " << s << endl;
		return(s);
	}
	public static double LogBell(double n)
	{
		double lbnr = LogBellNR(n);
		double lbwm = WMLogBellN((int)n);
		return((lbnr+lbwm)/2.0);
	}
	public static double LogBellNR(double n)
	{
		double lbn = 0.0;
	    if (n == 1)
		{
	        return(lbn);
		}
	    double R = ComputeR(n);
	    double p1 = -0.5*Math.log(R+1);
	    double p2 = n*(R+(1/R)-1)-1;
	    double p3 = 1 - (R*R*(2*R*R+7*R+10))/(24*n*Math.pow(R+1,3));
	    lbn = p1+p2*p3;
		return(lbn);
	}
	public static void Init(int n)
	{
		rand.setSeed(System.nanoTime());
		nvars = n;
		ls2a = new Matrix(nvars+1,nvars+1,Double.MAX_VALUE);
		lba = new ArrayList<Double>();
		for(int i=0;i<nvars+1;++i) lba.add(Double.MAX_VALUE);
	}
	public static double GetLogS2Approx(int n,int k)
	{
		double x = ls2a.get(n,k);
		if (x == Double.MAX_VALUE)
		{
			x = LogS2Approx(n,k);
			ls2a.set(n,k,x);
		}
		return(x);
	}
	public static double GetLogBell(int n)
	{
		double x = lba.get(n);
		if (x == Double.MAX_VALUE)
		{
			x = LogBell(n);
			lba.set(n,x);
		}
		return(x);
	}
	public static ArrayList<Integer> GetRandomPartition(int nn,int kk)
	{
		int n = nn;
		ArrayList<Integer> x = new ArrayList<Integer>();
		for(int i=0;i<n;++i) x.add(0);
		int k = kk;
		boolean flag = false;
		while(flag == false)
		{
	        double r,u = Math.abs(rand.nextDouble());
			if (k > 1)
			{
	            r = Math.exp(GetLogS2Approx(n-1,k-1) - GetLogS2Approx(n,k));
			}
			else
			{
	            r = 0;
			}
			if (u <= r)
			{
	            x.set(n-1,k);
				k = k - 1;
			}
			else
			{
	            x.set(n-1,Math.abs(rand.nextInt() % k) + 1);
			}
			n = n -1;
			if (n == 0)
			{
				flag = true;
			}
		}
		return(x);
	}
	public static ArrayList<Integer> GetRandomPartition(int n)
	{
		//SSwSeed();
		//vector<double> res((unsigned long)(n),0.0);
		ArrayList<Double> res = new ArrayList<Double>();
		int i;
		for(i=1;i<=n;++i)
		{
			double s2 = GetLogS2Approx(n,i);
			double b = GetLogBell(n);
			double lnz = s2 - b;
			double z = Math.exp(lnz);
			res.add(z);
			//System.out.println("s2 : "+s2);
			//System.out.println("b : "+b);
			//OUTPUT << "+++ " << s2 << " " << b << " " << z << endl;
		}
		//System.out.println("res : "+res);
		double s = 0.0;
		for(i=1;i<=n;++i)
		{
			s = s + res.get(i-1);
		}
		//System.out.println("s : "+s);
		for(i=1;i<=n;++i)
		{
			res.set(i-1,res.get(i-1) / s);
			//OUTPUT << res[i-1] << endl;
		}
		//System.out.println("res2 : "+res);
		double u = Math.abs(rand.nextDouble());
		//System.out.println("u is : "+u);
		s = 0.0;
		for(i=1;i<=n;++i)
		{
			s = s + res.get(i-1);
			if (u <= s) break;
		}
		//System.out.println("i is : "+i);
		if (i == n+1)
		{
			System.out.println("+++Error in RP");
			System.exit(1);
		}
		
		return(GetRandomPartition(n,i));
	}
	public static double WMLogBellN(int nn)
	{
		double n = (double)nn;
		double lnn = Math.log(n);
		double lnlnn = Math.log(lnn);
		
		double lnbn = n*lnn - n*lnlnn - n + (n*lnlnn)/lnn + n/lnn + (n/2)*(lnlnn/lnn)*(lnlnn/lnn);
		
		return(lnbn);
	}
	public static BigInteger BIBell(int n)
	{
		BigInteger res = new BigInteger("0");
		int k;
		for(k=0;k<=n;++k)
		{
			BigInteger biss = BIStirlingSecond(n,k);
			res = res.add(biss);
			//System.out.println(res.toString());
		}
		return(res);
	}
	public static BigDecimal xBDln(BigDecimal x) 
	{
		long ITER = 100000000;
		MathContext context = new MathContext(100);
	    if (x.equals(BigDecimal.ONE)) 
	    {
	        return BigDecimal.ZERO;
	    }
	    x = x.subtract(BigDecimal.ONE);
	    BigDecimal ret = new BigDecimal(ITER + 1);
	    for (long i = ITER; i >= 0; i--) 
	    {
	    	BigDecimal N = new BigDecimal(i / 2 + 1).pow(2);
	        N = N.multiply(x, context);
	        ret = N.divide(ret, context);

	        N = new BigDecimal(i + 1);
	        ret = ret.add(N, context);

	    }
	    ret = x.divide(ret, context);
	    return ret;
	}
	public static double BIln(BigInteger val)
	{
		double log2 = BIln2(val);
		return(log2*Math.log(2.0));
	}
	public static double BIln2(BigInteger val)
	{
	    // Get the minimum number of bits necessary to hold this value.
	    int n = val.bitLength();

	    // Calculate the double-precision fraction of this number; as if the
	    // binary point was left of the most significant '1' bit.
	    // (Get the most significant 53 bits and divide by 2^53)
	    long mask = 1L << 52; // mantissa is 53 bits (including hidden bit)
	    long mantissa = 0;
	    int j = 0;
	    for (int i = 1; i < 54; i++)
	    {
	        j = n - i;
	        if (j < 0) break;

	        if (val.testBit(j)) mantissa |= mask;
	        mask >>>= 1;
	    }
	    // Round up if next bit is 1.
	    if (j > 0 && val.testBit(j - 1)) mantissa++;

	    double f = mantissa / (double)(1L << 52);

	    // Add the logarithm to the number of bits, and subtract 1 because the
	    // number of bits is always higher than necessary for a number
	    // (ie. log2(val)<n for every val).
	    return ((n - 1 + Math.log(f) * 1.44269504088896340735992468100189213742664595415298D));
	    // Magic number converts from base e to base 2 before adding. For other
	    // bases, correct the result, NOT this number!
	}
	public static BigInteger BIStirlingSecond(int n,int k)
	{
		BigInteger s2 = new BigInteger("0");
		for(int i=0;i<=k;++i)
		{
			
			s2 = s2.add(BIpow(-1,i).multiply(BINCR(k,i)).multiply(BIpow(k-i,n)));
		}
		s2 = s2.divide(BIfactorial(k));
		return(s2);
	}
	private static BigInteger BIfactorial(int k) 
	{
		BigInteger fact = new BigInteger("1");
		for(int i=1;i<=k;++i)
		{
			fact = fact.multiply(new BigInteger(String.valueOf(i)));
		}
		return(fact);
	}
	private static BigInteger BINCR(int n, int r) 
	{
		BigInteger res = BIfactorial(n);
		res = res.divide(BIfactorial(r));
		res = res.divide(BIfactorial(n-r));
		return(res);
	}
	private static BigInteger BIpow(int x, int y) 
	{
		BigInteger pow = new BigInteger("1");
		BigInteger xx =  new BigInteger(String.valueOf(x));
		for(int i=1;i<=y;++i)
		{
			pow = pow.multiply(xx);
		}
		return(pow);
	}
}