package edu.utoledo.nlowe.CustomDataTypes;

import java.util.Iterator;

/**
 * A custom, generic, linked list implementation
 */
public class CustomLinkedList<T> implements Iterable<T>, PerformanceTraceable
{

    /** A pointer to the first item in the list */
    protected Node<T> head = null;
    /** A pointer to the last item in the list */
    protected Node<T> tail = null;
    /** The cached size of the list */
    protected int size = 0;

    protected int comparisonCount = 0;
    protected int referenceChangeCount = 0;

    @Override
    public long getComparisonCount()
    {
        return comparisonCount;
    }

    @Override
    public long getReferenceAssignmentCount()
    {
        return referenceChangeCount;
    }

    /**
     * An iterator for traversing the list in order
     */
    private final class CustomLinkedListIterator<U> implements Iterator<U>
    {

        /** The next item in the list */
        private Node<U> pointer;

        public CustomLinkedListIterator(Node<U> head)
        {
            this.pointer = head;
        }

        @Override
        public boolean hasNext()
        {
            return pointer != null;
        }

        @Override
        public U next()
        {
            U result = pointer.getValue();
            pointer = pointer.next();
            return result;
        }
    }

    /**
     * The internal data type used to create the list structure. Contains a
     * a payload of the specified generic type and a reference to the next
     * node in the list. If the link is null, then this node is the last
     * node in the list
     *
     * @param <U> The underlying payload type
     */
    protected final class Node<U>
    {

        /** The payload of the node */
        private U value;
        /** The next node in the list */
        private Node<U> next;

        Node(U value)
        {
            this(value, null);
        }

        private Node(U value, Node<U> next)
        {
            this.value = value;
            this.next = next;
        }

        /**
         * Sets the payload of this node to the specified value
         *
         * @param value The value to set
         */
        public void setValue(U value)
        {
            this.value = value;
        }

        /**
         * @return the payload of this node
         */
        public U getValue()
        {
            return value;
        }

        /**
         * @return the next node in the list
         */
        public Node<U> next()
        {
            return next;
        }

        /**
         * Sets the link to the next node to point to the specified node
         *
         * @param next the node to point to
         */
        public void linkTo(Node<U> next)
        {
            this.next = next;
        }
    }

    /**
     * @param index the index being inserted at
     * @return <code>true</code> if and only if the specified index is greater than or equal to
     * zero and less than or equal to the size of the list
     */
    protected boolean validateInsertableBounds(int index)
    {
        return index >= 0 && index <= size;
    }

    /**
     * @param index the index being accessed
     * @return <code>true</code> if and only if the specified index is greater than or equal to
     * zero and less than the size of the list
     */
    protected boolean validateBounds(int index)
    {
        return index >= 0 && index < size;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new CustomLinkedListIterator<>(this.head);
    }

    /**
     * Adds the specified item to the end of the list
     *
     * @param value the item to add
     */
    public void add(T value)
    {
        add(this.size(), value);
    }

    /**
     * Adds the specified item at the specified location in the list
     *
     * @param index The location to insert the item at. Must be between 0 and the size of the list inclusive
     * @param value The value to insert
     * @throws IndexOutOfBoundsException
     */
    public void add(int index, T value) throws IndexOutOfBoundsException
    {
        Node<T> node = new Node<>(value);
        if (!validateInsertableBounds(index))
        {
            throw new IndexOutOfBoundsException("The Index " + index + " is not in the bounds of the list!");
        }
        else if (size == 0)
        {
            // This is the first element we've added to the list
            // So we can just update the head and tail pointers
            head = tail = node;
            referenceChangeCount += 2;
        }
        else if (index == 0)
        {
            // We have at least one element in the list already
            // So link the new node to the first node and update the head pointer
            node.linkTo(head);
            head = node;
            referenceChangeCount += 2;
        }
        else if (index == size)
        {
            // We have at least one element in the list already
            // Link the last element in the list to the new node and update the tail pointer
            tail.linkTo(node);
            tail = node;
            referenceChangeCount += 2;
        }
        else if (index > 0 && index < size)
        {
            // Were inserting somewhere in the middle
            // Get the node immediately preceding the target index and the node after it
            Node<T> parent = getNodeAt(index - 1);
            Node<T> next = parent.next();

            // Link all the things
            node.linkTo(next);
            parent.linkTo(node);

            referenceChangeCount += 2;
        }

        // We added something, increase the cached size of the list
        size++;
    }

