package edu.utoledo.nlowe.Sorting.Tests.DeltaGenerator;

import edu.utoledo.nlowe.Sorting.DeltaGenerator.KnuthSequenceGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the Knuth Sequence
 */
public class KnuthSequenceGeneratorTest
{
    private KnuthSequenceGenerator generator;

    @Before
    public void setUp()
    {
        generator = new KnuthSequenceGenerator();
    }

    @Test
    public void knuthValid()
    {
        assertEquals(364, generator.generateDelta(1000, -1));
        assertEquals(121, generator.generateDelta(1000, 364));
        assertEquals(40, generator.generateDelta(1000, 121));
        assertEquals(13, generator.generateDelta(1000, 40));
        assertEquals(4, generator.generateDelta(1000, 13));
        assertEquals(1, generator.generateDelta(1000, 4));
        assertEquals(0, generator.generateDelta(1000, 1));
        assertEquals(0, generator.generateDelta(1000, 0));
    }
}
