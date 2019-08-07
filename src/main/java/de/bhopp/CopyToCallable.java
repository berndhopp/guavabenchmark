package de.bhopp;

import com.google.common.io.ByteStreams;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.concurrent.Callable;

public class CopyToCallable implements Callable<Void> {

  @Override
  public Void call() throws Exception {
    try(
        InputStream inputStream = new TestInputStream();
        OutputStream outputStream = new CheckSumOutputStream(1)
    ) {
      ByteStreams.copy(inputStream, outputStream);
    }

    return null;
  }
}
