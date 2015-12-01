package edu.utoledo.nlowe.Sorting.Samples;

import edu.utoledo.nlowe.Sorting.AverageSortResult;
import edu.utoledo.nlowe.Sorting.DeltaGenerator.HibbardSequenceGenerator;
import edu.utoledo.nlowe.Sorting.DeltaGenerator.KnuthSequenceGenerator;
import edu.utoledo.nlowe.Sorting.DeltaGenerator.PrattSequenceGenerator;
import edu.utoledo.nlowe.Sorting.SortEngine;
import edu.utoledo.nlowe.Sorting.SortResult;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * A minimal application for benchmarking the various sorting algorithms implemented
 * in <code>SortEngine</code>
 */
public class Benchmarks
{
    public int INITIAL_SIZE = 100;
    public int MAX_SIZE = 20_000;
    public int STEP = 100;

    public int WARMUP_ROUNDS = 30000;
    public int SLOW_ROUNDS = 10;
    public int FAST_ROUNDS = 100;

    public static int RANDOM_LOWER_BOUND = 0;
    public static int RANDOM_UPPER_BOUND = 9_999_999;

    private long TOTAL_BUBBLE = 0L;
    private long TOTAL_INSERTION = 0L;
    private long TOTAL_SELECTION = 0L;
    private long TOTAL_QUICK = 0L;
    private long TOTAL_HIBBARD = 0L;
    private long TOTAL_KNUTH = 0L;
    private long TOTAL_PRATT = 0L;

    private String outFile = null;
    private String delimiter = "\t";

    public static void main(String[] args)
    {
        Benchmarks runner = new Benchmarks();

        // Parse command line arguments, if any
        if(args.length % 2 == 0)
        {
            for(int i=0; i<args.length; i++)
            {
                if(args[i].equalsIgnoreCase("--out-file") || args[i].equalsIgnoreCase("-o"))
                {
                    runner.outFile = args[++i];
                }
                else if(args[i].equalsIgnoreCase("--delimiter") || args[i].equalsIgnoreCase("-d"))
                {
                    runner.delimiter = args[++i];
                }
                else if(args[i].equalsIgnoreCase("--gen-min") || args[i].equals("-m"))
                {
                    RANDOM_LOWER_BOUND = Integer.parseInt(args[++i]);
                }
                else if(args[i].equalsIgnoreCase("--gen-max") || args[i].equals("-M"))
                {
                    RANDOM_UPPER_BOUND = Integer.parseInt(args[++i]);
                }
                else if(args[i].equalsIgnoreCase("--initial-size") || args[i].equalsIgnoreCase("-i"))
                {
                    runner.INITIAL_SIZE = Integer.parseInt(args[++i]);
                }
                else if(args[i].equalsIgnoreCase("--max-size") || args[i].equalsIgnoreCase("-mx"))
                {
                    runner.MAX_SIZE = Integer.parseInt(args[++i]);
                }
                else if(args[i].equalsIgnoreCase("--step") || args[i].equalsIgnoreCase("-s"))
                {
                    runner.STEP = Integer.parseInt(args[++i]);
                }
                else if(args[i].equalsIgnoreCase("--slow-rounds") || args[i].equalsIgnoreCase("-r"))
                {
                    runner.SLOW_ROUNDS = Integer.parseInt(args[++i]);
                }
                else if(args[i].equalsIgnoreCase("--fast-rounds") || args[i].equalsIgnoreCase("-f"))
                {
                    runner.FAST_ROUNDS = Integer.parseInt(args[++i]);
                }
                else if(args[i].equalsIgnoreCase("--warmup") || args[i].equalsIgnoreCase("-w"))
                {
                    runner.WARMUP_ROUNDS = Integer.parseInt(args[++i]);
                }
            }
        }
        else
        {
            System.err.println("WARNING: Invalid number of arguments (each key must have a value). They will be ignored");
        }

        if(runner.FAST_ROUNDS < runner.SLOW_ROUNDS)
        {
            System.err.println("WARNING: The number of rounds for the faster sorts is lower than that for the slow sorts! The faster sorts will still run as many rounds as the slow sorts!");
        }

        try
        {
            runner.runAllBenchmarks();
        }
        catch (IOException e)
        {
            System.err.println("Encountered an error when writing benchmark results");
            e.printStackTrace();
        }
    }

