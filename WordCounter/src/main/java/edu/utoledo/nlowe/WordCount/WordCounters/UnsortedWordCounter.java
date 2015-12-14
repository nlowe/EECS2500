package edu.utoledo.nlowe.WordCount.WordCounters;

import edu.utoledo.nlowe.CustomDataTypes.CustomLinkedList;
import edu.utoledo.nlowe.WordCount.Word;
import edu.utoledo.nlowe.WordCount.WordCounter;

import java.util.Iterator;

/**
 * A Word Counter implementation that simply inserts new words
 * at the start of a list
 */
public class UnsortedWordCounter extends WordCounter
{

    /** All encountered words are collected in this list */
    private CustomLinkedList<Word> words = new CustomLinkedList<>();

    /** The number of comparisons made during the lifetime of this class */
    private long comparisons = 0;

    @Override
    public void encounter(String word)
    {
        for (Word w : words)
        {
            comparisons++;

            if (w.getKey().equals(word))
            {
                // We found the word. Increment the word count and exit early
                w.increment();
                return;
            }
        }

        // The word was not found, add it to the list
        words.add(0, new Word(word));
    }

    @Override
    public long getWordCount()
    {
        long count = 0;

        for (Word w : words)
        {
            count += w.getValue();
        }

        return count;
    }

    @Override
    public long getDistinctWordCount()
    {
        return words.size();
    }

    @Override
    public long getComparisonCount()
    {
        return comparisons + words.getComparisonCount();
    }

    @Override
    public long getReferenceAssignmentCount()
    {
        return words.getReferenceAssignmentCount();
    }

    @Override
    public Iterator<Word> iterator()
    {
        return words.iterator();
    }
}
