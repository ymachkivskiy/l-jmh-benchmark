package org.ym.example.jmh.inlinecode;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.util.concurrent.TimeUnit;

import static org.openjdk.jmh.annotations.Mode.AverageTime;
import static org.openjdk.jmh.annotations.Mode.SingleShotTime;

/**
 * Created by ork on 30.01.17.
 */
public class MultiplyModesInNonDefaultTimeUnitBenchmark {


    @Benchmark
    @BenchmarkMode({SingleShotTime, AverageTime})
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public double testDoubleMultiplication() {

        double result = 0;

        for (int i = 0; i < 500_000; i++) {

            double a = 343.983;
            double b = 332399922.1124373;

            result += a * b + i * result;
        }

        return result;
    }
}
