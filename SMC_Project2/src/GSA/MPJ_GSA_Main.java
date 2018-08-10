package GSA;
import java.util.ArrayList;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instances;
import Bundles_Arrangement.Bundles_Arrangement;
import Bundles_Arrangement.Bundles_RandomCreation;
import Bundles_BChopRandom.RGFtoBundles;
import Bundles_BChopRandom.RandPart;
import Instances_Format.CreateInstances;
import Instances_Format.Randomise_Data;
import Modelling.CV_RepeatModelling;
import Modelling.runModelling;
import Sampled_Data.Sampling_Patient;
import VFpreparation.ReadFile;
import VFpreparation.form_ClusteredData;
import VFpreparation.getSource;
import Weighted_Kappa.ReadBundles;
import Weighted_Kappa.calWK;
import mpi.*;

public class MPJ_GSA_Main {
	
	static Random rand = new Random ();
	static double iniTemp= 0.0;
	static double qa=0.0;
	static double qv= 0.0;
	
	public static void main (String [] args) throws Exception
	{
		double [][] gsa_para = Read_Parameter.GSA_parameters();
		long timestart = 0;
		int iter = (int) gsa_para [0][3];
		double [][] runResult = new double [25][12];
		
		for (int i=0; i<gsa_para.length; i++)
		{
			qa = gsa_para [i][0];
			iniTemp = gsa_para [i][4];
			qv = gsa_para [i][6];
			
			MPI.Init(args) ;
			int run = MPI.COMM_WORLD.Rank();
			
			
			//Classifier model = new NaiveBayesMultinomial ();
			Classifier model = new NaiveBayesUpdateable ();
			//Classifier model = new NaiveBayes ();
			//Classifier model = new J48 ();
			String modelname = "NBU";
			runResult [run][0] = run+1;
			timestart = System.currentTimeMillis();
			
			runGSA_VF(run, runResult, timestart, iter);
			
			Write_RunResult.writeRunExperiment(runResult, qa, run);
			MPI.Finalize();
		}
		
	}
	
