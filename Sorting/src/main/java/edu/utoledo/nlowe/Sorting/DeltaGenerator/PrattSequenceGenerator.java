package edu.utoledo.nlowe.Sorting.DeltaGenerator;

import edu.utoledo.nlowe.Sorting.ShellSortDeltaGenerator;

import java.util.ArrayList;

/**
 * A shell sort delta generator that generates the Pratt sequence:
 * <p>
 * All values of P and Q such that 2**P * 3**Q < dataSize
 * <p>
 * Unless the data size changes, the delta values are precomputed and stored
 * <p>
 * Successive iterations are the next smallest value in the sequence
 */
public class PrattSequenceGenerator implements ShellSortDeltaGenerator
{
    private ArrayList<Integer> sequence = new ArrayList<>();

    /** The size the generator was initialized with. If this changes, the sequence will be precomputed again */
    private int initializationSize = -1;
    /** The index of the last delta returned */
    private int lastDeltaIndex = 0;

    /**
     * Precompute Pratt's sequence up to <code>max</code>
     * @param max the maximum number in the sequence
     */
    private void precompute(int max)
    {
        sequence.clear();

        // Continue adding powers of three to the sequence until we're past the max
        int pow3 = 1;
        while(pow3 <= max)
        {
            // For each power of three, add successive powers of two to the sequence until we're past the max
            int pow2 = pow3;
            while(pow2 <= max)
            {
                sequence.add(pow2);
                pow2 *= 2;
            }

            pow3 *= 3;
        }

        // Sort in descending order
        sequence.sort((a, b) -> a.compareTo(b) * -1);
    }

    @Override
    public int generateDelta(int dataSize, int lastDelta)
    {
        // Precompute the sequence if this is the first delta or the data size changed
        if(lastDelta < 0 || dataSize != initializationSize)
        {
            initializationSize = dataSize;
            lastDeltaIndex = 0;
            precompute(dataSize);
            return sequence.get(0);
        }

        if(sequence.isEmpty() || lastDeltaIndex >= sequence.size())
        {
            return 0;
        }
        else if(lastDeltaIndex > 0 && sequence.get(lastDeltaIndex-1) == lastDelta)
        {
            return sequence.get(lastDeltaIndex);
        }
        else
        {
            if(++lastDeltaIndex >= sequence.size())
            {
                return 0;
            }
            else
            {
                return sequence.get(lastDeltaIndex);
            }
        }
    }
}
