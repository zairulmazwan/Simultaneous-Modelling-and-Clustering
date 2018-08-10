package SM_SimulatedAnnealing;

import java.util.ArrayList;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import Bundles_Arrangement.Bundles_Arrangement;
import Bundles_Arrangement.Bundles_RandomCreation;
import Data_Retrieval.Prepare_PatientData;
import Instances_Format.CreateInstances;
import Instances_Format.Randomise_Data;
import Modelling.runModelling;
import SM_HillClimbing.HC_RegResult;
import VFSix_Bundles.getClusters;
import VFpreparation.ReadFile;
import VFpreparation.form_ClusteredData;
import VFpreparation.getSource;

public class Main_SM_SimulatedAnnealing {
	
	static Random rand = new Random (System.currentTimeMillis());
	
	public static void main (String [] args) throws Exception
	{
		String filesource = getSource.getData();
		String sep = ",";
		double [][] rawData = ReadFile.ReadFileArray(filesource, sep); //raw data
		double [][] vf52Data = Prepare_PatientData.allPatientVFData(rawData); //data contains 12159 record of VF
		double [][] BundledData = null;
		
		Instances iData = null;
		int iter0 = 5000;
		int iter2 = (5*iter0)/100;
		int iter = iter0-iter2;
		int fold = 10;
		double newAccuracy =0;
		double curAccuracy = 0;
		Classifier model = new NaiveBayesMultinomial ();
		//Classifier model = new J48 ();
		//Classifier model = new NaiveBayes ();
		String modelName = "MNB";
		//String modelName = "NBayes";
		//String modelName = "J48";
		
		ArrayList<ArrayList<Integer>> newCluster = new ArrayList<ArrayList<Integer>> ();
		ArrayList<ArrayList<Integer>> curCluster = new ArrayList<ArrayList<Integer>> ();
		
		newCluster = Bundles_RandomCreation.initialCluster(newCluster); // get a random bundles
		//newCluster = getClusters.getCluster(6); // get an initial bundles - 6 bundles
		BundledData = form_ClusteredData.getData(newCluster, vf52Data); //prepare a VF data according to the bundles
		
		iData = CreateInstances.createInstances(BundledData, newCluster); // transform the VF data into Instances format
		iData.deleteAttributeAt(0); //remove PID column from the data
		iData.setClassIndex(iData.numAttributes()-1); // set the class variable
		iData = Randomise_Data.RandData(iData);  //randomise the data
		newAccuracy = runModelling.modellingAccuracy(iData, model, modelName, fold);
		
		System.out.println ("Initial cluster size is : "+newCluster.size());
		System.out.println ("Cluster elements are : "+newCluster);
		System.out.println ("Initial accuracy is : "+newAccuracy);
		//System.out.println (iData);
		curAccuracy = newAccuracy;
		curCluster = Bundles_RandomCreation.cloneArrayList(newCluster);
	
		System.out.println ();
		long [] timestart = new long [1];
		
		double temp = SA_Temp.temp(vf52Data, model, modelName, fold, iter2, timestart);
		double lamda = SA_Temp.lamda(temp, iter);
		
		double [][] result = new double [iter][6];
		
		for (int i=0; i<iter; i++)
		{
			
			newCluster = Bundles_RandomCreation.cloneArrayList(curCluster);
			newCluster = Bundles_Arrangement.newArrangement (newCluster); // get a new cluster for next solution
			
			BundledData = form_ClusteredData.getData(newCluster, vf52Data); //prepare a VF data according to the bundles
			iData = CreateInstances.createInstances(BundledData, newCluster); // transform the VF data into Instances format
			iData.deleteAttributeAt(0); //remove PID column from the data
			iData.setClassIndex(iData.numAttributes()-1); // set the class variable
			iData = Randomise_Data.RandData(iData);  //randomise the data
			newAccuracy = runModelling.modellingAccuracy(iData, model, modelName, fold);
			
			System.out.println ("Iteration : "+i);
			System.out.println ("Current accuracy is : "+curAccuracy);
			System.out.println ("New accuracy is : "+newAccuracy);
			System.out.println ("Current cluster size is : "+curCluster.size());
			System.out.println ("New cluster size is : "+newCluster.size());
			System.out.println ("Current temp is : "+temp);
			
			if (newAccuracy>curAccuracy)
			{
				curCluster.clear();
				curAccuracy = newAccuracy;
				curCluster = Bundles_RandomCreation.cloneArrayList(newCluster);
				System.out.println ("This is PERFORMED");
			}
			else
			{
				double diffFit = newAccuracy-curAccuracy;
				double prob = Math.exp (diffFit/temp);
				double randomNumber = rand.nextDouble(); //to get random number
				
				if (prob > randomNumber)
				{
					curCluster.clear();
					curCluster = Bundles_RandomCreation.cloneArrayList(newCluster);
					curAccuracy = newAccuracy;
					System.out.println("This IS PERFORMED - prob > randomNumber");
				}
			}
			
			temp *= lamda;
			result = SA_RegResult.result(result, i, curAccuracy, newAccuracy, temp, curCluster.size(), newCluster.size());
			newCluster.clear();
			System.out.println ();
			
		}
		
		long startTime = timestart [0];
		long endTime = System.currentTimeMillis();
		double time = (endTime-startTime)/1000;
		
		SA_WriteResult.writeResult(result, modelName, time);
		SA_WriteResult.writeBundles(curCluster, modelName);
		System.out.println ("Model is : "+modelName);
		System.out.println ("Final accuracy is : "+curAccuracy);
		System.out.println ("Final cluster size is : "+curCluster.size());
		System.out.println ("Final cluster elements is : "+curCluster);
		System.out.println ("Running time is : "+time);
	}

}
