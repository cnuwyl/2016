package recommendfriend;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class RecommReducer extends Reducer<Text,Text,Text,Text>{
	protected void reduce(Text key, Iterator<Text> values, Context context) throws IOException ,InterruptedException {
		Set<String> set = new HashSet<String>();
		while(values.hasNext()){
			Text text = values.next();
		 	set.add(text.toString());
		}
		
		if(set.size()>1){//如果同一个人有多个联系人，那么这些联系人之间
			for(Iterator j = set.iterator();j.hasNext();){
				String str1 = (String) j.next();
				for(Iterator k = set.iterator();k.hasNext();){
					String str2 = (String) k.next();
					if(!str1.equalsIgnoreCase(str2)){
						
						context.write(new Text(str1),new Text(str2));
					}
				}
			}
		
	};
}
}