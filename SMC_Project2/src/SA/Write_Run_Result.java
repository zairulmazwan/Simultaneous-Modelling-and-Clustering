package SA;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Write_Run_Result {
	
	public static void writeRunExperiment (double [][] finalFit, int k)
	{

		//String path = "C:\\Zairul Mazwan\\Java Output\\CS2004 Output\\GSA Output\\GSA_1_1\\GSA_RunResult_.txt"
		//String path = "D:\\Java Output\\CS2004\\GSA v1.1\\Run Results\\GSA_RunResult_"+qa_para+".txt";
		String path = "D:\\Java Output\\MPJ Experiment - 2nd Paper\\SA\\VF\\VF_SA_Run_Result_"+k+".txt";
		
		try
		{
			FileWriter fw = new FileWriter (path);
			BufferedWriter bw = new BufferedWriter (fw);
			
			
			bw.write("Run");
			bw.write(" ");
			bw.write("FinFit");
			bw.write(" ");
			bw.write("FinBundles");
			bw.write(" ");
			bw.write("Fit_Conv_Point");
			bw.write(" ");
			bw.write("IniTemp");
			bw.write(" ");
			bw.write("BestFit");
			bw.write(" ");
			bw.write("BestBundles");
			bw.write(" ");
			bw.write("ConvPoint");
			bw.write(" ");
			bw.write("Time");
			bw.write(" ");
			bw.write("WK");
			bw.newLine();
			
			
			
			for (int i=0; i<finalFit.length; i++)
			{
				double run = finalFit[i][0];
				double finFit = finalFit[i][1];
				double finBund = finalFit[i][2];
				double fit_conv = finalFit[i][3];
				double iniTemp = finalFit[i][4];
				double bestFit = finalFit[i][5];
				double bestBund = finalFit[i][6];
				double conv = finalFit[i][7];
				double time = finalFit[i][8];
				double wk = finalFit[i][9];
				
				bw.write(Double.toString(run));
				bw.write(" ");
				bw.write(Double.toString(finFit));
				bw.write(" ");
				bw.write(Double.toString(finBund));
				bw.write(" ");
				bw.write(Double.toString(fit_conv));
				bw.write(" ");
				bw.write(Double.toString(iniTemp));
				bw.write(" ");
				bw.write(Double.toString(bestFit));
				bw.write(" ");
				bw.write(Double.toString(bestBund));
				bw.write(" ");
				bw.write(Double.toString(conv));
				bw.write(" ");
				bw.write(Double.toString(time));
				bw.write(" ");
				bw.write(Double.toString(wk));
				bw.newLine();
				
			}
			bw.close();
			fw.close();
		}
		catch (Exception e)
		{
			   System.err.println("Problem writing SA run result to the file");
		}
	}

}
