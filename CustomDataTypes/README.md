# CustomDataTypes
Custom implementations of the various abstract data
types discussed in EECS2500.

## Building
Build the library with maven:

```bash
mvn package
```

Or, require it as a dependency:

```xml
<dependencies>
    <!-- ... -->
    <dependency>
        <groupId>edu.utoledo.nlowe</groupId>
        <artifactId>CustomDataTypes</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    <!-- ... -->
</dependencies>
```


Add the jar to your class path and import the package.

## Types
All types provided by this library are as follows:

### CustomLinkedList
A custom implementation of a linked list

```java
import edu.utoledo.nlowe.CustomDataTypes

//...

public static void main(String[] args){
    CustomLinkedList<String> list = new CustomLinkedList<>();

    list.add("ABC");
    list.add("123");
    list.add("Foobar");

    System.out.println(list.getFirst()); // ABC
    System.out.println(list.getLast());  // Foobar
    System.out.println(list.get(1));     // 123
}
```

Adding, accessing, and removing elements at the very
start or very end of the list is `O(1)`, all other
operations are `O(N)`.

The list also implements `Iterable<T>`, so you can
use it in for-each loops.

### CustomStack
A custom implementation of a stack. Extends `CustomLinkedList`.

```java
import edu.utoledo.nlowe.CustomDataTypes

//...

public static void main(String[] args){
    CustomStack<String> stack = new CustomStack<>();

    stack.push("ABC");
    stack.push("123");
    stack.push("Foobar");

    System.out.println(stack.pop());  // Foobar
    System.out.println(stack.peek()); // 123
    System.out.println(stack.pop());  // 123
    System.out.println(stack.pop());  // ABC
}
```

All stack operations are `O(1)`.
