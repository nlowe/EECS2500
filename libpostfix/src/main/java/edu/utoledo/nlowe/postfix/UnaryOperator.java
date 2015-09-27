package edu.utoledo.nlowe.postfix;

/**
 * A functional interface for Binary Operators (those that take one argument)
 */
public interface UnaryOperator extends Operator {
    int evaluate(int input);
}
