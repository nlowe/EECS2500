package edu.utoledo.nlowe.postfix;

import edu.utoledo.nlowe.CustomDataTypes.CustomStack;
import edu.utoledo.nlowe.postfix.exception.PostfixArithmeticException;
import edu.utoledo.nlowe.postfix.exception.PostfixOverflowException;
import edu.utoledo.nlowe.postfix.exception.PostfixUnderflowException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * A math engine that accepts string expressions in postfix notation as an integer.
 * <p>
 * All literals must be integers, and all operations will be rounded to an integer.
 * <p>
 * The following binary operations are supported by default:
 * * '+'        Addition
 * * '-'        Subtraction
 * * 'x' or '*' Multiplication
 * * '/'        Division
 * * '%'        Modulus
 * * '^'        Exponentiation
 * * '<'        Left Shift
 * * '>'        Right Shift
 * <p>
 * The following unary operators are supported by default:
 * * 'Q'        Square Root
 * * 'C'        Cube Root
 * <p>
 * Additional operators may be registered by calling <code>register()</code> and
 * passing a lambda for either a <code>BinaryOperator</code> or a <code>UnaryOperator</code>
 */
public class PostfixEngine
{

    /** A regular expression that matches a single integer (positive, or negative) */
    public static final String NUMERIC_REGEX = "^(-)?[0-9]+$";

    /** A regular expression that matches oen or more spaces or tabs. Used to split tokens in postfix notation */
    public static final String TOKEN_SEPARATOR_REGEX = "[ \\t]+";

    /** All operators registered with the engine */
    private final HashMap<String, Operator> operators = new HashMap<>();

    public PostfixEngine()
    {
        // Register default valid operators
        register("+", (a, b) -> a + b);
        register("-", (a, b) -> a - b);
        register("x", (a, b) -> a * b);
        register("*", (a, b) -> a * b);
        register("/", (a, b) -> a / b);
        register("%", (a, b) -> a % b);
        register("^", (a, b) -> (long) Math.pow(a, b));
        register("<", (a, b) -> a << b);
        register(">", (a, b) -> a >> b);
        register("Q", (a) -> (long) Math.sqrt(a));
        register("C", (a) -> (long) Math.cbrt(a));
    }

    /**
     * Converts the given expression from infix notation to postfix notation
     *
     * @param expression a valid expression in infix notation
     * @return The equivalent postfix expression
     */
    public String convertInfixExpression(String expression)
    {
        if (expression == null || expression.length() == 0)
        {
            throw new IllegalArgumentException("Nothing to convert");
        }

        StringBuilder result = new StringBuilder();
        CustomStack<String> buffer = new CustomStack<>();

        // Not all infix expressions are delimiated by tabs or spaces.
        // Strip them out and parse the expression character by character
        String simplifiedExpression = expression.replaceAll(TOKEN_SEPARATOR_REGEX, "");

        int index = 0;
        do
        {
            String token = null;

            if (simplifiedExpression.charAt(index) == '(' ||
                    simplifiedExpression.charAt(index) == ')' ||
                    isValidOperator(String.valueOf(simplifiedExpression.charAt(index)))
                    )
            {
                // The token is just a parenthesis or operator
                token = String.valueOf(simplifiedExpression.charAt(index++));
            }
            else if (String.valueOf(simplifiedExpression.charAt(index)).matches(NUMERIC_REGEX))
            {
                // The token is a number or the start of a number
                StringBuilder currentToken = new StringBuilder(String.valueOf(simplifiedExpression.charAt(index++)));

                // While there are more numbers remaining, append them to the currentToken String Builder
                boolean moreNumbers = false;
                do
                {
                    if (index < simplifiedExpression.length() && String.valueOf(simplifiedExpression.charAt(index)).matches(NUMERIC_REGEX))
                    {
                        currentToken.append(simplifiedExpression.charAt(index++));
                        moreNumbers = true;
                    }
                    else
                    {
                        moreNumbers = false;
                    }
                } while (moreNumbers);

                // We've found the entire number
                token = currentToken.toString();
            }
            else
            {
                throw new IllegalArgumentException("Unrecognized token '" + simplifiedExpression.charAt(index) + "'");
            }

            // Now that we've extracted the token, we can parse it
            if (token.length() == 1 && isValidOperator(token))
            {
                // Convert everything this operator needs
                while (buffer.size() > 0 && !buffer.peek().equals("("))
                {
                    //Operator Precedence is ignored for this project
                    result.append(buffer.pop()).append(" ");
                }
                buffer.push(token);
            }
            else if (token.equals("("))
            {
                buffer.push(token);
            }
            else if (token.equals(")"))
            {
                // End of sub group
                while (!buffer.peek().equals("("))
                {
                    result.append(buffer.pop()).append(" ");
                }
                buffer.pop();
            }
            else
            {
                // Just a literal, append it
                result.append(token).append(" ");
            }
        } while (index < simplifiedExpression.length());

        // Append any extra operators left on the expression
        while (buffer.size() > 0)
        {
            result.append(buffer.pop()).append(" ");
        }

        return result.toString().trim();
    }

