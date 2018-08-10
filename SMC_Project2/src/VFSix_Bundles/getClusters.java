package VFSix_Bundles;
import java.util.ArrayList;

public class getClusters {
		
		public static ArrayList<ArrayList<Integer>> getCluster (int noCluster) //create VF cluster
		{
			ArrayList<ArrayList<Integer>> VFCluster = new ArrayList<ArrayList<Integer>> (noCluster);
					
			for (int i=0; i<noCluster; i++)
			{
				Six_Bundles.setBundleArrayList(i, VFCluster);
			}
			
			/*for (int i=0; i<VFCluster.size(); i++)
			 {
				 int size = VFCluster.get(i).size();
				 for (int j=0; j<size; j++)
				 {
					 int object = (VFCluster.get(i).get(j))-1;
					 //System.out.println (object);
					 VFCluster.get(i).set(j, object);
				 }
			 }*/
					
			return VFCluster;
		}
		
		
		
		public static void printCluster (ArrayList<ArrayList<Integer>> Cluster)
		{
			int noCluster = Cluster.size();
			//ArrayList<ArrayList<Integer>> Cluster = getCluster (noCluster);
			
			//System.out.println(Cluster);	
			
			for (int i=0; i<Cluster.size(); i++)
			{		
				System.out.println("Bundle "+(i+1) +" is ");
				System.out.println(Cluster.get(i));
				System.out.println("Cluster size is : "+Cluster.get(i).size());
				System.out.println();	
			}
			
		}
		
		
	}

