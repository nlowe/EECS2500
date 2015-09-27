package edu.utoledo.nlowe.CustomDataTypes;

/**
 * Created by nathan on 9/26/15
 */
public class CustomStack<T> extends CustomLinkedList<T> {

    public void push(T element){
        this.add(0, element);
    }

    public T peek(){
        return this.getFirst();
    }

    public T pop(){
        T element = this.getFirst();
        this.remove(0);
        return element;
    }
}
