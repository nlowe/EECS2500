package edu.utoledo.nlowe.CustomDataTypes.tests;

import edu.utoledo.nlowe.CustomDataTypes.CustomSortedLinkedList;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for edu.utoledo.nlowe.CustomDataTypes.CustomSortedLinkedList
 */
public class SortedLinkedListTest
{

    @Test
    public void sortsItemsOnInsert()
    {
        CustomSortedLinkedList<Integer> list = new CustomSortedLinkedList<>();

        list.add(5);
        list.add(1);
        list.add(3);
        list.add(12);
        list.add(7);

        assertEquals((Integer) 1, list.getFirst());
        assertEquals((Integer) 3, list.get(1));
        assertEquals((Integer) 5, list.get(2));
        assertEquals((Integer) 7, list.get(3));
        assertEquals((Integer) 12, list.getLast());
    }

    @Test
    public void canReturnEarlyWhenTargetAlreadyInList()
    {
        CustomSortedLinkedList<String> list = new CustomSortedLinkedList<>();

        list.add("a");
        list.addOr("a", (s) -> assertEquals("a", s));

        assertEquals(1, list.size());

        list.addOr("b", (s) -> fail());
        list.addOr("c", (s) -> fail());

        list.addOr("b", (s) -> assertEquals("b", s));
        list.addOr("c", (s) -> assertEquals("c", s));

        assertEquals(3, list.size());
    }

    @Test
    public void throwsExceptionWhenInsertingAtArbitraryIndex()
    {
        try
        {
            CustomSortedLinkedList<String> list = new CustomSortedLinkedList<>();

            list.add("asdf");
            list.add("fdsa");

            list.add(1, "zztop");
            fail();
        }
        catch (Exception e)
        {
            assertTrue(e instanceof IllegalStateException);
            assertEquals("Cannot insert arbitrarily into sorted list", e.getMessage());
        }
    }

    @Test
    public void throwsExceptionWhenChangingAtIndex()
    {
        try
        {
            CustomSortedLinkedList<String> list = new CustomSortedLinkedList<>();

            list.add("asdf");
            list.add("fdsa");

            list.set(1, "zztop");
            fail();
        }
        catch (Exception e)
        {
            assertTrue(e instanceof IllegalStateException);
            assertEquals("Cannot change values in a sorted list. Remove and re-add instead", e.getMessage());
        }
    }

}
