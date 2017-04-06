package com.andre.khicrawler.downloader.impl;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;

/**
 * Because we all love swing
 */
public class ProgressFrame extends JFrame {

    private JProgressBar progressBar;
    private DefaultListModel<DownloadData> listModel;
    private Integer totalLinksSize;

    public ProgressFrame(String destinationFolder, int linkListSize) {
        this.totalLinksSize = linkListSize;

        setTitle("Crawling Process");
        setBounds(300, 500, 400, 300);
        setLayout(new GridLayout(3, 1));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panelDestinationPath = new JPanel(new FlowLayout());
        panelDestinationPath.add(new JLabel("Destination Folder: " + destinationFolder));
        add(panelDestinationPath);

        JPanel panelProgressBar = new JPanel(new FlowLayout());
        panelProgressBar.add(new JLabel("Progress"));
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setSize(600, progressBar.getHeight());
        panelProgressBar.add(progressBar);
        add(panelProgressBar);

        listModel = new DefaultListModel<>();
        JList<DownloadData> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL);

        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(300, 200));
        add(listScroller);

        setVisible(true);
    }

    private void updatePercentages() {
        if (!listModel.isEmpty()) {
            List<DownloadData> itemList = getItemListFromListModel(listModel);
            int newValue = itemList.stream().mapToInt(a -> a.getPercentageComplete().intValue()).sum() / totalLinksSize;
            progressBar.setValue(newValue);
        }
    }

    public void upsertListModel(DownloadData downloadData) {
        if (listModel.contains(downloadData)) {
            listModel.setElementAt(downloadData, listModel.indexOf(downloadData));
        } else {
            listModel.addElement(downloadData);
        }
        updatePercentages();
    }

    private List<DownloadData> getItemListFromListModel(DefaultListModel<DownloadData> listModel) {
        return Arrays.stream(listModel.toArray()).map(item -> (DownloadData) item).collect(Collectors.toList());
    }


}
