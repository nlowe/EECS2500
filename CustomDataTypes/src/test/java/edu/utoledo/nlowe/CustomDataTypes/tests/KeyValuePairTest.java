package edu.utoledo.nlowe.CustomDataTypes.tests;

import edu.utoledo.nlowe.CustomDataTypes.KeyValuePair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by nathan on 12/5/15
 */
public class KeyValuePairTest
{
    @Test
    public void toStringValid()
    {
        assertEquals("{a, b}", new KeyValuePair<>("a", "b").toString());
    }
}
