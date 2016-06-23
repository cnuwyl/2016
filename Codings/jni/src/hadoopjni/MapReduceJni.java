package hadoopjni;

import java.io.IOException;
import java.lang.Iterable;

import loadlib.LoadLib;
import loadlib.TestLoadLib;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class MapReduceJni {
	
public static class MapTestJni extends Mapper<Writable, Text, Text, Text> {
	    
	    protected String s; 
	    protected boolean t;
	    protected void setup(Context context) throws IOException, InterruptedException 
	    {   t = LoadLib.Init("input/lex.txt");
	        System.out.println(t);
	        s = LoadLib.SegmentALine("-value ");  
	    } 
	     
	    protected void map(Writable key, Text value, Context context) 
	    throws IOException, InterruptedException { 
	        context.write(new Text("key"), new Text(s.toString()+t+"恭喜成功打开lex.txt 文件，java调用Ｃ++成功运行在hadoop上！"));        
	    } 
	} 
	
	
	public static class ReduceTestJni extends Reducer<Text, Text, Text, Text> {
	    protected void reduce(Text key, Iterable<Text> values, Context context) 
	    throws IOException, InterruptedException { 
	        String outString = ""; 
	        boolean b = false;
	        for (Text value: values) 
	        { 
	            outString = value.toString(); 
	            
	        } 
	         
	        context.write(key, new Text(outString)); 
	    } 
	}
	
	
	public void runTestJni (String[] args) throws Exception { 
        
        //  the configuration 
        Configuration conf = new Configuration(); 
       GenericOptionsParser goparser = new GenericOptionsParser(conf, args); 
        String otherargs [] = goparser.getRemainingArgs();
        
        // the job 
        Job job; 
        job = new Job(conf, "@here-TestLoadLib-hadoopJni"); 
        job.setJarByClass(TestJni.class); 

        // the mapper 
        job.setMapperClass(MapTestJni.class); 
        job.setMapOutputKeyClass(Text.class); 
        job.setMapOutputValueClass(Text.class); 
         
        // the reducer 
        job.setReducerClass(ReduceTestJni.class); 
        job.setOutputKeyClass(Text.class); 
        job.setOutputValueClass(Text.class); 
        job.setNumReduceTasks(1); 
         
        // the path 
        FileInputFormat.addInputPath(job, new Path(otherargs[0])); //此处为什么必须写上otherargs[0]我不明白
       // System.out.println(new Path(otherargs[0]).toString());  //lib里面什么都没有.只是一个空目录
        FileInputFormat.addInputPath(job, new Path(otherargs[1])); 
        FileOutputFormat.setOutputPath(job, new Path(otherargs[2])); 
        job.waitForCompletion(true); 
    } 

}
