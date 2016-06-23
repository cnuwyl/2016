package hadoopjni;

import loadlib.LoadLib;

public  class TestJni {
	public static void main(String[] args) throws Exception { 

        System.out.println ("In this project, we test jni on hadoop!\n"); 
     
        // test jni on linux local 
       /* String s =LoadLib.SegmentALine("now we test LoadLib");
        System.out.print(s);*/
        
        // test jni on hadoop 
        new MapReduceJni().runTestJni(args); 
    } // main 
} 