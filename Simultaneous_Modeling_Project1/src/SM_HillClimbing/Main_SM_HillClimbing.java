package SM_HillClimbing;

import weka.core.Instances;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.classifiers.Classifier;

import java.util.ArrayList;

import Bundles_Arrangement.Bundles_RandomCreation;
import Bundles_Arrangement.Bundles_Arrangement;
import Data_Retrieval.Prepare_PatientData;
import Instances_Format.CreateInstances;
import Instances_Format.Randomise_Data;
import Modelling.newSolution_Accuracy;
import Modelling.runModelling;
import VFSix_Bundles.getClusters;
import VFpreparation.ReadFile;
import VFpreparation.form_ClusteredData;
import VFpreparation.getSource;
import Sampled_Data.Sampling_Patient;

public class Main_SM_HillClimbing {
	
	public static void main (String [] args) throws Exception
	{
		String filesource = getSource.getData();
		String sep = ",";
		double [][] rawData = ReadFile.ReadFileArray(filesource, sep); //raw data
		//double [][] vf52Data = Prepare_PatientData.patientVFData(rawData); //data contains 12159 record of VF
		double [][] vf52Data = Sampling_Patient.searchPatient(rawData); // contains sampled VF
		double [][] BundledData = null;
		
		Instances iData_initial = null;
		Instances iData = null;
		int iter = 500;
		int fold = 10;
		double newAccuracy =0;
		double curAccuracy = 0;
		Classifier model = new NaiveBayesMultinomial ();
		//Classifier model = new NaiveBayesUpdateable ();
		//Classifier model = new J48 ();
		//Classifier model = new NaiveBayes ();
		String modelName = "NBM";
		//String modelName = "NB";
		//String modelName = "J48";
		//String modelName = "NBU";
		
		ArrayList<ArrayList<Integer>> newCluster = new ArrayList<ArrayList<Integer>> ();
		ArrayList<ArrayList<Integer>> curCluster = new ArrayList<ArrayList<Integer>> ();
		
		//newCluster = Bundles_RandomCreation.initialCluster(newCluster); // get a random bundles
		newCluster = getClusters.getCluster(6); // get an initial bundles - 6 bundles
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
		double [][] result = new double [iter][5];
		System.out.println ();
		long timestart = 0;
		
		for (int i=0; i<iter; i++)
		{
			if (i==0)
			{
				timestart = System.currentTimeMillis();
			}
			
			newCluster = Bundles_RandomCreation.cloneArrayList(curCluster);
			newCluster = Bundles_Arrangement.newArrangement (newCluster); // get a new cluster for next solution
			//newAccuracy = newSolution_Accuracy.newAccuracy(iData, newCluster, vf52Data, model, modelName, fold);
			
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
			
			if (newAccuracy>curAccuracy)
			{
				curCluster.clear();
				curAccuracy = newAccuracy;
				curCluster = Bundles_RandomCreation.cloneArrayList(newCluster);
				System.out.println ("This is PERFORMED");
			}
			
			result = HC_RegResult.result(result, i, curAccuracy, newAccuracy, curCluster.size(), newCluster.size());
			newCluster.clear();
			System.out.println ();
			
		}
		
		long endTime = System.currentTimeMillis();
		long time = (endTime-timestart)/1000;
		
		HC_WriteResult.writeResult(result, modelName, time);
		HC_WriteResult.writeBundles(curCluster, modelName);
		System.out.println ("Model is : "+modelName);
		System.out.println ("Final accuracy is : "+curAccuracy);
		System.out.println ("Final cluster size is : "+curCluster.size());
		System.out.println ("Final cluster elements is : "+curCluster);
		System.out.println ("Running time is : "+time);
	}

}
