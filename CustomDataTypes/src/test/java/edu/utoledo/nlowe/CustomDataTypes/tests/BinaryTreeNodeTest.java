package edu.utoledo.nlowe.CustomDataTypes.tests;

import edu.utoledo.nlowe.CustomDataTypes.BinaryTreeNode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the <code>BinaryTreeNode</code> class
 */
public class BinaryTreeNodeTest
{
    @Test
    public void correctlyDeterminesEquality()
    {
        assertTrue(new BinaryTreeNode<>("foobar").equals(new BinaryTreeNode<>("foobar")));
    }

    @Test
    public void formatsToStringAsJSON()
    {
        assertEquals("{\"payload\": \"foobar\", \"left\": null, \"right\": null}", new BinaryTreeNode<>("foobar").toString());
    }

    @Test
    public void formatsFullToStringAsJSON()
    {
        BinaryTreeNode<String> node = new BinaryTreeNode<>("foobar");
        node.setLeftBranch(new BinaryTreeNode<>("left"));
        node.setRightBranch(new BinaryTreeNode<>("right"));

        assertEquals("{\"payload\": \"foobar\", \"left\": {\"payload\": \"left\", \"left\": null, \"right\": null}, \"right\": {\"payload\": \"right\", \"left\": null, \"right\": null}}", node.toString());
    }
}
