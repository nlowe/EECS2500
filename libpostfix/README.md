# libpostfix - Postfix (RPN) Expression Evaluator
A simple expression evaluator for postfix (Reverse Polish Notation). As per the project specs,
this project only supports operations with `int`s.

### Supported Operations
* Addition (`2 2 +` == `4`)
* Subtraction (`4 2 -` == `2`)
* Multiplication (`2 2 x` == `4`)
* Division (`4 2 /` == `2`)
* Modulus (`3 2 %` == `1`)
* Power (`3 2 ^` == `9`)
* Left-Shift (`2 1 <`)
* Right-Shift (`2 1 >`)
* Square-Root (`4 Q` == `2`) (We have to use `Q` because that's what the project spec calls for)
* Cube-Root (`27 C` == `3`)

### Building
```bash
mvn package
```

### Usage
Require `libpostfix` as a part of your project:

```xml
<dependencies>
    <!-- ... -->
    <dependency>
        <groupId>edu.utoledo.nlowe</groupId>
        <artifactId>libpostfix</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    <!-- ... -->
</dependencies>
```

Create an instance of the engine and start evaluating expressions!

```java
import edu.utoledo.nlowe.postfix.PostfixEngine;

//...
PostfixEngine engine = new PostfixEngine();
engine.evaluate("2 2 +") // int: 4

```