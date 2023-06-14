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
        //verweis auf datei mit entsprechenden Texten
        Path file = Paths.get("./src/aufgabe3/resources.txt");

        //Spark Config
        SparkConf sparkConf = new SparkConf().setAppName("test").setMaster("local[*]");
        JavaSparkContext context = new JavaSparkContext(sparkConf);

        //Ein RDD mit den Texten aus der Datei befüllen
        JavaRDD<String> input = context.textFile(file.toString(), 1);

        //Die eingelesenen Werte in 2-Gramme aufteilen
        JavaPairRDD<String, List> twoGrams = generate2grams(input);

        //Ausgabe map
        for (Tuple2<?, ?> tuple: twoGrams.collect()) {
            //Leon Kolb: le
            //Leon Ko... etc
            System.out.println(tuple._1() + ": " + tuple._2());
        }

        //Doppelte Einträge löschen
        JavaPairRDD<String, HashSet> unique = generateUnique(twoGrams);

        //Ausgabe Shuffle
        for (Tuple2<?, ?> tuple: unique.collect()) {
            //Leon Kolb: le, eo
            //Planks.. etc
            System.out.println(tuple._1() + ": " + tuple._2());
        }

        //Anzahl der Einträge zählen
        JavaPairRDD<String, Integer> counted = count(unique);

        //Ausgabe Shuffle
        for (Tuple2<?, ?> tuple: counted.collect()) {
            //Leon Kolb: 8
            //Planks.. etc
            System.out.println(tuple._1() + ": " + tuple._2());
        }

        //Spark Context Stoppen
        context.stop();
        context.close();
    }

    private static JavaPairRDD<String, List> generate2grams(JavaRDD<String> input){
        //Key = inputText
        //value = inputText[idenx] + inputText[index+1]

        //RDD mit key value Paaren befüllen
        //value ist liste aller 2 Gramme

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
        //HashSets speichern Werte nicht doppelt ab
        //Aufdiese Weise werden dopplungen vermieden

        return twoGrams.mapValues(value -> {
            HashSet<String> hs = new HashSet<String>();
            for(Object o : value) {
                hs.add(value.toString());
            }
            return hs;
        });
    }

    private static JavaPairRDD<String, Integer> count(JavaPairRDD<String, HashSet> unique){
       //key value Paar mit länge des HashSets
        return unique.mapValues(set -> {
            return set.size();
        });
    }
}
