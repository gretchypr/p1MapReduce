package bigdata.p1UniqueScreenameCount;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CountScreennamesReducer extends Reducer<Text, Text, IntWritable, Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        //super.reduce(key, values, context);

        // key is the abreviation of a state
        // values is a list of 1s, one for each school found for the state

        // setup a counter
        int count = 0;
        // Set that will hold all user names
        HashSet<Text> screenname_set = new HashSet<Text>();
        // Screenname list
        String screennames = "[ ";
        // iterator over list of 1s, to count them (no size() or length() method available)
        for (Text screenname : values ){
        	// Add screename to set, if already in set then go to next value (don't repeat values)
        	if(screenname_set.add(screenname)) {
        		screennames = screennames + screenname.toString() + ", ";
                count++;
        	}
        	
        }
        // Remove trailing whitespace
        screennames = screennames.trim();
        // Remove final ,
        screennames = screennames.substring(0, screennames.length()-2);
        // Add closing bracket
        screennames = screennames + " ]";

        // Return count of unique screennames and the list of usernames
        context.write(new IntWritable(count), new Text(screennames));
    }


}
