package aufgabe3;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class Teilaufgabe_3C {

    public static void main(String[] args) {
        Path file = Paths.get("./src/aufgabe3/resources.txt");

        //Spark Config
        SparkConf sparkConf = new SparkConf().setAppName("test").setMaster("local[*]");
        JavaSparkContext context = new JavaSparkContext(sparkConf);

        JavaRDD<String> input = context.textFile(file.toString(), 1);

        JavaPairRDD<String, List> twoGrams = generate2grams(input);

        JavaPairRDD<String, HashSet> unique = generateUnique(twoGrams);

        JavaPairRDD<String, Integer> counted = count(unique);

        List<Tuple2<String, Integer>> output = counted.collect();
        for (Tuple2<?, ?> tuple : output) {
            System.out.println(tuple._1() + ": " + tuple._2());
        }
        context.stop();
        context.close();
    }

    private static JavaPairRDD<String, List> generate2grams(JavaRDD<String> input){
        JavaPairRDD<String, List> twoGrams = input.mapToPair(key -> {
            String value = key.toLowerCase();

            List<String> list = new ArrayList<String>();

            for(int i = 0; i < value.length() - 1; i++) {
                list.add(value.substring(i, i+2));
            }

            return new Tuple2<>(key,list);
        });

        return twoGrams;
    }

    private static JavaPairRDD<String, HashSet> generateUnique(JavaPairRDD<String, List> twoGrams){
        return twoGrams.mapValues(value -> {
            HashSet<String> hs = new HashSet<String>();
            for(Object o : value) {
                hs.add(value.toString());
            }
            return hs;
        });
    }

    private static JavaPairRDD<String, Integer> count(JavaPairRDD<String, HashSet> unique){
        return unique.mapValues(set -> {
            return set.size();
        });
    }
}