    /**
     * Updates the value at the specified location
     *
     * @param index the index of the element to update
     * @param value the value to set
     * @throws IndexOutOfBoundsException
     */
    public void set(int index, T value) throws IndexOutOfBoundsException
    {
        if (!validateBounds(index))
        {
            throw new IndexOutOfBoundsException("The Index " + index + " is not in the bounds of the list!");
        }

        getNodeAt(index).setValue(value);
    }

    /**
     * Removes the specified element from the list
     *
     * @param index the index of the element to remove
     * @throws IndexOutOfBoundsException
     */
    public void remove(int index) throws IndexOutOfBoundsException
    {
        if (!validateBounds(index))
        {
            throw new IndexOutOfBoundsException("The Index " + index + " is not in the bounds of the list!");
        }

        if (index == 0)
        {
            // Removing the first element.
            // Point the head pointer to the proceeding node
            // Then link the old element to null so it can be GC'd
            Node<T> oldHead = head;

            head = head.next();
            referenceChangeCount++;

            oldHead.linkTo(null);
        }
        else
        {
            // Get the parent node and the node to remove from it
            Node<T> parent = getNodeAt(index - 1);
            Node<T> toRemove = parent.next();

            // Link the parent to the node after the target node
            parent.linkTo(toRemove.next());

            // Link the target node to null so it can be GC'd
            toRemove.linkTo(null);

            // If we removed the last element in the list, we need to update the tail pointer
            if (index == size - 1)
            {
                tail = parent;
            }

            referenceChangeCount += 2;
        }

        // We removed an element, decrease the cached size
        size--;
    }

    /**
     * Traverses the list and returns true if the element is found
     *
     * @param value the value to search for
     * @return <code>True</code> if the element is in the list, false otherwise
     */
    public boolean contains(T value)
    {
        // We implement Iterable<T>, so we can just use a for-each loop
        for (T element : this)
        {
            comparisonCount++;
            if (element.equals(value))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Removes all items from the list
     */
    public void clear()
    {
        // Simply set the head and tail pointers to null and reset the size cache
        head = tail = null;
        referenceChangeCount += 2;
        size = 0;
    }

    /**
     * A helper method to get the specified node in the list
     *
     * @param index the index of the target node
     * @return the node at the specified index
     */
    protected Node<T> getNodeAt(int index)
    {
        // We should never be trying to get a node outside of the bounds of the list
        // An exception should have been thrown by now if the user was trying to
        assert (validateBounds(index));

        int i = 0;
        Node<T> node = head;
        while (i++ != index)
        {
            node = node.next();
        }

        return node;
    }

    /**
     * Gets the element at the specified index
     *
     * @param index the index of the element to get
     * @return the value at the specified index
     * @throws IndexOutOfBoundsException
     */
    public T get(int index) throws IndexOutOfBoundsException
    {
        if (!validateBounds(index))
        {
            throw new IndexOutOfBoundsException("The Index " + index + " is not in the bounds of the list!");
        }

        return getNodeAt(index).getValue();
    }

    /**
     * @return the first element in the list
     */
    public T getFirst()
    {
        return head == null ? null : head.getValue();
    }

    /**
     * @return the last element in the list. The list is not traversed.
     */
    public T getLast()
    {
        return tail == null ? null : tail.getValue();
    }

    /**
     * @return the size of the list. The list is not traversed
     */
    public int size()
    {
        return size;
    }

}
