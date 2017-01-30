package org.ym.example.jmh;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class SpecificBenchmarkRunner {


    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .include(SimpleSingleModeBenchmark.class.getName() + ".testBigDecimalMultiplication")
                .build();

        new Runner(opts).run();

    }
}
