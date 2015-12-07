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
     * The key is <code>True</code> when the node's right branch has been visited
     */
    private final CustomStack<KeyValuePair<BinaryTreeNode<K, V>, Boolean>> traversalStack;

    public InorderBinaryTreeIterator(BinaryTreeNode<K, V> head)
    {
        super(head);
        traversalStack = new CustomStack<>();
    }

    @Override
    public KeyValuePair<K, V> next()
    {
        if(traversalStack.size() > 0 && nextNode.equals(traversalStack.peek().getKey()))
        {
            if(traversalStack.peek().getValue())
            {
                // We've already printed this node, we need to continue to go "up"
                do
                {
                    traversalStack.pop();
                    nextNode = traversalStack.peek().getKey();
                }while (traversalStack.size() > 0 && traversalStack.peek().getValue());
            }

            KeyValuePair<K, V> result = nextNode.getPayload();
            traversalStack.peek().setValue(true);

            if(nextNode.getRightBranch() != null)
            {
                // Visit the right branch next
                nextNode = nextNode.getRightBranch();
            }
            else
            {
                traversalStack.pop();

                // Visit the next thing on the stack next
                if(traversalStack.size() > 0)
                {
                    nextNode = traversalStack.peek().getKey();
                }else
                {
                    nextNode = null;
                }
            }

            return result;
        }


        // Visit the left branch first
        while (nextNode.getLeftBranch() != null)
        {
            traversalStack.push(new KeyValuePair<>(nextNode, false));
            nextNode = nextNode.getLeftBranch();
        }

        KeyValuePair<K, V> result = nextNode.getPayload();

        if(traversalStack.size() > 0)
        {
            // Visit the parent node next, since we just finished visiting the left branch
            nextNode = traversalStack.peek().getKey();
        }
        else
        {
            // We're done, nothing left to visit
            nextNode = null;
        }

        return result;
    }
}
