package SM_StochasticHillClimbing;

import java.util.ArrayList;
import java.util.Random;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.core.Instances;
import Bundles_Arrangement.Bundles_Arrangement;
import Bundles_Arrangement.Bundles_RandomCreation;
import Data_Retrieval.Prepare_PatientData;
import Instances_Format.CreateInstances;
import Instances_Format.Randomise_Data;
import Modelling.runModelling;
import VFSix_Bundles.getClusters;
import VFpreparation.ReadFile;
import VFpreparation.form_ClusteredData;
import VFpreparation.getSource;

public class Main_SM_SHC {
	
	static Random rand = new Random (System.currentTimeMillis());
	
	public static void main (String [] args) throws Exception
	{
		String filesource = getSource.getData();
		String sep = ",";
		double [][] rawData = ReadFile.ReadFileArray(filesource, sep); //raw data
		double [][] vf52Data = Prepare_PatientData.allPatientVFData(rawData); //data contains 12159 record of VF
		double [][] BundledData = null;
		
		//Instances iData_initial = null;
		Instances iData = null;
		int iter = 500;
		int fold = 10;
		double newAccuracy =0;
		double curAccuracy = 0;
		Classifier model = new NaiveBayesMultinomial ();
		//Classifier model = new NaiveBayes ();
		String modelName = "NBayesMultinomial";
		//String modelName = "NBayes";
		
		ArrayList<ArrayList<Integer>> newCluster = new ArrayList<ArrayList<Integer>> ();
		ArrayList<ArrayList<Integer>> curCluster = new ArrayList<ArrayList<Integer>> ();
		
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
		
		double [][] result = new double [iter][6];
		
		System.out.println ();
		
		for (int i=0; i<iter; i++)
		{
			System.out.println ("Iteration : "+i);
			System.out.println ("Current accuracy is : "+curAccuracy);
			System.out.println ("Current cluster size is : "+curCluster.size());
			
			newCluster = Bundles_RandomCreation.cloneArrayList(curCluster);
			newCluster = Bundles_Arrangement.newArrangement (newCluster); // get a new cluster for next solution
			
			BundledData = form_ClusteredData.getData(newCluster, vf52Data); //prepare a VF data according to the bundles
			iData = CreateInstances.createInstances(BundledData, newCluster); // transform the VF data into Instances format
			iData.deleteAttributeAt(0); //remove PID column from the data
			iData.setClassIndex(iData.numAttributes()-1); // set the class variable
			iData = Randomise_Data.RandData(iData);  //randomise the data
			newAccuracy = runModelling.modellingAccuracy(iData, model, modelName, fold);
			
			System.out.println ("New accuracy is : "+newAccuracy);		
			System.out.println ("New cluster size is : "+newCluster.size());
			
			double prob = 0;
			double T = 0.0721;
			
				//double diff = Math.abs(newAccuracy-curAccuracy);
				double diff = curAccuracy-newAccuracy;
				System.out.println ("diff is : "+diff);		
				//diff /=i;
				//System.out.println ("diff2 is : "+diff);		
				prob = 1/(1+Math.exp((diff)/(T)));
				double randNumber = rand.nextDouble();
				
				System.out.println ("Prob is : "+prob);		
				System.out.println ("Rand number is : "+randNumber);		
				
				if (prob > randNumber)
				{
					curCluster.clear();
					curAccuracy = newAccuracy;
					curCluster = Bundles_RandomCreation.cloneArrayList(newCluster);
					System.out.println ("This is PERFORMED ---> prob > randNumber");
				}
			
			SHC_RegResult.result(result, i, curAccuracy, newAccuracy, prob, curCluster.size(), newCluster.size());
			newCluster.clear();
			System.out.println ();
			
		}
		
		SHC_WriteResult.writeResult(result, modelName);
		SHC_WriteResult.writeBundles(curCluster);
		System.out.println ("Model is : "+modelName);
		System.out.println ("Final accuracy is : "+curAccuracy);
		System.out.println ("Final cluster size is : "+curCluster.size());
		System.out.println ("Final cluster elements is : "+curCluster);
		//System.out.println ("Running time is : "+time);
		
	}

}
