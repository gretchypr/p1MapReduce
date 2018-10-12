package bigdata.p1WordOccurenceCount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CountWordOccurenceMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	 @Override
	    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		 	// Get the received JSON
		 	JsonNode twitter_json = new ObjectMapper().readTree(value.toString());
	        String newKey = "";
	        // Get full text
	        String full_text = twitter_json.get("extended_tweet").get("full_text").textValue();
	        
	        String[] words = full_text.split(" ");
	        for (String word: words) {
	        	 if(word.contains("Trump") || word.contains("trump")) {
	 	        	newKey = "Trump";
	 	        	context.write(new Text(newKey), new IntWritable(1));
	 	        }
	 	        if(word.contains("Flu") || word.contains("flu")) {
	 	        	newKey = "Flu";
	 	        	context.write(new Text(newKey), new IntWritable(1));
	 	        }
	 	        if(word.contains("Zika") || word.contains("zika")) {
	 	        	newKey = "Zika";
	 	        	context.write(new Text(newKey), new IntWritable(1));
	 	        }
	 	        if(word.contains("Diarrhea") || word.contains("diarrhea")) {
	 	        	newKey = "Diarrhea";
	 	        	context.write(new Text(newKey), new IntWritable(1));
	 	        }
	 	        if(word.contains("Ebola") || word.contains("ebola")) {
	 	        	newKey = "Ebola";
	 	        	context.write(new Text(newKey), new IntWritable(1));
	 	        }
	 	        if(word.contains("Headache") || word.contains("headache")) {
	 	        	newKey = "Headache";
	 	        	context.write(new Text(newKey), new IntWritable(1));
	 	        }
	 	        if(word.contains("Measles") || word.contains("Measles")) {
	 	        	newKey = "Measles";
	 	        	context.write(new Text(newKey), new IntWritable(1));
	 	        }
	        }	       

	    }
}
