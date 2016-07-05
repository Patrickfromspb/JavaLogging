package com.gargolin;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Created by User on 05.07.2016.
 */
public class ClipBoard {
    public static void main(String[] args) {
        StringSelection selection = new StringSelection("I am the smartest");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

}
