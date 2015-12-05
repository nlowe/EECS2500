package edu.utoledo.nlowe.CustomDataTypes.Iterator;

import edu.utoledo.nlowe.CustomDataTypes.KeyValuePair;
import edu.utoledo.nlowe.CustomDataTypes.TreeNode;

/**
 * An iterator that traverses a tree in pre-order
 */
public class PreorderTreeIterator<K extends Comparable<K>, V> extends TreeIterator<K, V>
{
    public PreorderTreeIterator(TreeNode<K, V> head)
    {
        super(head);
    }

    @Override
    public boolean hasNext()
    {
        return nextNode != null && traversalStack.size() > 0;
    }

    @Override
    public KeyValuePair<K, V> next()
    {
        // "Visit" this node
        KeyValuePair<K, V> payload = nextNode.getPayload();
        traversalStack.push(nextNode);

        if(nextNode.getLeftBranch() != null)
        {
            // Go left next, if we can
            nextNode = nextNode.getLeftBranch();
        }
        else if(nextNode.getRightBranch() != null)
        {
            // go right next, if we can
            nextNode = nextNode.getRightBranch();
        }
        else
        {
            do
            {
                // Try to go up and right. If we can't we're done iterating
                nextNode = traversalStack.pop().getRightBranch();
            }while (traversalStack.size() > 0 && nextNode == null);
        }

        return payload;
    }
}
