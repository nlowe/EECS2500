package edu.utoledo.nlowe.CustomDataTypes;

import java.util.Iterator;

/**
 * Created by nathan on 9/8/15
 */
public class CustomLinkedList<T> implements Iterable<T>{

    private Leaf<T> head = null;
    private Leaf<T> tail = null;
    private int size = 0;

    private final class LinkedListIterator<U> implements Iterator<U>{

        private Leaf<U> pointer;

        public LinkedListIterator(Leaf<U> head){
            this.pointer = head;
        }

        @Override
        public boolean hasNext() {
            return pointer != null;
        }

        @Override
        public U next() {
            U result = pointer.getValue();
            pointer = pointer.next();
            return result;
        }
    }

    private final class Leaf<U> {
        private U value;
        private Leaf<U> next;

        Leaf(U value) {
            this(value, null);
        }

        private Leaf(U value, Leaf<U> next) {
            this.value = value;
            this.next = next;
        }

        public void setValue(U value) {
            this.value = value;
        }

        public U getValue() {
            return value;
        }

        public Leaf<U> next() {
            return next;
        }

        public void linkTo(Leaf<U> next) {
            this.next = next;
        }
    }

    private boolean validateInsertableBounds(int index){
        return index >= 0 && index <=size;
    }

    private boolean validateBounds(int index){
        return index >= 0 && index < size;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator<>(this.head);
    }

    public void add(T value){
        add(this.size(), value);
    }

    public void add(int index, T value) throws IndexOutOfBoundsException{
        Leaf<T> node = new Leaf<>(value);
        if(!validateInsertableBounds(index)){
            throw new IndexOutOfBoundsException("The Index " + index + " is not in the bounds of the list!");
        }else if(size == 0){
            head = tail = node;
        }else if(index == 0){
            node.linkTo(head);
            head = node;
        }else if(index == size){
            tail.linkTo(node);
            tail = node;
        }else if(index > 0 && index < size){
            Leaf<T> parent = getNodeAt(index-1);
            Leaf<T> next = parent.next();

            node.linkTo(next);
            parent.linkTo(node);
        }

        size++;
    }

    public void set(int index, T value) throws IndexOutOfBoundsException{
        if(!validateBounds(index)){
            throw new IndexOutOfBoundsException("The Index " + index + " is not in the bounds of the list!");
        }

        getNodeAt(index).setValue(value);
    }

    public void remove(int index) throws IndexOutOfBoundsException{
        if(!validateBounds(index)){
            throw new IndexOutOfBoundsException("The Index " + index + " is not in the bounds of the list!");
        }

        if(index == 0){
            Leaf<T> oldHead = head;

            head = head.next();

            oldHead.linkTo(null);
        }else{
            Leaf<T> parent = getNodeAt(index-1);
            Leaf<T> toRemove = parent.next();

            parent.linkTo(toRemove.next());
            toRemove.linkTo(null);

            if(index == size-1){
                tail = parent;
            }
        }

        size--;
    }

    public boolean contains(T value){
        Leaf<T> next = head;

        while(next != null){
            if(next.getValue().equals(value)) {
                return true;
            }else{
                next = next.next();
            }
        }

        return false;
    }

    public void clear(){
        head = tail = null;
        size = 0;
    }

    private Leaf<T> getNodeAt(int index){
        assert(validateBounds(index));

        int i = 0;
        Leaf<T> node = head;
        while(i++ != index){
            node = node.next();
        }

        return node;
    }

    public T get(int index) throws IndexOutOfBoundsException{
        if(!validateBounds(index)){
            throw new IndexOutOfBoundsException("The Index " + index + " is not in the bounds of the list!");
        }

        return getNodeAt(index).getValue();
    }

    public T getFirst(){
        return head == null ? null : head.getValue();
    }

    public T getLast(){
        return tail == null ? null : tail.getValue();
    }

    public int size(){
        return size;
    }

}
