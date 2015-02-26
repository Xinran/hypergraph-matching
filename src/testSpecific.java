import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import cern.colt.bitvector.BitMatrix;


/*
 * Use genHyperWithTrianglePosition to do test which sets triangles' positions
 * 100*100 array, set triangles'number 10, 20, 30 each for 1000 times and see if all the triangles are in the Indexing triangles 
 * 100*100 array, set sharing vertices number 0,1,2,3 for 1000 times each and set the indexing element 5, 10, 15 if all the triangles are in the second Indexing
 */
public class testSpecific {
	public static int row = 100, column = 100, triangleNum = 2, loop = 1, sharingV = 0;
	/**
	 * @param args
	 */
	public void testTriangle()
	{
		int i, flag = 0;
		BitMatrix hyper = new BitMatrix(column, row);
		//Set<ArrayList<Integer>> knownTriangles = new HashSet<ArrayList<Integer>>();
		genQuery g = new genQuery();
		determin c = new determin();
		
		System.out.print("testing>..");
		for(i = 0; i < loop; i++)
		{
			HashMap<BitMatrix, Set<ArrayList<Integer>>> returnedValue = g.genHyperWithTrianglePosition(row, column, triangleNum);
			ArrayList<BitMatrix> hyperList= new ArrayList<BitMatrix>(returnedValue.keySet());
			hyper = hyperList.get(0);
			Set<ArrayList<Integer>> triangle = c.getHyperTriangleIndex(hyper);
			Set<Set<Integer>> triangleConvert = new HashSet<Set<Integer>>();
			System.out.print("we get:");
			System.out.println(triangle);
			ArrayList<Set<ArrayList<Integer>>> knownTriangleList = new ArrayList<Set<ArrayList<Integer>>>(returnedValue.values());
			Set<ArrayList<Integer>> knownTriangles = new HashSet<ArrayList<Integer>>(knownTriangleList.get(0));
			Set<Set<Integer>> knownTrianglesConvert = new HashSet<Set<Integer>>();
			//convert set of arrays to set of sets
			for(ArrayList<Integer> eachTriangle: triangle)
			{
				Set<Integer> temp1 = new HashSet<Integer>();
				for(Integer each: eachTriangle)
					temp1.add(each);
				triangleConvert.add(temp1);
			}
			for(ArrayList<Integer> eachTriangle: knownTriangles)
			{
				Set<Integer> temp2 = new HashSet<Integer>();
				for(Integer each: eachTriangle)
					temp2.add(each);
				knownTrianglesConvert.add(temp2);
			}
			System.out.println(knownTriangles);
			
			for(Set<Integer> eachTriangle: knownTrianglesConvert)
			{
				if(triangleConvert.contains(eachTriangle))
				{
					flag = 1;
				}
				else
				{
					flag = 0;
					break;
				}
			}
			if(flag == 0)
				break;
		}
		System.out.println(flag);
	}
	
	public void test2Triangle()
	{
		int i, flag = 0;
		BitMatrix hyper = new BitMatrix(column, row);
		//Set<ArrayList<Integer>> knownTriangles = new HashSet<ArrayList<Integer>>();
		genQuery g = new genQuery();
		determin c = new determin();
		secondlevelindx s = new secondlevelindx();
		
		System.out.print("testing>..");
		for(i = 0; i < loop; i++)
		{
			HashMap<BitMatrix, Set<ArrayList<Integer>>> returnedValue = g.genHyperWith2TrianglePosition(row, column, triangleNum, sharingV);
			ArrayList<BitMatrix> hyperList= new ArrayList<BitMatrix>(returnedValue.keySet());
			hyper = hyperList.get(0);
			Set<ArrayList<Integer>> triangle = c.getHyperTriangleIndex(hyper);
			ArrayList<ArrayList<Integer>> triangle2ndlevel = s.get2ndLevelIndx(triangle);

			Set<Set<Integer>> triangle2ndConvert = new HashSet<Set<Integer>>();
			System.out.print("we get:");
			System.out.println(triangle2ndlevel);
			ArrayList<Set<ArrayList<Integer>>> knownTriangleList2nd = new ArrayList<Set<ArrayList<Integer>>>(returnedValue.values());
			Set<ArrayList<Integer>> knownTriangles2nd = new HashSet<ArrayList<Integer>>(knownTriangleList2nd.get(0));
			Set<Set<Integer>> knownTriangles2ndConvert = new HashSet<Set<Integer>>();
			//convert set of arrays to set of sets
			for(ArrayList<Integer> eachTriangle2ndlevel: triangle2ndlevel)
			{
				Set<Integer> temp1 = new HashSet<Integer>();
				for(Integer each: eachTriangle2ndlevel)
					temp1.add(each);
				triangle2ndConvert.add(temp1);
			}
			for(ArrayList<Integer> eachTriangle: knownTriangles2nd)
			{
				Set<Integer> temp2 = new HashSet<Integer>();
				for(Integer each: eachTriangle)
					temp2.add(each);
				knownTriangles2ndConvert.add(temp2);
			}
			System.out.println(knownTriangles2nd);
			
			for(Set<Integer> eachTriangle: knownTriangles2ndConvert)
			{
				if(knownTriangles2ndConvert.contains(eachTriangle))
				{
					flag = 1;
				}
				else
				{
					flag = 0;
					break;
				}
			}
			if(flag == 0)
				break;
		}
		System.out.println(flag);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testSpecific t = new testSpecific();
		t.test2Triangle();
	}

}
