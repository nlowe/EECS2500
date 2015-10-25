package edu.utoledo.nlowe.CustomDataTypes.tests;

import edu.utoledo.nlowe.CustomDataTypes.CustomLinkedList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for edu.utoledo.nlowe.CustomDataTypes.CustomLinkedList
 */
public class LinkedListTest
{

    private CustomLinkedList<String> list;

    @Before
    public void setUp()
    {
        list = new CustomLinkedList<>();
    }

    @Test
    public void canInsertWhenListIsEmpty()
    {
        assertEquals(0, list.size());
        list.add("asdf");
        assertEquals(1, list.size());
        assertEquals("asdf", list.get(0));
    }

    @Test
    public void cannotInsertAtNegativeIndex()
    {
        try
        {
            list.add(-1, "asdf");
            fail();
        }
        catch (Exception e)
        {
            assertTrue(e instanceof IndexOutOfBoundsException && e.getMessage().equals("The Index -1 is not in the bounds of the list!"));
        }
    }

    @Test
    public void insertsInTheCorrectOrder()
    {
        list.add("one");
        list.add("two");
        list.add("three");
        assertEquals("one", list.get(0));
        assertEquals("two", list.get(1));
        assertEquals("three", list.get(2));
    }

    @Test
    public void canInsertInTheMiddle()
    {
        list.add("one");
        list.add("two");
        list.add("four");

        list.add(2, "three");

        assertEquals("two", list.get(1));
        assertEquals("three", list.get(2));
        assertEquals("four", list.get(3));
    }

    @Test
    public void canInsertAtTheStart()
    {
        list.add("one");
        assertEquals("one", list.getFirst());

        list.add(0, "first");
        assertEquals("first", list.getFirst());
    }

    @Test
    public void canInsertAtTheEnd()
    {
        list.add("one");
        list.add("two");
        list.add("three");

        assertEquals("three", list.getLast());

        list.add(list.size(), "last");
        assertEquals("last", list.getLast());
    }

    @Test
    public void cannotGetIndexesLessThanZero()
    {
        list.add("one");
        try
        {
            list.get(-1);
            fail();
        }
        catch (Exception e)
        {
            assertTrue(e instanceof IndexOutOfBoundsException);
            assertEquals(e.getMessage(), "The Index -1 is not in the bounds of the list!");
        }
    }

    @Test
    public void cannotGetIndexesPastEnd()
    {
        list.add("one");
        try
        {
            list.get(1);
            fail("[-] Cannot get indexes past the end of the list");
        }
        catch (Exception e)
        {
            assertTrue(e instanceof IndexOutOfBoundsException);
            assertEquals(e.getMessage(), "The Index 1 is not in the bounds of the list!");
        }
    }

    @Test
    public void canUpdateAnExistingValue()
    {
        list.add("one");
        list.add("foobar");
        list.add("three");

        list.set(1, "two");
        assertEquals("two", list.get(1));
    }

    @Test
    public void cannotUpdateIndexesLessThanZero()
    {
        list.add("one");
        try
        {
            list.set(-1, "asdf");
            fail("[-] Cannot update indexes less than zero");
        }
        catch (Exception e)
        {
            assertTrue(e instanceof IndexOutOfBoundsException);
            assertEquals(e.getMessage(), "The Index -1 is not in the bounds of the list!");
        }
    }

    @Test
    public void cannotUpdateIndexesPastTheEnd()
    {
        list.add("one");
        try
        {
            list.set(1, "asdf");
            fail("[-] Cannot update indexes past the end");
        }
        catch (Exception e)
        {
            assertTrue(e instanceof IndexOutOfBoundsException);
            assertEquals(e.getMessage(), "The Index 1 is not in the bounds of the list!");
        }
    }

    @Test
    public void canCheckIfAnElementIsInTheList()
    {
        assertFalse(list.contains("empty"));

        list.add("a");
        list.add("b");
        list.add("c");

        assertTrue(list.contains("a"));
        assertFalse(list.contains("C"));
    }

    @Test
    public void canRemoveFirstElement()
    {
        list.add("one");
        list.add("two");
        list.add("three");

        list.remove(0);

        assertEquals(2, list.size());
        assertEquals("two", list.getFirst());
        assertEquals("three", list.getLast());
    }

    @Test
    public void canRemoveMiddleElement()
    {
        list.add("one");
        list.add("two");
        list.add("three");

        list.remove(1);

        assertEquals(2, list.size());
        assertEquals("one", list.getFirst());
        assertEquals("three", list.getLast());
    }

    @Test
    public void canRemoveLastElement()
    {
        list.add("one");
        list.add("two");
        list.add("three");

        list.remove(list.size() - 1);

        assertEquals(2, list.size());
        assertEquals("one", list.getFirst());
        assertEquals("two", list.getLast());
    }

    @Test
    public void cannotRemoveIndexesBelowZero()
    {
        list.add("one");
        try
        {
            list.remove(-1);
            fail();
        }
        catch (Exception e)
        {
            assertTrue(e instanceof IndexOutOfBoundsException);
            assertEquals(e.getMessage(), "The Index -1 is not in the bounds of the list!");
        }
    }

    @Test
    public void cannotRemoveIndexesPastTheEnd()
    {
        list.add("one");
        try
        {
            list.remove(1);
            fail();
        }
        catch (Exception e)
        {
            assertTrue(e instanceof IndexOutOfBoundsException);
            assertEquals(e.getMessage(), "The Index 1 is not in the bounds of the list!");
        }
    }

    @Test
    public void canClear()
    {
        list.add("one");
        list.add("two");
        list.clear();

        assertEquals(0, list.size());
        assertEquals(null, list.getFirst());
        assertEquals(null, list.getLast());
    }

    @Test
    public void canIterate()
    {
        list.add("one");
        list.add("two");
        list.add("three");

        int i = 0;
        for (String value : list)
        {
            switch (i)
            {
                case 0:
                    assertEquals("one", value);
                    break;
                case 1:
                    assertEquals("two", value);
                    break;
                case 2:
                    assertEquals("three", value);
                    break;
                default:
                    fail();
            }

            i++;
        }
    }

    @Test
    public void canStream()
    {
        list.add("one");
        list.add("two");
        list.add("three");

        final boolean[] found = {false, false, false};

        list.forEach((s) -> {
            switch (s)
            {
                case "one":
                    found[0] = true;
                    break;
                case "two":
                    found[1] = true;
                    break;
                case "three":
                    found[2] = true;
                    break;
                default:
                    fail();
            }
        });

        assertTrue(found[0] && found[1] && found[2]);
    }
}
