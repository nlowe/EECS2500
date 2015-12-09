package edu.utoledo.nlowe.CustomDataTypes;

/**
 * A node in a Binary Tree Map
 */
public class BinaryTreeNode<K, V>
{
    /** The data in this node */
    private final KeyValuePair<K, V> payload;

    /** The left branch of the tree */
    private BinaryTreeNode<K, V> left;
    /** The right branch of the tree */
    private BinaryTreeNode<K, V> right;

    protected BinaryTreeNode(KeyValuePair<K, V> payload)
    {
        this.payload = payload;
    }

    /**
     * @return the payload contained within the node
     */
    public KeyValuePair<K, V> getPayload()
    {
        return payload;
    }

    /**
     * @return the left branch of the tree that continues from this node
     */
    public BinaryTreeNode<K, V> getLeftBranch()
    {
        return left;
    }

    /**
     * Set the left branch of the tree from this node
     * @param left the branch to set
     */
    public void setLeftBranch(BinaryTreeNode<K, V> left)
    {
        this.left = left;
    }

    /**
     * @return the right branch of the tree that continues from this node
     */
    public BinaryTreeNode<K, V> getRightBranch()
    {
        return right;
    }

    /**
     * Set the right branch of the tree from this node
     * @param right the branch to set
     */
    public void setRightBranch(BinaryTreeNode<K, V> right)
    {
        this.right = right;
    }

    /**
     * Graft the specified branch into the tree at this node on either the left or right sub-branch
     *
     * @param branch the branch to graft in
     * @param left whether or not to graft the branch in at the left of this node
     */
    public void graft(BinaryTreeNode<K, V> branch, boolean left)
    {
        if(left)
        {
            setLeftBranch(branch);
        }
        else
        {
            setRightBranch(branch);
        }
    }

    public boolean equals(BinaryTreeNode<K, V> o)
    {
        return o != null && payload.getKey().equals(o.payload.getKey());
    }

    @Override
    public String toString()
    {
        return "{\"key\": \"" + payload.getKey() +
                "\", \"value\": \"" + getPayload().getValue() +
                "\", \"left\": " + (left == null ? "null" : left.toString()) +
                ", \"right\": " + (right == null ? "null" : right.toString()) + "}";
    }

}
