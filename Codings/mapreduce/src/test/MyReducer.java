package test;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;


public class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
	protected void reduce(Text key, Iterable<IntWritable> values,Context context) 
			throws IOException ,InterruptedException {
		int sum = 0;
		//key是经过字典排序后的不重复的键，values里面是装的是一个集合，装的是同一个key所对应的值
		for (IntWritable value:values){
			 sum += value.get(); 
		}
		context.write(key,new IntWritable(sum));
		
	};
	
}
