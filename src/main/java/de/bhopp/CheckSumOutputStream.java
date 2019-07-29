package de.bhopp;

import static com.google.common.base.Preconditions.checkState;

import java.io.OutputStream;

public class CheckSumOutputStream extends OutputStream {
  private int checkSum = 0;
  private final int expectedCheckSum;

  CheckSumOutputStream(int expectedCheckSum) {
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
