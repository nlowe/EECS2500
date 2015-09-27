package edu.utoledo.nlowe.postfix;

/**
 * A functional interface for Binary Operators (those that take two arguments)
 */
public interface BinaryOperator extends Operator{
    long evaluate(long a, long b);
}
