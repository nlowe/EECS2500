package edu.utoledo.nlowe.WordCount.WordCounters;

import edu.utoledo.nlowe.CustomDataTypes.CustomSortedLinkedList;
import edu.utoledo.nlowe.WordCount.Word;
import edu.utoledo.nlowe.WordCount.WordCounter;

/**
 * A word counter whose underlying data type is sorted alphabetically
 */
public class SortedWordCounter extends WordCounter
{
    CustomSortedLinkedList<Word> words = new CustomSortedLinkedList<>();

    @Override
    public void encounter(String word)
    {
        words.addOr(new Word(word), Word::increment);
    }

    public Word getFirstWord()
    {
        return words.getFirst();
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
        return words.getComparisonCount();
    }

    @Override
    public long getReferenceAssignmentCount()
    {
        return words.getReferenceAssignmentCount();
    }
}
