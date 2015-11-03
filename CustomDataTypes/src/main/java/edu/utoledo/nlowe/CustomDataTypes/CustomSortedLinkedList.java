package edu.utoledo.nlowe.CustomDataTypes;


import java.util.function.Consumer;

/**
 * A Custom, Generic, Sorted Linked List implementation.
 * <p>
 * Please note that <code>contains(T target)</code> is still O(n) since arbitrary
 * access is still linear.
 */
public class CustomSortedLinkedList<T extends Comparable<T>> extends CustomLinkedList<T>
{

    @Override
    public void add(T value)
    {
        this.addOr(value, null);
    }

    public void addOr(T value, Consumer<T> ifFound)
    {
        Node<T> toInsert = new Node<>(value);

        // Assume we're inserting and making a comparison, fix later
        size++;
        comparisonCount++;

        int headComparison = head != null ? head.getValue().compareTo(value) : -1;

        if (head == null)
        {
            comparisonCount--;
            head = tail = toInsert;
            referenceChangeCount += 2;
        }
        else if (headComparison == 0)
        {
            size--;
            if (ifFound != null) ifFound.accept(head.getValue());
        }
        else if (headComparison > 0)
        {
            toInsert.linkTo(head);
            head = toInsert;
            referenceChangeCount += 2;
        }
        else
        {
            Node<T> ref = head;
            while (ref.next() != null)
            {
                comparisonCount++;

                int comparison = ref.next().getValue().compareTo(value);
                if (comparison > 0)
                {
                    toInsert.linkTo(ref.next());
                    ref.linkTo(toInsert);
                    referenceChangeCount += 2;
                    return;
                }
                else if (comparison == 0)
                {
                    size--;
                    if (ifFound != null) ifFound.accept(ref.next().getValue());
                    return;
                }

                ref = ref.next();
            }

            tail.linkTo(toInsert);
            tail = toInsert;
            referenceChangeCount += 2;
        }
    }

    @Override
    public void add(int index, T value) throws IndexOutOfBoundsException
    {
        throw new IllegalStateException("Cannot insert arbitrarily into sorted list");
    }

    @Override
    public void set(int index, T value) throws IndexOutOfBoundsException
    {
        throw new IllegalStateException("Cannot change values in a sorted list. Remove and re-add instead");
    }
}
