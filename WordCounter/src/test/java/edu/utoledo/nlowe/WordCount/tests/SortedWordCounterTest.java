package edu.utoledo.nlowe.WordCount.tests;

import edu.utoledo.nlowe.WordCount.Word;
import edu.utoledo.nlowe.WordCount.WordCounters.SortedWordCounter;
import org.junit.Test;

import java.util.Arrays;

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

        assertEquals("abc", counter.iterator().next().getKey());

        counter.encounter("aabb");
        assertEquals("aabb", counter.iterator().next().getKey());

        counter.encounter("abc");
        assertEquals("aabb", counter.iterator().next().getKey());

        assertEquals(4, counter.getWordCount());
        assertEquals(3, counter.getDistinctWordCount());
    }

    @Test
    public void wordCountTestTwo()
    {
        String[] words = new String[]{
                "a", "b", "c", "d", "e",
                "a", "b", "c", "d", "e",
                "a?", "b!", "c-", "d\"", "e'",
                "ab", "cd", "ef", "gh", "ij",
                "a-b", "c-d", "e-f", "g-h", "i-j"
        };

        SortedWordCounter counter = new SortedWordCounter();
        Arrays.stream(words).forEach((w) -> counter.encounter(Word.sanitize(w)));

        assertEquals(25, counter.getWordCount());
        assertEquals(15, counter.getDistinctWordCount());
    }


}
