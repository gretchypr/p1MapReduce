package bigdata.KeywordCount;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



public class CountKeywordOccurenceMapper extends Mapper<LongWritable, Text, Text, IntWritable>{
	 @Override
	    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		 	// Get the received JSON
		 	JsonNode twitter_json = new ObjectMapper().readTree(value.toString());
	        // Get full text
	        String full_text_string = twitter_json.get("extended_tweet").get("full_text").textValue();
	        // Get each words from the message string
	        String[] full_text = full_text_string.split(" ");
	        
	        String[] stop_word_list = {"how", "a", "with", "the", "in", "then", "out",
	        							"which", "how's", "what", "when", "what's", "of",
	        							"he", "she", "he's", "she's", "this", "that", "but",
	        							"by", "at", "are", "and", "an", "as", "am", "i", "i've",
	        							"any", "aren't", "be", "been", "being", "because", "can't",
	        							"cannot", "could", "couldn't", "did", "didn't", "do", "does",
	        							"doesn't", "doing", "for", "from", "has", "hasn't", "had",
	        							"hadn't", "have", "haven't", "him", "her", "he'd", "he'll",
	        							"his", "i'm", "i'll", "i'd", "if", "is", "isn't", "it", "it's",
	        							"its", "let's", "or", "other", "she'd", "she'll", "should",
	        							"shouldn't", "so", "such", "that's", "they", "they're", "they've",
	        							"their", "theirs", "this", "those", "to", "too", "very", "was",
	        							"wasn't", "we", "we're", "we've", "we'll", "were", "weren't",
	        							"when's", "where", "where's", "will", "who", "who's", "why",
	        							"why's", "would", "wouldn't", "won't", "you", "your", "you'd",
	        							"you'll", "you've", "yours"};
	        HashSet<String> stop_words = new HashSet<String>();
	        for (String word: stop_word_list) {
	        	stop_words.add(word);
	        }	        
	        
	        for (String word: full_text) {
	        	if (!stop_words.contains(word)) {
		        	context.write(new Text(full_text_string), new IntWritable(1));
	        	}	        	
	        }
	        

	    }

}
