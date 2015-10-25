package edu.utoledo.nlowe.WordCount.tests;

import edu.utoledo.nlowe.WordCount.Word;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the Word class
 */
public class WordTests
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
    }

    @Test
    public void correctlyCompares(){
        Word a = new Word("AbZd");
        Word b = new Word("wxyz");

        assertEquals("AbZd".compareTo("wxyz"), a.compareTo(b));

        a.increment();
        b.increment();
        b.increment();

        assertEquals("AbZd".compareTo("wxyz"), a.compareTo(b));

        assertEquals(1, a.compareTo(null));
    }

    @Test
    public void correctlyDetectsEquality(){
        Word a = new Word("abcd");
        Word b = new Word("abcd");

        assertEquals(a, b);

        a.increment();
        b.increment();
        b.increment();

        assertEquals(a, b);
    }

    @Test
    public void keepsTrackOfCount()
    {
        Word a = new Word("abcd");

        assertEquals(1, a.getOccurrenceCount());

        a.increment();
        a.increment();

        assertEquals(3, a.getOccurrenceCount());
    }

}
