import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import cern.colt.bitvector.BitMatrix;


public class testSmall {
	public static int row = 10, column = 10,truthNum = 20;

	public static int x = 4, y =5, z = 1;
	/*
	 * bruteforce() using bruteforce method to see if a sub-hypergraph is in a hypergraph
	 * return value is edge set 
	 */
	public 	Set<Set<Integer>> bruteforceEdge(BitMatrix array, BitMatrix hyper)
	{
		
		Set<Set<Integer>> match = new HashSet<Set<Integer>>();
		ArrayList<Integer> allRows = new ArrayList<Integer>();
		ArrayList<Integer> allColumns = new ArrayList<Integer>();
		int rows = array.rows();
		int columns = array.columns();
		int rowsH = hyper.rows();
		int columnsH = hyper.columns();
		int i, j,flag;
		for(i = 0 ; i < rowsH; i++)
		{
			allRows.add(i);
		}
		for(i = 0 ; i < columnsH; i++)
		{
			allColumns.add(i);
		}
		ArrayList<ArrayList<Integer>> permuRow = determin.Permutation(allRows,rows);
		ArrayList<ArrayList<Integer>> permuColumn = determin.Permutation(allColumns,columns);
		for(ArrayList<Integer> currentRPermu: permuRow)
		{				
			HashMap<Integer, Integer> ehash = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> vhash = new HashMap<Integer, Integer>();
			for(i = 0 ; i < currentRPermu.size(); i ++)
			{
				ehash.put(i, currentRPermu.get(i));
			}
			for(ArrayList<Integer> currentCPermu: permuColumn)
			{
				flag = 1;
				for(i = 0 ; i < currentCPermu.size(); i ++)
				{
					vhash.put(i, currentCPermu.get(i));
				}
				for(i = 0 ; i < array.rows() ; i++)
				{
					for(j = 0; j < array.columns(); j++)
					{
						if(array.get(j, i) == hyper.get(vhash.get(j), ehash.get(i)))
						
						{
							flag = flag * 1;
						}
						else
						{
							flag = 0;
							break;
						}
						
					}
					if(flag == 0)
						continue;
				}
				if(flag ==1)//each combination of edges gives one result,which is enough
				{
					Set<Integer> tmp = new HashSet<Integer>();
					//System.out.print(ehash);//all the matches, some of them are the same edges with different permutation, so below, delete the repeat
					for(Integer key: ehash.keySet())
					{
						tmp.add(ehash.get(key));
					}
					match.add(tmp);
					break;
				}
			}
		}
		return match;
	}
	/*
	 * bruteforce() using bruteforce method to see if a sub-hypergraph is in a hypergraph
	 * return value is vertex set 
	 */
	public 	Set<Set<Integer>> bruteforceVer(BitMatrix array, BitMatrix hyper)
	{
		
		Set<Set<Integer>> match = new HashSet<Set<Integer>>();
		ArrayList<Integer> allRows = new ArrayList<Integer>();
		ArrayList<Integer> allColumns = new ArrayList<Integer>();
		int rows = array.rows();
		int columns = array.columns();
		int rowsH = hyper.rows();
		int columnsH = hyper.columns();
		int i, j,flag;
		for(i = 0 ; i < rowsH; i++)
		{
			allRows.add(i);
		}
		for(i = 0 ; i < columnsH; i++)
		{
			allColumns.add(i);
		}
		ArrayList<ArrayList<Integer>> permuRow = determin.Permutation(allRows,rows);
		ArrayList<ArrayList<Integer>> permuColumn = determin.Permutation(allColumns,columns);
		for(ArrayList<Integer> currentRPermu: permuRow)
		{				
			HashMap<Integer, Integer> ehash = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> vhash = new HashMap<Integer, Integer>();
			for(i = 0 ; i < currentRPermu.size(); i ++)
			{
				ehash.put(i, currentRPermu.get(i));
			}
			for(ArrayList<Integer> currentCPermu: permuColumn)
			{
				flag = 1;
				for(i = 0 ; i < currentCPermu.size(); i ++)
				{
					vhash.put(i, currentCPermu.get(i));
				}
				for(i = 0 ; i < array.rows() ; i++)
				{
					for(j = 0; j < array.columns(); j++)
					{
						if(array.get(j, i) == hyper.get(vhash.get(j), ehash.get(i)))
						
						{
							flag = flag * 1;
						}
						else
						{
							flag = 0;
							break;
						}
						
					}
					if(flag == 0)
						continue;
				}
				if(flag ==1)//each combination of edges gives one result,which is enough
				{
					Set<Integer> tmp = new HashSet<Integer>();
					//System.out.print(ehash);//all the matches, some of them are the same edges with different permutation, so below, delete the repeat
					for(Integer key: vhash.keySet())
					{
						tmp.add(vhash.get(key));
					}
					match.add(tmp);
					break;
				}
			}
		}
		return match;
	}	
	 public static void main(String args[])
	 { 
		BitMatrix hyper = new BitMatrix(column, row);
		long totaltime=0, totaltime1=0;
		genQuery g = new genQuery();
		createindexwithoutDup c = new createindexwithoutDup();
		testSmall thisTest = new testSmall(); 
		
		System.out.print("testing>..\n");
		hyper = g.genHyper(row, column, truthNum);		  
		ArrayList<Set<Integer>> relateEdgeList = c.relatedEdges(hyper);
		Set<ArrayList<Integer>> triangle = c.getTriIndex(hyper, relateEdgeList);
		Set<ArrayList<Integer>> star = c.getStarIndex(hyper);
	 
		System.out.println(star+",***"+triangle);	
		int triangleNum = triangle.size();
		int starNum = star.size();
		//heuristicly choose which is first to test
		int first = 0;//0 is default meaning triangles index are first to test
		int i, j;


		BitMatrix testcase = g.oneQueryTriangle(x,y,z);
		first = 0;//using star
		if(first == 0)//test use triangle to match
	    {
			
		  long startTime = System.currentTimeMillis();
		  Set<ArrayList<Integer>> r = c.determineTriangleNew(testcase, triangle,hyper);
		  long endTime   = System.currentTimeMillis();
		  totaltime1 = endTime - startTime + totaltime; 	
		  System.out.println(r);
	  
		
		}
		else	
		{
			  long startTime1 = System.currentTimeMillis();
			  Set<Integer> rr = c.determineStar(testcase, star,hyper);
			  long endTime1   = System.currentTimeMillis();
			  totaltime1 = endTime1 - startTime1 + totaltime1; 	
			  System.out.println(rr);
			  	
	    }
		/*
		if(first == 0)
		{
			Set<Set<Integer>> bruteResult = thisTest.bruteforceEdge(testcase, hyper);
			System.out.println(bruteResult);
		}
		else if(first == 1)
		{

			Set<Set<Integer>> bruteResult = thisTest.bruteforceVer(testcase, hyper);
			System.out.println(bruteResult);
		}*/
	 }
}
