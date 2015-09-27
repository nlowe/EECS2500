package edu.utoledo.nlowe.postfix;

/**
 * Created by nathan on 9/21/15
 */
public interface BinaryOperator extends Operator{
    int evaluate(int a, int b);
}
