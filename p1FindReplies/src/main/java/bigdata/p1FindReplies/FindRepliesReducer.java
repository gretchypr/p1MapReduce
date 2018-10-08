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
	 	String newKey = "";
        // Count
        for (Text value : values ){
        	String message_value = value.toString();
        	if(message_value.contains("-")) {
        		newKey = message_value.substring(message_value.indexOf("-"));
        	}
        	else {
        		mess_set.add(message_value);
        	}
        } 
        if(newKey != "")
        {
        	context.write(new Text(newKey), new Text(mess_set.toString()));
        }
    }

}
