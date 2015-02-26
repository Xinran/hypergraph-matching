import java.util.ArrayList;
import java.util.Set;

import cern.colt.bitvector.BitMatrix;

/*
 * This class tests the time complexity of the different size hypergraph and the
 * index constructing time
 */
public class indexTime {

	public static void main(String args[])
	{
		int row = 600, column = 600, truthNum =600, loops, i;
		BitMatrix hyper = new BitMatrix(column, row);
		genQuery g = new genQuery();
		hyper = g.genHyper(row, column, row*2);
		long time;		
		createindexwithoutDup cc = new createindexwithoutDup();  
		
		System.out.print("testingIndexingtime(indexTime.java)>..");
		System.out.println("size|total1s|time(s)");


		time=0;
		for(loops = 0; loops < 10; loops++)
		{
			long startTime = System.currentTimeMillis();
			
			ArrayList<Set<Integer>> relateEdgeList=cc.relatedEdges(hyper);
			Set<ArrayList<Integer>> triangle = cc.getTriIndex(hyper, relateEdgeList);
		
			long endTime   = System.currentTimeMillis();
			time = endTime - startTime+time + time;			
			//System.out.print(triangle.size());
		}
		System.out.print(time/10+" ");
		time=0;
		for(loops = 0; loops < 10; loops++)
		{
			long startTime = System.currentTimeMillis();
			 
			Set<ArrayList<Integer>> triangle = cc.getHyperTriangleIndexNaive(hyper); 
			long endTime   = System.currentTimeMillis();
			time = endTime - startTime+time + time;			
			//System.out.print(triangle.size());
		}
		System.out.println(time/10);
		
	}
}
