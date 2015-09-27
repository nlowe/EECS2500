package edu.utoledo.nlowe.postfix.tests;

import edu.utoledo.nlowe.postfix.samples.ConverterCalculator;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by nathan on 9/27/15
 */
public class ConverterCalculatorTest
{

    private ByteArrayOutputStream out;

    @Before
    public void setUp()
    {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @Test
    public void canEvaluateFromArgs()
    {
        ConverterCalculator.main(new String[]{"2", "+", "2"});

        String expected = String.format(
                "The equivalent postfix expression is: 2 2 +%n" +
                "The expression evaluates to: 4%n"
        );
        assertEquals(expected, out.toString());
    }

    @Test
    public void canEvaluateFromStandardIn()
    {
        System.setIn(new ByteArrayInputStream("2 + 2\n".getBytes()));
        ConverterCalculator.main(new String[]{});

        String expected = String.format(
                "Enter the infix expression to convert and evaluate: The equivalent postfix expression is: 2 2 +%n" +
                "The expression evaluates to: 4%n"
        );
        assertEquals(expected, out.toString());
    }

    @Test
    public void warnsAboutUnderflow()
    {
        ConverterCalculator.main(new String[]{"(0-1)*(2<31)"});

        String expected = String.format(
                "The equivalent postfix expression is: 0 1 - 2 31 < *%n" +
                "WARNING: Integer Underflow detected! The expression evaluates to: -4294967296%n"
        );
        assertEquals(expected, out.toString());
    }

    @Test
    public void warnsAboutOverflow()
    {
        ConverterCalculator.main(new String[]{"(2<31)+1"});

        String expected = String.format(
                "The equivalent postfix expression is: 2 31 < 1 +%n" +
                "WARNING: Integer Overflow detected! The expression evaluates to: 4294967297%n"
        );
        assertEquals(expected, out.toString());
    }

    @Test
    public void warnsAboutUnknownOperators()
    {
        System.setErr(new PrintStream(new ByteArrayOutputStream()));
        ConverterCalculator.main(new String[]{"2@3"});

        String expected = String.format(
                "Encountered an error while evaluating the postfix expression: Unrecognized token '@'%n"
        );
        assertEquals(expected, out.toString());
    }

    @Test
    public void canInstantiateClass()
    {
        // Code coverage expects the class to be instantiated at least once
        // Since the only method is the main method, we just need to create an object to trick code coverage
        new ConverterCalculator();
    }
}
