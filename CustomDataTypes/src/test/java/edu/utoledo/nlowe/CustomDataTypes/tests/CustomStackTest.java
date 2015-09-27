package edu.utoledo.nlowe.CustomDataTypes.tests;
import static org.junit.Assert.*;

import edu.utoledo.nlowe.CustomDataTypes.CustomStack;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by nathan on 9/26/15
 */
public class CustomStackTest {

    private CustomStack<String> stack;

    @BeforeClass
    public static void setUpTestCase(){
        System.out.println("=== CustomStack [Tests] ===");
    }

    @Before
    public void setUp(){
        stack = new CustomStack<>();
    }

    @Test
    public void worksLikeAStack(){
        stack.push("a");
        stack.push("b");
        stack.push("c");

        assertEquals("c", stack.peek());
        assertEquals("c", stack.pop());
        assertEquals("b", stack.peek());
        assertEquals("b", stack.pop());
        assertEquals("a", stack.peek());
        assertEquals("a", stack.pop());

        assertTrue(stack.size() == 0);

        System.out.println("[+] Works like a stack");
    }

    @AfterClass
    public static void tearDownTestCase(){
        System.out.println("=== CustomStack [Tests: Finished] ===");
    }

}
