package edu.utoledo.nlowe.CustomDataTypes.tests;
import static org.junit.Assert.*;

import edu.utoledo.nlowe.CustomDataTypes.CustomLinkedList;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by nathan on 9/8/15
 */
public class LinkedListTests {

    private CustomLinkedList<String> list;

    @BeforeClass
    public static void setUpTestCase(){
        System.out.println("=== CustomLinkedList [Tests] ===");
    }

    @Before
    public void setUp(){
        list = new CustomLinkedList<>();
    }

    @Test
    public void canInsertWhenListIsEmpty(){
        assertEquals(0, list.size());
        list.add("asdf");
        assertEquals(1, list.size());
        assertEquals("asdf", list.get(0));
        System.out.println("[+] Can Insert when the list is empty");
    }

    @Test
    public void cannotInsertAtNegativeIndex(){
        try{
            list.add(-1, "asdf");
            fail("[-] Cannot Insert at negative indexes");
        }catch (Exception e){
            assertTrue(e instanceof IndexOutOfBoundsException && e.getMessage().equals("The Index -1 is not in the bounds of the list!"));
            System.out.println("[+] Cannot insert at negative indexes");
        }
    }

    @Test
    public void insertsInTheCorrectOrder() {
        list.add("one");
        list.add("two");
        list.add("three");
        assertEquals("one", list.get(0));
        assertEquals("two", list.get(1));
        assertEquals("three", list.get(2));
        System.out.println("[+] Inserts in the correct order");
    }

    @Test
    public void canInsertInTheMiddle(){
        list.add("one");
        list.add("two");
        list.add("four");

        list.add(2, "three");

        assertEquals("two", list.get(1));
        assertEquals("three", list.get(2));
        assertEquals("four", list.get(3));

        System.out.println("[+] Can Insert in the middle");
    }

    @Test
    public void canInsertAtTheStart(){
        list.add("one");
        assertEquals("one", list.getFirst());

        list.add(0, "first");
        assertEquals("first", list.getFirst());

        System.out.println("[+] Can Insert At the Start");
    }

    @Test
    public void canInsertAtTheEnd(){
        list.add("one");
        list.add("two");
        list.add("three");

        assertEquals("three", list.getLast());

        list.add(list.size(), "last");
        assertEquals("last", list.getLast());

        System.out.println("[+] Can Insert At the End");
    }

    @Test
    public void cannotGetIndexesLessThanZero(){
        list.add("one");
        try{
            list.get(-1);
            fail("[-] Cannot get indexes less than zero");
        }catch (Exception e){
            assertTrue(e instanceof IndexOutOfBoundsException && e.getMessage().equals("The Index -1 is not in the bounds of the list!"));
            System.out.println("[+] Cannot get indexes less than zero");
        }
    }

    @Test
    public void cannotGetIndexesPastEnd(){
        list.add("one");
        try{
            list.get(1);
            fail("[-] Cannot get indexes past the end of the list");
        }catch (Exception e){
            assertTrue(e instanceof IndexOutOfBoundsException && e.getMessage().equals("The Index 1 is not in the bounds of the list!"));
            System.out.println("[+] Cannot get indexes past the end of the list");
        }
    }

    @Test
    public void canUpdateAnExistingValue(){
        list.add("one");
        list.add("foobar");
        list.add("three");

        list.set(1, "two");
        assertEquals("two", list.get(1));
    }

    @Test
    public void cannotUpdateIndexesLessThanZero(){
        list.add("one");
        try{
            list.set(-1, "asdf");
            fail("[-] Cannot update indexes less than zero");
        }catch (Exception e){
            assertTrue(e instanceof IndexOutOfBoundsException && e.getMessage().equals("The Index -1 is not in the bounds of the list!"));
            System.out.println("[+] Cannot update indexes less than zero");
        }
    }

    @Test
    public void cannotUpdateIndexesPastTheEnd(){
        list.add("one");
        try{
            list.set(1, "asdf");
            fail("[-] Cannot update indexes past the end");
        }catch (Exception e){
            assertTrue(e instanceof IndexOutOfBoundsException && e.getMessage().equals("The Index 1 is not in the bounds of the list!"));
            System.out.println("[+] Cannot update indexes past the end");
        }
    }

    @Test
    public void canCheckIfAnElementIsInTheList(){
        assertFalse(list.contains("empty"));

        list.add("a");
        list.add("b");
        list.add("c");

        assertTrue(list.contains("a"));
        assertFalse(list.contains("C"));

        System.out.println("[+] Can verify if an element is in the list");
    }

    @Test
    public void canRemoveFirstElement(){
        list.add("one");
        list.add("two");
        list.add("three");

        list.remove(0);

        assertEquals(2, list.size());
        assertEquals("two", list.getFirst());
        assertEquals("three", list.getLast());

        System.out.println("[+] Can remove the first element");
    }

    @Test
    public void canRemoveMiddleElement(){
        list.add("one");
        list.add("two");
        list.add("three");

        list.remove(1);

        assertEquals(2, list.size());
        assertEquals("one", list.getFirst());
        assertEquals("three", list.getLast());

        System.out.println("[+] Can remove the first element");
    }

    @Test
    public void canRemoveLastElement(){
        list.add("one");
        list.add("two");
        list.add("three");

        list.remove(list.size()-1);

        assertEquals(2, list.size());
        assertEquals("one", list.getFirst());
        assertEquals("two", list.getLast());

        System.out.println("[+] Can remove the first element");
    }

    @Test
    public void cannotRemoveIndexesBelowZero(){
        list.add("one");
        try{
            list.remove(-1);
            fail("[-] Cannot remove indexes less than zero");
        }catch (Exception e){
            assertTrue(e instanceof IndexOutOfBoundsException && e.getMessage().equals("The Index -1 is not in the bounds of the list!"));
            System.out.println("[+] Cannot remove indexes less than zero");
        }
    }

    @Test
    public void cannotRemoveIndexesPastTheEnd(){
        list.add("one");
        try{
            list.remove(1);
            fail("[-] Cannot remove indexes past the end");
        }catch (Exception e){
            assertTrue(e instanceof IndexOutOfBoundsException && e.getMessage().equals("The Index 1 is not in the bounds of the list!"));
            System.out.println("[+] Cannot remove indexes past the end");
        }
    }

    @Test
    public void canClear(){
        list.add("one");
        list.add("two");
        list.clear();

        assertEquals(0, list.size());
        assertEquals(null, list.getFirst());
        assertEquals(null, list.getLast());

        System.out.println("[+] Can clear the list");
    }

    @Test
    public void canIterate(){
        list.add("one");
        list.add("two");
        list.add("three");

        int i=0;
        for(String value : list){
            switch(i){
                case 0: assertEquals("one", value); break;
                case 1: assertEquals("two", value); break;
                case 2: assertEquals("three", value); break;
                default: fail("[-] Can Iterate: Found an element I didn't add!");
            }

            i++;
        }

        System.out.println("[+] Can Iterate over elements");
    }

    @Test
    public void canStream(){
        list.add("one");
        list.add("two");
        list.add("three");

        final boolean[] found = {false, false, false};

        list.forEach((s) -> {
            switch (s) {
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
                    fail("[-] Can Stream: Found an element I didn't add!");
            }
        });

        assertTrue(found[0] && found[1] && found[2]);

        System.out.println("[+] Can Stream over elements");
    }

    @AfterClass
    public static void tearDownTestCase(){
        System.out.println("=== CustomLinkedList [Tests: Finished] ===");
    }

}
