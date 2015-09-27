package edu.utoledo.nlowe.postfix.exception;

/**
 * Created by nathan on 9/27/15
 */
public class PostfixUnderflowException extends PostfixArithmeticException {
    public PostfixUnderflowException(long result, String message) {
        super(result, message);
    }
}
