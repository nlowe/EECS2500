package edu.utoledo.nlowe.postfix.exception;

/**
 * Indicates the result of the evaluated postfix expression has overflowed the size of an integer.
 * The result (as a long), is available in <code>getResult()</code>
 */
public class PostfixOverflowException extends PostfixArithmeticException
{
    public PostfixOverflowException(long result, String message)
    {
        super(result, message);
    }
}
