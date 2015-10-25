package edu.utoledo.nlowe.WordCount.tests;

import edu.utoledo.nlowe.WordCount.WordCounters.FrontSelfAdjustingWordCounter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for edu.utoledo.nlowe.WordCount.WordCounters.FrontSelfAdjustingWordCounter
 */
public class FrontSelfAdjustingCounterTest
{

    @Test
    public void putsWordsAtFront()
    {
        FrontSelfAdjustingWordCounter counter = new FrontSelfAdjustingWordCounter();

        counter.encounter("abc");
        counter.encounter("def");

        assertEquals("def", counter.getTopTenWords().getFirst().getValue());

        counter.encounter("abc");
        assertEquals("abc", counter.getTopTenWords().getFirst().getValue());

        counter.encounter("abc");
        assertEquals("abc", counter.getTopTenWords().getFirst().getValue());

        counter.encounter("hjklm");
        assertEquals("hjklm", counter.getTopTenWords().getFirst().getValue());

        assertEquals(5, counter.getWordCount());
        assertEquals(3, counter.getDistinctWordCount());
    }

}
