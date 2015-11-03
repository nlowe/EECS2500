package edu.utoledo.nlowe.CustomDataTypes;

import java.util.Iterator;

/**
 * An iterator for linked data types
 */
public class NodeIterator<T> implements Iterator<T>
{

    /** The next item in the list */
    private Node<T> pointer;

    public NodeIterator(Node<T> head)
    {
        this.pointer = head;
    }

    @Override
    public boolean hasNext()
    {
        return pointer != null;
    }

    @Override
    public T next()
    {
        T result = pointer.getValue();
        pointer = pointer.next();
        return result;
    }
}
