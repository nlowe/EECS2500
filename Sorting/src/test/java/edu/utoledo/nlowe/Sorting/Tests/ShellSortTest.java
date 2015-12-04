package edu.utoledo.nlowe.Sorting.Tests;

import edu.utoledo.nlowe.Sorting.DeltaGenerator.HibbardSequenceGenerator;
import edu.utoledo.nlowe.Sorting.DeltaGenerator.KnuthSequenceGenerator;
import edu.utoledo.nlowe.Sorting.DeltaGenerator.PrattSequenceGenerator;
import edu.utoledo.nlowe.Sorting.Samples.Benchmarks;
import edu.utoledo.nlowe.Sorting.SortEngine;
import edu.utoledo.nlowe.Sorting.SortResult;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for Shell Sort
 */
public class ShellSortTest
{

    @Test
    public void hibbardSequenceValid()
    {
        Integer[] data = Benchmarks.generate(100);

        SortEngine.shellSort(data, new HibbardSequenceGenerator());

        assertTrue(SortEngine.isSorted(data));
    }

    @Test
    public void hibbardSequenceValidPerfectlySorted()
    {
        Integer[] data = {-4, -3, -2, -1, 0, 1, 2, 3, 4, 5};

        SortResult result = SortEngine.shellSort(data, new HibbardSequenceGenerator());

        assertTrue(SortEngine.isSorted(data));
        assertEquals(0, result.swaps);
    }

    @Test
    public void hibbardSequenceValidPerfectlyUnSorted()
    {
        Integer[] data = {5, 4, 3, 2, 1, 0, -1, -2, -3, -4};

        SortEngine.shellSort(data, new HibbardSequenceGenerator());

        assertTrue(SortEngine.isSorted(data));
    }

    @Test
    public void knuthSequenceValid()
    {
        Integer[] data = Benchmarks.generate(100);

        SortEngine.shellSort(data, new KnuthSequenceGenerator());

        assertTrue(SortEngine.isSorted(data));
    }

    @Test
    public void knuthSequenceValidPerfectlySorted()
    {
        Integer[] data = {-4, -3, -2, -1, 0, 1, 2, 3, 4, 5};

        SortResult result = SortEngine.shellSort(data, new KnuthSequenceGenerator());

        assertTrue(SortEngine.isSorted(data));
        assertEquals(0, result.swaps);
    }

    @Test
    public void knuthSequenceValidPerfectlyUnSorted()
    {
        Integer[] data = {5, 4, 3, 2, 1, 0, -1, -2, -3, -4};

        SortEngine.shellSort(data, new KnuthSequenceGenerator());

        assertTrue(SortEngine.isSorted(data));
    }

    @Test
    public void prattSequenceValid()
    {
        Integer[] data = Benchmarks.generate(100);

        SortEngine.shellSort(data, new PrattSequenceGenerator());

        assertTrue(SortEngine.isSorted(data));
    }

    @Test
    public void prattSequenceValidPerfectlySorted()
    {
        Integer[] data = {-4, -3, -2, -1, 0, 1, 2, 3, 4, 5};

        SortResult result = SortEngine.shellSort(data, new PrattSequenceGenerator());

        assertTrue(SortEngine.isSorted(data));
        assertEquals(0, result.swaps);
    }

    @Test
    public void prattSequenceValidPerfectlyUnSorted()
    {
        Integer[] data = {5, 4, 3, 2, 1, 0, -1, -2, -3, -4};

        SortEngine.shellSort(data, new PrattSequenceGenerator());

        assertTrue(SortEngine.isSorted(data));
    }
}
