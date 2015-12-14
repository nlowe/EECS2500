package edu.utoledo.nlowe.WordCount.tests;

import edu.utoledo.nlowe.WordCount.Word;
import edu.utoledo.nlowe.WordCount.WordCounters.BinarySearchTreeWordCounter;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the <code>BinarySearchTreeWordCounter</code>
 */
public class BinarySearchTreeWordCounterTest
{
    @Test
    public void sortsWords()
    {
        BinarySearchTreeWordCounter counter = new BinarySearchTreeWordCounter();

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

        BinarySearchTreeWordCounter counter = new BinarySearchTreeWordCounter();
        Arrays.stream(words).forEach((w) -> counter.encounter(Word.sanitize(w)));

        assertEquals(25, counter.getWordCount());
        assertEquals(15, counter.getDistinctWordCount());
    }
}
