import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        // Champion word to be selected
        String champion = "";
        
        // Counter for the number of words read
        int wordCount = 0;

        // Read words from standard input
        while (!StdIn.isEmpty()) {
            // Read the next word
            String word = StdIn.readString();
            
            // Increment word count
            wordCount++;

            // With probability 1/wordCount, replace the champion
            if (StdRandom.bernoulli(1.0 / wordCount)) {
                champion = word;
            }
        }

        // Print the champion word
        StdOut.println(champion);
    }
}