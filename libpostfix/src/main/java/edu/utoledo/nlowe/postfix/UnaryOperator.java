package edu.utoledo.nlowe.postfix;

/**
 * Created by nathan on 9/21/15
 */
public interface UnaryOperator extends Operator {
    int evaluate(int input);
}
