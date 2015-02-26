import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.regex.Pattern;


import tutorial.MapStringList;

public class similarity {
	public static void main(String[] args) {
		int i, j;
		  int[][] test = {
	    	      {0,1,1,0,0,0,0,0,0},
	    	      {1,0,0,1,1,0,0,0,0},
	    	      {1,0,0,0,0,1,0,0,0},
	    	      {0,1,0,0,1,0,1,0,0},
	    	      {0,1,0,1,0,1,0,0,0},
	    	      {0,0,1,0,1,0,0,1,0},
	    	      {0,0,0,1,0,0,0,1,0},
	    	      {0,0,0,0,0,1,1,0,0},
	    	      {0,0,0,0,0,0,0,0,0}
		  };
		  shortest s = new shortest();
		  //i = s.getShortest(test, 4, 8);
		  //System.out.println(i);
		  
		  
		  int[][] test1 = {
				  {1,1,1,0},
				  {0,1,1,1},
				  {0,0,1,1}
		  };
		  //int simple[][] = s.hyperedge2edgeSimple(test1);
		  /*
		  for(i= 0 ; i < simple.length; i++)
		  {
			  for(j = 0 ; j < simple[0].length; j ++)
				  System.out.print(simple[i][j]);
			  //System.out.println();
		  }
		  */
		  int[][] testHyper = {
	    	      {1,1,1,1,0,0,0,0,0,0},
	    	      {0,1,1,0,1,1,0,0,0,0},
	    	      {0,0,0,0,0,1,1,1,1,0},
	    	      {0,0,0,0,0,0,0,0,1,1},
	    	      {0,0,0,0,0,0,0,0,0,0}
		  };
		  double sim;
		  sim = s.similarity(testHyper, s.hyperedge2edgeSimple(testHyper), 0, 4);
		  System.out.print(sim);
		  
	}
	
}
