package edu.utoledo.nlowe.WordCount;

import edu.utoledo.nlowe.CustomDataTypes.CustomSortedLinkedList;

/**
 * Created by nathan on 10/24/15
 */
public class SortedWordCounter extends WordCounter
{
    CustomSortedLinkedList<Word> words = new CustomSortedLinkedList<>();

    @Override
    public void encounter(String word)
    {
        for(Word w : words){
            if(w.getValue().equals(word)){
                w.increment();
                return;
            }
        }

        words.add(new Word(word));
    }

    @Override
    public long getWordCount()
    {
        long count = 0;

        for(Word w : words){
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
    public long getReferenceChangeCount()
    {
        return words.getReferenceAssignmentCount();
    }
}
