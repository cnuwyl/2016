package loadlib; 
   
/**
 * This class is for verifying the jni technology. 
 * It call the function defined in LoadLib.java
 * 
 */ 
 
public class TestLoadLib { 
    
    public static void main(String[] args) throws Exception { 
        System.out.println ("In this project, we test jni to c++!\n"); 
        String s = LoadLib.SegmentALine("now we test LoadLib"); 
        System.out.print(s);         
    }
}

