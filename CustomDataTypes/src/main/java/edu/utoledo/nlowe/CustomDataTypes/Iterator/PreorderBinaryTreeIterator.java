package edu.utoledo.nlowe.CustomDataTypes.Iterator;

import edu.utoledo.nlowe.CustomDataTypes.CustomStack;
import edu.utoledo.nlowe.CustomDataTypes.KeyValuePair;
import edu.utoledo.nlowe.CustomDataTypes.BinaryTreeNode;
import edu.utoledo.nlowe.CustomDataTypes.TraversalOrder;

/**
 * An iterator that traverses a tree preorder
 */
public class PreorderBinaryTreeIterator<K extends Comparable<K>, V> extends BinaryTreeIterator<K, V>
{
    /**
     * The traversal stack
     *
     * The value is <code>true</code> when a node's right branch has been visited
     */
    protected final CustomStack<KeyValuePair<BinaryTreeNode<K, V>, Boolean>> traversalStack;

    public PreorderBinaryTreeIterator(BinaryTreeNode<K, V> head)
    {
        super(head);
        traversalStack = new CustomStack<>();
        traversalStack.push(new KeyValuePair<>(head, false));
    }

    @Override
    public KeyValuePair<K, V> next()
    {
        // "Visit" this node
        KeyValuePair<K, V> payload = nextNode.getPayload();

        if(nextNode.getLeftBranch() != null)
        {
            // Visit the left branch next
            nextNode = nextNode.getLeftBranch();
            traversalStack.push(new KeyValuePair<>(nextNode, false));
        }
        else if(nextNode.getRightBranch() != null)
        {
            // visit the right branch next
            nextNode = nextNode.getRightBranch();
            traversalStack.peek().setValue(true);
            traversalStack.push(new KeyValuePair<>(nextNode, false));
        }
        else
        {
            // go "up" and find a node that we haven't visited the right branch of
            do
            {
                nextNode = traversalStack.pop().getKey();
            }while(
                // Keep doing this while there are more items on the stack and
                //  the node currently on the top of the stack doesn't have a right branch or
                //  we have already visited it's right branch
                traversalStack.size() > 0 &&
                (
                        traversalStack.peek().getKey().getRightBranch() == null ||
                        traversalStack.peek().getValue()
                )
            );

            if(traversalStack.size() > 0)
            {
                // Go right next
                nextNode = traversalStack.peek().getKey().getRightBranch();
                traversalStack.peek().setValue(true);
                traversalStack.push(new KeyValuePair<>(nextNode, false));
            }
            else
            {
                // We're done
                nextNode = null;
            }
        }

        return payload;
    }

    @Override
    public TraversalOrder getTraversalOrder()
    {
        return TraversalOrder.PREORDER;
    }
}
