package Modelling;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import Data_Retrieval.Prepare_PatientData;
import Bundles.Form_8Bundles;
import Bundles.BundledData;
import Bundles.Bundles_Creation;
import Bundles.MV3Bundles;
import Bundles.VAR_5Bundles;
import Bundles.Var_3Bundles;
import Bundles.Write_Data;
import Instances_Format.CreateInstances;
import Instances_Format.Randomise_Data;
import Read_Data.Read_Data;
import Sampled_Data.Sampling_Patient;
import VFSix_Bundles.getClusters;
import VFpreparation.ReadFile;
import VFpreparation.form_ClusteredData;
import VFpreparation.getSource;
import Weighted_Kappa.ReadBundles;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.trees.J48;
import weka.core.FastVector;
import weka.core.Instances;

public class runModelling {
	
	
	public static void main (String [] args) throws Exception
	{
		String filesource = getSource.getData();
		String sep = ",";
		double [][] rawData = ReadFile.ReadFileArray(filesource, sep); // read raw data from path and create into an array
		int lenghtVar = rawData[0].length;
		int fold = 10;
		//Classifier model = new NaiveBayesUpdateable ();
		Classifier model = new NaiveBayesMultinomial ();
		//Classifier model = new J48 ();
		String modelName = "NBU";
		
		//ArrayList<ArrayList<Integer>> curClusters = Form_8Bundles.getBundles (8);
		//ArrayList<ArrayList<Integer>> curClusters = Var_3Bundles.VARBundles();
		//ArrayList<ArrayList<Integer>> curClusters = VAR_5Bundles.VARBundles();
		//ArrayList<ArrayList<Integer>> curClusters = getClusters.getCluster(6);
		//ArrayList<ArrayList<Integer>> curClusters = new ArrayList<ArrayList<Integer>> ();
		//curClusters = Bundles_Creation.initialCluster(curClusters, lenghtVar); //random initial clusters
		String file_name2 = "Six_Bundles.txt";
		String filepath2 =  "D:\\Java Input\\Bundles Files\\"+file_name2;
		
		ArrayList<ArrayList<Integer>> curClusters = ReadBundles.readBundles (filepath2, file_name2);
		
		System.out.println ("Bundles : "+curClusters);
		for (int i=0; i<25; i++)
		{
		
		double [][] allVFData =  Sampling_Patient.searchPatient(rawData);
		//double [][] allVFData =  Prepare_PatientData.allPatientVFData(rawData);
		String Data = "processedData";
		writeData(Data, allVFData);
		//double [][] bundledData = BundledData.bundlesData(rawData, curClusters); //prepare data based on bundles
		double [][] bundledData = form_ClusteredData.getData(curClusters, allVFData);
		bundledData = form_ClusteredData.getData(curClusters, allVFData);
		String avgData = "avgData";
		//writeData(avgData, bundledData);
		
		//System.out.println ("No. of col : "+bundledData[0].length);
		//System.out.println ("No. of row : "+bundledData.length);
		//Read_Data.printArray(bundledData);
		
		
			Instances iData = CreateInstances.createInstances(bundledData, curClusters);
			iData.deleteAttributeAt(0); //remove PID column from the data
			iData.setClassIndex(iData.numAttributes()-1); //assigns the last attribute as the class variable
			iData = Randomise_Data.RandData(iData); //randomise the data (this dataset is ready for modelling)
			
			//System.out.println (iData);
		
			double [][] accFold = new double [1][10];
			int k = 0;
			double acc = modellingAccuracy (iData, model, modelName, fold);
			int run = 1;
			System.out.println (acc);
		}
		
		
		
		//printArray (accFold);
		//Write_accFold.write_accFold(run, fold, accFold);
		
	}
	
	
	public static double modellingAccuracy (Instances iData, Classifier model, String modelName, int fold) throws Exception
	{	
		FastVector prediction = new FastVector();
		Instances [][] split = Classification.crossValidation(iData, fold);
		Instances [] trainingSplit = split [0];
		Instances [] testSplit = split [1];
				
		//System.out.println("TestSplit size is : "+testSplit.length);
		//System.out.println("TrainingSplit size is : "+trainingSplit.length);
		
		//System.out.println("testSplit[0] is : "+testSplit[0]);
		//System.out.println("TrainingSplit size is : "+trainingSplit.length);
		//System.out.println();
		
		//double [][] result = new double [fold][2];
		double sumAccuracy = 0;
		double accuracy = 0;
		
		for (int i=0; i<fold; i++)
		{
			//System.out.println("Iteration "+i);
			//System.out.println("Model is : "+classifier);
			Evaluation validation = Classification.classify(model, trainingSplit[i], testSplit[i]);
			prediction.appendElements(validation.predictions());
			//System.out.println(model.toString());
			accuracy = Classification.calAccuracy(prediction);
			sumAccuracy += accuracy;
			//System.out.println("Accuracy of model "+model.getClass().getSimpleName() +" is " +String.format("%.2f%%", accuracy));
			//System.out.println("Accuracy of the model is " +accuracy +"\n");
		}
		accuracy = sumAccuracy/fold;
		//System.out.println("SumAccuracy of the model is " +sumAccuracy +"\n");
		return accuracy;
	}

