package recommendfriend;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class RecommMapper extends Mapper<LongWritable,Text,Text,Text> {
	protected void map(LongWritable key, Text value, Context context){
		String line = value.toString();
		String[] str = line.split(":");
		try {
			context.write(new Text(str[0]),new Text(str[1]));
			context.write(new Text(str[1]),new Text(str[0]));
			System.out.println(str[0]+"\t"+str[1]+"\t"+str[2]);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}