package bigdata.p1FindCountMessagesByUser;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class FindCountMessagesByUser {
	public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: FindCountMessagesByUser <input path> <output path>");
            System.exit(-1);
        }
        Job job = new Job();
        job.setJarByClass(bigdata.p1FindCountMessagesByUser.FindCountMessagesByUser.class);
        job.setJobName("Find and count messages by user");

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(bigdata.p1FindCountMessagesByUser.FindCountMessagesByUserMapper.class);
        job.setReducerClass(bigdata.p1FindCountMessagesByUser.FindCountMessagesByUserReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
