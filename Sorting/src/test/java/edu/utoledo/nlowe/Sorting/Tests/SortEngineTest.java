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

    @Test
    public void insertionSortValid()
    {
        Integer[] data = Benchmarks.generate(100);

        SortEngine.insertionSort(data);

        assertTrue(SortEngine.isSorted(data));
    }

    @Test
    public void insertionSortValidPerfectlySorted()
    {
        Integer[] data = {-4,-3,-2,-1,0,1,2,3,4,5};

        SortResult result = SortEngine.insertionSort(data);

        assertTrue(SortEngine.isSorted(data));
        assertEquals(0, result.swaps);
    }

    @Test
    public void insertionSortValidPerfectlyUnSorted()
    {
        Integer[] data = {5,4,3,2,1,0,-1,-2,-3,-4};

        SortEngine.insertionSort(data);

        assertTrue(SortEngine.isSorted(data));
    }

    @Test
    public void selectionSortValid()
    {
        Integer[] data = Benchmarks.generate(100);

        SortEngine.selectionSort(data);

        assertTrue(SortEngine.isSorted(data));
    }

    @Test
    public void selectionSortValidPerfectlySorted()
    {
        Integer[] data = {-4,-3,-2,-1,0,1,2,3,4,5};

        SortResult result = SortEngine.selectionSort(data);

        assertTrue(SortEngine.isSorted(data));
        assertEquals(0, result.swaps);
    }

    @Test
    public void selectionSortValidPerfectlyUnSorted()
    {
        Integer[] data = {5,4,3,2,1,0,-1,-2,-3,-4};

        SortEngine.selectionSort(data);

        assertTrue(SortEngine.isSorted(data));
    }

    @Test
    public void bubbleSortValid()
    {
        Integer[] data = Benchmarks.generate(100);

        SortEngine.bubbleSort(data);

        assertTrue(SortEngine.isSorted(data));
    }

    @Test
    public void bubbleSortValidPerfectlySorted()
    {
        Integer[] data = {-4,-3,-2,-1,0,1,2,3,4,5};

        SortResult result = SortEngine.bubbleSort(data);

        assertTrue(SortEngine.isSorted(data));
        assertEquals(0, result.swaps);
    }

    @Test
    public void bubbleSortValidPerfectlyUnSorted()
    {
        Integer[] data = {5,4,3,2,1,0,-1,-2,-3,-4};

        SortEngine.bubbleSort(data);

        assertTrue(SortEngine.isSorted(data));
    }

    @Test
    public void quickSortValid()
    {
        Integer[] data = Benchmarks.generate(100);

        SortEngine.quickSort(data);

        assertTrue(SortEngine.isSorted(data));
    }

    @Test
    public void quickSortValidPerfectlySorted()
    {
        Integer[] data = {-4,-3,-2,-1,0,1,2,3,4,5};

        SortResult result = SortEngine.quickSort(data);

        assertTrue(SortEngine.isSorted(data));
        assertEquals(0, result.swaps);
    }

    @Test
    public void quickSortValidPerfectlyUnSorted()
    {
        Integer[] data = {5,4,3,2,1,0,-1,-2,-3,-4};

        SortEngine.quickSort(data);

        assertTrue(SortEngine.isSorted(data));
    }
}
