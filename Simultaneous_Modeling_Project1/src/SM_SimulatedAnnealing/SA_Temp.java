package SM_SimulatedAnnealing;
import java.util.ArrayList;

import weka.classifiers.Classifier;
import Bundles_Arrangement.Bundles_RandomCreation;
import Bundles_Arrangement.Bundles_Arrangement;
import Modelling.newSolution_Accuracy;


public class SA_Temp {
	
		public static double temp (double [][] VF52, Classifier model, String modelName, int fold, int iter2, long [] time) throws Exception
		{
			double temp = 0;
			ArrayList<ArrayList<Integer>> currentCluster = new ArrayList<ArrayList<Integer>> ();
			ArrayList<ArrayList<Integer>> newCluster = new ArrayList<ArrayList<Integer>> ();
			double currentAccuracy = 0;
			double newAccuracy = 0;
			double difFit = 0;
			
			currentCluster =  Bundles_RandomCreation.initialCluster(currentCluster);
			
			currentAccuracy = newSolution_Accuracy.newAccuracy(newCluster, VF52, model, modelName, fold);
			long timeStart=0;
			
			for (int i=0; i<iter2; i++)
			{
				if (i==0)
				{
					timeStart = System.currentTimeMillis();
					time [0] = timeStart;
				}
				
				System.out.println("Calculating temperature...");
				newCluster = Bundles_RandomCreation.cloneArrayList(currentCluster);
				newCluster = Bundles_Arrangement.newArrangement(newCluster);
				newAccuracy = newSolution_Accuracy.newAccuracy(newCluster, VF52, model, modelName, fold);
				
				//System.out.println("Current accuracy is : "+currentAccuracy);
				//System.out.println("New accuracy is : "+newAccuracy);
				
				difFit = Math.abs(newAccuracy - currentAccuracy);
				
				temp += difFit;
				
				currentAccuracy = newAccuracy;
				currentCluster.clear();
				currentCluster = Bundles_RandomCreation.cloneArrayList(newCluster);
			}
			temp/=iter2;
			return temp;
		}
		
		public static double lamda1 (double initialTemp, int iter)
		{
			double finTemp = 0.0000001;
			//System.out.println("Initial temp is : "+initialTemp);
			//System.out.println("Iter is : "+iter);
			
			double lamda = finTemp/initialTemp;
			lamda = Math.exp((1.0/iter)*Math.log(lamda));
			
			return lamda;
		}

		
		public static double lamda (double initialTemp, int iter)
		{
			double finTemp = 0.0000001;
			//System.out.println("Initial temp is : "+initialTemp);
			//System.out.println("Iter is : "+iter);
			
			double x = Math.log(finTemp)-Math.log(initialTemp);
			x/=iter;
			double lamda = Math.exp(x);
			
			return lamda;
		}
	}
