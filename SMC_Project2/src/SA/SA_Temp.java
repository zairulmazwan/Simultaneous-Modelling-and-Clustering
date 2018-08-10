package SA;
import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.core.Instances;
import Bundles.BundledData;
import Bundles.Bundles_Arrangement;
//import Bundles_Arrangement.Bundles_Arrangement;
//import Modelling.newSolution_Accuracy;
import Bundles.Bundles_Creation;
import Instances_Format.CreateInstances;
import Instances_Format.Randomise_Data;
import Modelling.runModelling;
import Read_Data.Read_Data;
import Sampled_Data.Sampling_Patient;
import VFpreparation.ReadFile;
import VFpreparation.form_ClusteredData;
import VFpreparation.getSource;


public class SA_Temp {
	
		public static double temp (Classifier model, String modelname, int iter2, long [] time) throws Exception
		{
			double temp = 0;
			//double [][] rawData = Read_Data.getAllData();
			String filesource = getSource.getData(); //to read VF data
			String sep = ",";
			String filename = "rawData";
			double [][] rawData = ReadFile.ReadFileArray(filesource, sep); // read raw data from path and create into an array
			double [][] allVFData =  Sampling_Patient.searchPatient(rawData); //prepare all data which consists of 1579 records from 1579 patients.
			
			//Write_Data.writeData(filename, rawData);
			int lenghtVar = rawData[0].length;
			System.out.println("No. of Var for temp : "+lenghtVar);
			int fold = 10;
			
			ArrayList<ArrayList<Integer>> currentCluster = new ArrayList<ArrayList<Integer>> ();
			ArrayList<ArrayList<Integer>> newCluster = new ArrayList<ArrayList<Integer>> ();
			double currentAccuracy = 0;
			double newAccuracy = 0;
			double difFit = 0;
			
			currentCluster =  Bundles_Creation.initialCluster(currentCluster, lenghtVar);
			//double [][] sumData = BundledData.bundlesData(rawData, currentCluster); //prepare data based on bundles
			//double [][] avgData = BundledData.averagedData(currentCluster, sumData); //average the bundled data
			double [][] BundledData = form_ClusteredData.getData(currentCluster, allVFData);
			
			Instances iData = CreateInstances.createInstances(BundledData, currentCluster);
			iData.setClassIndex(iData.numAttributes()-1); //assigns the last attribute as the class variable
			iData = Randomise_Data.RandData(iData); //randomise the data (this dataset is ready for modelling)
			
			currentAccuracy = runModelling.modellingAccuracy(iData, model, modelname, fold);
			
			long timeStart=0;
			
			for (int i=0; i<iter2; i++)
			{
				if (i==0)
				{
					timeStart = System.currentTimeMillis();
					time [0] = timeStart;
				}
				
				System.out.println("Calculating temperature...");
				newCluster = Bundles_Creation.cloneArrayList(currentCluster);
				newCluster = Bundles_Arrangement.newArrangement(newCluster);
				BundledData = form_ClusteredData.getData(newCluster, allVFData);
				//sumData = BundledData.bundlesData(rawData, currentCluster); //prepare data based on bundles
				//avgData = BundledData.averagedData(currentCluster, sumData); //average the bundled data
				
				iData = CreateInstances.createInstances(BundledData, newCluster);
				iData.setClassIndex(iData.numAttributes()-1); //assigns the last attribute as the class variable
				iData = Randomise_Data.RandData(iData); //randomise the data (this dataset is ready for modelling)
				
				newAccuracy = runModelling.modellingAccuracy(iData, model, modelname, fold);
				
				//System.out.println("Current accuracy is : "+currentAccuracy);
				//System.out.println("New accuracy is : "+newAccuracy);
				
				difFit = Math.abs(newAccuracy - currentAccuracy);
				
				temp += difFit;
				
				currentAccuracy = newAccuracy;
				currentCluster.clear();
				currentCluster = Bundles_Creation.cloneArrayList(newCluster);
			}
			temp/=iter2;
			System.out.println("Initial temp is : "+temp);
			return temp;
		}
		
		public static double lamda1 (double initialTemp, int iter)
		{
			double finTemp = 0.001;
			//System.out.println("Initial temp is : "+initialTemp);
			//System.out.println("Iter is : "+iter);
			
			double lamda = finTemp/initialTemp;
			lamda = Math.exp((1.0/iter)*Math.log(lamda));
			
			return lamda;
		}

		
		public static double lamda (double initialTemp, int iter)
		{
			double finTemp = 0.001;
			//System.out.println("Initial temp is : "+initialTemp);
			//System.out.println("Iter is : "+iter);
			
			double x = Math.log(finTemp)-Math.log(initialTemp);
			x/=iter;
			double lamda = Math.exp(x);
			
			return lamda;
		}
	}
