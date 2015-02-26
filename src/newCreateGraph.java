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
 * THIS CLASS IS MATCHING THE NEW METHOD IN CLASS createindexwithoutDup.java
 * This class create the hypergraph of the paper-author relationship: hyper[][]
 * paper is edge(rows) , author is vertex(columns)
 */
public class newCreateGraph {
	
	public static int loops = 1;
	public static double ratio = 0.9;
	public static int x = 3, y =3, z = 1;
	
	public static void main(String[] args) {
		createGraph creat = new createGraph();
		String[] result2;
		BitMatrix hyper = new BitMatrix(314,360);
		Iterator itr;
		int size;
		int a, b, i, j;
		long totaltime=0, totaltime1=0;
		genQuery g = new genQuery();
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

		  createindexwithoutDup c = new createindexwithoutDup();
		  ArrayList<Set<Integer>> relateEdgeList = c.relatedEdges(hyper);
		  System.out.println(relateEdgeList.size());
		  Set<ArrayList<Integer>> triangle = c.getTriIndex(hyper, relateEdgeList);//8ge
 		  Set<ArrayList<Integer>> star = c.getStarIndex(hyper);//54ge


		  /*
		  int[][] testTriangle = {
	    	      { 1,1,0,0,0},
	    	      { 0,1,1,1,0},
	    	      { 0,0,1,0,0},
	    	      { 1,0,0,1,1},
	    	      { 0,0,0,0,1}
		  };
		  
		  BitMatrix testtriangle = new BitMatrix(4,3);
		  testtriangle.put(0, 0, true);
		  testtriangle.put(1, 0, true);
		  testtriangle.put(1, 1, true);
		  testtriangle.put(2, 1, true);
		  testtriangle.put(0, 2, true); 
		  testtriangle.put(2, 2, true);
		  testtriangle.put(3, 2, true);
		  //testtriangle.put(3, 0, true);
		  //testtriangle.put(4, 3, true);	*/
 		  for (i = 0; i < 10; i++)
 		  {
	 		 BitMatrix testtriangle = g.oneQueryTriangle(x,y,z);
	
			  ArrayList<Set<Integer>> relateEdgeListQ = c.relatedEdges(testtriangle);
			  Set<ArrayList<Integer>> triangleQ = c.getTriIndex(testtriangle, relateEdgeListQ);
			  System.out.println(triangleQ);
			  //ArrayList<ArrayList<Integer>> t = new ArrayList<ArrayList<Integer>>(triangleQ);
			  //System.out.print(c.getLayerTriangle(t.get(0), testtriangle));//1
	
			  //System.out.print(c.getEofLayerTriangle(t.get(0), testtriangle, 1));//[3]
			  //System.out.print(c.getVofLayerTriangle(t.get(0), testtriangle, 1));//[]
			  long startTime = System.currentTimeMillis();
			  //ArrayList<Integer> r = 
			  c.determineOneEdge(testtriangle,hyper);
			  
			  long endTime   = System.currentTimeMillis();
			  totaltime = endTime - startTime + totaltime; 
			  long startTime1 = System.currentTimeMillis();
			  Set<ArrayList<Integer>> rr = c.determineTriangle(testtriangle, triangle,hyper);
			  long endTime1   = System.currentTimeMillis();
			  totaltime1 = endTime1 - startTime1 + totaltime1; 			
 		  }
 		  System.out.println(totaltime+","+totaltime1);
	}
}
