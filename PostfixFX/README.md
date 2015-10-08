# PostfixFX - JavaFX Calculator
A calculator that works with both standard (infix) notation, and RPN (postfix) notation

### Supported Operators
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

This will create `PostfixFX-<VERSION>-jar-with-dependencies.jar` in `target/`

It can then be run with:

```bash
$ java -jar PostfixFX/target/PostfixFX-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Additional Licenses
This project uses icons from the [Google Material Design icon set](https://github.com/google/material-design-icons/), which is licensed under the [Creative Common Attribution 4.0 International License (CC-BY 4.0)](http://creativecommons.org/licenses/by/4.0/)
