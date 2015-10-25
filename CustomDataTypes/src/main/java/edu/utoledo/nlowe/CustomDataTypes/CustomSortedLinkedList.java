package edu.utoledo.nlowe.CustomDataTypes;


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
        Node<T> toInsert = new Node<>(value);
        size++;
        referenceChangeCount += 2;

        if (head == null)
        {
            head = tail = toInsert;
        }
        else if (head.getValue().compareTo(value) >= 0)
        {
            toInsert.linkTo(head);
            head = toInsert;
        }
        else
        {
            Node<T> ref = head;
            while (ref.next() != null)
            {
                if (ref.next().getValue().compareTo(value) > 0)
                {
                    toInsert.linkTo(ref.next());
                    ref.linkTo(toInsert);
                    return;
                }
                ref = ref.next();
            }

            tail.linkTo(toInsert);
            tail = toInsert;
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
