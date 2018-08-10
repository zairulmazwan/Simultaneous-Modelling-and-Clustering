/*
 This program is written for modelling the K-means clustering results.
 */

package SMC_Run_Modelling;

import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instances;
import Bundles_BChopRandom.RGFtoBundles;
import Bundles_BChopRandom.RandPart;
import Instances_Format.CreateInstances;
import Instances_Format.Randomise_Data;
import Modelling.CV_RepeatModelling;
import Modelling.No_CVModelling;
import Modelling.runModelling;
import Sampled_Data.Sampling_Patient;
import VFSix_Bundles.ReadBundles;
import VFpreparation.ReadFile;
import VFpreparation.form_ClusteredData;
import VFpreparation.getSource;
import Data_Retrieval.Prepare_PatientData;

public class SMC_Run_Modelling_Main {
	
	
	public static void main (String [] args) throws Exception
	{
		String filepath = "C:\\Matlab Program\\Output\\Kmeans_Bundles\\Bundles Outputs\\";
		
		
		
		for (int i=0; i<25; i++)
		{
			String bundlesName = "KM_Bundles_"+(i+1)+".txt";
			System.out.print("No"+i);
			runSMC (filepath, bundlesName);
			//System.out.println ();
		}
		
	}
	
	public static void runSMC (String filepath, String bundlesName) throws Exception
	{
		String filesource = getSource.getData(); //to read VF data
		String sep = ",";
		
		double [][] rawData = ReadFile.ReadFileArray(filesource, sep); // read raw data from path and create into an array
		String filename = "rawData";
		
		Classifier model = new NaiveBayesUpdateable ();
		String modelName = "NBU";
		
		//double [][] allVFData =  Prepare_PatientData.allPatientVFData(rawData); //prepare all data which consists of 12159 records from 1579 patients. Using all data with the 6NFB gives the same accuracy
		double [][] allVFData =  Sampling_Patient.searchPatient(rawData); //prepare all data which consists of 1579 records from 1579 patients.
		
		ArrayList<ArrayList<Integer>> curClusters = new ArrayList<ArrayList<Integer>> ();
		//String filepath = "C:\\Matlab Program\\Output\\Kmeans_Bundles\\Bundles Outputs\\";
		//String bundlesName = "KM_Bundles_1.txt";
		curClusters = ReadBundles.readBundles(filepath, bundlesName);
		
		//System.out.println (curClusters);
		
		double [][] BundledData = null; // to be used to bundle data from the cleaned data
		BundledData = form_ClusteredData.getData(curClusters, allVFData);
		Instances iData = CreateInstances.createInstances(BundledData, curClusters);
		iData.deleteAttributeAt(0); //remove PID column from the data
		iData.setClassIndex(iData.numAttributes()-1); //assigns the last attribute as the class variable
		iData = Randomise_Data.RandData(iData); //randomise the data (this dataset is ready for modelling)
		//System.out.println (iData.numAttributes());
		//System.out.println (iData);
		
		//double accuracy = runModelling.modellingAccuracy(iData, model, modelName, 10); //10fold
		//double accuracy = CV_RepeatModelling.repeatModelling(10, iData, model, modelName, 2); //repeat CV
		double accuracy = No_CVModelling.modelling(iData, model, modelName); //No CV
		
		System.out.println (" "+accuracy);
	}

}
