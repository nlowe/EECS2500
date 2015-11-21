package edu.utoledo.nlowe.Sorting;

/**
 * A data object for storing statistics recorded during a sort
 */
public class SortResult
{
    /** The number of comparisons made during the sort */
    public final int comparisons;
    /** The number of swaps made during the sort */
    public final int swaps;
    /** The time in milliseconds that the sort took to complete */
    public final long Time;

    protected SortResult(int comparisons, int swaps, long time)
    {
        this.comparisons = comparisons;
        this.swaps = swaps;
        this.Time = time;
    }
}
