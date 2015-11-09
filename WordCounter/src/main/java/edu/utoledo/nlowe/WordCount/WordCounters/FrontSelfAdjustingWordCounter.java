package edu.utoledo.nlowe.WordCount.WordCounters;

import edu.utoledo.nlowe.CustomDataTypes.Node;
import edu.utoledo.nlowe.CustomDataTypes.NodeIterator;
import edu.utoledo.nlowe.WordCount.Word;
import edu.utoledo.nlowe.WordCount.WordCounter;

import java.util.Iterator;

/**
 * A word counter which moves words to the front of the list
 * each time they are encountered
 */
public class FrontSelfAdjustingWordCounter extends WordCounter
{

    /** A pointer to the start of the word linked-list */
    private Node<Word> head = null;

    /** The number of comparisons made during the lifetime of the word counter */
    private long comparisons = 0;
    /** The number of reference changes made to the internal data structure of the word counter */
    private long referenceChanges = 0;

    @Override
    public void encounter(String word)
    {
        if (head == null)
        {
            // There are no words in the list. Start the list now
            head = new Node<>(new Word(word));
            referenceChanges++;
            return;
        }

        comparisons++;
        if (head.getValue().getValue().equals(word))
        {
            // The word is already at the front of the list
            head.getValue().increment();
        }
        else
        {
            // The word is somewhere else in the list, or not in the list at all
            Node<Word> parent = head;
            do
            {
                Node<Word> target = parent.next();

                if (target == null)
                {
                    // The word is not in the list. Add it
                    Node<Word> added = new Node<>(new Word(word));

                    added.linkTo(head);
                    head = added;
                    referenceChanges += 2;

                    return;
                }

                comparisons++;
                if (target.getValue().getValue().equals(word))
                {
                    // We found the word. Increment the count
                    target.getValue().increment();

                    // And move it to the front of the list
                    parent.linkTo(target.next());
                    target.linkTo(head);
                    head = target;

                    referenceChanges += 3;
                    return;
                }

                parent = parent.next();
            } while (parent != null);
        }
    }

    @Override
    public long getWordCount()
    {
        long count = 0;

        for(Word w : this){
            count += w.getOccurrenceCount();
        }

        return count;
    }

    @Override
    public long getDistinctWordCount()
    {
        long count = 0;

        for(Word w : this){
            count++;
        }

        return count;
    }

    @Override
    public Iterator<Word> iterator()
    {
        return new NodeIterator<>(head);
    }

    @Override
    public long getComparisonCount()
    {
        return comparisons;
    }

    @Override
    public long getReferenceAssignmentCount()
    {
        return referenceChanges;
    }
}
