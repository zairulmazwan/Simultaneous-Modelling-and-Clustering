package Simultaneous_Modelling;

import java.util.ArrayList;

import Data_Retrieval.Prepare_PatientData;
import Data_Retrieval.ReadWrite_DataRetrieval;
import Instances_Format.CreateInstances;
import Instances_Format.Randomise_Data;
import Modelling.runModelling;
import VFSix_Bundles.getClusters;
import VFpreparation.ReadFile;
import VFpreparation.form_ClusteredData;
import VFpreparation.getSource;
import weka.core.Instances;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.trees.J48;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.Classifier;

import java.util.Random;


public class Main_SM {
	
	public static void main (String [] args) throws Exception
	{
		String filesource = getSource.getData();
		String sep = ",";
		double [][] rawData = ReadFile.ReadFileArray(filesource, sep);
		double [][] vfData = Prepare_PatientData.allPatientVFData(rawData); //data contains 12159 record of VF
		
		//ReadWrite_DataRetrieval.writeAllPatient(vfData); // to write the 52 VF data to a txt file
		
		Instances iData = null;
		Classifier model = new NaiveBayesMultinomial ();
		//Classifier model = new J48 ();
		//Classifier model = new NaiveBayes ();
		String modelName = "Multinomial_NaiveBayes";
		//String modelName = "Tree_J48";
		//String modelName = "NaiveBayes";
		
		int iter = 10000;
		int fold = 10;
		double initialAccuracy = 0;
		double curAccuracy = 0;
		double newAccuracy = 0;
		
		ArrayList<ArrayList<Integer>> VFCluster = getClusters.getCluster(6);
		vfData = form_ClusteredData.getData(VFCluster, vfData);
		//ReadFile.PrintArray(vfData);
		//ReadFile.writeBundle(vfData, modelName, VFCluster);
		
		iData = CreateInstances.createInstances(vfData, VFCluster); //convert data from an array format to instances format
		iData.setClassIndex(iData.numAttributes()-1); // set the class variable
		iData.deleteAttributeAt(0);; //remove PID column from the data
		iData = Randomise_Data.RandData(iData); //randomise the data
		//System.out.println (iData);
		System.out.println ();
		
		for (int i=0; i<iter; i++)
		{
			System.out.println ("Iteration "+i);
			
			iData = Randomise_Data.RandData(iData); //randomise the data
			initialAccuracy = runModelling.modellingAccuracy(iData, model, modelName, fold);
			
			System.out.println ("Cluster size is : "+VFCluster.size());
			System.out.println ("Cluster elements are : "+VFCluster);
			System.out.println (initialAccuracy);
			System.out.println ();
		}
		System.out.println (iData);
		//System.out.println (model);
		
		
	}

}
