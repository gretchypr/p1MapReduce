package bigdata.p1UniqueScreenameCount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class CountScreennamesReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        
        // setup a counter
        int count = 0;
        // iterator over list of 1s, to count them (no size() or length() method available)
        for (IntWritable screenname : values ){
        	count++;	
        }

        // Return count of unique screennames and the list of usernames
        context.write(key, new IntWritable(count));
    }
}