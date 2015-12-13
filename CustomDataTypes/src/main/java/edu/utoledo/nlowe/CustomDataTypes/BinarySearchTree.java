package edu.utoledo.nlowe.CustomDataTypes;

import edu.utoledo.nlowe.CustomDataTypes.Iterator.InorderBinaryTreeIterator;
import edu.utoledo.nlowe.CustomDataTypes.Iterator.PostorderBinaryTreeIterator;
import edu.utoledo.nlowe.CustomDataTypes.Iterator.PreorderBinaryTreeIterator;
import edu.utoledo.nlowe.CustomDataTypes.Iterator.BinaryTreeIterator;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * A Tree that exhibits the Binary Search Tree Property:
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
public class BinarySearchTree<T extends Comparable<T>>
        implements PerformanceTraceable, Iterable<T>
{
    /** The number of comparisons made during the lifetime of the tree */
    private long comparisons = 0L;
    /** The number of reference changes made during the lifetime of the tree */
    private long referenceChanges = 0L;
    /** The number of nodes in the tree */
    private long nodeCount = 0L;

    /** The first or "root" node of the tree. All other nodes are either in this node's left or right branch */
    private BinaryTreeNode<T> rootNode;

    /**
     * Add the specified element to the tree
     *
     * @param element The element to add
     */
    public void add(T element)
    {
        addOr(element, null);
    }

    /**
     * Adds the specified element to the tree. If the element already exists in
     * the tree, this method will call the <code>ifFound</code> consumer instead.
     * The item will <b>NOT</b> be added to the tree if this happens.
     *
     * @param element the element to add
     * @param ifFound The consumer to call if the specified element is already in the tree
     */
    public void addOr(T element, Consumer<T> ifFound)
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
            BinaryTreeNode<T> parent;
            BinaryTreeNode<T> candidate = rootNode;
            int branchComparisonResult;

            do
            {
                // Remember where we used to be
                parent = candidate;

                // Find which branch to take
                comparisons++;
                branchComparisonResult = element.compareTo(candidate.getPayload());

                if(branchComparisonResult < 0)
                {
                    // The element we're inserting is less than the candidate
                    // Take the left branch
                    candidate = candidate.getLeftBranch();
                }
                else if(branchComparisonResult == 0)
                {
                    // The element we're inserting has the same key as another element
                    if(ifFound != null){
                        // Perform the specified action on its value if specified
                        ifFound.accept(candidate.getPayload());
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
            parent.graft(new BinaryTreeNode<>(element), branchComparisonResult < 0);
        }
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
    public BinaryTreeIterator<T> traverse(TraversalOrder order)
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
    public Iterator<T> iterator()
    {
        return traverse(TraversalOrder.INORDER);
    }
}
