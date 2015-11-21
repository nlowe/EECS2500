package edu.utoledo.nlowe.Sorting;

/**
 * A functional interface for generating delta values for Shell Sort
 */
public interface ShellSortDeltaGenerator
{
    /**
     * Generate the next delta value for shell sort.
     *
     * The following properties should hold:
     * <ul>
     *     <li>Successive calls with the same data size and lastDelta should return the same delta</li>
     *     <li>if lastDelta is -1, there was no previous delta and the first delta value should be returned</li>
     *     <li>the returned delta should be less than the last delta</li>
     *     <li>the returned delta should not be larger than the size of the input data</li>
     *     <li>The generated sequence eventually collapses to 0, indicating that the list should now be sorted</li>
     * </ul>
     *
     * The behavior of the generator is undefined if lastDelta was returned some <code>n</code> iterations prior.
     * This will never happen in our implementation of shell sort
     *
     * @param dataSize The size of the input data being sorted
     * @param lastDelta The size of the last delta returned by the generator
     * @return The next delta in the sequence
     */
    int generateDelta(int dataSize, int lastDelta);
}
