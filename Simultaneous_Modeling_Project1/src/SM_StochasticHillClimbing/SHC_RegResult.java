package SM_StochasticHillClimbing;

public class SHC_RegResult {

		public static double [][] result (double [][] result, int i, double curAccuracy, double newAccuracy, double prob, int curClsSize, int newClsSize)
		{
			double [][] curResult = result;
			
			curResult [i][0] = i;
			curResult [i][1] = curAccuracy;
			curResult [i][2] = newAccuracy;
			curResult [i][3] = prob;
			curResult [i][4] = curClsSize;
			curResult [i][5] = newClsSize;
			
			return curResult;
		}

	}
