package edu.utoledo.nlowe.CustomDataTypes.tests;

import edu.utoledo.nlowe.CustomDataTypes.BinarySearchTree;
import edu.utoledo.nlowe.CustomDataTypes.Iterator.BinaryTreeIterator;
import edu.utoledo.nlowe.CustomDataTypes.OrderedKeyValuePair;
import edu.utoledo.nlowe.CustomDataTypes.TraversalOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Tests for the BinarySearchTree
 */
public class BinarySearchTreeTest
{

    private BinarySearchTree<String> map;

    @Before
    public void setUp()
    {
        map = new BinarySearchTree<>();

        map.add("f");
        map.add("b");
        map.add("g");
        map.add("a");
        map.add("d");
        map.add("i");
        map.add("c");
        map.add("e");
        map.add("h");
    }

    @Test
    public void storesElementsInCorrectOrder()
    {
        Iterator<String> elements = map.iterator();

        assertEquals("a", elements.next());
        assertEquals("b", elements.next());
        assertEquals("c", elements.next());
        assertEquals("d", elements.next());
        assertEquals("e", elements.next());
        assertEquals("f", elements.next());
        assertEquals("g", elements.next());
        assertEquals("h", elements.next());
        assertEquals("i", elements.next());

        assertFalse(elements.hasNext());
    }

    @Test
    public void canTraverseBigTree()
    {
        BinarySearchTree<Integer> bigTree = new BinarySearchTree<>();

        new Random().ints()
                .limit(500)
                .boxed()
                .forEach(bigTree::add);

        BinaryTreeIterator<Integer> preOrder = bigTree.traverse(TraversalOrder.PREORDER);
        BinaryTreeIterator<Integer> inOrder = bigTree.traverse(TraversalOrder.INORDER);
        BinaryTreeIterator<Integer> postOrder = bigTree.traverse(TraversalOrder.POSTORDER);

        assertEquals(TraversalOrder.PREORDER, preOrder.getTraversalOrder());
        assertEquals(TraversalOrder.INORDER, inOrder.getTraversalOrder());
        assertEquals(TraversalOrder.POSTORDER, postOrder.getTraversalOrder());

        int count = 0;

        while(count < bigTree.getNodeCount())
        {
            count++;

            if(!preOrder.hasNext())
            {
                System.err.println("Preorder failed to find element " + count);
                fail();
            }
            else if(!inOrder.hasNext())
            {
                System.err.println("Inorder failed to find element " + count);
                fail();
            }
            else if(!postOrder.hasNext())
            {
                System.err.println("Postorder failed to find element " + count);
                fail();
            }

            preOrder.next();
            inOrder.next();
            postOrder.next();
        }

        assertFalse(preOrder.hasNext());
        assertFalse(inOrder.hasNext());
        assertFalse(postOrder.hasNext());
    }

    @Test
    public void canTraversePreorder()
    {
        Iterator<String> elements = map.traverse(TraversalOrder.PREORDER);

        assertEquals("f", elements.next());
        assertEquals("b", elements.next());
        assertEquals("a", elements.next());
        assertEquals("d", elements.next());
        assertEquals("c", elements.next());
        assertEquals("e", elements.next());
        assertEquals("g", elements.next());
        assertEquals("i", elements.next());
        assertEquals("h", elements.next());

        assertFalse(elements.hasNext());
    }

    @Test
    public void canTraversePostorder()
    {
        Iterator<String> elements = map.traverse(TraversalOrder.POSTORDER);

        assertEquals("a", elements.next());
        assertEquals("c", elements.next());
        assertEquals("e", elements.next());
        assertEquals("d", elements.next());
        assertEquals("b", elements.next());
        assertEquals("h", elements.next());
        assertEquals("i", elements.next());
        assertEquals("g", elements.next());
        assertEquals("f", elements.next());

        assertFalse(elements.hasNext());
    }

    @Test
    public void supportsMutator()
    {
        BinarySearchTree<OrderedKeyValuePair<String, Integer>> tree = new BinarySearchTree<>();

        tree.add(new OrderedKeyValuePair<>("b", 1));
        tree.add(new OrderedKeyValuePair<>("a", 1));
        tree.add(new OrderedKeyValuePair<>("c", 1));

        for(OrderedKeyValuePair<String,Integer> e : tree)
        {
            switch(e.getKey())
            {
                case "a":
                case "b":
                case "c": assertEquals((Integer)1, e.getValue()); break;
                default: fail();
            }
        }

        tree.addOr(new OrderedKeyValuePair<>("c", 1), (kvp) -> kvp.setValue(999));

        for(OrderedKeyValuePair<String,Integer> e : tree)
        {
            switch(e.getKey())
            {
                case "a":
                case "b": assertEquals((Integer)1, e.getValue()); break;
                case "c": assertEquals((Integer)999, e.getValue()); break;
                default: fail();
            }
        }
    }

    @Test
    public void canClear()
    {
        map.clear();

        assertEquals(0, map.getNodeCount());
        assertFalse(map.iterator().hasNext());
    }

    @Test
    public void keepsTrackOfStats()
    {
        assertEquals(9, map.getReferenceAssignmentCount());
        assertEquals(17, map.getComparisonCount());
    }
}
