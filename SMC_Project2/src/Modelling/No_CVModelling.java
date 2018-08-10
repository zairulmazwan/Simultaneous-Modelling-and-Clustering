package Modelling;

import java.util.ArrayList;
import java.util.Random;

import Bundles.BundledData;
import Bundles.MV3Bundles;
import Instances_Format.CreateInstances;
import Instances_Format.Randomise_Data;
import Read_Data.Read_Data;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.FastVector;
import weka.core.Instances;

public class No_CVModelling {
	
	public static void main (String [] arg) throws Exception
	{
		double [][] rawData = Read_Data.getAllData();
		int lenghtVar = rawData[0].length;
		int fold = 10;
		Classifier model = new NaiveBayesUpdateable ();
		//Classifier model = new NaiveBayesMultinomial ();
		//Classifier model = new J48 ();
		String modelName = "NBU";
		
		//ArrayList<ArrayList<Integer>> curClusters = Form_8Bundles.getBundles (8);
		//ArrayList<ArrayList<Integer>> curClusters = Var_3Bundles.VARBundles();
		//ArrayList<ArrayList<Integer>> curClusters = VAR_5Bundles.VARBundles();
		ArrayList<ArrayList<Integer>> curClusters = MV3Bundles.VARBundles();
		//ArrayList<ArrayList<Integer>> curClusters = new ArrayList<ArrayList<Integer>> ();
		//curClusters = Bundles_Creation.initialCluster(curClusters, lenghtVar); //random initial clusters
		
		System.out.println ("Bundles : "+curClusters);
		
		
		double [][] bundledData = BundledData.bundlesData(rawData, curClusters); //prepare data based on bundles
		bundledData = BundledData.averagedData(curClusters, bundledData); //average the bundled data
		String avgData = "avgData";
		//Write_Data.writeData(avgData, bundledData);
		
		System.out.println ("No. of col : "+bundledData[0].length);
		//Read_Data.printArray(bundledData);
		
		Instances iData = CreateInstances.createInstances(bundledData, curClusters);
		iData.setClassIndex(iData.numAttributes()-1); //assigns the last attribute as the class variable
		iData = Randomise_Data.RandData(iData); //randomise the data (this dataset is ready for modelling)
		
		double acc = modelling (iData, model, modelName);
	
		System.out.println (acc);
	}
	
	public static double modelling (Instances iData, Classifier model, String modelName) throws Exception
	{
		double accuracy = 0;
		FastVector prediction = new FastVector();
	
		Evaluation validation = classify(model, iData);
		prediction.appendElements(validation.predictions());
		accuracy = Classification.calAccuracy(prediction);
		
		return accuracy;
	}
	
	public static Evaluation classify (Classifier model, Instances iData) throws Exception
	{
		Evaluation ev = new Evaluation (iData);
		model.buildClassifier(iData);
		ev.evaluateModel(model, iData);
		
		return ev;
	}
	
	public static Instances RandData (Instances data)
	{
		int seed = 789;
		int folds = 10;
		Instances randData = null;
		
		Random rand = new Random (seed);
		randData = new Instances (data);
		randData.randomize(rand);
		
		if (randData.classAttribute().isNominal())
			randData.stratify(folds);
		
		return randData;
			
	}

}
