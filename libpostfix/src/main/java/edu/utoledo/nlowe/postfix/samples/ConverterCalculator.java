package edu.utoledo.nlowe.postfix.samples;

import edu.utoledo.nlowe.postfix.PostfixEngine;
import edu.utoledo.nlowe.postfix.exception.PostfixArithmeticException;
import edu.utoledo.nlowe.postfix.exception.PostfixOverflowException;

import java.util.Arrays;
import java.util.Scanner;

/**
 * The calculator for Assignment #1
 * <p>
 * Reads an infix expression from standard input and writes the following to standard output:
 * <ul>
 *     <li>The equivalent expression in postfix notation</li>
 *     <li>The value of the converted expression</li>
 * </ul>
 */
public class ConverterCalculator
{

    public static void main(String[] args)
    {
        PostfixEngine engine = new PostfixEngine();

        // Read the expression in on standard input
        // Either through command line arguments, or interactively via a scanner
        String expression;
        if (args.length > 0)
        {
            // The expression was passed on the command line
            StringBuilder expressionBuilder = new StringBuilder();
            Arrays.stream(args).forEach(expressionBuilder::append);
            expression = expressionBuilder.toString();
        }
        else
        {
            try (Scanner in = new Scanner(System.in))
            {
                System.out.print("Enter the infix expression to convert and evaluate: ");
                expression = in.nextLine();
            }
        }

        // Try to evaluate the expression, and warn on under/overflow or invalid input
        try
        {
            String postfix = engine.convertInfixExpression(expression);
            System.out.println("The equivalent postfix expression is: " + postfix);

            long result = engine.evaluate(postfix);
            System.out.println("The expression evaluates to: " + result);
        }
        catch (PostfixArithmeticException e)
        {
            System.out.println("WARNING: Integer " + (e instanceof PostfixOverflowException ? "Overflow" : "Underflow") + " detected! The expression evaluates to: " + e.getResult());
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Encountered an error while evaluating the postfix expression: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
