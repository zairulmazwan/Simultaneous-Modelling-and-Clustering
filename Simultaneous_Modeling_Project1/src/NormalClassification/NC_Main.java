package NormalClassification;

import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import Data_Retrieval.Prepare_PatientData;
import Instances_Format.CreateInstances;
import Instances_Format.Randomise_Data;
import Modelling.runModelling;
import VFSix_Bundles.getClusters;
import VFpreparation.ReadFile;
import VFpreparation.form_ClusteredData;
import VFpreparation.getSource;

public class NC_Main {
	
	public static void main (String [] args) throws Exception
	{
		String filesource = getSource.getData();
		String sep = ",";
		double [][] rawData = ReadFile.ReadFileArray(filesource, sep); //raw data
		double [][] vf52Data = Prepare_PatientData.allPatientVFData(rawData); //data contains 12159 record of VF
		double [][] BundledData = null;
		
		Instances iData_initial = null;
		Instances iData = null;
		int iter = 3000;
		int fold = 10;
		double accuracy1 =0;
		double accuracy2 =0;
		double accuracy3 =0;
		
		
		Classifier model1 = new NaiveBayesMultinomial ();
		Classifier model2 = new NaiveBayes ();
		Classifier model3 = new J48 ();
		String modelName1 = "NBayesMultinomial";
		String modelName2 = "NBayes";
		String modelName3 = "TreeJ48";
		
		ArrayList<ArrayList<Integer>> cluster = new ArrayList<ArrayList<Integer>> ();
		cluster = getClusters.getCluster(6); // get an initial bundles - 6 bundles
		BundledData = form_ClusteredData.getData(cluster, vf52Data); //prepare a VF data according to the bundles
		
		iData = CreateInstances.createInstances(BundledData, cluster); // transform the VF data into Instances format
		iData.deleteAttributeAt(0); //remove PID column from the data
		iData.setClassIndex(iData.numAttributes()-1); // set the class variable
		iData = Randomise_Data.RandData(iData);  //randomise the data
		accuracy1 = runModelling.modellingAccuracy(iData, model1, modelName1, fold);
		accuracy2 = runModelling.modellingAccuracy(iData, model2, modelName2, fold);
		accuracy3 = runModelling.modellingAccuracy(iData, model3, modelName3, fold);
		
		System.out.println ("RESULT: ");
		System.out.println ("Classification for : "+modelName1);
		System.out.println ("Cluster size is : "+cluster.size());
		System.out.println ("Cluster elements are : "+cluster);
		System.out.println ("Initial accuracy is : "+accuracy1);
		System.out.println ("-----------------------------------");
		
		System.out.println ("Classification for : "+modelName2);
		System.out.println ("Cluster size is : "+cluster.size());
		System.out.println ("Cluster elements are : "+cluster);
		System.out.println ("Initial accuracy is : "+accuracy2);
		System.out.println ("-----------------------------------");
		
		System.out.println ("Classification for : "+modelName3);
		System.out.println ("Cluster size is : "+cluster.size());
		System.out.println ("Cluster elements are : "+cluster);
		System.out.println ("Initial accuracy is : "+accuracy3);
		System.out.println ("-----------------------------------");
	}

}
