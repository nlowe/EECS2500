package edu.utoledo.nlowe.WordCount.tests;

import edu.utoledo.nlowe.CustomDataTypes.CustomLinkedList;
import edu.utoledo.nlowe.WordCount.Word;
import edu.utoledo.nlowe.WordCount.WordCounters.BubbleSelfAdjustingWordCounter;
import org.junit.Test;

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

        CustomLinkedList<Word> topTen = counter.getTopTenWords();

        assertEquals(3, topTen.size());

        assertEquals("abc", topTen.getFirst().getValue());
        assertEquals("cdefg", topTen.get(1).getValue());
        assertEquals("def", topTen.getLast().getValue());

        assertEquals(8, counter.getWordCount());
        assertEquals(3, counter.getDistinctWordCount());
    }

}
