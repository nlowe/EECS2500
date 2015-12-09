package edu.utoledo.nlowe.CustomDataTypes.Iterator;

import edu.utoledo.nlowe.CustomDataTypes.KeyValuePair;
import edu.utoledo.nlowe.CustomDataTypes.BinaryTreeNode;
import edu.utoledo.nlowe.CustomDataTypes.TraversalOrder;

import java.util.Iterator;

/**
 * An iterator for traversing a binary tree
 */
public abstract class BinaryTreeIterator<K extends Comparable<K>, V> implements Iterator<KeyValuePair<K, V>>
{
    protected BinaryTreeNode<K, V> nextNode;

    public BinaryTreeIterator(BinaryTreeNode<K, V> head)
    {
        this.nextNode = head;
    }

    @Override
    public boolean hasNext()
    {
        return nextNode != null;
    }

    /**
     * @return The traversal order this iterator implements
     */
    public abstract TraversalOrder getTraversalOrder();

}
