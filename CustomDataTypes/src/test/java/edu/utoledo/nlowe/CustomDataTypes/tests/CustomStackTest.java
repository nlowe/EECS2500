package edu.utoledo.nlowe.CustomDataTypes.tests;

import edu.utoledo.nlowe.CustomDataTypes.CustomStack;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for edu.utoledo.nlowe.CustomDataTypes.CustomStack
 */
public class CustomStackTest
{

    private CustomStack<String> stack;

    @Before
    public void setUp()
    {
        stack = new CustomStack<>();
    }

    @Test
    public void worksLikeAStack()
    {
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
    }
}
