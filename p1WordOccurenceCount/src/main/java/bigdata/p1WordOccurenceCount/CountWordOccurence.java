package bigdata.p1WordOccurenceCount;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class CountWordOccurence {
	public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: CountKeywordOccurence <input path> <output path>");
            System.exit(-1);
        }
        Job job = new Job();
        job.setJarByClass(bigdata.p1WordOccurenceCount.CountWordOccurence.class);
        job.setJobName("Count Number of occurences for these words: Trump, Flu, Zika, Diarrhea, Ebola, Headache, Measles");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(bigdata.p1WordOccurenceCount.CountWordOccurenceMapper.class);
        job.setReducerClass(bigdata.p1WordOccurenceCount.CountWordOccurenceReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
