package edu.utoledo.nlowe.WordCount.WordCounters;

import edu.utoledo.nlowe.CustomDataTypes.CustomLinkedList;
import edu.utoledo.nlowe.WordCount.Word;
import edu.utoledo.nlowe.WordCount.WordCounter;

/**
 * A Word Counter implementation that simply inserts new words
 * at the start of a list
 */
public class UnsortedWordCounter extends WordCounter
{

    private CustomLinkedList<Word> words = new CustomLinkedList<>();

    private int comparisons = 0;

    @Override
    public void encounter(String word)
    {
        for (Word w : words)
        {
            comparisons++;

            if (w.getValue().equals(word))
            {
                w.increment();
                return;
            }
        }

        words.add(0, new Word(word));
    }

    @Override
    public long getWordCount()
    {
        long count = 0;

        for (Word w : words)
        {
            count += w.getOccurrenceCount();
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
}
