package edu.utoledo.nlowe.WordCount.tests;

import edu.utoledo.nlowe.CustomDataTypes.CustomLinkedList;
import edu.utoledo.nlowe.WordCount.Word;
import edu.utoledo.nlowe.WordCount.WordCounters.BubbleSelfAdjustingWordCounter;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests for edu.utoledo.nlowe.WordCount.WordCounters.BubbleSelfAdjustingWordCounter
 */
public class BubbleSelfAdjustingListTest
{

    @Test
    public void wordsBubbleUpWhenInserted()
    {
        BubbleSelfAdjustingWordCounter counter = new BubbleSelfAdjustingWordCounter();

        counter.encounter("abc");
        counter.encounter("def");
        counter.encounter("abc");
        counter.encounter("cdefg");
        counter.encounter("abc");
        counter.encounter("def");
        counter.encounter("cdefg");
        counter.encounter("abc");

        Iterator<Word> words = counter.iterator();

        Word current = words.next();
        assertEquals("abc", current.getValue());
        assertEquals(4, current.getOccurrenceCount());

        current = words.next();
        assertEquals("cdefg", current.getValue());
        assertEquals(2, current.getOccurrenceCount());

        current = words.next();
        assertEquals("def", current.getValue());
        assertEquals(2, current.getOccurrenceCount());

        assertFalse(words.hasNext());

        assertEquals(8, counter.getWordCount());
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

        BubbleSelfAdjustingWordCounter counter = new BubbleSelfAdjustingWordCounter();
        Arrays.stream(words).forEach((w) -> counter.encounter(Word.sanitize(w)));

        assertEquals(25, counter.getWordCount());
        assertEquals(15, counter.getDistinctWordCount());
    }

}
