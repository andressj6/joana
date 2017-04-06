package com.andre.joana.downloader.wrapper;

/**
 * Not my solution. See (and thanks)
 * http://stackoverflow.com/questions/2263062/how-to-monitor-progress-jprogressbar-with-filechannels-transferfrom-method
 */
public interface ReadableByteChannelObserver {
    /**
     * The ReadableByteChannelObserver receives onDataReceivedCallback() messages
     * from the read loop.  It is passed the progress as a percentage
     * if known, or -1.0 to indicate indeterminate progress.
     * <p>
     * This callback hangs the read loop so a smart implementation will
     * spend the least amount of time possible here before returning.
     * <p>
     * One possible implementation is to push the progress message
     * atomically onto a queue managed by a secondary thread then
     * wake that thread up.  The queue manager thread then updates
     * the user interface progress bar. This lets the read loop
     * continue as fast as possible.
     */
    void onDataReceivedCallback(ObservableReadableByteChannel rbc, double progress);
}
