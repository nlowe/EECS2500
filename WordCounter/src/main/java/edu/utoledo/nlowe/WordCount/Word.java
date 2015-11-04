package edu.utoledo.nlowe.WordCount;

/**
 * Records a word and the occurrence of the word
 */
public class Word implements Comparable<Word>
{
    /** The word that this object represents */
    private final String value;
    /** The number of times this word has been encountered */
    private int count = 1;

    public Word(String value)
    {
        this.value = value;
    }

    /**
     * Converts the input string to lower case and strips all non-letter characters
     * from the beginning and end of the string.
     *
     * @param input the string to sanitize
     * @return the sanitized string
     */
    public static String sanitize(String input)
    {
        char[] parts = input.toLowerCase().toCharArray();
        int lastLetterIndex=parts.length;

        StringBuilder output = new StringBuilder(parts.length);
        boolean start = false;
        for (int i = parts.length - 1; i >= 0; i--)
        {
            boolean isLetter = (parts[i] >= 'a' && parts[i] <= 'z');
            if (start || isLetter)
            {
                start = true;
                output.append(parts[i]);
                if(isLetter)
                {
                    lastLetterIndex = i;
                }
            }
        }

        String temp = output.reverse().toString();
        return temp.isEmpty() ? "" : temp.substring(lastLetterIndex);
    }

    /**
     * Increase the occurance count of this word by one
     */
    public void increment()
    {
        count++;
    }

    /**
     * @return the String representation of this word
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @return the number of times this word has been encountered
     */
    public int getOccurrenceCount()
    {
        return count;
    }

    @Override
    public int compareTo(Word o)
    {

        return o != null ? this.value.compareTo(o.getValue()) : 1;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj != null && obj instanceof Word && this.value.equals(((Word) obj).value);
    }
}
