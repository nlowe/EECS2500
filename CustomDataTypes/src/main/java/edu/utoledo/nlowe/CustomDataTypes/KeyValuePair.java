package edu.utoledo.nlowe.CustomDataTypes;

/**
 * A data type for a key-value relationship. The key should be immutable.
 */
public class KeyValuePair<K, V>
{
    /** The key of the element */
    protected final K key;
    /** The value of the element */
    private V value;

    public KeyValuePair(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    /**
     * @return The key of the element
     */
    public K getKey()
    {
        return key;
    }

    /**
     * @return The value of the element
     */
    public V getValue()
    {
        return value;
    }

    /**
     * Sets the value of the element
     *
     * @param value the new value to set
     */
    public void setValue(V value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "{\"key\": \"" + key.toString() + "\", \"value\": \"" + value.toString() + "\"}";
    }
}
