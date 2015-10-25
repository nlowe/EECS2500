package edu.utoledo.nlowe.WordCount;

/**
 * Records a word and the occurrence of the word
 */
public class Word implements Comparable<Word>
{
    private final String value;
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
     * @return the sanatized string
     */
    public static String sanitize(String input)
    {
        char[] parts = input.toLowerCase().toCharArray();

        StringBuilder output = new StringBuilder(parts.length);
        boolean start = false;
        for (int i = parts.length - 1; i >= 0; i--)
        {
            if (start || (parts[i] >= 'a' && parts[i] <= 'z'))
            {
                start = true;
                output.append(parts[i]);
            }
        }

        return output.reverse().toString();
    }

    public void increment()
    {
        count++;
    }

    public String getValue()
    {
        return value;
    }

    public int getOccurrenceCount()
    {
        return count;
    }

    @Override
    public int compareTo(Word o)
    {
        if (o != null)
        {
            return this.value.compareTo(o.getValue());
        }
        else
        {
            return 1;
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj != null && obj instanceof Word && this.value.equals(((Word) obj).value);
    }
}
