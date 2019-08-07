package de.bhopp;

import java.io.InputStream;

public class TestInputStream extends InputStream {
  private boolean readOut = false;

  @Override
  public int read() {
    if (readOut) {
      return -1;
    }

    readOut = true;

    return 1;
  }
}
