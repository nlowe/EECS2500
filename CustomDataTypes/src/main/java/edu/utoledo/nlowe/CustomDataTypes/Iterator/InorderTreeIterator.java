package edu.utoledo.nlowe.CustomDataTypes.Iterator;

import edu.utoledo.nlowe.CustomDataTypes.KeyValuePair;
import edu.utoledo.nlowe.CustomDataTypes.TreeNode;

/**
 * An iterator that traverses a tree in-order
 */
public class InorderTreeIterator<K extends Comparable<K>, V> extends TreeIterator<K, V>
{
    public InorderTreeIterator(TreeNode<K, V> head)
    {
        super(head);
    }

    @Override
    public KeyValuePair<K, V> next()
    {
        // If we've already "visited" this node, it's time to print it
        if(traversalStack.size() > 0 && nextNode.equals(traversalStack.peek()))
        {
            KeyValuePair<K, V> result = nextNode.getPayload();
            nextNode = traversalStack.pop().getRightBranch();
            return result;
        }

        // Go to the left-most node
        while (nextNode.getLeftBranch() != null)
        {
            traversalStack.push(nextNode);
            nextNode = nextNode.getLeftBranch();
        }

        KeyValuePair<K, V> result = nextNode.getPayload();

        if(traversalStack.size() > 0)
        {
            // We need to go "up" next
            nextNode = traversalStack.peek();
        }
        else
        {
            // We need to go "right" next
            nextNode = nextNode.getRightBranch();
        }

        return result;
    }
}
