package edu.utoledo.nlowe.CustomDataTypes;

/**
 * An ordered key-value pair that can be compared to other key-value pairs
 */
public class OrderedKeyValuePair<K extends Comparable<K>, V> extends KeyValuePair<K, V> implements Comparable<OrderedKeyValuePair<K, V>>
{
    public OrderedKeyValuePair(K key, V value)
    {
        super(key, value);
    }

    @Override
    public int compareTo(OrderedKeyValuePair<K, V> o)
    {
        return key.compareTo(o.key);
    }
}
