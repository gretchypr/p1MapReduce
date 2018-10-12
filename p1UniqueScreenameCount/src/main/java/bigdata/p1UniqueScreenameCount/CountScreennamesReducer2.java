package bigdata.p1UniqueScreenameCount;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CountScreennamesReducer2 extends Reducer<Text, Text, IntWritable, Text> {
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        
        // setup a counter
        int count = 0;
        // Set that will hold all user names
        HashSet<String> screenname_set = new HashSet<String>();
        // iterator over list of 1s, to count them (no size() or length() method available)
        for (Text screenname : values ){
        	// Add screename to set, if already in set then go to next value (don't repeat values)
        	System.out.println(screenname);
        	if(screenname_set.add(screenname.toString())) {
                count++;
        	}
        	
        }

        // Return count of unique screennames and the list of usernames
        context.write(new IntWritable(count), new Text(screenname_set.toString()));
    }


}
