package edu.utoledo.nlowe.CustomDataTypes;

/**
 * The order in which to traverse a tree
 */
public enum TraversalOrder
{
    /** Visit the current element first, then the left subtree, then finally the right subtree */
    PREORDER,
    /** Visit the left subtree first, then the current element, then finally the right subtree */
    INORDER,
    /** Visit the left subtree first, then the right subtree, then finally the current element */
    POSTORDER;
}
