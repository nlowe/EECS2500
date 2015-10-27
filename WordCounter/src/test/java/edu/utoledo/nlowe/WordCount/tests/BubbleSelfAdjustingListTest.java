package edu.utoledo.nlowe.WordCount.tests;

import edu.utoledo.nlowe.CustomDataTypes.CustomLinkedList;
import edu.utoledo.nlowe.WordCount.Word;
import edu.utoledo.nlowe.WordCount.WordCounters.BubbleSelfAdjustingWordCounter;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

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

        CustomLinkedList<Word> topTen = counter.getTopWords(3);

        assertEquals(3, topTen.size());

        assertEquals("abc", topTen.getFirst().getValue());
        assertEquals("cdefg", topTen.get(1).getValue());
        assertEquals("def", topTen.getLast().getValue());

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
