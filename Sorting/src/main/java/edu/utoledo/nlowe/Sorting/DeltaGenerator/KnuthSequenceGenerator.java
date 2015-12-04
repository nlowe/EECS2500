package edu.utoledo.nlowe.Sorting.DeltaGenerator;

import edu.utoledo.nlowe.Sorting.ShellSortDeltaGenerator;

/**
 * A shell sort delta generator that generates the Knuth sequence:
 * <p>
 * The first delta will be calculated as follows:
 * <ul>
 *  <li>Start at 1</li>
 *  <li>Triple the delta and add one until the delta is greater than the input size</li>
 *  <li>Divide by 3, discarding the remainder</li>
 * </ul>
 * <p>
 * Successive iterations are one third of the last delta (discarding the remainder)
 */
public class KnuthSequenceGenerator implements ShellSortDeltaGenerator
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
                delta *= 3;
                delta += 1;
            } while (delta < dataSize);

            return delta / 3;
        }
        else
        {
            return lastDelta / 3;
        }
    }
}
