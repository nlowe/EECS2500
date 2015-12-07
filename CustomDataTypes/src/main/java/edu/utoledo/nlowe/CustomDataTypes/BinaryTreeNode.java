package edu.utoledo.nlowe.CustomDataTypes;

/**
 * A node in a tree
 */
public class BinaryTreeNode<K, V>
{
    private final KeyValuePair<K, V> payload;

    private BinaryTreeNode<K, V> left;
    private BinaryTreeNode<K, V> right;

    protected BinaryTreeNode(KeyValuePair<K, V> payload)
    {
        this.payload = payload;
    }

    public KeyValuePair<K, V> getPayload()
    {
        return payload;
    }

    public BinaryTreeNode<K, V> getLeftBranch()
    {
        return left;
    }

    public void setLeftBranch(BinaryTreeNode<K, V> left)
    {
        this.left = left;
    }

    public BinaryTreeNode<K, V> getRightBranch()
    {
        return right;
    }

    public void setRightBranch(BinaryTreeNode<K, V> right)
    {
        this.right = right;
    }

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
