package edu.utoledo.nlowe.CustomDataTypes.Iterator;

import edu.utoledo.nlowe.CustomDataTypes.CustomStack;
import edu.utoledo.nlowe.CustomDataTypes.KeyValuePair;
import edu.utoledo.nlowe.CustomDataTypes.BinaryTreeNode;

/**
 * An iterator that traverses a tree in-order
 */
public class InorderBinaryTreeIterator<K extends Comparable<K>, V> extends BinaryTreeIterator<K, V>
{
    /**
     * The traversal stack for this iterator.
     *
     * The key is <code>True</code> when the node has been visited
     */
    private final CustomStack<KeyValuePair<BinaryTreeNode<K, V>, Boolean>> traversalStack;

    public InorderBinaryTreeIterator(BinaryTreeNode<K, V> head)
    {
        super(head);
        traversalStack = new CustomStack<>();
        traversalStack.push(new KeyValuePair<>(head, false));

        // Navigate to the first node to visit
        if(head != null)
        {
            moveLeft();
        }
    }

    /**
     * Moves the <code>nextNode</code> pointer as far left from the current node as possible
     */
    private void moveLeft()
    {
        while (traversalStack.peek().getKey().getLeftBranch() != null)
        {
            nextNode = traversalStack.peek().getKey().getLeftBranch();
            traversalStack.push(new KeyValuePair<>(nextNode, false));
        }
    }

    @Override
    public KeyValuePair<K, V> next()
    {
        KeyValuePair<K, V> result = nextNode.getPayload();
        traversalStack.peek().setValue(true);

        if(nextNode.getRightBranch() != null)
        {
            // go right next
            traversalStack.peek().setValue(true);
            nextNode = nextNode.getRightBranch();
            traversalStack.push(new KeyValuePair<>(nextNode, false));
            moveLeft();
        }
        else
        {
            // go up next
            while (traversalStack.size() > 0 && traversalStack.peek().getValue())
            {
                nextNode = traversalStack.pop().getKey();
            }

            // The next node is now on the top of the stack
            // If the stack is empty, we're done
            if(traversalStack.size() > 0)
            {
                nextNode = traversalStack.peek().getKey();
            }
            else
            {
                nextNode = null;
            }
        }

        return result;
    }
}
