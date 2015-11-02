# WordCounter
Various Word Counter implementations.

Benchmarks are run against the entire text of _Hamlet_.

## Implementations
1. Unsorted
  * A word counter in which newly encountered words are simply added to the beginning of a list
2. Sorted
  * A word counter in which the list of distinct words is sorted alphabetically
3. Front-Self-Adjusting
  * When a word is encountered that is already in the list of distinct words, it is moved to the front of the list
  * Newly encountered words are added to the binging of the list
4. Bubble-Self-Adjusting
  * When a word is encountered that is already in the list of distinct words, it's position is interchanged with the preceding word such that, over time, frequently encountered words "bubble up" to the front of the list
  * Newly encountered words are added to the beginning of the list

## Building
```bash
mvn package
```

This will create `WordCounter-<VERSION>-jar-with-dependencies.jar` in `target/`

It can then be run with:

```bash
$ java -jar WordCounter/target/PostfixFX-1.0-SNAPSHOT-jar-with-dependencies.jar
```