	public static double modellingAccuracy1 (Instances iData, Classifier model, String modelName, int fold, double [][] accFold, int k) throws Exception
	{	
		FastVector prediction = new FastVector();
		Instances [][] split = Classification.crossValidation(iData, fold);
		Instances [] trainingSplit = split [0];
		Instances [] testSplit = split [1];
				
		//System.out.println("TestSplit size is : "+testSplit.length);
		//System.out.println("TrainingSplit size is : "+trainingSplit.length);
		
		//System.out.println("testSplit[0] is : "+testSplit[0]);
		//System.out.println("TrainingSplit size is : "+trainingSplit.length);
		//System.out.println();
		
		//double [][] result = new double [fold][2];
		double sumAccuracy = 0;
		double accuracy = 0;
		
		for (int i=0; i<fold; i++)
		{
			//System.out.println("Iteration "+i);
			//System.out.println("Model is : "+classifier);
			Evaluation validation = Classification.classify(model, trainingSplit[i], testSplit[i]);
			prediction.appendElements(validation.predictions());
			//System.out.println(model.toString());
			accuracy = Classification.calAccuracy(prediction);
			accFold [k][i] = accuracy;
			sumAccuracy += accuracy;
			//System.out.println("Accuracy of model "+model.getClass().getSimpleName() +" is " +String.format("%.2f%%", accuracy));
			//System.out.println("Accuracy of the model is " +accuracy +"\n");
		}
		accuracy = sumAccuracy/fold;
		//System.out.println("SumAccuracy of the model is " +sumAccuracy +"\n");
		return accuracy;
	}
	
	// This modelling accuracy gets the minimum accuracy in the 10 folds accuracies
	public static double modellingAccuracy2 (Instances iData, Classifier model, String modelName, int fold, double [][] accFold, int k) throws Exception
	{	
		FastVector prediction = new FastVector();
		Instances [][] split = Classification.crossValidation(iData, fold);
		Instances [] trainingSplit = split [0];
		Instances [] testSplit = split [1];
				
		//System.out.println("TestSplit size is : "+testSplit.length);
		//System.out.println("TrainingSplit size is : "+trainingSplit.length);
		
		//System.out.println("testSplit[0] is : "+testSplit[0]);
		//System.out.println("TrainingSplit size is : "+trainingSplit.length);
		//System.out.println();
		
		//double [][] result = new double [fold][2];
		double sumAccuracy = 0;
		double accuracy = 0;
		ArrayList<Double> minAcc =  new ArrayList<Double> ();
		for (int i=0; i<fold; i++)
		{
			//System.out.println("Iteration "+i);
			//System.out.println("Model is : "+classifier);
			Evaluation validation = Classification.classify(model, trainingSplit[i], testSplit[i]);
			prediction.appendElements(validation.predictions());
			//System.out.println(model.toString());
			accuracy = Classification.calAccuracy(prediction);
			accFold [k][i] = accuracy;
			minAcc.add(accuracy);
			sumAccuracy += accuracy;
			//System.out.println("Accuracy of model "+model.getClass().getSimpleName() +" is " +String.format("%.2f%%", accuracy));
			//System.out.println("Accuracy of the model is " +accuracy +"\n");
		}
		
		accuracy = Collections.min(minAcc);
		//accuracy = sumAccuracy/fold;
		//System.out.println("SumAccuracy of the model is " +sumAccuracy +"\n");
		return accuracy;
	}
	
	public static void printArray (double [][] array)
	{
		for (int i=0; i<array.length; i++)
		{
			for (int j=0; j<array[i].length; j++)
			{
				System.out.print (array[i][j]);
				System.out.print (" ");
			}
			
		}
		
		System.out.println ();
	}
	
	public static void writeData (String filename, double [][] data)
	{
		String filepath = "D:\\Java Output\\RunModelling\\"+filename+".txt";
		//String filepath = "C:\\Zairul Mazwan\\Java Output\\SMC Framework Benchmark\\"+filename+".txt";
		
		try
		{
			FileWriter fw = new FileWriter (filepath);
			BufferedWriter bw = new BufferedWriter (fw);
			
			for (int i=0; i<data.length; i++)
			{
				for (int j=0; j<data[i].length; j++)
				{
					double record = data [i][j];
					bw.write(Double.toString(record));
					bw.write (" ");
					
				}
				bw.newLine();
			}
			
			bw.close();
			fw.close();
			
		}
		catch (IOException e)
		{
			 System.err.println("Problem writing all data to the file");
		}
		
	}


}

