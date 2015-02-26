import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import cern.colt.bitvector.BitMatrix;


/*
 * OBSOLETE
 * Use genHyper to do test which is randomly give hyper[i][j] 0 or 1
 */
public class testKnownCorrectness {
	public static int times = 2;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int Second = 0;
		long time[] = new long[times];
		genQuery g = new genQuery();
		newDeter c = new newDeter();
		secondlevelindx s = new secondlevelindx();
		System.out.print("testing>..");

	
		//heuristicly choose which is first to test
		int first = 0, i, j;//0 is default meaning triangles index are first to test

	    int row = 100, column = 100;
		 
		  for(j = 0; j < 1; j ++)
		  {		

		  // Create file 
		try{
		  FileWriter fstream1 = new FileWriter("correct025.txt");
		  BufferedWriter out1 = new BufferedWriter(fstream1);;


			for(i = 0; i < times; i++)
			{
			  	column = 100 + 25*j;
			  	int truthNum = row*3;
				BitMatrix hyper = new BitMatrix(column,row);
				hyper = g.genHyper(row, column, truthNum);		
				Set<ArrayList<Integer>> triangle = c.getHyperTriangleIndex(hyper);
				//System.out.print(triangle);
				Set<ArrayList<Integer>> star = c.getHyperStarIndex(hyper);
				ArrayList<ArrayList<Integer>> star2nd = s.get2ndLevelStar(star,  hyper);
			    ArrayList<ArrayList<Integer>> triangle2nd = s.get2ndLevelIndx(triangle);
			    System.out.println(triangle2nd);
				int triangleNum = triangle.size();
				int starNum = star.size();
			    if(triangle.size() > star.size())
			    	first = 1;
			    else
			    	first = 0;
				  Random generator = new Random();
				  int randomNumR = generator.nextInt(2)+4;

				  int randomNumC = generator.nextInt(2)+4;
 
					  BitMatrix testTriangle = g.knownTQuery(randomNumR, randomNumC, hyper, triangle);
						/*	  new BitMatrix(4,5);
					  testTriangle.put(0, 0, true);
					  testTriangle.put(1, 0, true);
					  testTriangle.put(2, 1, true);
					  testTriangle.put(1, 1, true);
					  testTriangle.put(0, 2, true);
					  testTriangle.put(2, 2, true);
					  testTriangle.put(3, 3, true);
					  testTriangle.put(2, 3, true);

					  testTriangle.put(1, 4, true);
					  testTriangle.put(3, 4, true); */
						if(first == 0)
					    { 
								  Set<ArrayList<Integer>> triangleT = c.getHyperTriangleIndex(testTriangle);
								  if(triangleT.size() > 0)
								  {
									  double Num = 0;
									  Set<Set<Object>> r = c.determineTriangle(testTriangle, triangle,hyper);
									  Set<Set<Object>> rn = c.determineTriangle(testTriangle, triangle,hyper);
									  for(Set<Object> current: rn)
									  {
										  if(r.contains(current))
											  Num++;
											  
									  }
									  Num = Num/r.size();
									  out1.write(Num+"\r\n");
									  r.clear();
									  rn.clear();
								  }
								  else  
								  {
									  double Num = 0;
									  Set<Set<Object>> rr = c.determineStar(testTriangle, star, hyper);
									  Set<Set<Object>> rrn= c.determineStarNotPermu(testTriangle, star,hyper);
									  for(Set<Object> current: rrn)
									  {
										  if(rr.contains(current))
											  Num++;
											  
									  }
									  Num = Num/rr.size();
									  out1.write(Num+"\r\n");
									  rr.clear();
									  rrn.clear();
								  }
				 
						
						}
						else	
					    {
				 
								  Set<ArrayList<Integer>> starT = c.getHyperStarIndex(testTriangle);
								  if(starT.size() > 0)
								  {
									  double Num = 0;
									  Set<Set<Object>> rr = c.determineStar(testTriangle, star, hyper);
									  Set<Set<Object>> rrn= c.determineStarNotPermu(testTriangle, star,hyper);
									  for(Set<Object> current: rrn)
									  {
										  if(rr.contains(current))
											  Num++;
											  
									  }
									  Num = Num/rr.size();
									  out1.write(Num+"\r\n");
									  rr.clear();
									  rrn.clear();
								  }
								  else  
								  {
									  double Num = 0;
									  Set<Set<Object>> r = c.determineTriangle(testTriangle, triangle,hyper);
									  Set<Set<Object>> rn = c.determineTriangle(testTriangle, triangle,hyper);
									  for(Set<Object> current: rn)
									  {
										  if(r.contains(current))
											  Num++;
											  
									  }
									  Num = Num/r.size();
									  out1.write(Num+"\r\n");
									  r.clear();
									  rn.clear();
								  } 
				
				
					    }
						   
				//////////////////////////////////////////SECONDLEVEL INDEXING/////////////////////////
						/////////////////////////////////////////////////////////////////////////
						  /*
						   *Two level Index Test process:(all qs have either star or triangle) 
						   *2. If q has second level triangle, use 2nd level index
						   *3. If q has no 2nd level triangle, do the same thing as 1st level index
						   */ 
 
							  Set<ArrayList<Integer>> triangleT = c.getHyperTriangleIndex(testTriangle);
							  ArrayList<ArrayList<Integer>> fouredgeSet = s.get2ndLevelIndx(triangleT);
				
							  Set<ArrayList<Integer>> starT = c.getHyperStarIndex(testTriangle);
							  ArrayList<ArrayList<Integer>> star2ndT = s.get2ndLevelStar(starT, hyper);
							  if(fouredgeSet.size() > 0)
							  {
								  Second ++;
								  System.out.println("second is:" +Second);
								  //Set<ArrayList<Integer>> matchT = s.determinSecond(testTriangle,fouredgeSet,triangle2nd, hyper);
							  }
							  else if(star2ndT.size() > 0)
							  {
								  Second ++;
								  System.out.println("second is:" +Second);
								  //Set<ArrayList<Integer>> matchS = s.determinSecond(testTriangle,star2ndT,star2nd, hyper);
							  }
							  else if(first==0)
							  {
								  if(triangleT.size() > 0)
								  {
									  double Num = 0;
									  Set<Set<Object>> r = c.determineTriangle(testTriangle, triangle,hyper);
									  Set<Set<Object>> rn = c.determineTriangle(testTriangle, triangle,hyper);
									  for(Set<Object> current: rn)
									  {
										  if(r.contains(current))
											  Num++;
											  
									  }
									  Num = Num/r.size();
									  out1.write(Num+"\r\n");
									  r.clear();
									  rn.clear();
								  }
								  else  
								  {
									  double Num = 0;
									  Set<Set<Object>> rr = c.determineStar(testTriangle, star, hyper);
									  Set<Set<Object>> rrn= c.determineStarNotPermu(testTriangle, star,hyper);
									  for(Set<Object> current: rrn)
									  {
										  if(rr.contains(current))
											  Num++;
											  
									  }
									  Num = Num/rr.size();
									  out1.write(Num+"\r\n");
									  rr.clear();
									  rrn.clear();
								  }
							  }
							  else 
							  {
								  if(starT.size() > 0)
								  {
									  double Num = 0;
									  Set<Set<Object>> rr = c.determineStar(testTriangle, star, hyper);
									  Set<Set<Object>> rrn= c.determineStarNotPermu(testTriangle, star,hyper);
									  for(Set<Object> current: rrn)
									  {
										  if(rr.contains(current))
											  Num++;
											  
									  }
									  Num = Num/rr.size();
									  out1.write(Num+"\r\n");
									  rr.clear();
									  rrn.clear();
								  }
								  else  
								  {
									  double Num = 0;
									  Set<Set<Object>> r = c.determineTriangle(testTriangle, triangle,hyper);
									  Set<Set<Object>> rn = c.determineTriangle(testTriangle, triangle,hyper);
									  for(Set<Object> current: rn)
									  {
										  if(r.contains(current))
											  Num++;
											  
									  }
									  Num = Num/r.size();
									  out1.write(Num+"\r\n");
									  r.clear();
									  rn.clear();
								  }
							  } 
				  }

				
			out1.close(); 
		}catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
	    }

		}
	}

}
