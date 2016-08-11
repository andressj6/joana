/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andre.khicrawler;

import java.io.File;

import javax.swing.*;

/**
 * @author André
 */
public class Main {

    public static void main(String[] args) throws Exception {
        String url = JOptionPane.showInputDialog(null, "Base URL", "");
        Crawler.crawl(url);
    }

}
