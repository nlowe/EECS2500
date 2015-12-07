package edu.utoledo.nlowe.WordCount.WordCounters;

import edu.utoledo.nlowe.CustomDataTypes.BinarySearchTreeMap;
import edu.utoledo.nlowe.CustomDataTypes.Iterator.BinaryTreeIterator;
import edu.utoledo.nlowe.CustomDataTypes.TraversalOrder;
import edu.utoledo.nlowe.WordCount.Word;
import edu.utoledo.nlowe.WordCount.WordCounter;

import java.util.Iterator;

/**
 * Created by nathan on 12/5/15
 */
public class BinarySearchTreeWordCounter extends WordCounter
{
    private BinarySearchTreeMap<String, Long> words = new BinarySearchTreeMap<>();

    @Override
    public void encounter(String word)
    {
        words.putOr(word, 1L, (e) -> e.setValue(e.getValue()+1));
    }

    @Override
    public long getWordCount()
    {
        long count = 0;

        BinaryTreeIterator<String, Long> itr = words.traverse(TraversalOrder.INORDER);

        while(itr.hasNext())
        {
            count += itr.next().getValue();
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
        // TODO: Is there a way to "map" an iterator?
        return null;
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
