package edu.utoledo.nlowe.Sorting;

/**
 * A compilation of various sorting algorithms
 */
public class SortEngine<T extends Comparable<T>>
{

    /**
     * Performs an in-place insertion sort on <code>data</code>
     *
     * @param data The data to sort
     * @return a SortResult with statistics about the operation
     */
    public SortResult insertionSort(T[] data)
    {
        long start = System.currentTimeMillis();

        long comparisons = 0;
        long swaps = 0;

        for (int offset = 1; offset < data.length; offset++)
        {
            // Place the next thing outside the sorted partition in the correct spot within the sorted partition
            for (int index = offset; index > 0; index--)
            {
                comparisons++;
                if (data[index].compareTo(data[index - 1]) < 0)
                {
                    swaps++;
                    swap(data, index, index - 1);
                }
                else
                {
                    break;
                }
            }
        }

        return new SortResult(comparisons, swaps, System.currentTimeMillis() - start);
    }

    /**
     * Performs an in-place selection sort on <code>data</code>
     *
     * @param data The data to sort
     * @return a SortResult with statistics about the operation
     */
    public SortResult selectionSort(T[] data)
    {
        long start = System.currentTimeMillis();

        long comparisons = 0;
        long swaps = 0;

        // Cache the end index so we don't have to compute it on each iteration
        int endIndex = data.length - 1;

        for (int offset = 0; offset < endIndex; offset++)
        {
            int minIndex = offset;

            // Look for the index of the 'smallest' element
            for (int i = offset + 1; i < endIndex + 1; i++)
            {
                comparisons++;
                if (data[i].compareTo(data[minIndex]) < 0)
                {
                    minIndex = i;
                }
            }

            // Swap the current element with the 'smallest' element if it needs swapped
            if (offset != minIndex)
            {
                swaps++;
                swap(data, offset, minIndex);
            }
        }

        return new SortResult(comparisons, swaps, System.currentTimeMillis() - start);
    }

    /**
     * Performs an in-place bubble sort on <code>data</code>
     *
     * @param data The data to sort
     * @return a SortResult with statistics about the operation
     */
    public SortResult bubbleSort(T[] data)
    {
        long start = System.currentTimeMillis();

        long comparisons = 0;
        long swaps = 0;

        // Cache the end index so we don't have to compute it on each iteration
        int endIndex = data.length - 1;

        for (int offset = 0; offset < endIndex; offset++)
        {
            // Walk backwards from the end up to the sorted offset, swapping lower items up one index
            for (int index = endIndex; index > offset; index--)
            {
                comparisons++;
                if (data[index].compareTo(data[index - 1]) < 0)
                {
                    swaps++;
                    swap(data, index, index - 1);
                }
            }
        }

        return new SortResult(comparisons, swaps, System.currentTimeMillis() - start);
    }

    /**
     * Performs an in-place quick sort on <code>data</code>
     *
     * @param data The data to sort
     * @return a SortResult with statistics about the operation
     */
    public SortResult quickSort(T[] data)
    {
        return quickSortIteration(data, 0, data.length - 1);
    }

    /**
     * Performs an in-place quick sort on <code>data</code> from <code>startIndex</code>
     * to <code>endIndex</code>, recursively sorting smaller and smaller partitions.
     *
     * @param data The data to sort
     * @return a SortResult with statistics about the operation and all recursive operations made
     */
    private SortResult quickSortIteration(T[] data, int startIndex, int endIndex)
    {
        long start = System.currentTimeMillis();

        long comparisons = 0;
        long swaps = 0;

        if (startIndex < endIndex)
        {
            int leftBound = startIndex;
            int rightBound = endIndex + 1;

            // For simplicity, assume the value we're pivoting around is the first element in the sub array
            T pivot = data[startIndex];

            do
            {
                // Find an item on the lower half to swap
                do comparisons++; while (++leftBound < data.length && data[leftBound].compareTo(pivot) < 0);
                // Find an item on the upper half to swap
                do comparisons++; while (data[--rightBound].compareTo(pivot) > 0);

                // If the pointers have not crossed, swap the elements at the pointers
                if (leftBound < rightBound)
                {
                    swaps++;
                    swap(data, leftBound, rightBound);
                }
                else
                {
                    // If they have, we're done 'partitioning' the sub-array
                    break;
                }
            } while (true);

            // rightBound is now at the index the pivot value should be placed at
            // Place the pivot at the correct place in the array
            swap(data, startIndex, rightBound);

            // Try to quicksort the lower half
            SortResult leftPartial = quickSortIteration(data, startIndex, rightBound - 1);
            swaps += leftPartial.swaps;
            comparisons += leftPartial.comparisons;

            // Try to quicksort the upper half
            SortResult rightPartial = quickSortIteration(data, rightBound + 1, endIndex);
            swaps += rightPartial.swaps;
            comparisons += rightPartial.comparisons;
        }

        return new SortResult(comparisons, swaps, System.currentTimeMillis() - start);
    }

    public SortResult shellSort(T[] data, ShellSortDeltaGenerator partitionGenerator)
    {
        long start = System.currentTimeMillis();

        long comparisons = 0;
        long swaps = 0;

        int delta = partitionGenerator.generateDelta(data.length, -1);

        do
        {
            for (int i = 0; i < (data.length - delta); i++)
            {
                for (int j = i; j >= 0; j -= delta)
                {
                    if (data[j].compareTo(data[j + delta]) <= 0)
                    {
                        break;
                    }
                    else
                    {
                        swap(data, j, j + delta);
                    }
                }
            }

            // Generate the next delta size
            int newDelta = partitionGenerator.generateDelta(data.length, delta);

            assert (newDelta < delta && newDelta >= 0 && newDelta <= data.length);
            delta = newDelta;
        } while (delta > 0);

        return new SortResult(comparisons, swaps, System.currentTimeMillis() - start);
    }

    /**
     * Swap elements <code>a</code> and <code>b</code> in <code>data</code>
     *
     * @param data the element to swap elements in
     * @param a    the index of the first element
     * @param b    the index of the second element
     */
    private void swap(T[] data, int a, int b)
    {
        assert (a >= 0 && a < data.length);
        assert (b >= 0 && b < data.length);

        T tmp = data[b];
        data[b] = data[a];
        data[a] = tmp;
    }

    /**
     * Checks to see if <code>data</code> is in perfectly sorted order
     *
     * @param data The array of data to check
     * @return true iff for i from 0 to data.length-1, data[i] < data[i+1]
     */
    public boolean isSorted(T[] data)
    {
        for (int i = 0; i < data.length - 1; i++)
        {
            if (data[i].compareTo(data[i + 1]) > 0) return false;
        }

        return true;
    }

}
