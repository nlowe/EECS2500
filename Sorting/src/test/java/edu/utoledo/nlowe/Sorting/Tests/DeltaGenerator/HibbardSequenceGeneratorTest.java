package edu.utoledo.nlowe.Sorting.Tests.DeltaGenerator;

import edu.utoledo.nlowe.Sorting.DeltaGenerator.HibbardSequenceGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the HibbardSequenceGenerator
 */
public class HibbardSequenceGeneratorTest
{
    private HibbardSequenceGenerator generator;

    @Before
    public void setUp()
    {
        generator = new HibbardSequenceGenerator();
    }

    @Test
    public void hibbardValid()
    {
        assertEquals(511, generator.generateDelta(1000, -1));
        assertEquals(255, generator.generateDelta(1000, 511));
        assertEquals(127, generator.generateDelta(1000, 255));
        assertEquals(63, generator.generateDelta(1000, 127));
        assertEquals(31, generator.generateDelta(1000, 63));
        assertEquals(15, generator.generateDelta(1000, 31));
        assertEquals(7, generator.generateDelta(1000, 15));
        assertEquals(3, generator.generateDelta(1000, 7));
        assertEquals(1, generator.generateDelta(1000, 3));
        assertEquals(0, generator.generateDelta(1000, 1));
        assertEquals(0, generator.generateDelta(1000, 0));
    }

}
