package edu.utoledo.nlowe.Sorting.Samples;

import edu.utoledo.nlowe.CustomDataTypes.CustomLinkedList;
import edu.utoledo.nlowe.Sorting.SortEngine;
import edu.utoledo.nlowe.Sorting.SortResult;

import java.util.Random;

/**
 * A minimal application for benchmarking the various sorting algorithms implemented
 * in <code>SortEngine</code>
 */
public class Benchmarks
{
    public static final int INITIAL_SIZE = 10;
    public static final int ROUNDS = 5;

    public static final int RANDOM_LOWER_BOUND = 0;
    public static final int RANDOM_UPPER_BOUND = 9_999_999;


    public static void main(String[] args)
    {
        SortEngine<Integer> sorter = new SortEngine<>();

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
