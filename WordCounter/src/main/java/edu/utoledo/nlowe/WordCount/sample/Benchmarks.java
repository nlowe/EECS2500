package edu.utoledo.nlowe.WordCount.sample;

import edu.utoledo.nlowe.WordCount.Word;
import edu.utoledo.nlowe.WordCount.WordCounter;
import edu.utoledo.nlowe.WordCount.WordCounters.*;

import java.io.*;

/**
 * A class to benchmark each of the WordCounters implementations
 * against the entire text of Hamlet.
 */
public class Benchmarks
{
    /** The path to the file to read. If left null, the file will be loaded from the classpath */
    private final String TEST_FILE;

    /** The token that separates words (Any whitespace) */
    public static final String WORD_SEPARATOR = "[\\s]";

    /** The exit code returned when an error occurs while running the benchmarks */
    public static final int EXIT_BENCHMARK_IO_ERROR = -1;

    public static final int OVERHEAD = 0;
    public static final int UNSORTED = 1;
    public static final int SORTED = 2;
    public static final int SELF_ADJUST_FRONT = 3;
    public static final int SELF_ADJUST_BUBBLE = 4;
    public static final int BINARY_SEARCH_TREE = 5;

    /** The names of all benchmarks */
    public static final String[] BENCHMARK_NAMES = {
            "Overhead",
            "Unsorted",
            "Sorted (Alphabetically)",
            "Self-Adjusting (Front)",
            "Self-Adjusting (Bubble)",
            "Binary Search Tree",
    };

    /** All word counters to benchmark */
    public final WordCounter[] counters = new WordCounter[]{
            new UnsortedWordCounter(),
            new SortedWordCounter(),
            new FrontSelfAdjustingWordCounter(),
            new BubbleSelfAdjustingWordCounter(),
            new BinarySearchTreeWordCounter()
    };

    /** Time results are stored here */
    private long[] results = new long[BENCHMARK_NAMES.length];

    public Benchmarks(String inputFile)
    {
        this.TEST_FILE = inputFile;
    }

    /**
     * Run the specified word counter using the specified input stream
     *
     * @param in      the stream to read words from
     * @param counter the word counter to benchmark
     * @throws IOException
     */
    public void runBenchmark(InputStream in, WordCounter counter) throws IOException
    {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in)))
        {
            // Read the file line by line
            String line;
            while ((line = reader.readLine()) != null)
            {
                for (String word : line.split(WORD_SEPARATOR))
                {
                    String sanitized = Word.sanitize(word);

                    // If, after sanitizing the word, it is not empty, submit it to the word counter
                    if (!sanitized.isEmpty() && counter != null)
                    {
                        counter.encounter(sanitized);
                    }
                }
            }
        }
    }

    /**
     * The project spec requires a constant to appear towards the top of the benchmark class that points to the
     * path to the input file. In order to maintain compatibility on different workstations, default to getting
     * the input stream from the class path instead if the constant is null
     *
     * @return an InputStream for the resource to benchmark against
     * @throws FileNotFoundException
     */
    public InputStream getResourceInputStream() throws FileNotFoundException
    {
        return TEST_FILE == null || TEST_FILE.isEmpty() ?
                Benchmarks.class.getClassLoader().getResourceAsStream("Shakespeare.txt") :
                new FileInputStream(TEST_FILE);
    }

    /**
     * Runs all benchmarks
     *
     * @return false iff there was an exception thrown while the benchmarks were running
     */
    public boolean runAllBenchmarks()
    {
        try
        {
            // The first pass is just to calculate overhead
            System.out.print("Benchmarking " + BENCHMARK_NAMES[OVERHEAD] + "...");
            long start = System.currentTimeMillis();
            runBenchmark(getResourceInputStream(), null);
            results[OVERHEAD] = System.currentTimeMillis() - start;
            System.out.println((double)results[OVERHEAD] / 1000d + " seconds");

            // Run the rest of the benchmarks
            for (int i = 1; i < BENCHMARK_NAMES.length; i++)
            {
                System.out.print("Benchmarking " + BENCHMARK_NAMES[i] + "...");
                start = System.currentTimeMillis();
                runBenchmark(getResourceInputStream(), counters[i - 1]);
                results[i] = System.currentTimeMillis() - start;
                System.out.println((double)results[i] / 1000d + " seconds");
            }

            return true;
        }
        catch (IOException e)
        {
            System.err.println("Encountered an error while running benchmarks");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * The main entry point of the program
     *
     * @param args Any arguments passed on the command line
     */
    public static void main(String[] args)
    {
        Benchmarks b;

        if((args[0].equalsIgnoreCase("--file") || args[0].equalsIgnoreCase("-f")) && args.length == 2)
        {
            b = new Benchmarks(args[1]);
        }else{
            b = new Benchmarks(null);
        }

        if (!b.runAllBenchmarks())
        {
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
            System.out.println("\t" + w.getKey() + "\t" + w.getValue());
            if (++counter == 100) break;
        }

        counter = 0;
        System.out.println("First 100 elements in Bubble Self-Adjusting:");
        for (Word w : b.counters[SELF_ADJUST_BUBBLE - 1])
        {
            System.out.println("\t" + w.getKey() + "\t" + w.getValue());
            if (++counter == 100) break;
        }
    }
}
