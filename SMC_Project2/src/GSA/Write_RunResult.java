package GSA;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class Write_RunResult {
	
		
	public static void writeRunExperiment (double [][] finalFit, double qa_para, int k)
	{
		//String path = "C:\\Zairul Mazwan\\Java Output\\CS2004 Output\\GSA Output\\GSA_1_1\\GSA_RunResult_.txt"
		//String path = "D:\\Java Output\\CS2004\\GSA v1.1\\Run Results\\GSA_RunResult_"+qa_para+".txt";
		String path = "D:\\Java Output\\MPJ Experiment - 2nd Paper\\GSA\\VF\\GSA_VF_RunResult_"+qa_para+"_"+k+".txt";
		
		try
		{
			FileWriter fw = new FileWriter (path);
			BufferedWriter bw = new BufferedWriter (fw);
			
			
			bw.write("Run");
			bw.write(" ");
			bw.write("QA");
			bw.write(" ");
			bw.write("QV");
			bw.write(" ");
			bw.write("Temp");
			bw.write(" ");
			bw.write("Fitness");
			bw.write(" ");
			bw.write("Fit_Conv");
			bw.write(" ");
			bw.write("Bundles");
			bw.write(" ");
			bw.write("Running_Time");
			bw.write(" ");
			bw.write("Conv_Point");
			bw.write(" ");
			bw.write("Best_Result");
			bw.write(" ");
			bw.write("Bundles");
			bw.write(" ");
			bw.write("WK");
			bw.newLine();
			
			
			for (int i=0; i<finalFit.length; i++)
			{
				double iter = finalFit[i][0];
				double qa = finalFit[i][1];
				double qv = finalFit[i][2];
				double temp = finalFit[i][3];
				double fitness = finalFit[i][4];
				double fit_conv = finalFit[i][5];
				double cur_bundles = finalFit[i][6];
				double time = finalFit[i][7];
				double conv_point = finalFit[i][8];
				double bestResult = finalFit[i][9];
				double best_bundles = finalFit[i][10];
				double wk = finalFit[i][11];
				
				bw.write(Double.toString(iter));
				bw.write(" ");
				bw.write(Double.toString(qa));
				bw.write(" ");
				bw.write(Double.toString(qv));
				bw.write(" ");
				bw.write(Double.toString(temp));
				bw.write(" ");
				bw.write(Double.toString(fitness));
				bw.write(" ");
				bw.write(Double.toString(fit_conv));
				bw.write(" ");
				bw.write(Double.toString(cur_bundles));
				bw.write(" ");
				bw.write(Double.toString(time));
				bw.write(" ");
				bw.write(Double.toString(conv_point));
				bw.write(" ");
				bw.write(Double.toString(bestResult));
				bw.write(" ");
				bw.write(Double.toString(best_bundles));
				bw.write(" ");
				bw.write(Double.toString(wk));
				bw.newLine();
				
			}
			bw.close();
			fw.close();
		}
		catch (Exception e)
		{
			   System.err.println("Problem writing GSA TSP result to the file");
		}
	}

}