	public static void runGSA_VF (int j, double [][] finalFit, long timestart, int iteration) throws Exception
	{
		
		rand.setSeed(System.nanoTime());	
		String filesource = getSource.getData();
		String sep = ",";
		
		//Classifier model = new NaiveBayesMultinomial ();
		//String modelname = "NBU";
		Classifier model = new NaiveBayesUpdateable ();
		//Classifier model = new J48 ();
		String modelname = "NBU";
		//Classifier model = new NaiveBayes ();
		//String modelname = "NaiveBayes";
		int fold = 2;
		int repeat = 10;
		int iter = iteration;
		//double qa = 0.1;
		//double qv = 1.37788166108833;
		double temp = iniTemp;
		double [] visitA = new double [1];
		double prob = 0;
		double random = 0;
		
		double [][] rawData = ReadFile.ReadFileArray(filesource, sep); // read raw data from path and create into an array
		double [][] BundledData = null; // to be used to bundle data from the cleaned data
		int lenghtVar = rawData[0].length; // for VF data, includes patient ID (54 columns)
		//System.out.println ("No of row : "+rawData.length);
		System.out.println ("No of col : "+rawData [0].length);
		//String rawdata =  "rawData";
		//Write_Data.writeData(rawdata, rawData);
		//ReadFile.PrintArray(rawData);
		
		//double [][] allVFData =  Prepare_PatientData.allPatientVFData(rawData); //prepare all data which consists of 12159 records from 1579 patients. Using all data with the 6NFB gives the same accuracy
		double [][] allVFData =  Sampling_Patient.searchPatient(rawData); //prepare all data which consists of 1579 records from 1579 patients.
		//ReadFile.PrintArray(allVFData);
		//String processedData =  "processedData_"+j;
		//Write_Data.writeData(processedData, allVFData);
		//System.out.println ("No of variables before clustered : "+allVFData[0].length);
		
		ArrayList<ArrayList<Integer>> curClusters = new ArrayList<ArrayList<Integer>> ();
		//curClusters = Bundles_RandomCreation.initialCluster(curClusters); //random initial clusters
		RandPart.Init(1000);
		ArrayList<Integer> rp = RandPart.GetRandomPartition(lenghtVar-2); //get random clusters using binary chop method
		curClusters = RGFtoBundles.RGFToGroup(rp);
		//curClusters = getClusters.getCluster(6); // the six nerve fibers bundles
		//System.out.println (iniCluster);	
		String iniBundles = "initialBundles_"+j;
		Write_Data.writeBundles(curClusters, iniBundles);
		
		BundledData = form_ClusteredData.getData(curClusters, allVFData);
		System.out.println ("No of records : "+BundledData.length);
		System.out.println ("No of variables : "+BundledData[0].length);
		String bundledData =  "bundledData_"+j;
		Write_Data.writeData(bundledData, BundledData);
		
		Instances iData = CreateInstances.createInstances(BundledData, curClusters); // transform the VF data into Instances format
		iData.deleteAttributeAt(0); //remove PID column from the data
		iData.setClassIndex(iData.numAttributes()-1); //assigns the last attribute as the class variable
		iData = Randomise_Data.RandData(iData); //randomise the data (this dataset is ready for modelling)
		
		//System.out.println (iData);
		
		//double curAccuracy = CV_RepeatModelling.repeatModelling(repeat, iData, model, modelname, fold);//repeat modelling
		double curAccuracy = runModelling.modellingAccuracy(iData, model, modelname, fold); //10 folds modelling
		System.out.println ("Initial accuracy is : "+curAccuracy+"\n");
		
		double [][] result = new double [iter][14];
		double best = 0;
		ArrayList<ArrayList<Integer>> bestClusters = new ArrayList<ArrayList<Integer>> ();
		int oldSwap = 0;
		int noSwap = 0;
		double [][] accFold =  new double [iter][10];
		
		String file_name2 = "Six_Bundles.txt"; //change VF bundle
		String filepath2 =  "D:\\Java Input\\Bundles Files\\"+file_name2;
		ArrayList<ArrayList<Integer>> Sixbundles = ReadBundles.readBundles (filepath2, file_name2);
		double wk = 0;		
		double pre_fit = 0.0;
		int fit_conv = 0;
		
		for (int i=0; i<iter; i++)
		{
			pre_fit = curAccuracy;
			result [i][0] = (i+1);
			result [i][1] = curAccuracy;
			oldSwap = noSwap;
			System.out.println ("\nRun # : "+j);
			System.out.println ("No. of record: "+BundledData.length);
			System.out.println ("Iteration : "+i);
			System.out.println ("QA : "+qa);
			System.out.println ("QV : "+qv);
			
			ArrayList<ArrayList<Integer>> newClusters = Bundles_RandomCreation.cloneArrayList(curClusters);
			
			double newVisit0 = GSA_Algo.SmallChange(qv, temp, visitA);
			System.out.println ("Current accuracy is : "+curAccuracy);
			System.out.println ("Newvisit0 is : "+newVisit0);
			System.out.println ("Current clusters size is : "+curClusters.size());
			noSwap = (int)(newVisit0*52);
		
			//System.out.println ("NoSwap is : "+noSwap);
			if(noSwap==0 ||noSwap==1)
			noSwap = 2;
			System.out.println ("NoSwap is : "+noSwap);
			
			newClusters = Bundles_Arrangement.newArrangementGSA(newClusters, noSwap);
			//System.out.println ("New cluster size is : "+newClusters.size());
			allVFData =  Sampling_Patient.searchPatient(rawData);// resampling each iteration
			BundledData = form_ClusteredData.getData(newClusters, allVFData); // form a bundled VF data
			iData = CreateInstances.createInstances(BundledData, newClusters); // convert into instances data
			iData.deleteAttributeAt(0); //remove PID column from the data
			iData.setClassIndex(iData.numAttributes()-1); //assigns the last attribute as the class variable
			iData = Randomise_Data.RandData(iData); //randomise the data (this dataset is ready for modelling)
			
			//double newAccuracy = CV_RepeatModelling.repeatModelling(repeat, iData, model, modelname, fold); //modelling process to get the accuracy
			double newAccuracy = runModelling.modellingAccuracy(iData, model, modelname, fold); //10 folds modelling
			System.out.println ("new accuracy is : "+newAccuracy);
			result [i][2] = newAccuracy;
			result [i][3] = curClusters.size();
			result [i][4] = newClusters.size();
			result [i][5] = oldSwap;
			result [i][6] = noSwap;
			
			finalFit [j][1] = qa; 
			finalFit [j][2] = qv; 
			finalFit [j][3] = temp;
				
			if (newAccuracy>best)
			{
				best = newAccuracy;
				finalFit [j][9] = newAccuracy; //to capture the best accuracy
				finalFit [j][8] = (i+1); //to capture the best accuracy point
				finalFit [j][10] = bestClusters.size(); //to capture the best accuracy point
				bestClusters = Bundles_RandomCreation.cloneArrayList(newClusters); // to capture the best clusters
			}
			
			
			if (newAccuracy>curAccuracy)
			{
				curClusters.clear();
				curClusters = Bundles_RandomCreation.cloneArrayList(newClusters);
				curAccuracy = newAccuracy;
				prob = 1;
			}
			else
			{
				random = rand.nextDouble();
				double difFit = (curAccuracy-newAccuracy)/100;  //current accuracy greater than the new accuracy
				//System.out.println("Different fitness is : "+difFit);
				result [i][7] = difFit;
				prob = GSA_Algo.prob(difFit, temp, qa);
				
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
			
			wk = calWK.calWeightedKappa(curClusters, Sixbundles);
			result [i][13] = wk;
			System.out.println ("Weighted Kappa is : "+wk);
			
			result [i][8] = prob;
			result [i][9] = random;
			
			temp = GSA_Algo.NewTemp(iniTemp, (double)(i+1), qv);
			System.out.println ("Temp : " +temp);
			result [i][10] = temp;
			result [i][11] = qa;
			result [i][12] = qv;
			
			newClusters.clear(); 
			
		}
		long timeEnd = System.currentTimeMillis();
		double time = (double) (timeEnd-timestart)/1000;
		
		finalFit [j][4] = curAccuracy; 
		finalFit [j][5] = fit_conv; 
		finalFit [j][6] = curClusters.size(); 
		finalFit [j][7] = time; 
		finalFit [j][11] = wk; 
		
		System.out.println("No of col is : "+result[0].length);
		System.out.println("No of row is : "+result.length);
		
		Write_Result.writeResult(result, j, qa);
		Write_Result.writeBundles(curClusters, modelname, qa, j);
		Write_Result.writeBestBundles(bestClusters, modelname, qa, j);
		System.out.println("Final accuracy is : "+curAccuracy);
		System.out.println("Final cluster size is : "+curClusters.size());
		System.out.println("Final cluster is : "+curClusters);
		System.out.println("Final WK is : "+wk);
		System.out.println("Output saved to : D:\\Java Output\\MPJ Experiment - 2nd Paper\\GSA\\VF");
	}
	

}
