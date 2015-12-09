package edu.utoledo.nlowe.CustomDataTypes;

import edu.utoledo.nlowe.CustomDataTypes.Iterator.InorderBinaryTreeIterator;
import edu.utoledo.nlowe.CustomDataTypes.Iterator.PostorderBinaryTreeIterator;
import edu.utoledo.nlowe.CustomDataTypes.Iterator.PreorderBinaryTreeIterator;
import edu.utoledo.nlowe.CustomDataTypes.Iterator.BinaryTreeIterator;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * A Tree Map that exhibits the Binary Search Tree Property:
 * <p/>
 * For any given node with a key of <code>k</code>:
 * <ul>
 *     <li>All items on the <code>leftBranch</code> of the node are "less" than <code>k</code></li>
 *     <li>All items on the <code>rightBranch</code> of the node are "greater" than <code>k</code></li>
 * </ul>
 * <p/>
 * This class implements <code>PerformanceTraceable</code>, and keeps track of all comparisons made
 * and all reference changes made over the lifetime of the tree. If the tree is cleared, these stats
 * are reset.
 */
public class BinarySearchTreeMap<K extends Comparable<K>, V>
        implements PerformanceTraceable, Iterable<KeyValuePair<K, V>>
{
    /** The number of comparisons made during the lifetime of the tree */
    private long comparisons = 0L;
    /** The number of reference changes made during the lifetime of the tree */
    private long referenceChanges = 0L;
    /** The number of nodes in the tree */
    private long nodeCount = 0L;

    /** The first or "root" node of the tree. All other nodes are either in this node's left or right branch */
    private BinaryTreeNode<K, V> rootNode;

    /**
     * Put the specified key and value in the tree
     *
     * @param key The key to put
     * @param value The value of the key to set
     */
    public void put(K key, V value)
    {
        putOr(key, value, null);
    }

    /**
     * Put the specified Key Value Pair in the tree
     *
     * @param element the element to add
     */
    public void put(KeyValuePair<K, V> element)
    {
        putOr(element, null);
    }

    /**
     * Put the specified key and value in the tree. If the specified key exists
     * in the tree, this method will call the <code>ifFound</code> Consumer
     * instead. The item will <b>NOT</b> be added to the tree if this happens.
     *
     * @param key The key to put
     * @param value The value of the key to set
     * @param ifFound The consumer to call if the specified key already exists
     */
    public void putOr(K key, V value, Consumer<KeyValuePair<K, V>> ifFound)
    {
        putOr(new KeyValuePair<>(key, value), ifFound);
    }

    /**
     * Puts the specified Key Value Pair in the tree. If key of the element already
     * exists in the tree, this method will call the <code>ifFound</code> consumer
     * instead. The item will <b>NOT</b> be added to the tree if this happens.
     *
     * @param element the element to add
     * @param ifFound The consumer to call if the specified key already exists
     */
    public void putOr(KeyValuePair<K, V> element, Consumer<KeyValuePair<K, V>> ifFound)
    {
        if(rootNode == null)
        {
            // This is the first node in the tree, set the root element
            rootNode = new BinaryTreeNode<>(element);
            referenceChanges++;
            nodeCount++;
        }
        else
        {
            BinaryTreeNode<K, V> parent;
            BinaryTreeNode<K, V> candidate = rootNode;
            int branchComparison;

            do
            {
                // Remember where we used to be
                parent = candidate;

                // Find which branch to take
                comparisons++;
                branchComparison = element.getKey().compareTo(candidate.getPayload().getKey());

                if(branchComparison < 0)
                {
                    // The element we're inserting is less than the candidate
                    // Take the left branch
                    candidate = candidate.getLeftBranch();
                }
                else if(branchComparison == 0)
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
            parent.graft(new BinaryTreeNode<>(element), branchComparison < 0);
        }
    }

    /**
     * Get the value in the tree for the specified key
     *
     * @param key The key of the element to retrieve
     * @return The value associated with the specified key, or null if
     * the specified key does not exist
     */
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

    /**
     * Reset all statistics and clear the tree
     */
    public void clear()
    {
        referenceChanges = nodeCount = comparisons = 0L;
        rootNode = null;
    }

    /**
     * @return The number of nodes in the tree
     */
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
