package SA;
import mpi.*;
import java.util.Random;
import Modelling.No_CVModelling;
import Sampled_Data.Sampling_Patient;
import VFpreparation.ReadFile;
import VFpreparation.form_ClusteredData;
import VFpreparation.getSource;
import Weighted_Kappa.ReadBundles;
import Weighted_Kappa.calWK;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instances;
import java.util.ArrayList;
import Bundles_BChopRandom.RandPart;
import Bundles_BChopRandom.RGFtoBundles;
import Instances_Format.CreateInstances;
import Instances_Format.Randomise_Data;
import Bundles_Arrangement.Bundles_Arrangement;
import Bundles_Arrangement.Bundles_RandomCreation;
import Modelling.CV_RepeatModelling;

public class SA_MPJ_Main {
	
static Random rand = new Random ();
	
	public static void main (String [] args) throws Exception
	{
		
		double [][] runResult = new double [25][10];
		
		MPI.Init(args) ;
		int run = MPI.COMM_WORLD.Rank();
		
		int iter0 = 100000;
		double iniTemp = 0;
		int iter2 = (5*iter0)/100;
		int iter = iter0-iter2;
		//Classifier model = new NaiveBayesMultinomial ();
		Classifier model = new NaiveBayesUpdateable ();
		//Classifier model = new NaiveBayes ();
		//Classifier model = new J48 ();
		String modelname = "NBU";
		long [] time = new long [1];
		
		
		iniTemp = SA_Temp.temp(model, modelname, iter2, time);
		runResult [run][0] = run+1;
		runSA(run, iniTemp, model, modelname, iter, time, runResult);
		//System.out.println("Result for running "+rank +" is :");
		Write_Run_Result.writeRunExperiment(runResult, (run+10));
		MPI.Finalize();
		
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
		int fold = 2;
		double prob = 0;
		double random = 0;
		double temp = iniTemp;
		int repeat = 10;
		
		System.out.println ("No. of variables : "+lenghtVar);
		//ArrayList<ArrayList<Integer>> curClusters = Var_3Bundles.VARBundles();
		ArrayList<ArrayList<Integer>> curClusters = new ArrayList<ArrayList<Integer>> ();
		//curClusters = VAR_5Bundles.VARBundles();
		//curClusters = Bundles_Creation.initialCluster(curClusters, lenghtVar); //random initial clusters
		RandPart.Init(1000);
		ArrayList<Integer> rp = RandPart.GetRandomPartition(lenghtVar-2); //get random clusters using binary chop method
		curClusters = RGFtoBundles.RGFToGroup(rp);
		
		System.out.println ("Initial cluster is : "+curClusters);
		//Write_Result.writeIniBundles(curClusters, modelname, run);
		
		//double [][] sumData = BundledData.bundlesData(rawData, curClusters); //prepare data based on bundles
		//double [][] avgData = BundledData.averagedData(curClusters, sumData); //average the bundled data
		double [][] BundledData = null; // to be used to bundle data from the cleaned data
		BundledData = form_ClusteredData.getData(curClusters, allVFData);
		String avg = "avgData_"+run;
		//Write_Data.writeData(avg, BundledData);
		
		Instances iData = CreateInstances.createInstances(BundledData, curClusters);
		iData.setClassIndex(iData.numAttributes()-1); //assigns the last attribute as the class variable
		iData = Randomise_Data.RandData(iData); //randomise the data (this dataset is ready for modelling)
		
		//double curAccuracy = No_CVModelling.modelling(iData, model, modelname);
		double curAccuracy = CV_RepeatModelling.repeatModelling(repeat, iData, model, modelname, fold); //2folds 10 repeats
		//double curAccuracy = No_CVModelling.modelling(iData, model, modelname);
		double [][] result = new double [iter][10];
		
		double lamda = SA_Temp.lamda(temp, iter);
		double best = 0;
		int bestPoint = 0;
		ArrayList<ArrayList<Integer>> bestClusters = new ArrayList<ArrayList<Integer>> ();
		
		runResult [run][4] = iniTemp;
		
		String file_name2 = "Six_Bundles.txt";
		String filepath2 =  "D:\\Java Input\\Bundles Files\\"+file_name2;
		double wk = 0;
		double pre_fit = 0.0;
		int fit_conv = 0;
		
		ArrayList<ArrayList<Integer>> bundles = ReadBundles.readBundles (filepath2, file_name2);
		
		for (int i=0; i<iter; i++)
		{
			pre_fit = curAccuracy;
			
			System.out.println ("Run : "+run);
			System.out.println ("Iteration : "+i);
			System.out.println ("No. of record in data is : "+allVFData.length);
			result [i][0] = i;
	
			ArrayList<ArrayList<Integer>> newClusters = Bundles_RandomCreation.cloneArrayList(curClusters);
			newClusters = Bundles_Arrangement.newArrangement (newClusters); // get a new cluster for next solution
			
			System.out.println ("Current accuracy is : "+curAccuracy);
			System.out.println ("Current clusters size is : "+curClusters.size());
						
			result [i][1] = curAccuracy;
			
			//sumData = BundledData.bundlesData(rawData, newClusters); // form a bundled VF data
			//avgData = BundledData.averagedData(newClusters, sumData); //average the bundled data
			allVFData =  Sampling_Patient.searchPatient(rawData); // re-sampling the data for every iter
			BundledData = form_ClusteredData.getData(newClusters, allVFData);
			iData = CreateInstances.createInstances(BundledData, newClusters); // convert into instances data
			iData.setClassIndex(iData.numAttributes()-1); //assigns the last attribute as the class variable
			iData = Randomise_Data.RandData(iData); //randomise the data (this dataset is ready for modelling)
			
			//double newAccuracy = runModelling.modellingAccuracy2(iData, model, modelname, fold, accFold, i); //modelling process to get the accuracy
			//double newAccuracy = No_CVModelling.modelling(iData, model, modelname);
			double newAccuracy = CV_RepeatModelling.repeatModelling(repeat, iData, model, modelname, fold); //2folds 10 repeats
			//double newAccuracy = No_CVModelling.modelling(iData, model, modelname);
			System.out.println ("new accuracy is : "+newAccuracy);
			
			result [i][2] = newAccuracy;
			result [i][3] = curClusters.size();
			result [i][4] = newClusters.size();
			
			if (newAccuracy>best)
			{
				best = newAccuracy;
				//finalFit [run][8] = newAccuracy; //to capture the best accuracy
				bestPoint = (i+2); //to capture the best accuracy point
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
			
			if (curAccuracy != pre_fit)// to capture the final fitness convergence point
			{
				fit_conv = (i+2);
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
		runResult [run][3] = fit_conv;
		runResult [run][5] = best;
		runResult [run][6] = bestClusters.size();
		runResult [run][7] = bestPoint;
		runResult [run][8] = duration;
		runResult [run][9] = wk;
		
		Write_Result.writeResult(result, (run+10));
		Write_Result.writeBundles(curClusters, (run+10));
		Write_Result.writeBestBundles(bestClusters, modelname, (run+10));
		System.out.println ("Output for SA VF 2 folds D:\\Zairul\\Output\\MPJ - 2nd Paper\\SA\\VF ");
		System.out.println ("Model is : "+modelname);
		System.out.println ("Final accuracy is : "+curAccuracy);
		System.out.println ("WK is : "+wk);
		System.out.println ("Final cluster size is : "+curClusters.size());
		System.out.println ("Final cluster elements is : "+curClusters);
		System.out.println ("Running time is : "+duration);
	}


}
