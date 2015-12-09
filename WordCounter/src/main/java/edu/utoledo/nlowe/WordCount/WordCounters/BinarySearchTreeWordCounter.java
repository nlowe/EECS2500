package edu.utoledo.nlowe.WordCount.WordCounters;

import edu.utoledo.nlowe.CustomDataTypes.BinarySearchTreeMap;
import edu.utoledo.nlowe.CustomDataTypes.Iterator.BinaryTreeIterator;
import edu.utoledo.nlowe.CustomDataTypes.KeyValuePair;
import edu.utoledo.nlowe.CustomDataTypes.TraversalOrder;
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
    private BinarySearchTreeMap<String, Integer> words = new BinarySearchTreeMap<>();

    @Override
    public void encounter(String word)
    {
        // Add the word to the tree, or increment its count if it's already in the tree
        words.putOr(word, 1, (w) -> w.setValue(w.getValue()+1));
    }

    @Override
    public long getWordCount()
    {
        long count = 0;

        for(KeyValuePair<String, Integer> word : words)
        {
            count += word.getValue();
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
        // The binary tree maps return an iterator of KeyValuePair's
        // We need to map these to Words
        return new Iterator<Word>()
        {
            private final Iterator<KeyValuePair<String, Integer>> itr = words.iterator();

            @Override
            public boolean hasNext()
            {
                return itr.hasNext();
            }

            @Override
            public Word next()
            {
                KeyValuePair<String, Integer> word = itr.next();
                return new Word(word.getKey(), word.getValue());
            }
        };
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
