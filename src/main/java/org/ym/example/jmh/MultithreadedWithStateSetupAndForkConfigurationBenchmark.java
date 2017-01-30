package org.ym.example.jmh;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import sun.misc.Contended;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Fork(value = 3, warmups = 2)
@Warmup(
        iterations = 2,
        time = 1500,
        timeUnit = TimeUnit.MILLISECONDS
)
@Measurement(
        iterations = 3,
        time = 5,
        timeUnit = TimeUnit.SECONDS,
        batchSize = 13
)
public class MultithreadedWithStateSetupAndForkConfigurationBenchmark {


    @State(Scope.Benchmark)
    public static class ThreadSharedBenchmarkState {
        public double dval;
        public BigDecimal bdVal;

        @Param("121")
        @Contended
        public long notInitializedLong;


        public volatile int sharedResult;

        @Setup(Level.Iteration)
        public void setUpNewState() {
            System.out.println("setting up the state");

            dval = new Random().nextDouble();
        }

        @Setup(Level.Trial)
        public void specialSetUpForBigDecimal() {
            System.out.println("setting up the bigDecimal state");

            bdVal = BigDecimal.valueOf(88393892.321);
        }

        @TearDown(Level.Iteration)
        public void tearDown() {
            System.out.println("tearing down state:" + this);
        }

        @Override
        public String toString() {
            return "ThreadSharedBenchmarkState{" +
                    "dval=" + dval +
                    ", bdVal=" + bdVal +
                    ", notInitializedLong=" + notInitializedLong +
                    '}';
        }
    }


    private int someWriteApiMethod(double d, long l, BigDecimal bigDecimal) {
        return bigDecimal.multiply(BigDecimal.valueOf(d)).divide(BigDecimal.valueOf(l), BigDecimal.ROUND_DOWN).hashCode();
    }

    @Group("writers") @GroupThreads(2)
    @Benchmark
    @OperationsPerInvocation(3)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void testApiWriteMethod(ThreadSharedBenchmarkState state) {
        for (int i = 0; i < 3; i++) {

            int result = someWriteApiMethod(state.dval, state.notInitializedLong, state.bdVal);

            state.sharedResult = result;
        }
    }



    @Group("readers") @GroupThreads(10)
    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void testApiReadMethod(ThreadSharedBenchmarkState state, Blackhole blackhole) {
        blackhole.consume(state.sharedResult);
    }


}
