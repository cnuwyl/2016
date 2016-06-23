package FakeSegmentForJni; 
   
/**
 * This class is for verifying the jni technology. 
 * It call the function defined in FakeSegmentForJni.java
 * 
 */ 
 
public class TestFakeSegmentForJni { 
       
    public static void main(String[] args) throws Exception { 
   
        System.out.println ("In this project, we test jni!\n"); 
           
        String s = FakeSegmentForJni.SegmentALine("now we test FakeSegmentForJni"); 
        System.out.print(s); 
           
    }
   
} 