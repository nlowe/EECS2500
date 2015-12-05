package edu.utoledo.nlowe.CustomDataTypes.Iterator;

import edu.utoledo.nlowe.CustomDataTypes.CustomStack;
import edu.utoledo.nlowe.CustomDataTypes.KeyValuePair;
import edu.utoledo.nlowe.CustomDataTypes.TreeNode;

import java.util.Iterator;

/**
 * Created by nathan on 12/4/15
 */
public abstract class TreeIterator<K extends Comparable<K>, V> implements Iterator<KeyValuePair<K, V>>
{
    protected final CustomStack<TreeNode<K, V>> traversalStack;
    protected TreeNode<K, V> nextNode;

    public TreeIterator(TreeNode<K, V> head)
    {
        this.nextNode = head;

        traversalStack = new CustomStack<>();
    }

    @Override
    public boolean hasNext()
    {
        return nextNode != null;
    }

}
