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
    }

    private void moveLeftmost()
    {
        while (nextNode != null)
        {
            traversalStack.push(new KeyValuePair<>(nextNode, false));
            nextNode = nextNode.getLeftBranch();
        }
    }

    @Override
    public KeyValuePair<K, V> next()
    {
        if(traversalStack.size() == 0)
        {
            moveLeftmost();
        }

        // If there is no right branch, or we already visited it, we can "visit" this node
        if(traversalStack.peek().getKey().getRightBranch() == null || traversalStack.peek().getValue())
        {
            return traversalStack.pop().getKey().getPayload();
        }else{
            // Go to the leftmost branch on the right subtree
            traversalStack.peek().setValue(true);
            nextNode = traversalStack.peek().getKey().getRightBranch();
            moveLeftmost();

            return next();
        }
    }
}
