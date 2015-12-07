package edu.utoledo.nlowe.CustomDataTypes.Iterator;

import edu.utoledo.nlowe.CustomDataTypes.KeyValuePair;
import edu.utoledo.nlowe.CustomDataTypes.BinaryTreeNode;

import java.util.Iterator;

/**
 * Created by nathan on 12/4/15
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

}
