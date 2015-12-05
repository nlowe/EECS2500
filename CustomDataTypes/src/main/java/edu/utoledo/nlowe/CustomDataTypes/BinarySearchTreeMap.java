package edu.utoledo.nlowe.CustomDataTypes;

import edu.utoledo.nlowe.CustomDataTypes.Iterator.InorderTreeIterator;
import edu.utoledo.nlowe.CustomDataTypes.Iterator.PostorderTreeIterator;
import edu.utoledo.nlowe.CustomDataTypes.Iterator.PreorderTreeIterator;
import edu.utoledo.nlowe.CustomDataTypes.Iterator.TreeIterator;

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

    private TreeNode<K, V> rootNode;

    public void put(K key, V value)
    {
        put(key, value, null);
    }

    public void put(KeyValuePair<K, V> element)
    {
        put(element, null);
    }

    public void put(K key, V value, Consumer<KeyValuePair<K, V>> ifFound)
    {
        put(new KeyValuePair<>(key, value), ifFound);
    }

    public void put(KeyValuePair<K, V> element, Consumer<KeyValuePair<K, V>> ifFound)
    {
        if(rootNode == null)
        {
            rootNode = new TreeNode<>(element);
            referenceChanges++;
        }
        else
        {
            TreeNode<K, V> parent;
            TreeNode<K, V> candidate = rootNode;
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
            referenceChanges++;
            parent.graft(new TreeNode<>(element), branch < 0);
        }
    }

    public V get(K key)
    {
        TreeNode<K, V> current = rootNode;

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
     * @return A <code>TreeIterator</code> that traverses the tree in the specified order
     */
    public TreeIterator<K,V> traverse(TraversalOrder order)
    {
        switch (order)
        {
            case PREORDER: return new PreorderTreeIterator<>(rootNode);
            case POSTORDER: return new PostorderTreeIterator<>(rootNode);
            case INORDER:
            default: return new InorderTreeIterator<>(rootNode);
        }
    }

    @Override
    public Iterator<KeyValuePair<K, V>> iterator()
    {
        return traverse(TraversalOrder.INORDER);
    }
}
