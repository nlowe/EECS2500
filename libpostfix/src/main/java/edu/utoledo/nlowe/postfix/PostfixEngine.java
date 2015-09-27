package edu.utoledo.nlowe.postfix;

import edu.utoledo.nlowe.CustomDataTypes.CustomStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by nathan on 9/17/15
 */
public class PostfixEngine {

    public static final String NUMERIC_REGEX = "^(-)?[0-9]+([.][0-9]+)?$";

    private final HashMap<String, Operator> operators = new HashMap<>();

    public PostfixEngine(){
        // Register default valid operators
        register("+", (a, b) -> a + b);
        register("-", (a, b) -> a - b);
        register("x", (a, b) -> a * b);
        register("*", (a, b) -> a * b);
        register("/", (a, b) -> a / b);
        register("%", (a, b) -> a % b);
        register("^", (a, b) -> (int) Math.pow(a, b));
        register("Q", (a) -> (int) Math.sqrt(a));
        register("C", (a) -> (int) Math.cbrt(a));
        register("<", (a, b) -> a << b);
        register(">", (a, b) -> a >> b);
    }

    public String convertInfixExpression(String expression){
        StringBuilder result = new StringBuilder();
        CustomStack<String> buffer = new CustomStack<>();

        String[] parts = expression.trim().split(" +");
        for(String token : parts){
            if(token.length() == 1 && isValidOperator(token)){
                while(buffer.size() > 0 && !buffer.peek().equals("(")){
                    //Operator Precedence is ignored for this project
                    buffer.pop();
                    result.append(buffer.peek()).append(" ");
                }
                buffer.push(token);
            }else if(token.equals("(")){
                buffer.push(token);
            }else if(token.equals(")")){
                while(!buffer.peek().equals("(")){
                    result.append(buffer.pop()).append(" ");
                }
                buffer.pop();
            }else{
                result.append(token).append(" ");
            }
        }

        return result.toString().trim();
    }

    public void register(String token, BinaryOperator operator){
        operators.put(token, operator);
    }

    public void register(String token, UnaryOperator operator){
        operators.put(token, operator);
    }

    public int evaluate(String expression) throws IllegalArgumentException {

        CustomStack<Integer> buffer = new CustomStack<>();

        String[] parts = expression.trim().split(" +");
        for(String token : parts){
            if(isValidOperator(token)){
                Operator op = getEvaluatorFunction(token);

                if(op instanceof BinaryOperator){
                    if(buffer.size() < 2){
                        throw new IllegalArgumentException("Malformed postfix expression (not enough literals): '" + expression + "'");
                    }

                    int b = buffer.pop();
                    int a = buffer.pop();

                    buffer.push(((BinaryOperator) op).evaluate(a, b));
                }else if(op instanceof UnaryOperator){
                    int input = buffer.pop();

                    buffer.push(((UnaryOperator)op).evaluate(input));
                }

            }else if(token.matches(NUMERIC_REGEX)){
                buffer.push(Integer.parseInt(token));
            }else{
                throw new IllegalArgumentException("Malformed postfix expression (unrecognized token " + token + "): '" + expression + "'");
            }
        }

        if(buffer.size() != 1){
            throw new IllegalArgumentException("Malformed postfix expression (too many literals): '" + expression + "'");
        }

        return buffer.pop();
    }

    public int evaluateInfix(String expression){
        return evaluate(convertInfixExpression(expression));
    }

    public Operator getEvaluatorFunction(String operator){
        if(!isValidOperator(operator)){
            throw new IllegalArgumentException("Undefined postfix operator: " + operator);
        }

        return operators.get(operator);
    }

    public boolean isValidOperator(String operator){
        return operators.keySet().contains(operator);
    }

    public Set<String> getSupportedOperators(){
        return Collections.unmodifiableSet(operators.keySet());
    }

}
