package edu.utoledo.nlowe.WordCount.tests;

import edu.utoledo.nlowe.WordCount.sample.Benchmarks;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Pattern;

import static org.junit.Assert.assertThat;

/**
 * Tests for the Benchmark Class
 */
public class BenchmarkTest
{

    /**
     * Piotr Gabryanczyk - March 27, 2009
     *
     * A regular expression matcher for hamcrest
     *
     * See https://piotrga.wordpress.com/2009/03/27/hamcrest-regex-matcher/
     */
    class RegexMatcher extends BaseMatcher<String>
    {
        private final Pattern regex;

        public RegexMatcher(String regex){
            this.regex = Pattern.compile(regex);
        }

        public boolean matches(Object o){
            return regex.matcher((String)o).find();
        }

        public void describeTo(Description description){
            description.appendText('"' + regex.pattern() + '"');
        }
    }

    private static final String BENCHMARK_REGEX = "Benchmarking Overhead\\.\\.\\.\\n" +
            "Benchmarking Unsorted\\.\\.\\.\\n" +
            "Benchmarking Sorted \\(Alphabetically\\)\\.\\.\\.\\n" +
            "Benchmarking Self-Adjusting \\(Front\\)\\.\\.\\.\\n" +
            "Benchmarking Self-Adjusting \\(Bubble\\)\\.\\.\\.\\n" +
            "Benchmarks Complete\\n" +
            "Results:\\n\\n\\n" +
            "Overhead: Duration: \\d+\\.\\d+ seconds\\n" +
            "Unsorted: Duration: \\d+\\.\\d+ seconds\\tWord Count: 31991, Distinct: 4868, Comparisons: \\d+, Reference Changes: \\d+\\n" +
            "Sorted \\(Alphabetically\\): Duration: \\d+\\.\\d+ seconds\\tWord Count: 31991, Distinct: 4868, Comparisons: \\d+, Reference Changes: \\d+\\n" +
            "Self-Adjusting \\(Front\\): Duration: \\d+\\.\\d+ seconds\\tWord Count: 31991, Distinct: 4868, Comparisons: \\d+, Reference Changes: \\d+\\n" +
            "Self-Adjusting \\(Bubble\\): Duration: \\d+\\.\\d+ seconds\\tWord Count: 31991, Distinct: 4868, Comparisons: \\d+, Reference Changes: \\d+\\n" +
            "\\n----------\\n\\n" +
            "First 100 elements in Front Self-Adjusting:\\n(\\t[a-z\\-']+, \\d+\\n){100}" +
            "First 100 elements in Bubble Self-Adjusting:\\n(\\t[a-z\\-'\\!\\,]+, \\d+\\n){99}(\\t[a-z\\-'\\!\\,]+, \\d+)";


    private RegexMatcher matches(String regex){
        return new RegexMatcher(regex);
    }

    @Test
    public void successfullyRunsBenchmarks(){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        new Benchmarks().main(new String[]{});

        assertThat(out.toString(), matches(BENCHMARK_REGEX));
    }

}
