/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andre.khicrawler;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author André
 */
public class Crawler {

    public static void crawl(String base) throws Exception {
        Document doc = Jsoup.connect(base).get();
        Elements links = doc.select("a[href$=.mp3]:contains(Download)");
        if (links.size() == 0) {
            throw new IllegalArgumentException("Page didn't return any downloadable link");
        }
        List<String> songLinkList = new ArrayList<>();
        for (Element e : links.toArray(new Element[]{})) {
            songLinkList.add(e.attr("href"));
        }
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
        for (String songPage : linkList) {
            Document doc = Jsoup.connect(songPage).get();
            Element link = doc.select("a[href$=.mp3]").first();
            download(link.attr("href"), f);
        }
    }

    private static synchronized void download(String path, File destFolder) throws Exception {
        String fileName = path.split("/")[path.split("/").length - 1];
        URL website = new URL(path);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(new File(destFolder.getAbsolutePath() + File.separator + fileName));
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        System.out.println(path + " > " + (destFolder.getAbsolutePath() + File.separator + fileName));
    }
}
