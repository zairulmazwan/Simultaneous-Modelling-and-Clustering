package Bundles_BChopRandom;
import java.util.ArrayList;
import java.util.Collections;


public class RGFtoBundles {
	
	public static ArrayList<ArrayList<Integer>> RGFToGroup(ArrayList<Integer> rgf)
	{
		ArrayList<ArrayList<Integer>> g = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> resg = new ArrayList<ArrayList<Integer>>();
		int i;
		int m = Collections.max(rgf);
		
		for(i=0;i<m;++i)g.add(new ArrayList<Integer>()); 
		for(i=0;i<rgf.size();++i)
		{
			int gv = rgf.get(i);
			g.get(gv-1).add(i);
		}
		for(i=0;i<g.size();++i)
		{
			if (g.get(i).size() > 0) resg.add((ArrayList<Integer>)(g.get(i).clone()));
		}
		return(resg);
	}

}
