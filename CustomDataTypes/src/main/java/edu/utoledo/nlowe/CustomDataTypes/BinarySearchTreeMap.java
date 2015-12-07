package edu.utoledo.nlowe.CustomDataTypes;

import edu.utoledo.nlowe.CustomDataTypes.Iterator.InorderBinaryTreeIterator;
import edu.utoledo.nlowe.CustomDataTypes.Iterator.PostorderBinaryTreeIterator;
import edu.utoledo.nlowe.CustomDataTypes.Iterator.PreorderBinaryTreeIterator;
import edu.utoledo.nlowe.CustomDataTypes.Iterator.BinaryTreeIterator;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Created by nathan on 12/4/15
 */
public class BinarySearchTreeMap<K extends Comparable<K>, V>
        implements PerformanceTraceable, Iterable<KeyValuePair<K, V>>
{
    private long comparisons = 0L;
    private long referenceChanges = 0L;
    private long nodeCount = 0L;

    private BinaryTreeNode<K, V> rootNode;

    public void put(K key, V value)
    {
        putOr(key, value, null);
    }

    public void put(KeyValuePair<K, V> element)
    {
        putOr(element, null);
    }

    public void putOr(K key, V value, Consumer<KeyValuePair<K, V>> ifFound)
    {
        putOr(new KeyValuePair<>(key, value), ifFound);
    }

    public void putOr(KeyValuePair<K, V> element, Consumer<KeyValuePair<K, V>> ifFound)
    {
        if(rootNode == null)
        {
            rootNode = new BinaryTreeNode<>(element);
            referenceChanges++;
            nodeCount++;
        }
        else
        {
            BinaryTreeNode<K, V> parent;
            BinaryTreeNode<K, V> candidate = rootNode;
            int branch;

            do
            {
                // Remember where we used to be
                parent = candidate;

                // Find which branch to take
                comparisons++;
                branch = element.getKey().compareTo(candidate.getPayload().getKey());

                if(branch < 0)
                {
                    // The element we're inserting is less than the candidate
                    // Take the left branch
                    candidate = candidate.getLeftBranch();
                }
                else if(branch == 0)
                {
                    // The element we're inserting has the same key as another element
                    if(ifFound != null){
                        // Perform the specified action on its value if specified
                        ifFound.accept(candidate.getPayload());
                    }
                    else
                    {
                        // Otherwise, replace the value
                        candidate.getPayload().setValue(element.getValue());
                    }

                    return;
                }
                else
                {
                    // The element we're inserting is greater than the candidate
                    // Take the right branch
                    candidate = candidate.getRightBranch();
                }
            }while (candidate != null);

            // We're supposed to insert a node
            // Insert it on the correct branch
            nodeCount++;
            referenceChanges++;
            parent.graft(new BinaryTreeNode<>(element), branch < 0);
        }
    }

    public V get(K key)
    {
        BinaryTreeNode<K, V> current = rootNode;

        do
        {
            int result = key.compareTo(current.getPayload().getKey());
            if(result < 0)
            {
                current = current.getLeftBranch();
            }
            else if(result == 0)
            {
                return current.getPayload().getValue();
            }
            else if( result > 0)
            {
                current = current.getRightBranch();
            }
        }while (current != null);

        return null;
    }

    public void clear()
    {
        referenceChanges = nodeCount = comparisons = 0L;
        rootNode = null;
    }

    public long getNodeCount()
    {
        return nodeCount;
    }

    @Override
    public long getComparisonCount()
    {
        return comparisons;
    }

    @Override
    public long getReferenceAssignmentCount()
    {
        return referenceChanges;
    }

    /**
     * Create an iterator to traverse the tree in the specified order
     *
     * @param order the order to perform the traversal in
     * @return A <code>BinaryTreeIterator</code> that traverses the tree in the specified order
     */
    public BinaryTreeIterator<K,V> traverse(TraversalOrder order)
    {
        switch (order)
        {
            case PREORDER: return new PreorderBinaryTreeIterator<>(rootNode);
            case POSTORDER: return new PostorderBinaryTreeIterator<>(rootNode);
            case INORDER:
            default: return new InorderBinaryTreeIterator<>(rootNode);
        }
    }

    @Override
    public Iterator<KeyValuePair<K, V>> iterator()
    {
        return traverse(TraversalOrder.INORDER);
    }
}
