package edu.utoledo.nlowe.CustomDataTypes;

/**
 * A custom, generic, Stack implementation
 */
public class CustomStack<T> extends CustomLinkedList<T> {

    /**
     * Pushes the specified element onto the top of the stack
     * @param element the element to add
     */
    public void push(T element){
        this.add(0, element);
    }

    /**
     * @return the element on the top of the stack without modifying the stack
     */
    public T peek(){
        return this.getFirst();
    }

    /**
     * Gets the element on the top of the stack and then removes the element
     * @return the element on the top of the stack
     */
    public T pop(){
        T element = this.getFirst();
        this.remove(0);
        return element;
    }
}
