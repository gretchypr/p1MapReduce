package bigdata.KeywordCount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class CountKeywordOccurenceReducer extends Reducer<Text, IntWritable, Text, IntWritable>{
	 @Override
	    protected void reduce(Text key, Iterable<IntWritable> values, Context context)
	            throws IOException, InterruptedException {
	        // Counter
	        int count = 0;
	        // Count
	        for (IntWritable value : values ){
	            count++;
	        }

	        context.write(key, new IntWritable(count));
	    }

}
