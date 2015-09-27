package edu.utoledo.nlowe.postfix;

/**
 * Created by nathan on 9/26/15
 */
public class InfixConverter {

    private static final String VALID_OPERATORS = "+-x*/^QC<>";

    public String convertToPostfix(String expression){
        StringBuilder result = new StringBuilder();

        String[] parts = expression.trim().split(" +");
        for(String part : parts){
            if(part.length() == 1 && VALID_OPERATORS.contains(part)){
                //operator
            }else if(part.equals("(")){

            }else if(part.equals(")")){

            }else{
                result.append(part).append(" ");
            }
        }

        return result.toString().trim();
    }
}
