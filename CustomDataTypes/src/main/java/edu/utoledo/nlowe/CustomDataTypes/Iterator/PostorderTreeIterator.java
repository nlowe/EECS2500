package edu.utoledo.nlowe.CustomDataTypes.Iterator;

import edu.utoledo.nlowe.CustomDataTypes.CustomStack;
import edu.utoledo.nlowe.CustomDataTypes.KeyValuePair;
import edu.utoledo.nlowe.CustomDataTypes.TreeNode;

/**
 * Created by nathan on 12/4/15
 */
public class PostorderTreeIterator<K extends Comparable<K>, V> extends TreeIterator<K, V>
{

    private CustomStack<Boolean> rightNodeVisited;

    public PostorderTreeIterator(TreeNode<K, V> head)
    {
        super(head);
        rightNodeVisited = new CustomStack<>();
    }

    private void moveLeftmost()
    {
        while (nextNode != null)
        {
            traversalStack.push(nextNode);
            rightNodeVisited.push(false);
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
        if(traversalStack.peek().getRightBranch() == null || rightNodeVisited.peek())
        {
            rightNodeVisited.pop();
            return traversalStack.pop().getPayload();
        }else{
            // Go to the leftmost branch on the right subtree
            rightNodeVisited.pop();
            rightNodeVisited.push(true);

            nextNode = traversalStack.peek().getRightBranch();
            moveLeftmost();

            return next();
        }
    }
}
