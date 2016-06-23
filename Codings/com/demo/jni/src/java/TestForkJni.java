package com.demo.jni;
   
/**
 * This class is for verifying the jni technology. 
 * It call the function defined in TestForkJni.java
 *  author : wang yuanlong
 * time : 2016-04-23
 */ 
 
public class TestForkJni { 
       
    public static void main(String[] args) throws Exception { 
        System.out.println ("In this java project, we test jni for c++!\n"); 
        String s = ForkJni.SegmentALine("now we test libForkJni"); 
        System.out.print(s); 
           
    }
   
} 