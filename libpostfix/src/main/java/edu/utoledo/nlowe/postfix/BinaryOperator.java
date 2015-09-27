package edu.utoledo.nlowe.postfix;

/**
 * A functional interface for Binary Operators (those that take two arguments)
 */
public interface BinaryOperator extends Operator{
    int evaluate(int a, int b);
}
