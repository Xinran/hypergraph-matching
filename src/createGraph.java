 import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.ArrayList;
import java.util.regex.Pattern;

import cern.colt.bitvector.BitMatrix;


import tutorial.MapStringList;

/*
 * ///OBSOLETE
 *  The latest version see file newCreateGraph.java
 */
/*
 * This class create the hypergraph of the paper-author relationship: hyper[][]
 * paper is edge(rows) , author is vertex(columns)
 */
public class createGraph {
	
	public static int loops = 1;
	public static double ratio = 0.9;

	public static void main(String[] args) {
		
		createGraph creat = new createGraph();
		String[] result2;
		BitMatrix hyper = new BitMatrix(314,360);
		Iterator itr;
		int size;
		int a, b, i, j;
		
		//hashtable keys are papers , key's values are this paper's authors
		MapStringList coauthor = new MapStringList();
		  //read the second file for author - paper relationship
		  try{
			  // Open the file that is the first 
			  // command line parameter
			  FileInputStream fstream2 = new FileInputStream("coauthor.txt");
			  // Get the object of DataInputStream
			  DataInputStream in2 = new DataInputStream(fstream2);
			  BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
			  String strLine2;
			  //Read File Line By Line
			  while ((strLine2 = br2.readLine()) != null)   
			  {
				  // Print the content on the console
				  //System.out.println (strLine2);
				  
				  // Create a pattern to match breaks
			      Pattern p = Pattern.compile("[\\s]+");
			      // Split input with the pattern
			      result2 = p.split(strLine2);
			      int temp = Integer.parseInt(result2[0]);
				  coauthor.add(result2[1], result2[0]);

			  }
			  //add hashtable : paper ---authors
			  //Close the input stream
			  in2.close();
			  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
			  }
		  itr = (coauthor.getKeys()).iterator(); 
		  while(itr.hasNext()) {
				//get each key: element
			    Object element = itr.next(); 
			    size = coauthor.getValues(element.toString()).size();
			    //array has the coauthors of current author
			    Object[] array = (coauthor.getValues(element.toString())).toArray();
			    
			    // create the TWO DIMENSIONAL ARRAY of hypergraph
			    for(Object ip : array)
			    {
			    	b = Integer.parseInt(ip.toString());//author:column:vertex
			    	a = Integer.parseInt(element.toString());//paper:row:edge
			    	hyper.put(b-1, a-315, true);
			    	//System.out.print(a);
			    	//System.out.println(b);
			    }
		  }
		  
		  
		  newDetermin c = new newDetermin();
		  secondlevelindx s = new secondlevelindx();
		  Set<ArrayList<Integer>> triangle = c.getHyperTriangleIndex(hyper);
		  Set<ArrayList<Integer>> star = c.getHyperStarIndex(hyper);

		  //System.out.println(triangle.size());//8
		  //System.out.println(star.size());54
		  ArrayList<ArrayList<Integer>> star2nd = s.get2ndLevelStar(star,  hyper);
		  ArrayList<ArrayList<Integer>> triangle2nd = s.get2ndLevelIndx(triangle);
		  long times[] = new long[100];
		  long times2[] = new long[100];
		  genQuery g = new genQuery();
		  Random generator = new Random();
		  //System.out.println(triangle2nd.size());2
		  //System.out.println(star2nd.size());6 
		  /*
		  int[][] testTriangle2level = {
	    	      { 1,1,0,1},
	    	      { 0,1,1,0},
	    	      { 1,0,1,0},
	    	      { 1,0,1,0},
	    	      { 0,1,1,0}
		  };
		  */

			try{
				  FileWriter fstream1 = new FileWriter("out1.txt");
				  BufferedWriter out1 = new BufferedWriter(fstream1);

	
				  FileWriter fstream2 = new FileWriter("out2.txt");
				  BufferedWriter out2 = new BufferedWriter(fstream2); 
				  
				  FileWriter fstream3 = new FileWriter("out3.txt");
				  BufferedWriter out3 = new BufferedWriter(fstream3);

	
				  FileWriter fstream4 = new FileWriter("out4.txt");
				  BufferedWriter out4 = new BufferedWriter(fstream4); 

			for(i = 0 ; i < loops; i++)
			{
				BitMatrix testStar;
				 double temp = generator.nextDouble(); 
				  if(temp < ratio)
				  {	  testStar = g.known2ndQuery(0, 0, hyper, triangle2nd);//g.knownTQuery(randomNumR, randomNumC, hyper, triangle);
						  /*new BitMatrix(5,4);
				  testStar.put(0, 0, true);
				  testStar.put(1, 0, true);
				  testStar.put(2, 1, true);
				  testStar.put(1, 1, true);
				  testStar.put(0, 2, true);
				  testStar.put(2, 2, true);
				  testStar.put(3, 2, true);
				  testStar.put(0, 3, true);
				  testStar.put(2, 3, true);
				  testStar.put(4, 3, true); */
				  }
				  else
				  {
					  int randomNumR = generator.nextInt(2)+4;

					  int randomNumC = generator.nextInt(2)+4;
					  do
					  {
						  testStar = g.knownTQuery(randomNumR, randomNumC, hyper, triangle);
					  }
					  while(c.getHyperStarIndex(testStar).size()==0);
				  }
 
				  long startTime = System.currentTimeMillis();
		  /*
		   *One level Index Test process:(all qs have either star or triangle)
		   *1. based on q's triangle
		   *2. If q has no triangle, based on q's star
		   *3. If q has triangle but match failed, then no match
		   *4. If q has star but match failed, then no match
		   *here, since triangle size < star size , by default check triangles first. Same for secondlevel
		    */
				  
				  Set<Set<Object>> r = c.determineTriangle(testStar, triangle,hyper);
				  out3.write(r.size()+"\r\n");
				  if (r.size() == 0)
				  {
					  Set<Integer> rr = c.determineStar(testStar, star, hyper);
					  out3.write(rr.size()+"\r\n");
				  }
				  long endTime   = System.currentTimeMillis();
				  long totalTime = endTime - startTime;
				  times[i] = totalTime;
				  String sTime = String.valueOf(times[i]);
				  out1.write(sTime+"\r\n");
				  out1.flush();
				  out3.flush();
				  
			   
			  /*
			   *Two level Index Test process:(all qs have either star or triangle) 
			   *2. If q has second level triangle, use 2nd level index
			   *3. If q has no 2nd level triangle, do the same thing as 1st level index
			  */  

				  		/*	  
				  long startTime2 = System.currentTimeMillis();
				  Set<ArrayList<Integer>> triangleT = c.getHyperTriangleIndex(testStar);
				  ArrayList<ArrayList<Integer>> fouredgeSet = s.get2ndLevelIndx(triangleT);

				  Set<ArrayList<Integer>> starT = c.getHyperStarIndex(testStar);
				  ArrayList<ArrayList<Integer>> star2ndT = s.get2ndLevelStar(starT, hyper);
 
				  if(fouredgeSet.size() > 0)
				  {
					  Set<Integer> matchT = s.determinSecond(testStar,fouredgeSet,triangle2nd, hyper);
					  out4.write(matchT.size()+"\r\n");
				  }
				  else if(star2ndT.size() > 0)
				  {
					  Set<Integer> matchS = s.determinSecond(testStar,star2ndT,star2nd, hyper);
					  out4.write(matchS.size()+"\r\n");
				  }
				  else
				  {
					  Set<Set<Object>> r1 = c.determineTriangle(testStar, triangle, hyper);
					  out4.write(r1.size()+"\r\n");

					  if (r.size() == 0)
					  {
						  Set<Integer> rr1 = c.determineStar(testStar, star, hyper);
						  out4.write(rr1.size()+"\r\n");

					  }
				  }
				  long endTime2   = System.currentTimeMillis();
				  long totalTime2 = endTime2 - startTime2;
				  times2[i] = totalTime2;
				  String sTime2 = String.valueOf(times2[i]);
				  out2.write(sTime2+"\r\n"); 
				  out2.flush();
				  out4.flush();*/
			}
			out1.close();
			out2.close();
		  }catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
	 }

	}
}
