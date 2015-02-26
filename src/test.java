import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import cern.colt.bitvector.BitMatrix;


/*
 * Use genHyper to do test which is randomly give hyper[i][j] 0 or 1
 */
public class test {
	public static int times = 10;
	public static int x = 3, y =3, z = 1;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub 
		double ratio = 0.99;
		long time=0;
		genQuery g = new genQuery();
		newDetermin c = new newDetermin();
		createindexwithoutDup cc = new createindexwithoutDup();
		secondlevelindx s = new secondlevelindx();
		System.out.print("testing>..");

	
		//heuristicly choose which is first to test
		int first = 0, i, j;//0 is default meaning triangles index are first to test

	    int row = 100, column = 100;
	    
		  for(j = 0; j < 1; j++)//j datasets each has i queries
		  {		
				BitMatrix hyper = new BitMatrix(column,row);
				hyper = g.genHyper(row, column, row*3);
				time=0;
				for(i = 0; i < times; i++)
				{				
				  BitMatrix testTriangle;
				  testTriangle = g.oneQueryTriangle(x,y,z);
				 long startTime = System.currentTimeMillis();
				  
				  ArrayList<Set<Integer>> relateEdgeList=cc.relatedEdges(hyper);

				  Set<ArrayList<Integer>> triangle = cc.getTriIndex(hyper, relateEdgeList); 
				  //long startTime = System.currentTimeMillis();
				  //Set<Set<Integer>> r = cc.determineTriangle(testTriangle, triangle,hyper);
				  
				  long endTime   = System.currentTimeMillis();
				  long totalTime = endTime - startTime;
				  time = totalTime+time;

			  }
				System.out.println(time);
		  }

		
	}

}
