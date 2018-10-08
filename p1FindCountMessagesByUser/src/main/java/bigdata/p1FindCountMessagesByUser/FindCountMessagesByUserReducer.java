package bigdata.p1FindCountMessagesByUser;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FindCountMessagesByUserReducer extends Reducer<Text, Text, Text, Text>{
	@Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
	 	
        // Count
		int count = 0;
		// List of messages
		HashSet<String> messages = new HashSet<String>();
        for (Text value : values ){
        	messages.add(value.toString());
        	count++;
        } 
        context.write(key, new Text(count + "-" +messages.toString()));
        
    }

}
