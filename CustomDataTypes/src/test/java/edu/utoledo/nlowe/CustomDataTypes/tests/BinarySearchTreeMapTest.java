package edu.utoledo.nlowe.CustomDataTypes.tests;

import edu.utoledo.nlowe.CustomDataTypes.BinarySearchTreeMap;
import edu.utoledo.nlowe.CustomDataTypes.KeyValuePair;
import edu.utoledo.nlowe.CustomDataTypes.TraversalOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

/**
 * Tests for the BinarySearchTreeMap
 */
public class BinarySearchTreeMapTest
{

    private BinarySearchTreeMap<String, Integer> map;

    @Before
    public void setUp()
    {
        map = new BinarySearchTreeMap<>();

        map.put("f", 1);
        map.put("b", 1);
        map.put("g", 1);
        map.put("a", 1);
        map.put("d", 1);
        map.put("i", 1);
        map.put("c", 1);
        map.put("e", 1);
        map.put("h", 1);
    }

    @Test
    public void storesElementsInCorrectOrder()
    {
        Iterator<KeyValuePair<String,Integer>> elements = map.iterator();

        assertEquals("a", elements.next().getKey());
        assertEquals("b", elements.next().getKey());
        assertEquals("c", elements.next().getKey());
        assertEquals("d", elements.next().getKey());
        assertEquals("e", elements.next().getKey());
        assertEquals("f", elements.next().getKey());
        assertEquals("g", elements.next().getKey());
        assertEquals("h", elements.next().getKey());
        assertEquals("i", elements.next().getKey());

        assertFalse(elements.hasNext());
    }

    @Test
    public void canTraversePreorder()
    {
        Iterator<KeyValuePair<String,Integer>> elements = map.traverse(TraversalOrder.PREORDER);

        assertEquals("f", elements.next().getKey());
        assertEquals("b", elements.next().getKey());
        assertEquals("a", elements.next().getKey());
        assertEquals("d", elements.next().getKey());
        assertEquals("c", elements.next().getKey());
        assertEquals("e", elements.next().getKey());
        assertEquals("g", elements.next().getKey());
        assertEquals("i", elements.next().getKey());
        assertEquals("h", elements.next().getKey());

        assertFalse(elements.hasNext());
    }

    @Test
    public void canTraversePostorder()
    {
        Iterator<KeyValuePair<String,Integer>> elements = map.traverse(TraversalOrder.POSTORDER);

        assertEquals("a", elements.next().getKey());
        assertEquals("c", elements.next().getKey());
        assertEquals("e", elements.next().getKey());
        assertEquals("d", elements.next().getKey());
        assertEquals("b", elements.next().getKey());
        assertEquals("h", elements.next().getKey());
        assertEquals("i", elements.next().getKey());
        assertEquals("g", elements.next().getKey());
        assertEquals("f", elements.next().getKey());

        assertFalse(elements.hasNext());
    }


    @Test
    public void canChangeValues()
    {
        map.put("d", 3);
        map.put("h", 9);
        map.put("f", 11);

        assertEquals((Integer) 3, map.get("d"));
        assertEquals((Integer) 9, map.get("h"));
        assertEquals((Integer) 11, map.get("f"));
    }

    @Test
    public void returnsNullWhenNotFound()
    {
        assertNull(map.get("z"));
    }

    @Test
    public void supportsPutByKVP()
    {
        map.put(new KeyValuePair<>("z", 64));

        assertEquals((Integer) 64, map.get("z"));
    }

    @Test
    public void supportsMutator()
    {
        map.put("f", 2, (e) -> e.setValue(e.getValue() * 9));

        assertEquals((Integer) 9, map.get("f"));
    }

    @Test
    public void supportsMutatorByKVP()
    {
        map.put(new KeyValuePair<>("f", 2), (e) -> e.setValue(e.getValue() * 9));

        assertEquals((Integer) 9, map.get("f"));
    }

    @Test
    public void keepsTrackOfStats()
    {
        assertEquals(9, map.getReferenceAssignmentCount());
        assertEquals(17, map.getComparisonCount());
    }
}
