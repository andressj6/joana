package com.andre.joana.downloader.wrapper;

@FunctionalInterface
public interface ObservableReadableByteChannelCallback {

    void onDataReceived(long readSoFar, double progress);
}
