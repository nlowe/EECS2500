package edu.utoledo.nlowe.CustomDataTypes.Iterator;

import edu.utoledo.nlowe.CustomDataTypes.BinaryTreeNode;
import edu.utoledo.nlowe.CustomDataTypes.CustomStack;
import edu.utoledo.nlowe.CustomDataTypes.KeyValuePair;
import edu.utoledo.nlowe.CustomDataTypes.TraversalOrder;

/**
 * An iterator that traverses a binary tree Postorder
 */
public class PostorderBinaryTreeIterator<T> extends BinaryTreeIterator<T>
{
    /**
     * The traversal stack. The value of the KVP is true if we have already
     * visited the right subtree of the node in question
     */
    protected final CustomStack<KeyValuePair<BinaryTreeNode<T>, Boolean>> traversalStack;

    public PostorderBinaryTreeIterator(BinaryTreeNode<T> head)
    {
        super(head);
        traversalStack = new CustomStack<>();
        traversalStack.push(new KeyValuePair<>(head, false));

        spider();
    }

    /**
     * Moves the next node pointer left as far as possible then
     * right once if possible until we reach a leaf
     */
    private void spider()
    {
        moveLeftmost();

        while (nextNode.getRightBranch() != null)
        {
            traversalStack.peek().setValue(true);
            nextNode = nextNode.getRightBranch();
            traversalStack.push(new KeyValuePair<>(nextNode, false));
            moveLeftmost();
        }
    }

    /**
     * Moves the next node pointer as far left as possible
     */
    private void moveLeftmost()
    {
        while (nextNode.getLeftBranch() != null)
        {
            nextNode = nextNode.getLeftBranch();
            traversalStack.push(new KeyValuePair<>(nextNode, false));
        }
    }

    @Override
    public T next()
    {
        // The next node is on the top of the stack, pop it off to go "up"
        T result = traversalStack.pop().getKey().getPayload();

        if (traversalStack.size() > 0)
        {
            nextNode = traversalStack.peek().getKey();

            if (traversalStack.peek().getKey().getRightBranch() != null && !traversalStack.peek().getValue())
            {
                // Go right next since we haven't visited the right branch
                traversalStack.peek().setValue(true);
                nextNode = traversalStack.peek().getKey().getRightBranch();
                traversalStack.push(new KeyValuePair<>(nextNode, false));

                // If the node we're scheduled to visit next isn't a leaf, find the next leaf
                spider();
            }
        }
        else
        {
            // Nothing left, we're done
            nextNode = null;
        }

        return result;
    }

    @Override
    public TraversalOrder getTraversalOrder()
    {
        return TraversalOrder.POSTORDER;
    }
}
