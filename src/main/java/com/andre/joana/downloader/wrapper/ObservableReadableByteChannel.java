package com.andre.joana.downloader.wrapper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;

/**
 * A {@link ReadableByteChannel} wrapper, with an implementation for monitoring
 * the progress of the data that is being transferred through it
 */
public class ObservableReadableByteChannel implements ReadableByteChannel {
    private ReadableByteChannelObserver delegate;
    private long expectedSize;
    private ReadableByteChannel rbc;
    private long readSoFar;

    public ObservableReadableByteChannel(ReadableByteChannel rbc, long expectedSize, ReadableByteChannelObserver delegate) {
        this.delegate = delegate;
        this.expectedSize = expectedSize;
        this.rbc = rbc;
    }

    public void close() throws IOException {
        rbc.close();
    }

    public long getReadSoFar() {
        return readSoFar;
    }

    public boolean isOpen() {
        return rbc.isOpen();
    }

    public int read(ByteBuffer bb) throws IOException {
        int n;
        double progress;

        if ((n = rbc.read(bb)) > 0) {
            readSoFar += n;
            progress = expectedSize > 0 ? (double) readSoFar / (double) expectedSize * 100.0 : -1.0;
            delegate.onDataReceived(this, progress);
        }

        return n;
    }
}
