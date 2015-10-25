package edu.utoledo.nlowe.WordCount.WordCounters;

import edu.utoledo.nlowe.CustomDataTypes.CustomLinkedList;
import edu.utoledo.nlowe.WordCount.Word;
import edu.utoledo.nlowe.WordCount.WordCounter;

/**
 * A word counter which moves words to the front of the list
 * each time they are encountered
 */
public class FrontSelfAdjustingWordCounter extends WordCounter
{

    /**
     * An internal data type to build our own linked list. This allows us to move
     * elements as we insert them instead of having to traverse the list a second time.
     */
    private class Node
    {
        /** The next node in the list */
        private Node link;
        /** The word that this node contains */
        private final Word value;

        public Node(Word value)
        {
            this.value = value;
        }

        /**
         * @return the Word that this list contains
         */
        public Word getWord()
        {
            return value;
        }

        /**
         * Links this node to the specified target node
         *
         * @param target the node to link to
         */
        public void linkTo(Node target)
        {
            link = target;
        }

        /**
         * @return the node immediately following this node
         */
        public Node next()
        {
            return link;
        }
    }

    /** A pointer to the start of the word linked-list */
    private Node head = null;

    private long comparisons = 0;
    private long referenceChanges = 0;

    @Override
    public void encounter(String word)
    {
        if (head == null)
        {
            // There are no words in the list. Start the list now
            head = new Node(new Word(word));
            referenceChanges++;
        }
        else if (head.getWord().getValue().equals(word))
        {
            // The word is already at the front of the list
            head.getWord().increment();
        }
        else
        {
            // The word is somewhere else in the list, or not in the list at all
            Node parent = head;
            do
            {
                Node target = parent.next();

                comparisons++;
                if (target == null)
                {
                    // The word is not in the list. Add it
                    Node added = new Node(new Word(word));

                    added.linkTo(head);
                    head = added;
                    referenceChanges++;

                    return;
                }
                else if (target.getWord().getValue().equals(word))
                {
                    // We found the word. Increment the count
                    target.getWord().increment();

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

    public CustomLinkedList<Word> getTopTenWords()
    {
        CustomLinkedList<Word> results = new CustomLinkedList<>();

        Node target = head;
        for (int i = 0; i < 10; i++)
        {
            if (target != null)
            {
                results.add(target.getWord());
                target = target.next();
            }
            else
            {
                break;
            }
        }

        return results;
    }

    @Override
    public long getWordCount()
    {
        long count = 0;

        Node element = head;
        do
        {
            count += element.getWord().getOccurrenceCount();
            element = element.next();
        } while (element != null);

        return count;
    }

    @Override
    public long getDistinctWordCount()
    {
        long count = 0;

        Node element = head;
        do
        {
            count++;
            element = element.next();
        } while (element != null);

        return count;
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
