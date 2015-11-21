package edu.utoledo.nlowe.Sorting.DeltaGenerator;

import edu.utoledo.nlowe.Sorting.ShellSortDeltaGenerator;

/**
 * A shell sort delta generator that generates the Hibbard Sequence:
 * <p>
 * The first delta will be calculated as follows:
 * <ul>
 *  <li>Start at 1</li>
 *  <li>Double the delta until the delta is greater than the input size</li>
 *  <li>Subtract 1 and divide by 2, discarding the remainder</li>
 * </ul>
 * <p>
 * Successive iterations are half the last delta (discarding the remainder)
 */
public class HibbardSequenceGenerator implements ShellSortDeltaGenerator
{
    @Override
    public int generateDelta(int dataSize, int lastDelta)
    {
        if (lastDelta < 0)
        {
            // Generate D0
            int delta = 1;

            do
            {
                delta *= 2;
            } while (delta < dataSize);

            return (delta - 1) / 2;
        }
        else
        {
            return lastDelta / 2;
        }
    }
}
