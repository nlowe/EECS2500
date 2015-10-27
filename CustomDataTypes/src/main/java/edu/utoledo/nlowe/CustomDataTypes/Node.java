package edu.utoledo.nlowe.CustomDataTypes;

/**
 * A building block for linked data types. Contains a payload of the
 * specified generic type and a reference to the next node in the list.
 * If the link is null, then this node is the last node in the list
 *
 * @param <U> The underlying payload type
 */
public class Node<U>
{

    /** The payload of the node */
    private U value;
    /** The next node in the list */
    private Node<U> next;

    public Node(U value)
    {
        this(value, null);
    }

    public Node(U value, Node<U> next)
    {
        this.value = value;
        this.next = next;
    }

    /**
     * Sets the payload of this node to the specified value
     *
     * @param value The value to set
     */
    public void setValue(U value)
    {
        this.value = value;
    }

    /**
     * @return the payload of this node
     */
    public U getValue()
    {
        return value;
    }

    /**
     * @return the next node in the list
     */
    public Node<U> next()
    {
        return next;
    }

    /**
     * Sets the link to the next node to point to the specified node
     *
     * @param next the node to point to
     */
    public void linkTo(Node<U> next)
    {
        this.next = next;
    }
}
