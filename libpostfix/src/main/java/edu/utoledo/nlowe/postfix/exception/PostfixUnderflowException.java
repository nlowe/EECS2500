package edu.utoledo.nlowe.postfix.exception;

/**
 * Indicates the result of the evaluated postfix expression has underflowed the size of an integer.
 * The result (as a long), is available in <code>getResult()</code>
 */
public class PostfixUnderflowException extends PostfixArithmeticException
{
    public PostfixUnderflowException(long result, String message)
    {
        super(result, message);
    }
}
