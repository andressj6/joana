package com.andre.khicrawler.downloader.impl;

import lombok.Data;

@Data
public class DownloadData {
    private String origin;
    private String destination;
    private Long percentageComplete;

    public DownloadData(String origin, String destination, Long percentageComplete) {
        this.origin = origin;
        this.destination = destination;
        this.percentageComplete = percentageComplete;
    }

    public boolean isCompleted() {
        return percentageComplete == 100L;
    }

    public String toString() {
        return getDestination() + (isCompleted() ? " - Completed"
                : " - Total Downloaded: " + percentageComplete + "%");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownloadData that = (DownloadData) o;

        return destination != null ? destination.equals(that.destination) : that.destination == null;
    }

    @Override
    public int hashCode() {
        return destination != null ? destination.hashCode() : 0;
    }
}
