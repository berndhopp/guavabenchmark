package de.bhopp;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.RunnerException;

@State(Scope.Benchmark)
public class CopyToBenchmark {
  //any insanely high number of threads will do
  private final int THREADS = 16384;
  private List<CopyToCallable> callables;
  private ExecutorService executorService;

  public static void main(String[] args) throws IOException, RunnerException {
    Main.main(args);
  }

  @Setup(Level.Invocation)
  public void setup(){
    callables = IntStream.range(0, THREADS).mapToObj(i -> new CopyToCallable()).collect(toList());
    executorService = Executors.newFixedThreadPool(THREADS);
  }

  @Benchmark
  public void testCopy() throws InterruptedException {
    executorService.invokeAll(callables);
    executorService.shutdown();
    executorService.awaitTermination(12, TimeUnit.HOURS);
  }
}
