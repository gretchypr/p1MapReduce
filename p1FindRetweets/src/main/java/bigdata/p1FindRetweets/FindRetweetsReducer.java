package bigdata.p1FindRetweets;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FindRetweetsReducer extends Reducer<Text, Text, Text, Text>{
	@Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
	 	// Set of messages
	 	HashSet<String> messages_set = new HashSet<String>();
        // Count
        for (Text value : values ){
        	messages_set.add(value.toString());
        	if(!value.toString().equals("0"))
        		System.out.println(value.toString());
        }
        // If it has a 0 then the retweeted message is one of the messages received in the input
        if(messages_set.contains("0")) {
        	messages_set.remove("0");
        	context.write(key, new Text(messages_set.toString()));
        }
        
    }
}
