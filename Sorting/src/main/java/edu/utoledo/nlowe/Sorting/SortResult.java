package edu.utoledo.nlowe.Sorting;

/**
 * A data object for storing statistics recorded during a sort
 */
public class SortResult
{
    /** The number of comparisons made during the sort */
    public final long comparisons;
    /** The number of swaps made during the sort */
    public final long swaps;
    /** The time in milliseconds that the sort took to complete */
    public final long time;

    protected SortResult(long comparisons, long swaps, long time)
    {
        this.comparisons = comparisons;
        this.swaps = swaps;
        this.time = time;
    }
}
