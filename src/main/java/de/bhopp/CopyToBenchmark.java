package de.bhopp;

import com.google.common.io.ByteStreams;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.util.NullOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.HOURS;
import static org.openjdk.jmh.annotations.Level.Invocation;

@State(Scope.Benchmark)
public class CopyToBenchmark {

    private static final int NUMBER_OF_THREADS = 10000;
    private final Collection<Callable<Object>> callables = new ArrayList<>(NUMBER_OF_THREADS);

    public static void main(String[] args) throws IOException, RunnerException {
        Main.main(args);
    }

    @Setup(Invocation)
    public void setup() {
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            callables.add(() -> {
                copy10kData();
                return null;
            });
        }
    }

    public void copy10kData() {
        try {
            ByteStreams.copy(new RandomInputStream(10000), new NullOutputStream());
        } catch (Throwable ignored) {
        }
    }

    @Benchmark
    public void testCopyMultiThreaded() throws InterruptedException {
        ExecutorService executorService = newFixedThreadPool(NUMBER_OF_THREADS);
        executorService.invokeAll(callables);
        executorService.shutdown();
        executorService.awaitTermination(12, HOURS);
    }
}
