package edu.utoledo.nlowe.WordCount.sample;

import edu.utoledo.nlowe.WordCount.SortedWordCounter;
import edu.utoledo.nlowe.WordCount.UnsortedWordCounter;
import edu.utoledo.nlowe.WordCount.Word;
import edu.utoledo.nlowe.WordCount.WordCounter;

import java.io.*;

/**
 * A class to benchmark each of the WordCounter implementations by
 * using Hamlet
 */
public class Benchmarks
{
    private static final String HAMLET_SOURCE = null;

    public static final String WORD_SEPARATOR = "[\\s]";

    public static final int OVERHEAD = 0;
    public static final int UNSORTED = 1;
    public static final int SORTED = 2;
    public static final int SELF_ADJUST_FRONT = 3;
    public static final int SELF_ADJUST_BUBBLE = 4;

    public static final String[] BENCHMARK_NAMES = {
            "Overhead",
            "Unsorted",
            "Sorted (Alphabetically)",
            "Self-Adjusting (Front)",
            "Self-Adjusting (Bubble)"
    };

    public static final WordCounter[] counters = new WordCounter[]{
            new UnsortedWordCounter(),
            new SortedWordCounter(),
            null,
            null
    };

    public static void runBenchmark(InputStream in, WordCounter counter) throws IOException
    {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
            String line;
            while((line = reader.readLine()) != null)
            {
                for(String word : line.split(WORD_SEPARATOR))
                {
                    String sanitized = Word.sanitize(word);

                    if(!sanitized.isEmpty() && counter != null)
                    {
                        counter.encounter(sanitized);
                    }
                }
            }
        }
    }

    public static InputStream getResourceInputStream() throws FileNotFoundException
    {
        return  HAMLET_SOURCE == null ?
                Benchmarks.class.getClassLoader().getResourceAsStream("Hamlet.txt") :
                new FileInputStream(HAMLET_SOURCE);
    }

    public static void main(String[] args)
    {
        long[] time = new long[5];

        // The first pass is just to calculate overhead
        long start = System.currentTimeMillis();
        try
        {
            runBenchmark(getResourceInputStream(), null);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        time[OVERHEAD] = System.currentTimeMillis() - start;

        // Unsorted
        start = System.currentTimeMillis();
        try
        {
            runBenchmark(getResourceInputStream(), counters[UNSORTED-1]);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        time[UNSORTED] = System.currentTimeMillis() - start;

        // Sorted
        start = System.currentTimeMillis();
        try
        {
            runBenchmark(getResourceInputStream(), counters[SORTED-1]);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        time[SORTED] = System.currentTimeMillis() - start;

        // Print Results
        System.out.println("Benchmarks Complete");
        for(int i=0; i<BENCHMARK_NAMES.length; i++)
        {
            System.out.print(BENCHMARK_NAMES[i] + ": Duration: " + (double) time[i] / 1000d + " seconds");

            if(i != OVERHEAD){
                WordCounter counter = counters[i-1];

                // Some counter implementations don't exist yet
                if(counter != null)
                    System.out.print("\tWord Count: " + counter.getWordCount() +
                            ", Distinct: " + counter.getDistinctWordCount() +
                            ", Comparisons: " + counter.getComparisonCount() +
                            ", Reference Changes: " + counter.getReferenceChangeCount());
            }

            System.out.println();
        }
    }
}
