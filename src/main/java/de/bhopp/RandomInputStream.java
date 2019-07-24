package de.bhopp;

import java.io.InputStream;
import java.util.Random;

public class RandomInputStream extends InputStream {
  private int pos = 0;
  private final int maxSize;
  private final Random random;

  public RandomInputStream(int seed, int maxSize) {
    this.random = new Random(seed);
    this.maxSize = maxSize;
  }

  @Override
  public int read() {
    if (++pos >= maxSize) {
      return -1;
    }

    return random.nextInt(Byte.MAX_VALUE);
  }
}
