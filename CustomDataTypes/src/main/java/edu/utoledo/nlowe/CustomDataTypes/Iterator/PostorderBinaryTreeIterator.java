package edu.utoledo.nlowe.CustomDataTypes.Iterator;

import edu.utoledo.nlowe.CustomDataTypes.CustomStack;
import edu.utoledo.nlowe.CustomDataTypes.KeyValuePair;
import edu.utoledo.nlowe.CustomDataTypes.BinaryTreeNode;

/**
 * Created by nathan on 12/4/15
 */
public class PostorderBinaryTreeIterator<K extends Comparable<K>, V> extends BinaryTreeIterator<K, V>
{
    /**
     * The traversal stack. The value of the KVP is true if we have already
     * visited the right subtree of the node in question
     */
    protected final CustomStack<KeyValuePair<BinaryTreeNode<K, V>, Boolean>> traversalStack;

    public PostorderBinaryTreeIterator(BinaryTreeNode<K, V> head)
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
    public KeyValuePair<K, V> next()
    {
        KeyValuePair<K, V> result = traversalStack.pop().getKey().getPayload();

        if(traversalStack.size() > 0)
        {
            nextNode = traversalStack.peek().getKey();

            if(traversalStack.peek().getKey().getRightBranch() != null && !traversalStack.peek().getValue())
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
}
