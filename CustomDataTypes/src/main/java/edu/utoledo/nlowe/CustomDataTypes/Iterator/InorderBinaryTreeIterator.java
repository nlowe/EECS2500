package edu.utoledo.nlowe.CustomDataTypes.Iterator;

import edu.utoledo.nlowe.CustomDataTypes.CustomStack;
import edu.utoledo.nlowe.CustomDataTypes.KeyValuePair;
import edu.utoledo.nlowe.CustomDataTypes.BinaryTreeNode;
import edu.utoledo.nlowe.CustomDataTypes.TraversalOrder;

/**
 * An iterator that traverses a tree in-order
 */
public class InorderBinaryTreeIterator<T> extends BinaryTreeIterator<T>
{
    /**
     * The traversal stack for this iterator.
     *
     * The key is <code>True</code> when the node has been visited
     */
    private final CustomStack<KeyValuePair<BinaryTreeNode<T>, Boolean>> traversalStack;

    public InorderBinaryTreeIterator(BinaryTreeNode<T> head)
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
    public T next()
    {
        T result = nextNode.getPayload();
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

    @Override
    public TraversalOrder getTraversalOrder()
    {
        return TraversalOrder.INORDER;
    }
}
