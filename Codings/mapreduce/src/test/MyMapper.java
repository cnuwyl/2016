package test;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	//key，value是输入文件中split后的一个键值对，键是下标，value是单词的值
	protected void map(LongWritable key, Text value, Context context)throws IOException,InterruptedException{
		String line = value.toString();
		StringTokenizer st = new StringTokenizer(line); //默认按空格分割
		
		while(st.hasMoreTokens()){
			String word = st.nextToken();
			context.write(new Text(word), new IntWritable(1));	//word, 1
		}
		
	};
}
