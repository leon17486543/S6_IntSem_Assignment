package aufgabe1;

import java.util.ArrayList;
import java.util.List;

public class Teilaufgabe_D {
    protected static String s1 = "Kolb";
    protected static String s2 = "Coronapandemie";

    public static void main(String[] args){
        new Teilaufgabe_D();
    }

    public Teilaufgabe_D(){
        System.out.println();

        List<String> list_s1 =  splitInto2Grams(s1);
        System.out.println("All 2-grams for: " + s1);
        System.out.println("List s1: " + list_s1);

        System.out.println();

        List<String> list_s2 =  splitInto2Grams(s2);
        System.out.println("All 2-grams for: " + s2);
        System.out.println("List s2: " + list_s2);

        System.out.println();

        double similarity = calculateSimilarity(list_s1, list_s2);

        System.out.println();
        System.out.println("Jaccard Similarity: " + similarity);
    }

    private List<String> splitInto2Grams(String s){
        List<String> list = new ArrayList<>();
        char[] split = s.toCharArray();

        for(int i= 0; i<split.length-1; i++){
            String twoGram = String.valueOf(split[i]) + String.valueOf(split[i+1]);
            list.add(twoGram);
        }

        //remove duplicates
        list = list.stream().distinct().toList();
        return list;
    }

    private double calculateSimilarity(List<String> list_s1, List<String> list_s2){
        List<String> totalGrams = new ArrayList<>();
        totalGrams.addAll(list_s1);
        totalGrams.addAll(list_s2);

        List<String> distinctTotal = totalGrams.stream().distinct().toList();

        System.out.println("Distinct 2-grams: " + distinctTotal.size());
        System.out.println(distinctTotal);

        int nInBoth = 0;
        List<String> list_Both = new ArrayList<>();

        for (String s1:list_s1) {
            for (String s2:list_s2) {
                if(s1.equals(s2)){
                    nInBoth++;
                    list_Both.add(s1);
                }

            }
        }
        System.out.println("2-grams in both: " + nInBoth);
        System.out.println(list_Both);

        double similarity = (double) nInBoth /(double) distinctTotal.size();
        return similarity;
    }
}
