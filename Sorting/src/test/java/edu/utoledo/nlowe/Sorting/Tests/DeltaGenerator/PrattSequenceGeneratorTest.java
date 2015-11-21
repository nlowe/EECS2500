package edu.utoledo.nlowe.Sorting.Tests.DeltaGenerator;

import edu.utoledo.nlowe.Sorting.DeltaGenerator.PrattSequenceGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the Pratt sequence
 */
public class PrattSequenceGeneratorTest
{

    private PrattSequenceGenerator generator;

    @Before
    public void setUp()
    {
        generator = new PrattSequenceGenerator();
    }

    @Test
    public void prattValid()
    {
        assertEquals(96, generator.generateDelta(100, -1));
        assertEquals(81, generator.generateDelta(100, 96));
        assertEquals(72, generator.generateDelta(100, 81));
        assertEquals(64, generator.generateDelta(100, 72));
        assertEquals(54, generator.generateDelta(100, 64));
        assertEquals(48, generator.generateDelta(100, 54));
        assertEquals(36, generator.generateDelta(100, 48));
        assertEquals(32, generator.generateDelta(100, 36));
        assertEquals(27, generator.generateDelta(100, 32));
        assertEquals(24, generator.generateDelta(100, 27));
        assertEquals(24, generator.generateDelta(100, 27));
        assertEquals(18, generator.generateDelta(100, 24));
        assertEquals(16, generator.generateDelta(100, 18));
        assertEquals(12, generator.generateDelta(100, 16));
        assertEquals(9, generator.generateDelta(100, 12));
        assertEquals(8, generator.generateDelta(100, 9));
        assertEquals(6, generator.generateDelta(100, 8));
        assertEquals(4, generator.generateDelta(100, 6));
        assertEquals(3, generator.generateDelta(100, 4));
        assertEquals(2, generator.generateDelta(100, 3));
        assertEquals(1, generator.generateDelta(100, 2));
        assertEquals(0, generator.generateDelta(100, 1));
        assertEquals(0, generator.generateDelta(100, 0));

    }
}
