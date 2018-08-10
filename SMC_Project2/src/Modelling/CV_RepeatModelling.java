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
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.FastVector;
import weka.core.Instances;
import weka.classifiers.evaluation.Prediction;

public class CV_RepeatModelling {
	
	public static void main (String [] args) throws Exception
	{
		double [][] rawData = Read_Data.getAllData();
		int lenghtVar = rawData[0].length;
		int fold = 2;
		int repeat = 10;
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
				
		double acc = repeatModelling (repeat, iData, model, modelName, fold);
	
		System.out.println (acc);
	}
	
	public static double CVmodelling (Instances iData, Classifier model, String modelName, int fold) throws Exception
	{
		FastVector prediction = new FastVector();
		Instances [][] split = Classification.crossValidation(iData, fold);
		Instances [] trainingSplit = split [0];
		Instances [] testSplit = split [1];
		
		double sumAccuracy = 0;
		double accuracy = 0;
		
		double [] acc = new double [fold];
		
		for (int i=0; i<fold; i++)
		{
			//System.out.println("Iteration "+i);
			//System.out.println("Model is : "+classifier);
			Evaluation validation = Classification.classify(model, trainingSplit[i], testSplit[i]);
			prediction.appendElements(validation.predictions());
	
			//System.out.println(model.toString());
			accuracy = Classification.calAccuracy(prediction);
			
			acc [i] = accuracy;
			
			sumAccuracy += accuracy;
			//System.out.println("Accuracy of model "+model.getClass().getSimpleName() +" is " +String.format("%.2f%%", accuracy));
			//System.out.println("Accuracy of the model is " +accuracy +"\n");
		}
		//printArray (acc);
		accuracy = sumAccuracy/fold;
		return accuracy;
	}
	
	public static double repeatModelling (int repeat, Instances iData, Classifier model, String modelName, int fold) throws Exception
	{
		double Sumaccuracy = 0;
		double accuracy = 0;
		
		double [] acc = new double [repeat];
		
		for (int i=0; i<repeat; i++)
		{
			iData = RandData (iData);
			accuracy = CVmodelling (iData, model, modelName, fold);
			acc [i] = accuracy;
			
			Sumaccuracy += accuracy;
			
		}
		
		//printArray (acc);
		accuracy = Sumaccuracy/repeat;
		return accuracy;
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
	
	public static void printArray (double [] array)
	{
		for (int i=0; i<array.length; i++)
		{
			System.out.print (array[i]);
			System.out.print (" ");
		}
		
		System.out.println ();
	}

}
