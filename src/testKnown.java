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
public class testKnown {
	public static int times = 10;
	public static int x = 5, y =5, z = 1;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double ratio = 0.2;
		long time[] = new long[times];
		long totalTime=0;
		long totalTimeQ=0;
		genQuery g = new genQuery();
		createindexwithoutDup c = new createindexwithoutDup();
		secondlevelindx s = new secondlevelindx();
		System.out.print("testing>..");

	
		//heuristicly choose which is first to test
		int first = 0, i, j;//0 is default meaning triangles index are first to test

	    int row = 100, column = 100, truthNum = 300;
		 
		  for(j = 0; j < 1; j ++)//100 datasets each has 10 queries
		  {		
			  	//column = 100 + 25*j;
			  	//int truthNum = row*3;
				BitMatrix hyper = new BitMatrix(column,row);
				hyper = g.genHyper(row, column, truthNum);		
				ArrayList<Set<Integer>> relateEdgeList = c.relatedEdges(hyper);
				Set<ArrayList<Integer>> triangle = c.getTriIndex(hyper, relateEdgeList);
				Set<ArrayList<Integer>> second = c.get2ndlevelTri(triangle);

				//System.out.print(triangle);
				Set<ArrayList<Integer>> star = c.getStarIndex(hyper);
				ArrayList<ArrayList<Integer>> star2nd = s.get2ndLevelStar(star,  hyper);
			    ArrayList<ArrayList<Integer>> triangle2nd = s.get2ndLevelIndx(triangle);
			    //System.out.println(triangle2nd);
				int triangleNum = triangle.size();
				int starNum = star.size();
			for(i = 0; i < times; i++)
			{

				  Random generator = new Random();

				  int randomNum = 0;
				  if(randomNum == 0)
				  {
					  double temp = generator.nextDouble();
					  BitMatrix testTriangle;
					  //System.out.print(temp);
					  if(temp < ratio)
					  {
						  testTriangle = //g.known2ndQuery(0, 0, hyper, triangle2nd);// 
								  new BitMatrix(4,5);
						  testTriangle.put(0, 0, true);
						  testTriangle.put(1, 0, true);
						  testTriangle.put(2, 1, true);
						  testTriangle.put(1, 1, true);
						  testTriangle.put(0, 2, true);
						  testTriangle.put(2, 2, true);
						  testTriangle.put(3, 3, true);
						  testTriangle.put(1, 3, true);
						  testTriangle.put(2, 4, true);
						  testTriangle.put(3, 4, true); 
					  }
						  
					  else
					  {
						  testTriangle = g.oneQueryTriangle(x,y,z);
					  }
					  

						//////////////////////////////////////////ONELEVEL INDEXING/////////////////////////
								/////////////////////////////////////////////////////////////////////////
					  long startTime = System.currentTimeMillis();
		
					  Set<ArrayList<Integer>> r = c.determineTriangle(testTriangle, triangle,hyper);
					  
					  long endTime   = System.currentTimeMillis();
					   totalTime = endTime - startTime+totalTime;
					  System.out.print(totalTime + ",");
	
	
		    
				//////////////////////////////////////////SECONDLEVEL INDEXING/////////////////////////
						/////////////////////////////////////////////////////////////////////////
						  /*
						   *Two level Index Test process:(all qs have either star or triangle) 
						   *2. If q has second level triangle, use 2nd level index
						   *3. If q has no 2nd level triangle, do the same thing as 1st level index
						   */ 

						  long times2[] = new long[times]; 		  
							  long startTimeQ = System.currentTimeMillis();
							  ArrayList<Set<Integer>> relatedEdgeListQ = c.relatedEdges(testTriangle);
							  Set<ArrayList<Integer>> triangleT = c.getTriIndex(testTriangle, relatedEdgeListQ);
							  Set<ArrayList<Integer>> secondQ = c.get2ndlevelTri(triangleT);

							  if(secondQ.size() > 0)
							  {
								  c.determinSecond(testTriangle, secondQ, second, hyper);
								  
							  }
  
							  else 
							  {
								Set<ArrayList<Integer>> rr = c.determineTriangle(testTriangle, triangle,hyper);
							  }
							  long endTimeQ   = System.currentTimeMillis();
							   totalTimeQ = endTimeQ - startTimeQ+totalTime; 
							  System.out.println(totalTimeQ);

				  }
			}
				
			  System.out.println(totalTime + ",aaa" + totalTimeQ);

		  
	    }

		
	}

}
