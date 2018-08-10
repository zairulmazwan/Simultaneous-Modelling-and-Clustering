package SA;

import java.util.ArrayList;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instances;
import Bundles.Bundles_Arrangement;
import Bundles.Bundles_RandomCreation;
import Instances_Format.CreateInstances;
import Instances_Format.Randomise_Data;
import Modelling.No_CVModelling;
import Modelling.runModelling;
import Sampled_Data.Sampling_Patient;
import VFpreparation.ReadFile;
import VFpreparation.form_ClusteredData;
import VFpreparation.getSource;
import Weighted_Kappa.ReadBundles;
import Weighted_Kappa.calWK;
import Bundles_BChopRandom.RGFtoBundles;
import Bundles_BChopRandom.RandPart;


public class SA_Run {
	
	static Random rand = new Random ();
	
	public static void main (String [] args) throws Exception
	{
		int run = 25;
		int iter0 = 100;
		double iniTemp = 0;
		int iter2 = (5*iter0)/100;
		int iter = iter0-iter2;
		//Classifier model = new NaiveBayesMultinomial ();
		Classifier model = new NaiveBayesUpdateable ();
		//Classifier model = new NaiveBayes ();
		//Classifier model = new J48 ();
		String modelname = "NBU";
		long [] time = new long [1];
		double [][] runResult = new double [run][9];
		
		for (int i=0; i<run; i++)
		{
			runResult [i][0] = i+1;
			iniTemp = SA_Temp.temp(model, modelname, iter2, time);
			runSA (i, iniTemp, model, modelname, iter, time, runResult);
		}
		
		Write_Run_Result.writeRunExperiment(runResult, 0);
	}
	