    public void runAllBenchmarks() throws IOException
    {
        SortEngine<Integer> sorter = new SortEngine<>();

        HibbardSequenceGenerator hibbard = new HibbardSequenceGenerator();
        KnuthSequenceGenerator knuth = new KnuthSequenceGenerator();
        PrattSequenceGenerator pratt = new PrattSequenceGenerator();

        PrintWriter writer = null;
        if(outFile != null){
            writer = new PrintWriter(new FileWriter(outFile));
        }

        String config = getConfigString();
        System.out.println(config);

        if(writer != null)
        {
            writer.write(config + "\n");
        }

        System.out.print("Warming up...");
        
        // Try to warm-up the JVM
        long warmupStart = System.currentTimeMillis();
        for(int i = 0; i < WARMUP_ROUNDS; i++)
        {
            Integer[] data = generate(5);
            sorter.bubbleSort(data.clone());
            sorter.insertionSort(data.clone());
            sorter.selectionSort(data.clone());
            sorter.quickSort(data.clone());

            sorter.shellSort(data.clone(), hibbard);
            sorter.shellSort(data.clone(), knuth);
            sorter.shellSort(data.clone(), pratt);
        }

        System.out.println((System.currentTimeMillis() - warmupStart ) +"ms\n\nResults:\n\n");

        String headers = getHeaders();
        System.out.println(headers);

        if(writer != null)
        {
            writer.write(headers + "\n");
        }

        for(int size=INITIAL_SIZE; size<= MAX_SIZE; size += STEP)
        {
            Integer[] data = generate(size);

            ArrayList<SortResult> bubble = new ArrayList<>(SLOW_ROUNDS);
            ArrayList<SortResult> insertion = new ArrayList<>(SLOW_ROUNDS);
            ArrayList<SortResult> selection = new ArrayList<>(SLOW_ROUNDS);
            ArrayList<SortResult> quick = new ArrayList<>(FAST_ROUNDS);
            
            ArrayList<SortResult> shellHibbard = new ArrayList<>(FAST_ROUNDS);
            ArrayList<SortResult> shellKnuth = new ArrayList<>(FAST_ROUNDS);
            ArrayList<SortResult> shellPratt = new ArrayList<>(FAST_ROUNDS);

            for(int i = 0; i < SLOW_ROUNDS; i++)
            {
                SortResult bubbleResult = sorter.bubbleSort(data.clone());
                TOTAL_BUBBLE += bubbleResult.time;
                bubble.add(bubbleResult);

                SortResult insertionResult = sorter.insertionSort(data.clone());
                TOTAL_INSERTION += insertionResult.time;
                insertion.add(insertionResult);

                SortResult selectionResult = sorter.selectionSort(data.clone());
                TOTAL_SELECTION += selectionResult.time;
                selection.add(selectionResult);

                SortResult quickResult = sorter.quickSort(data.clone());
                TOTAL_QUICK += quickResult.time;
                quick.add(quickResult);

                SortResult hibbardResult = sorter.shellSort(data.clone(), hibbard);
                TOTAL_HIBBARD += hibbardResult.time;
                shellHibbard.add(hibbardResult);

                SortResult knuthResult = sorter.shellSort(data.clone(), knuth);
                TOTAL_KNUTH += knuthResult.time;
                shellKnuth.add(knuthResult);

                SortResult prattResult = sorter.shellSort(data.clone(), pratt);
                TOTAL_PRATT += prattResult.time;
                shellPratt.add(prattResult);
            }

            // The quicker sorts use more rounds to even out the performance results
            for(int i = SLOW_ROUNDS; i < FAST_ROUNDS; i++)
            {
                quick.add(sorter.quickSort(data.clone()));

                shellHibbard.add(sorter.shellSort(data.clone(), hibbard));
                shellKnuth.add(sorter.shellSort(data.clone(), knuth));
                shellPratt.add(sorter.shellSort(data.clone(), pratt));
            }

            AverageSortResult averageBubble = new AverageSortResult(bubble);
            AverageSortResult averageInsertion = new AverageSortResult(insertion);
            AverageSortResult averageSelection = new AverageSortResult(selection);
            AverageSortResult averageQuick = new AverageSortResult(quick);

            AverageSortResult averageHibbard = new AverageSortResult(shellHibbard);
            AverageSortResult averageKnuth = new AverageSortResult(shellKnuth);
            AverageSortResult averagePratt = new AverageSortResult(shellPratt);

            String results = getResultString(
                    size, averageBubble, averageInsertion,
                    averageSelection, averageQuick, averageHibbard,
                    averageKnuth, averagePratt
            );

            // Print the intermediate results
            System.out.println(results);
            if(writer != null)
            {
                writer.write(results + "\n");
                writer.flush();
            }
        }

        if(writer != null)
        {
            writer.close();
        }

        System.out.println("\n\nBenchmarks complete. Total runtime per algorithm:");
        System.out.println("\tBubble: " + TOTAL_BUBBLE + "ms");
        System.out.println("\tSelection: " + TOTAL_SELECTION + "ms");
        System.out.println("\tInsertion: " + TOTAL_INSERTION + "ms");
        System.out.println("\tQuick: " + TOTAL_QUICK + "ms");
        System.out.println("\tHibbard: " + TOTAL_HIBBARD + "ms");
        System.out.println("\tKnuth: " + TOTAL_KNUTH + "ms");
        System.out.println("\tPratt: " + TOTAL_PRATT + "ms");

    }

