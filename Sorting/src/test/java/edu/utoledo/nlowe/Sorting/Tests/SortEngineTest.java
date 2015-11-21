package edu.utoledo.nlowe.Sorting.Tests;

import edu.utoledo.nlowe.Sorting.Samples.Benchmarks;
import edu.utoledo.nlowe.Sorting.SortEngine;
import edu.utoledo.nlowe.Sorting.SortResult;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the Sort Engine
 */
public class SortEngineTest
{
    private SortEngine<Integer> sorter;

    @Before
    public void setUp()
    {
        sorter = new SortEngine<>();
    }

    @Test
    public void insertionSortValid()
    {
        Integer[] data = Benchmarks.generate(100);

        sorter.insertionSort(data);

        assertTrue(sorter.isSorted(data));
    }

    @Test
    public void insertionSortValidPerfectlySorted()
    {
        Integer[] data = {-4,-3,-2,-1,0,1,2,3,4,5};

        SortResult result = sorter.insertionSort(data);

        assertTrue(sorter.isSorted(data));
        assertEquals(0, result.swaps);
    }

    @Test
    public void insertionSortValidPerfectlyUnSorted()
    {
        Integer[] data = {5,4,3,2,1,0,-1,-2,-3,-4};

        sorter.insertionSort(data);

        assertTrue(sorter.isSorted(data));
    }

    @Test
    public void selectionSortValid()
    {
        Integer[] data = Benchmarks.generate(100);

        sorter.selectionSort(data);

        assertTrue(sorter.isSorted(data));
    }

    @Test
    public void selectionSortValidPerfectlySorted()
    {
        Integer[] data = {-4,-3,-2,-1,0,1,2,3,4,5};

        SortResult result = sorter.selectionSort(data);

        assertTrue(sorter.isSorted(data));
        assertEquals(0, result.swaps);
    }

    @Test
    public void selectionSortValidPerfectlyUnSorted()
    {
        Integer[] data = {5,4,3,2,1,0,-1,-2,-3,-4};

        sorter.selectionSort(data);

        assertTrue(sorter.isSorted(data));
    }

    @Test
    public void bubbleSortValid()
    {
        Integer[] data = Benchmarks.generate(100);

        sorter.bubbleSort(data);

        assertTrue(sorter.isSorted(data));
    }

    @Test
    public void bubbleSortValidPerfectlySorted()
    {
        Integer[] data = {-4,-3,-2,-1,0,1,2,3,4,5};

        SortResult result = sorter.bubbleSort(data);

        assertTrue(sorter.isSorted(data));
        assertEquals(0, result.swaps);
    }

    @Test
    public void bubbleSortValidPerfectlyUnSorted()
    {
        Integer[] data = {5,4,3,2,1,0,-1,-2,-3,-4};

        sorter.bubbleSort(data);

        assertTrue(sorter.isSorted(data));
    }

    @Test
    public void quickSortValid()
    {
        Integer[] data = Benchmarks.generate(100);

        sorter.quickSort(data);

        assertTrue(sorter.isSorted(data));
    }

    @Test
    public void quickSortValidPerfectlySorted()
    {
        Integer[] data = {-4,-3,-2,-1,0,1,2,3,4,5};

        SortResult result = sorter.quickSort(data);

        assertTrue(sorter.isSorted(data));
        assertEquals(0, result.swaps);
    }

    @Test
    public void quickSortValidPerfectlyUnSorted()
    {
        Integer[] data = {5,4,3,2,1,0,-1,-2,-3,-4};

        sorter.quickSort(data);

        assertTrue(sorter.isSorted(data));
    }
}
