package edu.utoledo.nlowe.postfix.tests;

import edu.utoledo.nlowe.postfix.PostfixEngine;
import edu.utoledo.nlowe.postfix.exception.PostfixArithmeticException;
import edu.utoledo.nlowe.postfix.exception.PostfixOverflowException;
import edu.utoledo.nlowe.postfix.exception.PostfixUnderflowException;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by nathan on 9/17/15
 */
public class PostfixEngineTest
{

    private PostfixEngine engine;

    @Before
    public void setUp()
    {
        engine = new PostfixEngine();
    }

    @Test
    public void cannotConvertEmptyInfixExpression()
    {
        try
        {
            engine.convertInfixExpression("");
            fail();
        }
        catch (Exception ex)
        {
            assertTrue(ex instanceof IllegalArgumentException);
            assertEquals("Nothing to convert", ex.getMessage());
        }

        try
        {
            engine.convertInfixExpression(null);
            fail();
        }
        catch (Exception ex)
        {
            assertTrue(ex instanceof IllegalArgumentException);
            assertEquals("Nothing to convert", ex.getMessage());
        }
    }

    @Test
    public void canConvertInfixExpression()
    {
        assertEquals("2 3 + 4 2 % *", engine.convertInfixExpression("( 2 + 3 ) * ( 4 % 2 )"));
    }

    @Test
    public void canEvaluateInfixExpression()
    {
        assertEquals(-2048, engine.evaluateInfix("( 0 - 1 ) * ( 2 < 10 )"));
    }

    @Test
    public void canEvaluateNestedInfixExpression()
    {
        assertEquals(7, engine.evaluateInfix("3*2-1+2"));
    }

    @Test
    public void cannotEvaluateInfixWithTooManyLiterals()
    {
        try
        {
            engine.evaluateInfix("2+2 4");
            fail();
        }
        catch (Exception e)
        {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("Malformed postfix expression (too many literals): '2 2 4 +'", e.getMessage());
        }
    }

    @Test
    public void canEvaluateUnaryOperatorWithSpaces()
    {
        assertEquals(2, engine.evaluateInfix("Q 4"));
        assertEquals(3, engine.evaluateInfix("C    27"));
    }

    @Test
    public void canConvertMixedSeparatorStyles()
    {
        assertEquals("12 3 45 6", engine.convertInfixExpression("12 3 45 6"));
    }

    @Test
    public void unaryOperatorsRequireAtLeastOneOperand()
    {
        try
        {
            engine.evaluate("Q");
            fail();
        }
        catch (Exception e)
        {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("Malformed postfix expression (not enough literals): 'Q'", e.getMessage());
        }

        try
        {
            engine.evaluate("Q   ");
            fail();
        }
        catch (Exception e)
        {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("Malformed postfix expression (not enough literals): 'Q   '", e.getMessage());
        }
    }

    @Test
    public void binaryOperatorsCannotComeBeforeOperands()
    {
        try
        {
            engine.convertInfixExpression("+ 3 4");
            fail();
        }
        catch(Exception e)
        {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("Malformed infix expression (missing operand for binary operator): '+ 3 4'", e.getMessage());
        }
    }

    @Test
    public void failsWithUnaryOperatorsMissingOperands()
    {
        try
        {
            engine.evaluateInfix("4Q");
            fail();
        }
        catch (Exception e)
        {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("Malformed infix expression (missing operand for unary operator): '4Q'", e.getMessage());
        }

        try
        {
            engine.evaluateInfix("27C");
            fail();
        }
        catch (Exception e)
        {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("Malformed infix expression (missing operand for unary operator): '27C'", e.getMessage());
        }
    }

    @Test
    public void failsToConvertUnmatchedParenthesis()
    {
        try
        {
            engine.convertInfixExpression("2*(");
            fail();
        }
        catch (Exception e)
        {
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals("Malformed infix expression (unmatched parenthesis): '2*('", e.getMessage());
        }
    }

    @Test
    public void failsToConvertIllegalInfixExpression()
    {
        try
        {
            engine.convertInfixExpression("2@3");
            fail();
        }
        catch (Exception ex)
        {
            assertTrue(ex instanceof IllegalArgumentException);
            assertEquals("Unrecognized token '@'", ex.getMessage());
        }
    }