    /**
     * Registers the token <code>token</code> to the specified binary operator functional interface.
     * <p>
     * If the specified token is already assigned to an operator, it will be overwritten.
     *
     * @param token    The token that denotes the operator
     * @param operator The functional interface to apply tokens to when this operator is encountered
     */
    public void register(String token, BinaryOperator operator)
    {
        operators.put(token, operator);
    }

    /**
     * Registers the token <code>token</code> to the specified unary operator functional interface.
     * <p>
     * If the specified token is already assigned to an operator, it will be overwritten.
     *
     * @param token    The token that denotes the operator
     * @param operator The functional interface to apply tokens to when this operator is encountered
     */
    public void register(String token, UnaryOperator operator)
    {
        operators.put(token, operator);
    }

    /**
     * Evaluates the specified expression following the rules of postfix notation.
     *
     * @param expression a valid postfix expression. each literal and operator must be separated by one or more space or tab characters
     * @return the integer value of the expression
     * @throws IllegalArgumentException if the expression is invalid in any way
     */
    public long evaluate(String expression) throws IllegalArgumentException, PostfixArithmeticException
    {
        CustomStack<Long> buffer = new CustomStack<>();

        // Split the expression into tokens by white space (one or more of either a space or a tab)
        String[] parts = expression.trim().split(TOKEN_SEPARATOR_REGEX);
        for (String token : parts)
        {
            if (isValidOperator(token))
            {
                // Try to evaluate the operator based on what is already on the stack
                Operator op = getEvaluatorFunction(token);

                if (op instanceof BinaryOperator)
                {
                    // Binary operators need at least two operands on the stack
                    if (buffer.size() < 2)
                    {
                        throw new IllegalArgumentException("Malformed postfix expression (not enough literals): '" + expression + "'");
                    }

                    // Extract the arguments backwards because we're using a stack
                    long b = buffer.pop();
                    long a = buffer.pop();

                    // Push the result onto the stack
                    buffer.push(((BinaryOperator) op).evaluate(a, b));
                }
                else if (op instanceof UnaryOperator)
                {
                    // Unary Operators need at least one operand on the stack
                    if (buffer.size() == 0)
                    {
                        throw new IllegalArgumentException("Malformed postfix expression (not enough literals): '" + expression + "'");
                    }

                    // Pop the operand off of the stack, and push the evaluated result onto the stack
                    buffer.push(((UnaryOperator) op).evaluate(buffer.pop()));
                }
            }
            else if (token.matches(NUMERIC_REGEX))
            {
                // Just an integer, push it onto the stack
                buffer.push(Long.parseLong(token));
            }
            else
            {
                // Not a valid operator or literal
                throw new IllegalArgumentException("Malformed postfix expression (unrecognized token " + token + "): '" + expression + "'");
            }
        }

        // If we're left with more than one item on the stack, then the expression has too many literals
        if (buffer.size() != 1)
        {
            throw new IllegalArgumentException("Malformed postfix expression (too many literals): '" + expression + "'");
        }

        // The result is the only thing left on the stack
        long result = buffer.pop();

        // Check for overflow / underflow
        if (result > Integer.MAX_VALUE)
        {
            throw new PostfixOverflowException(result, "Integer overflow while evaluating expression '" + expression + "'");
        }
        else if (result < Integer.MIN_VALUE)
        {
            throw new PostfixUnderflowException(result, "Integer underflow while evaluating expression '" + expression + "'");
        }
        else
        {
            return result;
        }
    }

    /**
     * Evalueates the specified infix expression by first converting to postfix and then evaluating the result.
     *
     * @param expression A valid infix expression
     * @return the integer result of the evaluated expression
     */
    public long evaluateInfix(String expression)
    {
        return evaluate(convertInfixExpression(expression));
    }

    /**
     * Gets the operator assigned to the specified token
     *
     * @param operator the token representing the operator
     * @return the operator's functional interface that is registered to the token
     */
    public Operator getEvaluatorFunction(String operator)
    {
        if (!isValidOperator(operator))
        {
            throw new IllegalArgumentException("Undefined postfix operator: " + operator);
        }

        return operators.get(operator);
    }

    /**
     * @param operator the operator to check
     * @return <code>true</code> if and only if the specified operator is registered
     */
    public boolean isValidOperator(String operator)
    {
        return operators.keySet().contains(operator);
    }

    /**
     * @return The unmodifiable set of all registered operator tokens
     */
    public Set<String> getSupportedOperators()
    {
        return Collections.unmodifiableSet(operators.keySet());
    }

}
