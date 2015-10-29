package edu.utoledo.nlowe.WordCount.WordCounters;

import edu.utoledo.nlowe.WordCount.Word;
import edu.utoledo.nlowe.WordCount.WordCounter;

import java.util.Iterator;

/**
 * A word counter in which more frequently encountered words "bubble-up" to the
 * top of the list one node at a time. New words are added to the front of the list.
 */
public class BubbleSelfAdjustingWordCounter extends WordCounter
{



    /**
     * An internal double-linked node to build the structure of the word list
     */
    private class Node
    {

        private Node previousLink;
        private Node link;

        private final Word word;

        public Node(Word word)
        {
            this.word = word;
        }

        public Word getWord()
        {
            return word;
        }

        public void linkBackwards(Node target)
        {
            this.previousLink = target;
        }

        public void linkForwards(Node target)
        {
            this.link = target;
        }

        public Node parent()
        {
            return previousLink;
        }

        public Node next()
        {
            return link;
        }

    }

    /** The first element in the word list */
    private Node head;

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

                    added.linkForwards(head);
                    head.linkBackwards(added);
                    head = added;
                    referenceChanges += 3;

                    return;
                }
                else if (target.getWord().getValue().equals(word))
                {
                    // We found the word. Increment the count
                    target.getWord().increment();

                    // Move the node up one
                    // First, link the parent to the following node

                    parent.linkForwards(target.next());
                    if (target.next() != null)
                    {
                        target.next().linkBackwards(parent);
                    }

                    //Now, insert in-between the parent's parent and the parent
                    if (parent.parent() == null)
                    {
                        head = target;
                    }
                    else
                    {
                        parent.parent().linkForwards(target);
                    }
                    target.linkBackwards(parent.parent());

                    target.linkForwards(parent);
                    parent.linkBackwards(target);

                    referenceChanges += 6;
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
    public Iterator<Word> iterator()
    {
        return new Iterator<Word>()
        {
            private Node element = head;

            @Override
            public boolean hasNext()
            {
                return element != null;
            }

            @Override
            public Word next()
            {
                Word w = element.getWord();
                element = element.next();
                return w;
            }
        };
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