    @Test
    public void canEvaluateSingleOperator()
    {
        assertEquals(4, engine.evaluate("2 2 +"));
        assertEquals(2, engine.evaluate("4 2 -"));
        assertEquals(4, engine.evaluate("2 2 *"));
        assertEquals(2, engine.evaluate("4 2 /"));
        assertEquals(1, engine.evaluate("3 2 %"));
        assertEquals(4, engine.evaluate("2 2 ^"));
    }

    @Test
    public void unaryOperatorTest()
    {
        assertEquals(2, engine.evaluate("4 Q"));
    }

    @Test
    public void canEvaluateWithManySpaces()
    {
        assertEquals(4, engine.evaluate("2           2      +"));
    }

    @Test
    public void canDetectOverflow()
    {
        try
        {
            engine.evaluate("2 31 ^ 1 +");
            fail();
        }
        catch (PostfixArithmeticException ex)
        {
            assertTrue(ex instanceof PostfixOverflowException);
            assertEquals((long) Math.pow(2, 31) + 1L, ex.getResult());
            assertEquals("Integer overflow while evaluating expression '2 31 ^ 1 +'", ex.getMessage());
        }
    }

    @Test
    public void canDetectUnderflow()
    {
        try
        {
            engine.evaluate("2 31 ^ -1 * 1 -");
            fail();
        }
        catch (PostfixArithmeticException ex)
        {
            assertTrue(ex instanceof PostfixUnderflowException);
            assertEquals(-1L * (long) Math.pow(2, 31) - 1L, ex.getResult());
            assertEquals("Integer underflow while evaluating expression '2 31 ^ -1 * 1 -'", ex.getMessage());
        }
    }

    @Test
    public void supportsRequiredExamples()
    {
        assertEquals(0, engine.evaluateInfix("( 2 + 3 ) * ( 4 % 2 )"));
        assertEquals(0, engine.evaluateInfix("(2+3)*(4%2)"));
        assertEquals(3, engine.evaluateInfix("Q(2+(4*2))"));
        assertEquals(3, engine.evaluateInfix("C(27)"));
    }

    @Test
    public void failsWithTooManyOperators()
    {
        try
        {
            engine.evaluate("1 1 + +");
        }
        catch (IllegalArgumentException ex)
        {
            assertEquals("Malformed postfix expression (not enough literals): '1 1 + +'", ex.getMessage());
        }
    }

    @Test
    public void failsWithTooManyLiterals()
    {
        try
        {
            engine.evaluate("1 1 1 +");
        }
        catch (IllegalArgumentException ex)
        {
            assertEquals("Malformed postfix expression (too many literals): '1 1 1 +'", ex.getMessage());
        }
    }

    @Test
    public void failsWithIllegalOperator()
    {
        try
        {
            engine.evaluate("1 1 @");
        }
        catch (IllegalArgumentException ex)
        {
            assertEquals("Malformed postfix expression (unrecognized token @): '1 1 @'", ex.getMessage());
        }
    }

    @Test
    public void throwsExceptionTryingToGetIllegalArgument()
    {
        try
        {
            engine.getEvaluatorFunction("@");
        }
        catch (IllegalArgumentException ex)
        {
            assertEquals("Undefined postfix operator: @", ex.getMessage());
        }
    }

    @Test
    public void returnsAllSupportedOperators()
    {
        Set<String> operators = engine.getSupportedOperators();
        assertTrue(
                operators.size() == 11 &&
                        operators.contains("+") &&
                        operators.contains("-") &&
                        operators.contains("*") &&
                        operators.contains("x") &&
                        operators.contains("/") &&
                        operators.contains("%") &&
                        operators.contains("^") &&
                        operators.contains("<") &&
                        operators.contains(">") &&
                        operators.contains("Q") &&
                        operators.contains("C")
                  );
    }

    @Test
    public void canParseComplexExample()
    {
        assertEquals(1, engine.evaluate("5 3 + 12 * 3 / 2 < 4 > 4 - Q 25 + C 2 ^ 2 %"));
    }
}
