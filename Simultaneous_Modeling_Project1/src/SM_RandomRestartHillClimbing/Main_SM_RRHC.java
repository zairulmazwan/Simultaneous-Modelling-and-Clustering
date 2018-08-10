package SM_RandomRestartHillClimbing;

import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import Bundles_Arrangement.Bundles_RandomCreation;
import Bundles_Arrangement.Bundles_Arrangement;
import Data_Retrieval.Prepare_PatientData;
import Instances_Format.CreateInstances;
import Instances_Format.Randomise_Data;
import Modelling.runModelling;
import VFSix_Bundles.getClusters;
import VFpreparation.ReadFile;
import VFpreparation.form_ClusteredData;
import VFpreparation.getSource;

public class Main_SM_RRHC {
	
	public static void main (String [] args) throws Exception
	{
		String filesource = getSource.getData();
		String sep = ",";
		double [][] rawData = ReadFile.ReadFileArray(filesource, sep); //raw data
		double [][] vf52Data = Prepare_PatientData.allPatientVFData(rawData); //data contains 12159 record of VF
		double [][] BundledData = null;
		
		Instances iData_initial = null;
		Instances iData = null;
		int restartIter = 10;
		double newAccuracy =0;
		double curAccuracy = 0;
		//Classifier model = new NaiveBayesMultinomial ();
		Classifier model = new J48 ();
		//Classifier model = new NaiveBayes ();
		//String modelName = "MNB";
		String modelName = "J48";
		//String modelName = "NB";
		long timeStart = 0;
		
		for (int i=0; i<restartIter; i++)
		{
			if (i == 0)
			{
				timeStart = System.currentTimeMillis();
			}
			
			runRestart (vf52Data, model, modelName, i);
			System.out.println ();
		}
		
		long endtime = System.currentTimeMillis();
		double time = (endtime-timeStart)/1000;
		RRHC_WriteResult.writetime(time, modelName);
	}
	 
	public static void runRestart (double [][] vf52Data, Classifier model, String modelName, int restart) throws Exception
	{
		int iter = 1000;
		int fold = 10;
		double curAccuracy = 0;
		double newAccuracy = 0;
		
		//ArrayList<ArrayList<Integer>> curCluster = getClusters.getCluster(6);
		ArrayList<ArrayList<Integer>> curCluster = new ArrayList<ArrayList<Integer>> () ;
		curCluster = Bundles_RandomCreation.initialCluster(curCluster);
		ArrayList<ArrayList<Integer>> newCluster = new ArrayList<ArrayList<Integer>> ();
		double [][] bundledData = form_ClusteredData.getData(curCluster, vf52Data);
		
		Instances iData = CreateInstances.createInstances(bundledData, curCluster);
		iData.deleteAttributeAt(0); //remove PID column from the data
		iData.setClassIndex(iData.numAttributes()-1); // set the class variable
		iData = Randomise_Data.RandData(iData);  //randomise the data
		newAccuracy = runModelling.modellingAccuracy(iData, model, modelName, fold);
	
		System.out.println ("Restart number : "+restart);
		System.out.println ("Initial accuracy is : "+newAccuracy);
		
		curAccuracy = newAccuracy;
		long timeStart = 0;
		
		double [][] result = new double [iter][5];
		
		for (int i=0; i<iter; i++)
		{
			if (i == 0)
			{
				timeStart = System.currentTimeMillis();
			}
			
			System.out.println ("Iteration : "+i);
			System.out.println ("Current accuracy is : "+curAccuracy);
			
			newCluster = Bundles_RandomCreation.cloneArrayList(curCluster);
			newCluster = Bundles_Arrangement.newArrangement(newCluster);
			
			bundledData = form_ClusteredData.getData(newCluster, vf52Data);
			
			iData = CreateInstances.createInstances(bundledData, newCluster);
			iData.deleteAttributeAt(0); //remove PID column from the data
			iData.setClassIndex(iData.numAttributes()-1); // set the class variable
			iData = Randomise_Data.RandData(iData);  //randomise the data
			newAccuracy = runModelling.modellingAccuracy(iData, model, modelName, fold);
			
			System.out.println ("New accuracy is : "+newAccuracy);
			System.out.println ("New cluster size is : "+newCluster.size());
			
			if (newAccuracy > curAccuracy)
			{
				curCluster.clear();
				curCluster = Bundles_RandomCreation.cloneArrayList(newCluster);
				curAccuracy = newAccuracy;
				System.out.println("This IS PERFORMED");
			}
			
			result = RRHC_RegResult.result(result, i, curAccuracy, newAccuracy, curCluster.size(), newCluster.size());
			newCluster.clear();
			System.out.println();
			
		}
		
		long endTime = System.currentTimeMillis();
		long time = (endTime-timeStart)/1000;
		RRHC_WriteResult.writeResult(result, modelName, time, restart);
		RRHC_WriteResult.writeBundles(curCluster, restart, modelName);
		
		System.out.println ("Model is : "+modelName);
		System.out.println ("Final accuracy is : "+curAccuracy);
		System.out.println ("Final cluster size is : "+curCluster.size());
	}

}
