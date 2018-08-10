package SM_SimulatedAnnealing;

public class SA_RegResult {
	
	public static double [][] result (double [][] result, int i, double curAccuracy, double newAccuracy, double curTemp, int curClsSize, int newCluster)
	{
		double [][] curResult = result;
		
		curResult [i][0] = i;
		curResult [i][1] = curAccuracy;
		curResult [i][2] = newAccuracy;
		curResult [i][3] = curTemp;
		curResult [i][4] = curClsSize;
		curResult [i][5] = newCluster;
		
		
		return curResult;
	}

}
