package aufgabe1;

public class Teilaufgabe_B {
    protected static String s1 = "leon";
    protected static String s2 = "Coronapandemie";

    public static void main(String[] args){
        new Teilaufgabe_B();
    }

    public Teilaufgabe_B(){
        //Aus Teilaufgabe_A
        int distance = 12;

        double similarity = calculateSimilarity(s1, s2, distance);
        System.out.println("Levenshtein Similarity: " + similarity);
    }

    public double calculateSimilarity(String s1, String s2, int distance){

        int maxLength = Math.max(s1.length(), s2.length());

        double lev_norm = (double) distance / (double) maxLength;
        double lev_s = 1 - lev_norm;

        return lev_s;
    }
}
