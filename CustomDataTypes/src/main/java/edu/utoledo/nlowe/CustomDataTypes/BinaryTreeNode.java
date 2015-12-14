package edu.utoledo.nlowe.CustomDataTypes;

/**
 * A node in a Binary Tree
 */
public class BinaryTreeNode<T>
{
    /** The data in this node */
    private final T payload;

    /** The left branch of the tree */
    private BinaryTreeNode<T> left;
    /** The right branch of the tree */
    private BinaryTreeNode<T> right;

    /**
     * Construct a Binary Tree node with empty branches from the given payload
     *
     * @param payload the payload of the node
     */
    public BinaryTreeNode(T payload)
    {
        this.payload = payload;
    }

    /**
     * @return the payload contained within the node
     */
    public T getPayload()
    {
        return payload;
    }

    /**
     * @return the left branch of the tree that continues from this node
     */
    public BinaryTreeNode<T> getLeftBranch()
    {
        return left;
    }

    /**
     * Set the left branch of the tree from this node
     *
     * @param left the branch to set
     */
    public void setLeftBranch(BinaryTreeNode<T> left)
    {
        this.left = left;
    }

    /**
     * @return the right branch of the tree that continues from this node
     */
    public BinaryTreeNode<T> getRightBranch()
    {
        return right;
    }

    /**
     * Set the right branch of the tree from this node
     *
     * @param right the branch to set
     */
    public void setRightBranch(BinaryTreeNode<T> right)
    {
        this.right = right;
    }

    /**
     * Graft the specified branch into the tree at this node on either the left or right sub-branch
     *
     * @param branch the branch to graft in
     * @param left   whether or not to graft the branch in at the left of this node
     */
    public void graft(BinaryTreeNode<T> branch, boolean left)
    {
        if (left)
        {
            setLeftBranch(branch);
        }
        else
        {
            setRightBranch(branch);
        }
    }

    @Override
    public String toString()
    {
        return "{\"payload\": \"" + payload +
                "\", \"left\": " + (left == null ? "null" : left.toString()) +
                ", \"right\": " + (right == null ? "null" : right.toString()) + "}";
    }

}
