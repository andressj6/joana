package com.andre.joana;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Renamer {

    public static void main(String args[]) throws UnsupportedEncodingException {
        File folder = new File("");
        for (File f : Objects.requireNonNull(folder.listFiles())) {
            f.renameTo(new File(URLDecoder.decode(f.getAbsolutePath(), StandardCharsets.UTF_8.toString())));
        }

    }

}
