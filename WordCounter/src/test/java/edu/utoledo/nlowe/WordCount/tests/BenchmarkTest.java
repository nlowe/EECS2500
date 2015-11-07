package edu.utoledo.nlowe.WordCount.tests;

import edu.utoledo.nlowe.WordCount.sample.Benchmarks;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import java.io.*;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Tests for the Benchmark Class
 */
public class BenchmarkTest
{

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    /**
     * Piotr Gabryanczyk - March 27, 2009
     * <p>
     * A regular expression matcher for hamcrest
     * <p>
     * See https://piotrga.wordpress.com/2009/03/27/hamcrest-regex-matcher/
     */
    class RegexMatcher extends BaseMatcher<String>
    {
        private final Pattern regex;

        public RegexMatcher(String regex)
        {
            this.regex = Pattern.compile(regex);
        }

        public boolean matches(Object o)
        {
            return regex.matcher((String) o).find();
        }

        public void describeTo(Description description)
        {
            description.appendText('"' + regex.pattern() + '"');
        }
    }

    private static final String BENCHMARK_REGEX = "Benchmarking Overhead\\.\\.\\.\\d+\\.\\d+ seconds\\n" +
            "Benchmarking Unsorted\\.\\.\\.\\d+\\.\\d+ seconds\\n" +
            "Benchmarking Sorted \\(Alphabetically\\)\\.\\.\\.\\d+\\.\\d+ seconds\\n" +
            "Benchmarking Self-Adjusting \\(Front\\)\\.\\.\\.\\d+\\.\\d+ seconds\\n" +
            "Benchmarking Self-Adjusting \\(Bubble\\)\\.\\.\\.\\d+\\.\\d+ seconds\\n" +
            "Benchmarks Complete\\n" +
            "Results:\\n\\n\\n" +
            "Overhead: Duration: \\d+\\.\\d+ seconds\\n" +
            "Unsorted: Duration: \\d+\\.\\d+ seconds\\tWord Count: \\d+, Distinct: \\d+, Comparisons: \\d+, Reference Changes: \\d+\\n" +
            "Sorted \\(Alphabetically\\): Duration: \\d+\\.\\d+ seconds\\tWord Count: \\d+, Distinct: \\d+, Comparisons: \\d+, Reference Changes: \\d+\\n" +
            "Self-Adjusting \\(Front\\): Duration: \\d+\\.\\d+ seconds\\tWord Count: \\d+, Distinct: \\d+, Comparisons: \\d+, Reference Changes: \\d+\\n" +
            "Self-Adjusting \\(Bubble\\): Duration: \\d+\\.\\d+ seconds\\tWord Count: \\d+, Distinct: \\d+, Comparisons: \\d+, Reference Changes: \\d+\\n" +
            "\\n----------\\n\\n" +
            "First 100 elements in Front Self-Adjusting:\\n(\\t[a-z\\-']+\\t\\d+\\n){100}" +
            "First 100 elements in Bubble Self-Adjusting:\\n(\\t[a-z\\-'\\!\\,]+\\t\\d+\\n){99}(\\t[a-z\\-'\\!\\,]+\\t\\d+)";

    public static final String FAILURE_REGEX = "Encountered an error while running benchmarks\n" +
            "java.io.FileNotFoundException: ThisFileTotallyDoesNotExist";

    private RegexMatcher matches(String regex)
    {
        return new RegexMatcher(regex);
    }

    @Test
    public void successfullyRunsBenchmarks()
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        try
        {
            Benchmarks.main(new String[]{new File(Benchmarks.class.getClassLoader().getResource("Hamlet.txt").toURI()).getAbsolutePath()});
        }
        catch (Exception e)
        {
            e.printStackTrace();
            fail();
        }

        assertThat(out.toString(), matches(BENCHMARK_REGEX));
    }

    @Test
    public void exitsWithErrorCodeWhenFileNotFoundInMain()
    {
        exit.expectSystemExitWithStatus(Benchmarks.EXIT_BENCHMARK_IO_ERROR);

        Benchmarks.main(new String[]{"ThisFileTotallyDoesNotExist" + Math.random() + ".foobar"});
    }

    @Test
    public void failsWithInvalidInput()
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setErr(new PrintStream(out));

        boolean success = new Benchmarks()
        {
            @Override
            public InputStream getResourceInputStream() throws FileNotFoundException
            {
                return new FileInputStream("ThisFileTotallyDoesNotExist" + Math.random() + ".foobar");
            }
        }.runAllBenchmarks();

        assertFalse(success);
        assertThat(out.toString(), matches(FAILURE_REGEX));

    }

}
