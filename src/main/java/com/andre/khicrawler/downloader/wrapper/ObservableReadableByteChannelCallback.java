package com.andre.khicrawler.downloader.wrapper;

@FunctionalInterface
public interface ObservableReadableByteChannelCallback {

    void onDataReceived(long readSoFar, double progress);
}
