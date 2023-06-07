package aufgabe1;

public class Teilaufgabe_A {
    protected static String s1 = "leon";
    protected static String s2 = "Coronapandemie";
    protected static int step = 0;

    public static void main(String[] args){
        new Teilaufgabe_A();
    }

    public Teilaufgabe_A(){
        int distance = calculateDistance(s1, s2);
        System.out.println("Levenshtein Distance: " + distance);
    }

    public int calculateDistance(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        // Erstellen einer Matrix zur Speicherung der Distanzen
        int[][] matrix = new int[m + 1][n + 1];

        // Initialisierung der ersten Zeile und Spalte der Matrix
        for (int i = 0; i <= m; i++) {
            matrix[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            matrix[0][j] = j;
        }

        printMatrix(matrix, m, n);

        // Berechnung der Distanzen für die restlichen Zeichen
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                System.out.println("______");
                System.out.println("Current Step: " + step +"\n");
                step++;

                // Überprüfung, ob das aktuelle Zeichen übereinstimmt
                int cost;
                System.out.println("Compare: " + s1.charAt(i - 1) + " & " + s2.charAt(i - 1));
                if(s1.charAt(i - 1) == s2.charAt(j - 1)){
                    System.out.println(" -> Equal: cost = 0"+"\n");
                    cost = 0;
                }else {
                    System.out.println(" -> Not Equal: cost = 1"+"\n");
                    cost = 1;
                }

                System.out.println("find lowest value for current cell: ");
                System.out.print(" " + matrix[i - 1][j - 1] + " + " +  cost + " |");
                System.out.print(" " + matrix[i - 1][j] + " + " +  1 + " |" + "\n");
                System.out.print(" " + matrix[i][j - 1] + " + " +  1 + " |");
                System.out.print(" " + "  ?   |" + "\n");

                // Berechnung der minimalen Distanz
                int lowestValue = Math.min(matrix[i - 1][j - 1] + cost, Math.min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1));
                matrix[i][j] = lowestValue;
                System.out.println("lowest value is: " + lowestValue+"\n");

                printMatrix(matrix, m, n);
            }
        }



        // Rückgabe der Levenshtein-Distanz
        return matrix[m][n];
    }

    private void printMatrix(int [][] matrix, int m, int n){
        if(step == 0)
            System.out.println("Initialise matrix:");

        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();

        String[][] placeHolder = new String[m + 2][n + 2];

        for(int i = 0; i < m+2; i++){
            for(int j = 0; j <n+2; j++){
                placeHolder[i][j] = " ";
            }
        }

        for(int i=2; i<m+2; i++){
            placeHolder[i][0] =  String.valueOf(c1[i-2]);
        }

        for(int i=2; i<n+2; i++){
            placeHolder[0][i] =  String.valueOf(c2[i-2]);
        }

        for(int i=1; i<m+2; i++){
            for(int j=1; j<n+2; j++){
                placeHolder[i][j] =  String.valueOf(matrix[i-1][j-1]);
            }
        }


        for(int i = 0; i < m+2; i++){
            for(int j = 0; j <n+2; j++){
                System.out.print(" " + placeHolder[i][j] + " |");
            }
            System.out.println("");
        }
        System.out.println(" ");
    }


}
