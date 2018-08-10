package SM_RandomRestartHillClimbing;

public class RRHC_RegResult {
	
	public static double [][] result (double [][] result, int i, double curAccuracy, double newAccuracy, int curClsSize, int newClsSize)
	{
		double [][] curResult = result;
		
		curResult [i][0] = i;
		curResult [i][1] = curAccuracy;
		curResult [i][2] = newAccuracy;
		curResult [i][3] = curClsSize;
		curResult [i][4] = newClsSize;
		
		return curResult;
	}

}
