package edu.utoledo.nlowe.WordCount.sample;

import edu.utoledo.nlowe.WordCount.Word;
import edu.utoledo.nlowe.WordCount.WordCounter;
import edu.utoledo.nlowe.WordCount.WordCounters.BubbleSelfAdjustingWordCounter;
import edu.utoledo.nlowe.WordCount.WordCounters.FrontSelfAdjustingWordCounter;
import edu.utoledo.nlowe.WordCount.WordCounters.SortedWordCounter;
import edu.utoledo.nlowe.WordCount.WordCounters.UnsortedWordCounter;

import java.io.*;

/**
 * A class to benchmark each of the WordCounters implementations by
 * using Hamlet
 */
public class Benchmarks
{
    private static final String HAMLET_SOURCE = null;

    public static final String WORD_SEPARATOR = "[\\s]";

    public static final int EXIT_BENCHMARK_IO_ERROR = -1;

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

    public final WordCounter[] counters = new WordCounter[]{
            new UnsortedWordCounter(),
            new SortedWordCounter(),
            new FrontSelfAdjustingWordCounter(),
            new BubbleSelfAdjustingWordCounter()
    };

    private long[] results = new long[5];

    /**
     * Run the specified word counter using the specified input stream
     *
     * @param in the stream to read words from
     * @param counter the word counter to benchmark
     * @throws IOException
     */
    public void runBenchmark(InputStream in, WordCounter counter) throws IOException
    {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                for (String word : line.split(WORD_SEPARATOR))
                {
                    String sanitized = Word.sanitize(word);

                    if (!sanitized.isEmpty() && counter != null)
                    {
                        counter.encounter(sanitized);
                    }
                }
            }
        }
    }

    public InputStream getResourceInputStream() throws FileNotFoundException
    {
        return HAMLET_SOURCE == null ?
                Benchmarks.class.getClassLoader().getResourceAsStream("Hamlet.txt") :
                new FileInputStream(HAMLET_SOURCE);
    }

    public boolean runAllBenchmarks()
    {
        try{
            // The first pass is just to calculate overhead
            System.out.println("Benchmarking " + BENCHMARK_NAMES[OVERHEAD] + "...");
            long start = System.currentTimeMillis();
            runBenchmark(getResourceInputStream(), null);
            results[OVERHEAD] = System.currentTimeMillis() - start;

            // Unsorted
            System.out.println("Benchmarking " + BENCHMARK_NAMES[UNSORTED] + "...");
            start = System.currentTimeMillis();
            runBenchmark(getResourceInputStream(), counters[UNSORTED - 1]);
            results[UNSORTED] = System.currentTimeMillis() - start;

            // Sorted
            System.out.println("Benchmarking " + BENCHMARK_NAMES[SORTED] + "...");
            start = System.currentTimeMillis();
            runBenchmark(getResourceInputStream(), counters[SORTED - 1]);
            results[SORTED] = System.currentTimeMillis() - start;

            // Front Self-Adjusted
            System.out.println("Benchmarking " + BENCHMARK_NAMES[SELF_ADJUST_FRONT] + "...");
            start = System.currentTimeMillis();
            runBenchmark(getResourceInputStream(), counters[SELF_ADJUST_FRONT - 1]);
            results[SELF_ADJUST_FRONT] = System.currentTimeMillis() - start;

            // Bubble Self-Adjusted
            System.out.println("Benchmarking " + BENCHMARK_NAMES[SELF_ADJUST_BUBBLE] + "...");
            start = System.currentTimeMillis();
            runBenchmark(getResourceInputStream(), counters[SELF_ADJUST_BUBBLE - 1]);
            results[SELF_ADJUST_BUBBLE] = System.currentTimeMillis() - start;

            return true;
        }
        catch (IOException e)
        {
            System.err.println("Encountered an error while running benchmarks");
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args)
    {
        Benchmarks b = new Benchmarks();
        if(!b.runAllBenchmarks()){
            System.exit(EXIT_BENCHMARK_IO_ERROR);
        }

        // Print Results
        System.out.println("Benchmarks Complete\nResults:\n\n");
        for (int i = 0; i < BENCHMARK_NAMES.length; i++)
        {
            System.out.print(BENCHMARK_NAMES[i] + ": Duration: " + (double) b.results[i] / 1000d + " seconds");

            if (i != OVERHEAD)
            {
                WordCounter counter = b.counters[i - 1];

                System.out.print("\tWord Count: " + counter.getWordCount() +
                        ", Distinct: " + counter.getDistinctWordCount() +
                        ", Comparisons: " + counter.getComparisonCount() +
                        ", Reference Changes: " + counter.getReferenceAssignmentCount());
            }

            System.out.println();
        }

        System.out.println("\n----------\n");

        // Print top 100 words for the latter two benchmarks
        int counter = 0;
        System.out.println("First 100 elements in Front Self-Adjusting:");
        for (Word w : b.counters[SELF_ADJUST_FRONT - 1])
        {
            System.out.println("\t" + w.getValue() + ", " + w.getOccurrenceCount());
            if(++counter == 100) break;
        }

        counter = 0;
        System.out.println("First 100 elements in Bubble Self-Adjusting:");
        for (Word w : b.counters[SELF_ADJUST_BUBBLE - 1])
        {
            System.out.println("\t" + w.getValue() + ", " + w.getOccurrenceCount());
            if(++counter == 100) break;
        }
    }
}
