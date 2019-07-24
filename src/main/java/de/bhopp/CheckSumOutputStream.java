package de.bhopp;

import static com.google.common.base.Preconditions.checkState;

import java.io.OutputStream;

public class CheckSumOutputStream extends OutputStream {
  int checkSum = 0;
  private final int expectedCheckSum;

  public CheckSumOutputStream(int expectedCheckSum) {
    this.expectedCheckSum = expectedCheckSum;
  }

  @Override
  public void write(int b) {
    checkSum ^= b;
  }

  @Override
  public void close() {
    checkState(expectedCheckSum == checkSum);
  }
}
