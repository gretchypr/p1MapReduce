package bigdata.p1FindReplies;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FindRepliesReducer extends Reducer<Text, Text, Text, Text>{
	@Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
	 	// Set of messages
	 	HashSet<String> mess_set = new HashSet<String>(); 
	 	boolean isInData = false;
        // Count
        for (Text value : values ){
        	String message_value = value.toString();
        	// Check if this reply is to a message in the input data
        	if(message_value.contains("A")) {
        		isInData = true;
        	}
        	else {
        		mess_set.add(message_value);
        	}
        } 
        if(isInData)
        {
        	context.write(key, new Text(mess_set.toString()));
        }
    }

}
