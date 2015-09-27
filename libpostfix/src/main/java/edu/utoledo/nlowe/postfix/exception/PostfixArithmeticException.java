package edu.utoledo.nlowe.postfix.exception;

/**
 * Created by nathan on 9/27/15
 */
public class PostfixArithmeticException extends ArithmeticException
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
