package com.andre.joana;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.andre.joana.downloader.impl.DownloadData;
import com.andre.joana.downloader.impl.Downloader;
import com.andre.joana.downloader.impl.ProgressFrame;

/**
 * @author André
 */
class Crawler {

    private static ProgressFrame frame;

    public static void crawl(String base) throws Exception {
        Document doc = Jsoup.connect(base).get();
        Elements links = doc.select("a[href$=.mp3]:contains(Download)");
        if (links.isEmpty()) {
            throw new IllegalArgumentException("Page didn't return any downloadable link");
        }
        List<String> songLinkList = links.stream().map(e -> e.attr("href")).collect(Collectors.toList());
        getDownloadLinks(songLinkList);
    }

    private static void getDownloadLinks(List<String> linkList) throws Exception {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setMultiSelectionEnabled(false);
        fc.showDialog(null, "Select Folder");
        File f = fc.getSelectedFile();
        if (f == null) {
            return;
        }
        createFrameWithProgressBar(f.getAbsolutePath(), linkList.size());
        for (String songPage : linkList) {
            Document doc = Jsoup.connect(songPage).get();
            Element link = doc.select("a[href$=.mp3]").first();
            download(link.attr("href"), f);
        }
    }

    private static void createFrameWithProgressBar(String absolutePath, int itemListSize) {
        frame = new ProgressFrame(absolutePath, itemListSize);
    }

    private static synchronized void download(String path, File destFolder) throws Exception {
        String fileName = path.split("/")[path.split("/").length - 1];
        String destinationFile = destFolder.getAbsolutePath() + File.separator + fileName;
        final DownloadData downloadData = new DownloadData(path, fileName, 0L);
        new Downloader(destinationFile, path, (readSoFar, progress) -> refreshDownloadedFilesList(downloadData, progress));
        System.out.println(path + " > " + (destFolder.getAbsolutePath() + File.separator + fileName));
    }

    private static void refreshDownloadedFilesList(DownloadData downloadData, double progress) {
        downloadData.setPercentageComplete(Math.round(progress));
        frame.upsertListModel(downloadData);
    }
}
