package edu.utoledo.nlowe.WordCount.tests;

import edu.utoledo.nlowe.WordCount.WordCounters.SortedWordCounter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for edu.utoledo.nlowe.WordCount.WordCounters.SortedWordCounter
 */
public class SortedWordCounterTest
{

    @Test
    public void sortsWords()
    {
        SortedWordCounter counter = new SortedWordCounter();

        counter.encounter("abc");
        counter.encounter("def");

        assertEquals("abc", counter.getFirstWord().getValue());

        counter.encounter("aabb");
        assertEquals("aabb", counter.getFirstWord().getValue());

        counter.encounter("abc");
        assertEquals("aabb", counter.getFirstWord().getValue());

        assertEquals(4, counter.getWordCount());
        assertEquals(3, counter.getDistinctWordCount());
    }

}
