package edu.utoledo.nlowe.postfix.exception;

/**
 * Indicates an exception has occurred during the evaluation of a postfix expression
 *
 * If applicable, the result is stored in the exception and can be retrieved by <code>getResult()</code>
 */
public abstract class PostfixArithmeticException extends ArithmeticException
{

    private final long result;

    public PostfixArithmeticException(long result, String message)
    {
        super(message);
        this.result = result;
    }

    public long getResult()
    {
        return result;
    }
}
