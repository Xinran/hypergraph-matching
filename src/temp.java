import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cern.colt.*;
import cern.colt.bitvector.BitMatrix;
class temp 
{
 public static void main(String args[])
  {
	 int j = 0;
		try{
	 FileWriter fstream1 = new FileWriter("100by100-300" + j + "out1.txt");		
	 }catch (Exception e){//Catch exception if any
		  System.err.println("Error: " + e.getMessage());
	 }
	 determin d = new determin();
	 ArrayList<Integer> a = new ArrayList();
	 a.add(1);
	 a.add(2);
	 a.add(3);
	 a.add(4);
	 a.add(5);
	 ArrayList<ArrayList<Integer>> edgeStarPermu = d.Permutation(a,2);
	 System.out.print(edgeStarPermu.size());
	 /*
	 BitMatrix a = new BitMatrix(5,6);
	 for(int i = 0; i < a.rows()-1; i++)
	 {
		 for(int j = 0; j < a.columns()-1; j++)
			 a.put(j, i, true);
	 }
	 
	 for(int i = 0; i < a.rows(); i++)
	 {
		 for(int j = 0; j < a.columns(); j++)
			System.out.print(a.get(j, i));
		 System.out.print("\n");
	 }
	 System.out.print(a.rows());
	 */
  }
}