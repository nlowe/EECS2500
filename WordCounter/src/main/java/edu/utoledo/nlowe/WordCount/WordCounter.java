package edu.utoledo.nlowe.WordCount;

/**
 * A Word Counter is a variant of a list that records the number of times
 * a word is encountered. Each implementation should keep track of the
 * number of comparisons made and references changed during the lifetime
 * of the Word Counter.
 */
public abstract class WordCounter
{

    /**
     * Records an encounter of the specified word
     *
     * @param word the word to inspect
     */
    public abstract void encounter(String word);

    /**
     * @return the total number of words encountered during the lifetime of this Word Counter
     */
    public abstract long getWordCount();

    /**
     * @return the number of unique words encountered during the lifetime of this Word Counter
     */
    public abstract long getDistinctWordCount();

    /**
     * @return The number of comparisons made during the lifetime of this Word Counter
     */
    public abstract long getComparisonCount();

    /**
     * @return The number of references that have been changed during the lifetime of this Word Counter
     */
    public abstract long getReferenceChangeCount();
}
