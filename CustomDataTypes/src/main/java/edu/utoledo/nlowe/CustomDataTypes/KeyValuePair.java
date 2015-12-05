package edu.utoledo.nlowe.CustomDataTypes;

/**
 * Created by nathan on 12/4/15
 */
public class KeyValuePair<K, V>
{
    private final K key;
    private V value;

    public KeyValuePair(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    public K getKey()
    {
        return key;
    }

    public V getValue()
    {
        return value;
    }

    public void setValue(V value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "{" + key.toString() + ", " + value.toString() + "}";
    }
}
