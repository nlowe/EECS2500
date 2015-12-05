package edu.utoledo.nlowe.CustomDataTypes;

/**
 * A node in a tree
 */
public class TreeNode<K, V>
{
    private final KeyValuePair<K, V> payload;

    private TreeNode<K, V> left;
    private TreeNode<K, V> right;

    protected TreeNode(KeyValuePair<K, V> payload)
    {
        this.payload = payload;
    }

    public KeyValuePair<K, V> getPayload()
    {
        return payload;
    }

    public TreeNode<K, V> getLeftBranch()
    {
        return left;
    }

    public void setLeftBranch(TreeNode<K, V> left)
    {
        this.left = left;
    }

    public TreeNode<K, V> getRightBranch()
    {
        return right;
    }

    public void setRightBranch(TreeNode<K, V> right)
    {
        this.right = right;
    }

    public void graft(TreeNode<K, V> branch, boolean left)
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

    public boolean equals(TreeNode<K, V> o)
    {
        return o != null && payload.getKey().equals(o.payload.getKey());
    }
}
