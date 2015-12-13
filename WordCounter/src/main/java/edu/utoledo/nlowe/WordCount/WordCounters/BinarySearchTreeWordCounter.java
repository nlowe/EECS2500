package edu.utoledo.nlowe.WordCount.WordCounters;

import edu.utoledo.nlowe.CustomDataTypes.BinarySearchTree;
import edu.utoledo.nlowe.WordCount.Word;
import edu.utoledo.nlowe.WordCount.WordCounter;

import java.util.Iterator;

/**
 * A word counter that places newly encountered words in a Binary Search Tree Map
 *
 * Additionally, this class's iterator returns the words in alphabetical order
 */
public class BinarySearchTreeWordCounter extends WordCounter
{
    /** All encountered words are collected in this map */
    private BinarySearchTree<Word> words = new BinarySearchTree<>();

    @Override
    public void encounter(String word)
    {
        // Add the word to the tree, or increment its count if it's already in the tree
        words.addOr(new Word(word), Word::increment);
    }

    @Override
    public long getWordCount()
    {
        long count = 0;

        for(Word word : words)
        {
            count += word.getOccurrenceCount();
        }

        return count;
    }

    @Override
    public long getDistinctWordCount()
    {
        return words.getNodeCount();
    }

    @Override
    public Iterator<Word> iterator()
    {
        return words.iterator();
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