	public static void runSA (int run, double iniTemp, Classifier model, String modelname, int iter, long [] time, double [][] runResult) throws Exception
	{
		String filesource = getSource.getData(); //to read VF data
		String sep = ",";
		
		double [][] rawData = ReadFile.ReadFileArray(filesource, sep); // read raw data from path and create into an array
		String filename = "rawData";
		//Write_Data.writeData(filename, rawData);
		
		double [][] allVFData =  Sampling_Patient.searchPatient(rawData); //prepare all data which consists of 1579 records from 1579 patients.
		String processedData =  "processedData_"+run;
		//Write_Data.writeData(processedData, allVFData);
		
		int lenghtVar = rawData[0].length;
		int fold = 10;
		double prob = 0;
		double random = 0;
		double temp = iniTemp;
		
		System.out.println ("No. of variables : "+lenghtVar);
		//ArrayList<ArrayList<Integer>> curClusters = Var_3Bundles.VARBundles();
		ArrayList<ArrayList<Integer>> curClusters = new ArrayList<ArrayList<Integer>> ();
		//curClusters = VAR_5Bundles.VARBundles();
		//curClusters = Bundles_Creation.initialCluster(curClusters, lenghtVar); //random initial clusters
		RandPart.Init(1000);
		ArrayList<Integer> rp = RandPart.GetRandomPartition(lenghtVar-2); //get random clusters using binary chop method
		curClusters = RGFtoBundles.RGFToGroup(rp);
		
		System.out.println ("Initial cluster is : "+curClusters);
		System.out.println ("Initial cluster size is : "+curClusters.size());
		//Write_Result.writeIniBundles(curClusters, modelname, run);
		
		//double [][] sumData = BundledData.bundlesData(rawData, curClusters); //prepare data based on bundles
		//double [][] avgData = BundledData.averagedData(curClusters, sumData); //average the bundled data
		double [][] BundledData = null; // to be used to bundle data from the cleaned data
		BundledData = form_ClusteredData.getData(curClusters, allVFData);
		String avg = "avgData_"+run;
		//Write_Data.writeData(avg, BundledData);
		
		Instances iData = CreateInstances.createInstances(BundledData, curClusters);
		iData.deleteAttributeAt(0); //remove PID column from the data
		iData.setClassIndex(iData.numAttributes()-1); //assigns the last attribute as the class variable
		iData = Randomise_Data.RandData(iData); //randomise the data (this dataset is ready for modelling)
		System.out.println ("Number of variables is : "+iData.numAttributes());
		
		//double curAccuracy = No_CVModelling.modelling(iData, model, modelname);
		double curAccuracy = No_CVModelling.modelling(iData, model, modelname);
		double [][] result = new double [iter][10];
		
		double lamda = SA_Temp.lamda(temp, iter);
		double best = 0;
		int covPoint = 0;
		ArrayList<ArrayList<Integer>> bestClusters = new ArrayList<ArrayList<Integer>> ();
		
		runResult [run][3] = iniTemp;
		
		String file_name2 = "Six_Bundles.txt";
		String filepath2 =  "D:\\Java Input\\Bundles Files\\"+file_name2;
		
		ArrayList<ArrayList<Integer>> bundles = ReadBundles.readBundles (filepath2, file_name2);
		double wk = 0;
		
		for (int i=0; i<iter; i++)
		{
			System.out.println ("Run : "+run);
			System.out.println ("Iteration : "+i);
			result [i][0] = i;
	
			ArrayList<ArrayList<Integer>> newClusters = Bundles_RandomCreation.cloneArrayList(curClusters);
			newClusters = Bundles_Arrangement.newArrangement (newClusters); // get a new cluster for next solution
			
			System.out.println ("Current accuracy is : "+curAccuracy);
			System.out.println ("Current clusters size is : "+curClusters.size());
						
			result [i][1] = curAccuracy;
			
			//sumData = BundledData.bundlesData(rawData, newClusters); // form a bundled VF data
			//avgData = BundledData.averagedData(newClusters, sumData); //average the bundled data
			BundledData = form_ClusteredData.getData(newClusters, allVFData);
			iData = CreateInstances.createInstances(BundledData, newClusters); // convert into instances data
			iData.setClassIndex(iData.numAttributes()-1); //assigns the last attribute as the class variable
			iData = Randomise_Data.RandData(iData); //randomise the data (this dataset is ready for modelling)
			
			//double newAccuracy = runModelling.modellingAccuracy2(iData, model, modelname, fold, accFold, i); //modelling process to get the accuracy
			double newAccuracy = No_CVModelling.modelling(iData, model, modelname);
			//double newAccuracy = No_CVModelling.modelling(iData, model, modelname);
			System.out.println ("new accuracy is : "+newAccuracy);
			
			result [i][2] = newAccuracy;
			result [i][3] = curClusters.size();
			result [i][4] = newClusters.size();
			
			if (newAccuracy>best)
			{
				best = newAccuracy;
				//finalFit [run][8] = newAccuracy; //to capture the best accuracy
				covPoint = (i+1); //to capture the best accuracy point
				bestClusters = Bundles_RandomCreation.cloneArrayList(newClusters); // to capture the best clusters
				//finalFit [run][9] = bestClusters.size(); 
			}
			
			if (newAccuracy>curAccuracy)
			{
				curClusters.clear();
				curAccuracy = newAccuracy;
				curClusters = Bundles_RandomCreation.cloneArrayList(newClusters);
				System.out.println ("This is PERFORMED");
				prob = 1;
			}
			else
			{
				double diffFit = newAccuracy-curAccuracy; 
				prob = Math.exp (diffFit/temp);
				random = rand.nextDouble();
			
				result [i][5] = diffFit;
				if (prob>random)
				{
					curClusters.clear();
					curClusters = Bundles_RandomCreation.cloneArrayList(newClusters);
					curAccuracy = newAccuracy;
					System.out.println("This IS PERFORMED");
				}
			}
			
			wk = calWK.calWeightedKappa(curClusters, bundles); 
			System.out.println ("Weighted Kappa is : "+wk);
			
			result [i][6] = prob;
			result [i][7] = random;
			
			temp *= lamda;
			result [i][8] = temp;
			result [i][9] = wk;
			newClusters.clear(); 
			System.out.println();
		}
		
		long startTime = time [0];
		long endTime = System.currentTimeMillis();
		double duration = (endTime-startTime)/1000;
		
		runResult [run][1] = curAccuracy;
		runResult [run][2] = curClusters.size();
		runResult [run][4] = best;
		runResult [run][5] = bestClusters.size();
		runResult [run][6] = covPoint;
		runResult [run][7] = duration;
		runResult [run][8] = wk;
		
		Write_Result.writeResult(result, run);
		Write_Result.writeBundles(curClusters, run);
		Write_Result.writeBestBundles(bestClusters, modelname, run);
		System.out.println ("Result for VF No Cross Validation");
		System.out.println ("Result path : D:\\Java Output\\Journal Paper Results\\SA\\VF\\NOCV VF");
		System.out.println ("Model is : "+modelname);
		System.out.println ("Final accuracy is : "+curAccuracy);
		System.out.println ("Final cluster size is : "+curClusters.size());
		System.out.println ("Final cluster elements is : "+curClusters);
		System.out.println ("Running time is : "+duration);
	}

}
