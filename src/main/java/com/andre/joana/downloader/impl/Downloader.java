package com.andre.joana.downloader.impl;

import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import com.andre.joana.downloader.wrapper.ObservableReadableByteChannel;
import com.andre.joana.downloader.wrapper.ReadableByteChannelObserver;

public class Downloader {

    private ReadableByteChannelObserver callback;

    /**
     * Creates a new instance of the downloader object, which will execute the proper download
     * process
     *
     * @param localPath        the path where the file will be saved
     * @param remoteURL        the file origin
     * @param progressCallback the callback which will be executed whenever the data is received
     */
    public Downloader(String localPath, String remoteURL, ReadableByteChannelObserver progressCallback) {
        this.callback = progressCallback;
        FileOutputStream fos;
        ReadableByteChannel rbc;
        URL url;

        try {
            url = new URL(remoteURL);
            rbc = new ObservableReadableByteChannel(Channels.newChannel(url.openStream()), contentLength(url), callback);
            fos = new FileOutputStream(localPath);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Request to get the size of the file being downloaded
     *
     * @return the content length of the file
     */
    private int contentLength(URL url) {
        HttpURLConnection connection;
        int contentLength = -1;

        try {
            HttpURLConnection.setFollowRedirects(false);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");

            contentLength = connection.getContentLength();
            connection.disconnect();
        } catch (Exception ignored) {
        }

        return contentLength;
    }

}
