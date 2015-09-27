package edu.utoledo.nlowe.postfix.exception;

/**
 * Created by nathan on 9/27/15
 */
public class PostfixOverflowException extends PostfixArithmeticException
{
    public PostfixOverflowException(long result, String message)
    {
        super(result, message);
    }
}
