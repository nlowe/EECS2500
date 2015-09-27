package edu.utoledo.nlowe.postfix.tests;
import static org.junit.Assert.*;

import edu.utoledo.nlowe.CustomDataTypes.CustomLinkedList;
import edu.utoledo.nlowe.postfix.PostfixEngine;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

/**
 * Created by nathan on 9/17/15
 */
public class PostfixEngineTest {

    private PostfixEngine engine;

    @BeforeClass
    public static void setUpTestCase(){
        System.out.println("=== PostfixEngine [Tests] ===");
    }

    @Before
    public void setUp(){
        engine = new PostfixEngine();
    }

    @Test
    public void canEvaluateSingleOperator(){
        System.out.print("[+] Can evaluate single operator (");

        assertEquals(4, engine.evaluate("2 2 +"));
        System.out.print("+, ");

        assertEquals(2, engine.evaluate("4 2 -"));
        System.out.print("-, ");

        assertEquals(4, engine.evaluate("2 2 *"));
        System.out.print("*, ");

        assertEquals(2, engine.evaluate("4 2 /"));
        System.out.print("/, ");

        assertEquals(1, engine.evaluate("3 2 %"));
        System.out.print("%, ");

        assertEquals(4, engine.evaluate("2 2 ^"));
        System.out.println("^)");
    }

    @Test
    public void unaryOperatorTest(){
        assertEquals(2, engine.evaluate("4 Q"));
    }

    @Test
    public void canEvaluateWithManySpaces(){
        assertEquals(4, engine.evaluate("2           2      +"));
        System.out.println("[+] Can Evaluate with Many Spaces");
    }

    @Test
    public void failsWithTooManyOperators(){
        try{
            engine.evaluate("1 1 + +");
            fail();
        }catch(IllegalArgumentException ex){
            assertEquals("Malformed postfix expression (not enough literals): '1 1 + +'", ex.getMessage());
            System.out.println("[+] Fails with too many operators");
        }
    }

    @Test
    public void failsWithTooManyLiterals(){
        try{
            engine.evaluate("1 1 1 +");
        }catch(IllegalArgumentException ex){
            assertEquals("Malformed postfix expression (too many literals): '1 1 1 +'", ex.getMessage());
            System.out.println("[+] Fails with too many literals");
        }
    }

    @Test
    public void failsWithIllegalOperator(){
        try{
            engine.evaluate("1 1 @");
        }catch(IllegalArgumentException ex){
            assertEquals("Malformed postfix expression (unrecognized token @): '1 1 @'", ex.getMessage());
            System.out.println("[+] Fails with illegal operator");
        }
    }

    @Test
    public void throwsExceptionTryingToGetIllegalArgument(){
        try{
            engine.getEvaluatorFunction("@");
        }catch(IllegalArgumentException ex){
            assertEquals("Undefined postfix operator: @", ex.getMessage());
            System.out.println("[+] Does not recognize illegal operators");
        }
    }

    @Test
    public void returnsAllSupportedOperators(){
        Set<String> operators = engine.getSupportedOperators();
        assertTrue(
                operators.size() == 11 &&
                operators.contains("+") &&
                operators.contains("-") &&
                operators.contains("*") &&
                operators.contains("x") &&
                operators.contains("/") &&
                operators.contains("%") &&
                operators.contains("^") &&
                operators.contains("<") &&
                operators.contains(">") &&
                operators.contains("Q") &&
                operators.contains("C")
        );
        System.out.println("[+] Returns all supported operators");
    }

    @Test
    public void canParseComplexExample(){
        assertEquals(1, engine.evaluate("5 3 + 12 * 3 / 2 < 4 > 4 - Q 25 + C 2 ^ 2 %"));
        System.out.println("[+] Can evaluate more complex expressions");
    }

    @AfterClass
    public static void tearDownTestCase(){
        System.out.println("=== PostfixEngine [Tests: Finished] ===");
    }
}
