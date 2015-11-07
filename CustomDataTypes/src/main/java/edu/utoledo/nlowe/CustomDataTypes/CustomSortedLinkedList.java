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
            // There's nothing in the list
            comparisonCount--;
            head = tail = toInsert;
            referenceChangeCount += 2;
        }
        else if (headComparison == 0)
        {
            // The element we're interested in is the first thing in the list
            size--;
            if (ifFound != null) ifFound.accept(head.getValue());
        }
        else if (headComparison > 0)
        {
            // The element needs to become the first element in the sorted list
            toInsert.linkTo(head);
            head = toInsert;
            referenceChangeCount += 2;
        }
        else
        {
            // The element is not the first element, or doesn't belong at the very start of the list
            // Search for an element that matches the target, or insert it if we've passed where it
            // should "logically" be in terms of sorting order
            Node<T> ref = head;
            while (ref.next() != null)
            {
                comparisonCount++;
                int comparison = ref.next().getValue().compareTo(value);

                if (comparison > 0)
                {
                    // The element cannot possibly come after this element. Insert before it
                    toInsert.linkTo(ref.next());
                    ref.linkTo(toInsert);
                    referenceChangeCount += 2;
                    return;
                }
                else if (comparison == 0)
                {
                    // We found the element. Perform the specified action if one was provided
                    size--;
                    if (ifFound != null) ifFound.accept(ref.next().getValue());
                    return;
                }

                ref = ref.next();
            }

            // The element hasn't been inserted yet, it belongs at the tail of the list
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
