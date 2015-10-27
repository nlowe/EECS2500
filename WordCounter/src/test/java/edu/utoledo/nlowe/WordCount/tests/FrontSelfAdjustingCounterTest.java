package edu.utoledo.nlowe.WordCount.tests;

import edu.utoledo.nlowe.WordCount.Word;
import edu.utoledo.nlowe.WordCount.WordCounters.FrontSelfAdjustingWordCounter;
import org.junit.Test;

import java.util.Arrays;

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

        assertEquals("def", counter.getTopWords(10).getFirst().getValue());

        counter.encounter("abc");
        assertEquals("abc", counter.getTopWords(10).getFirst().getValue());

        counter.encounter("abc");
        assertEquals("abc", counter.getTopWords(10).getFirst().getValue());

        counter.encounter("hjklm");
        assertEquals("hjklm", counter.getTopWords(10).getFirst().getValue());

        assertEquals(5, counter.getWordCount());
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

        FrontSelfAdjustingWordCounter counter = new FrontSelfAdjustingWordCounter();
        Arrays.stream(words).forEach((w) -> counter.encounter(Word.sanitize(w)));

        assertEquals(25, counter.getWordCount());
        assertEquals(15, counter.getDistinctWordCount());
    }

}
