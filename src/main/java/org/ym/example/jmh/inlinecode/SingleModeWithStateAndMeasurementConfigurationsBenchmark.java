package org.ym.example.jmh.inlinecode;

import org.openjdk.jmh.annotations.*;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Warmup(
        iterations = 5,
        time = 10,
        timeUnit = TimeUnit.MICROSECONDS,
        batchSize = 3
)
@Measurement(
        iterations = 7,
        time = 10,
        timeUnit = TimeUnit.SECONDS
)
public class SingleModeWithStateAndMeasurementConfigurationsBenchmark {


    @State(Scope.Thread)
    public static class SimpleAdditionState {
        public BigDecimal first = BigDecimal.valueOf(3432.221);
        public BigDecimal second = BigDecimal.valueOf(34979.112);

        public BigDecimal product;
    }


    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testMultiplicationWithState(SimpleAdditionState state) {
        state.product = state.first.multiply(state.second);
    }

}
