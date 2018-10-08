package bigdata.p1FindReplies;

import java.io.File;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FindReplies {

	public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: FindReplies <input path> <output path>");
            System.exit(-1);
        }
        Job job = new Job();
        job.setJarByClass(bigdata.p1FindReplies.FindReplies.class);
        job.setJobName("Find replies per message");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(bigdata.p1FindReplies.FindRepliesMapper.class);
        job.setReducerClass(bigdata.p1FindReplies.FindRepliesReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}
