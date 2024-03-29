package edu.utoledo.nlowe.WordCount.tests;

import edu.utoledo.nlowe.WordCount.Word;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for edu.utoledo.nlowe.WordCount.Word
 */
public class WordTest
{

    @Test
    public void canCorrectlySanitize()
    {
        assertEquals("castle", Word.sanitize("Castle"));
        assertEquals("castle", Word.sanitize("castle"));
        assertEquals("castle", Word.sanitize("Castle?"));
        assertEquals("castle", Word.sanitize("castle?'"));
        assertEquals("ab-cd", Word.sanitize("ab-cd"));
        assertEquals("ab-cd", Word.sanitize("ab-cd'"));
        assertEquals("abc--def", Word.sanitize("\",.?abc--def"));
    }

    @Test
    public void correctlyCompares()
    {
        Word a = new Word("AbZd");
        Word b = new Word("wxyz");

        assertEquals("AbZd".compareTo("wxyz"), a.compareTo(b));
        assertEquals("wxyz".compareTo("AbZd"), b.compareTo(a));

        a.increment();
        b.increment();
        b.increment();

        assertEquals("AbZd".compareTo("wxyz"), a.compareTo(b));
        assertEquals("wxyz".compareTo("AbZd"), b.compareTo(a));

        assertEquals(0, a.compareTo(a));
        assertEquals(0, b.compareTo(b));
        assertEquals(1, a.compareTo(null));
    }

    @Test
    public void correctlyDetectsEquality()
    {
        Word a = new Word("abcd");
        Word b = new Word("abcd");

        assertEquals(a, b);

        a.increment();
        b.increment();
        b.increment();

        assertTrue(a.equals(b));
    }

    @Test
    public void keepsTrackOfCount()
    {
        Word a = new Word("abcd");

        assertEquals((Integer)1, a.getValue());

        a.increment();
        a.increment();

        assertEquals((Integer)3, a.getValue());
    }

}