    private String getHeaders()
    {
        return "Data Size" + delimiter +
            "Bubble Comparisons" + delimiter + "Bubble Swaps" + delimiter + "Bubble Time" + delimiter +
            "Insertion Comparisons" + delimiter + "Insertion Swaps" + delimiter + "Insertion Time" + delimiter +
            "Selection Comparisons" + delimiter + "Selection Swaps" + delimiter + "Selection Time" + delimiter +
            "Quick Comparisons" + delimiter + "Quick Swaps" + delimiter + "Quick Time" + delimiter +
            "Hibbard Comparisons" + delimiter + "Hibbard Swaps" + delimiter + "Hibbard Time" + delimiter +
            "Knuth Comparisons" + delimiter + "Knuth Swaps" + delimiter + "Knuth Time" + delimiter +
            "Pratt Comparisons" + delimiter + "Pratt Swaps" + delimiter + "Pratt Time";
    }

    private String getResultString(
            int dataSize, AverageSortResult bubble, AverageSortResult insertion, AverageSortResult selection, AverageSortResult quick,
            AverageSortResult hibbard, AverageSortResult knuth, AverageSortResult pratt
                                  )
    {
        return dataSize + delimiter +
                bubble.comparisons + delimiter + bubble.swaps + delimiter + bubble.time + delimiter +
                insertion.comparisons + delimiter + insertion.swaps + delimiter + insertion.time + delimiter +
                selection.comparisons + delimiter + selection.swaps + delimiter + selection.time + delimiter +
                quick.comparisons + delimiter + quick.swaps + delimiter + quick.time + delimiter +
                hibbard.comparisons + delimiter + hibbard.swaps + delimiter + hibbard.time + delimiter +
                knuth.comparisons + delimiter + knuth.swaps + delimiter + knuth.time + delimiter +
                pratt.comparisons + delimiter + pratt.swaps + delimiter + pratt.time;
    }

    private String getConfigString()
    {
        return "# Configuration: \n" +
        "# \tWill generate random numbers from " + RANDOM_LOWER_BOUND + " to " + RANDOM_UPPER_BOUND + "\n" +
        "# \tData size ranges from " + INITIAL_SIZE + " to " + MAX_SIZE + " in steps of " + STEP + "\n" +
        "# \tSlower sorts will have " + SLOW_ROUNDS + " rounds to average results for each size\n" +
        "# \tFaster sorts will have " + FAST_ROUNDS + " rounds to average results for each size\n" +
        "# \tWarming up the JVM over " + WARMUP_ROUNDS + " rounds for each algorithm";
    }

    /**
     * Generate an Integer array of the specified size from a random number generator.
     * The lower and upper bounds are specified by the <code>RANDOM_LOWER_BOUND</code>
     * and <code>RANDOM_UPPER_BOUND</code> constants in this class.
     *
     * @param size the size of the integer array to generate
     * @return an Integer array of the specified size, filled with random integers from
     * <code>RANDOM_LOWER_BOUND</code> to <code>RANDOM_UPPER_BOUND</code>
     */
    public static Integer[] generate(int size)
    {
        return new Random().ints(RANDOM_LOWER_BOUND, RANDOM_UPPER_BOUND).limit(size).boxed().toArray(Integer[]::new);
    }
}
