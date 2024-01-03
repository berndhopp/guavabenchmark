package de.bhopp;

import java.io.InputStream;

public class RandomInputStream extends InputStream {
    int value = 17;
    int readCount = 0;
    final int maxRead;

    public RandomInputStream(int maxRead) {
        this.maxRead = maxRead;
    }

    @Override
    public int read() {
        if (readCount >= maxRead) {
            return -1;
        }

        value = value * value;
        readCount += 4;
        return value;
    }
}
