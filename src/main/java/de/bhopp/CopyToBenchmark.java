package de.bhopp;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.openjdk.jmh.annotations.Level.Invocation;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.RunnerException;

@State(Scope.Benchmark)
public class CopyToBenchmark {
  //any insanely high number of threads will do
  private static final int THREADS = 16384;
  private List<CopyToCallable> callables;
  private ExecutorService executorService;

  public static void main(String[] args) throws IOException, RunnerException {
    Main.main(args);
  }

  @Setup(Invocation)
  public void setup(){
    callables = range(0, THREADS).mapToObj(i -> new CopyToCallable()).collect(toList());
    executorService = newFixedThreadPool(THREADS);
  }

  @Benchmark
  public void testCopy() throws InterruptedException {
    executorService.invokeAll(callables);
    executorService.shutdown();
    executorService.awaitTermination(12, HOURS);
  }
}
